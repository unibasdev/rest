package it.unibas.mediapesata.service;

import it.unibas.mediapesata.modello.Utente;
import it.unibas.mediapesata.modello.dto.CredenzialiDTO;
import it.unibas.mediapesata.modello.dto.UtenteDTO;
import it.unibas.mediapesata.persistenza.DAOFactory;
import it.unibas.mediapesata.persistenza.IDAOUtente;
import it.unibas.mediapesata.util.JWTUtil;
import it.unibas.mediapesata.util.Mapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityNotFoundException;

@Slf4j
@ApplicationScoped
public class ServiceUtenti {

    private final IDAOUtente daoUtente = DAOFactory.getInstance().getDAOUtente();

    public String login(CredenzialiDTO credenzialiDTO) {
        Utente utente = daoUtente.findByEmail(credenzialiDTO.getEmail());
        if (utente == null) {
            throw new IllegalArgumentException("Utente con email " + credenzialiDTO.getEmail() + " non trovato");
        }
        if (!credenzialiDTO.getPassword().equals(utente.getPassword())) {
            throw new IllegalArgumentException("Password non valida per l'utente " + credenzialiDTO.getEmail());
        }
        String token = JWTUtil.generaToken(credenzialiDTO.getEmail());
        log.debug("All'utente {} e' stato generato il token {} ", credenzialiDTO.getEmail(), token);
        return token;
    }

    public UtenteDTO me(String email) {
        Utente utente = daoUtente.findByEmail(email);
        if (utente == null) {
            throw new EntityNotFoundException("Utente con email " + email + " non trovato");
        }
        return Mapper.map(utente, UtenteDTO.class);
    }

}
