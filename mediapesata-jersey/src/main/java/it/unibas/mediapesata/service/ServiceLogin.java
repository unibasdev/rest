package it.unibas.mediapesata.service;

import it.unibas.mediapesata.modello.Utente;
import it.unibas.mediapesata.modello.dto.UtenteDTO;
import it.unibas.mediapesata.persistenza.DAOFactory;
import it.unibas.mediapesata.persistenza.IDAOUtente;
import it.unibas.mediapesata.util.JWTUtil;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityNotFoundException;

@Slf4j
@ApplicationScoped
public class ServiceLogin {

    private IDAOUtente daoUtente = DAOFactory.getInstance().getDAOUtente();

    public String login(UtenteDTO utenteDTO) {
        Utente utente = daoUtente.findByEmail(utenteDTO.getEmail());
        if (utente == null) {
            throw new EntityNotFoundException("Utente con email " + utenteDTO.getEmail() + " non trovato");
        }
        if (!utenteDTO.getPassword().equals(utente.getPassword())) {
            throw new IllegalArgumentException("Password non valida per l'utente " + utenteDTO.getEmail());
        }
        String token = JWTUtil.generaToken(utenteDTO.getEmail());
        log.debug("All'utente {} e' stato generato il token {} ", utenteDTO.getEmail(), token);
        return token;
    }

}
