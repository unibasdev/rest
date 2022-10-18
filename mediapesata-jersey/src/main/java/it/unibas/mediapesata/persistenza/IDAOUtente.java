package it.unibas.mediapesata.persistenza;

import it.unibas.mediapesata.modello.Utente;


public interface IDAOUtente extends IDAOGenerico<Utente> {

    Utente findByEmail(String email) throws DAOException;

}
