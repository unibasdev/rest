package it.unibas.mediapesata.persistenza.mock;

import it.unibas.mediapesata.modello.Utente;
import it.unibas.mediapesata.persistenza.DAOException;
import it.unibas.mediapesata.persistenza.IDAOUtente;
import java.util.List;

public class DAOUtenteMock implements IDAOUtente {

    @Override
    public Utente findByEmail(String email) throws DAOException {
        RepositoryMock repositoryMock = RepositoryMock.getInstance();
        for (Utente utente : repositoryMock.getUtenti()) {
            if(utente.getEmail().equals(email)){
                return utente;
            }
        }
        return null;
    }

    @Override
    public Utente findById(Long id) throws DAOException {
        RepositoryMock repositoryMock = RepositoryMock.getInstance();
        return repositoryMock.getUtente(id);
    }

    @Override
    public List<Utente> findAll() throws DAOException {
        RepositoryMock repositoryMock = RepositoryMock.getInstance();
        return repositoryMock.getUtenti();
    }

    @Override
    public Utente makePersistent(Utente utente) throws DAOException {
        RepositoryMock repositoryMock = RepositoryMock.getInstance();
        repositoryMock.addUtente(utente);
        return utente;
    }

    @Override
    public void makeTransient(Utente entity) throws DAOException {
        RepositoryMock repositoryMock = RepositoryMock.getInstance();
        repositoryMock.removeUtente(entity);
    }

}
