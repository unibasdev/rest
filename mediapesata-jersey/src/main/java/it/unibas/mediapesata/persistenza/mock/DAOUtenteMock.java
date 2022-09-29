package it.unibas.mediapesata.persistenza.mock;

import it.unibas.mediapesata.modello.Utente;
import it.unibas.mediapesata.persistenza.DAOException;
import it.unibas.mediapesata.persistenza.IDAOUtente;

public class DAOUtenteMock extends DAOGenericoMock<Utente> implements IDAOUtente {

    @Override
    public Utente findByEmail(String email) throws DAOException {
        for (Utente utente : this.findAll()) {
            if (utente.getEmail().equals(email)) {
                return utente;
            }
        }
        return null;
    }

}
