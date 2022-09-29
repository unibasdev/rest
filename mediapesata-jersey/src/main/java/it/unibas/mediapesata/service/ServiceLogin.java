package it.unibas.mediapesata.service;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibas.mediapesata.modello.Utente;
import it.unibas.mediapesata.modello.dto.UtenteDTO;
import it.unibas.mediapesata.persistenza.DAOFactory;
import it.unibas.mediapesata.persistenza.IDAOUtente;
import it.unibas.mediapesata.util.JWTUtil;

public class ServiceLogin {

    private final static Logger logger = LoggerFactory.getLogger(ServiceLogin.class);
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
        logger.debug("All'utente {} e' stato generato il token {} ", utenteDTO.getEmail(), token);
        return token;
    }

}
