package it.unibas.mediapesata.persistenza.mockesteso;

import it.unibas.mediapesata.modello.Utente;
import it.unibas.mediapesata.persistenza.DAOException;
import it.unibas.mediapesata.persistenza.IDAOUtente;

import java.util.List;

public class DAOUtenteMockEsteso implements IDAOUtente {

    @Override
    public Utente findByEmail(String email) throws DAOException {
        RepositoryMockEsteso repositoryMock = RepositoryMockEsteso.getInstance();
        for (Utente utente : repositoryMock.getUtenti()) {
            if(utente.getEmail().equals(email)){
                return utente;
            }
        }
        return null;
    }

    @Override
    public Utente findById(Long id) throws DAOException {
        RepositoryMockEsteso repositoryMock = RepositoryMockEsteso.getInstance();
        return repositoryMock.getUtente(id);
    }

    @Override
    public List<Utente> findAll() throws DAOException {
        RepositoryMockEsteso repositoryMock = RepositoryMockEsteso.getInstance();
        return repositoryMock.getUtenti();
    }

    @Override
    public Utente makePersistent(Utente utente) throws DAOException {
        RepositoryMockEsteso repositoryMock = RepositoryMockEsteso.getInstance();
        repositoryMock.addUtente(utente);
        return utente;
    }

    @Override
    public void makeTransient(Utente entity) throws DAOException {
        RepositoryMockEsteso repositoryMock = RepositoryMockEsteso.getInstance();
        repositoryMock.removeUtente(entity);
    }

}
