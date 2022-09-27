package it.unibas.mediapesata.persistenza.mock;

import it.unibas.mediapesata.modello.Studente;
import it.unibas.mediapesata.persistenza.DAOException;
import it.unibas.mediapesata.persistenza.IDAOStudente;
import java.util.ArrayList;
import java.util.List;

public class DAOStudenteMock implements IDAOStudente {

    @Override
    public List<Studente> findByCognomeNomeAnnoIscrizione(String cognome, String nome, Integer annoIscrizione) throws DAOException {
        List<Studente> studenti = new ArrayList<>();
        RepositoryMock repositoryMock = RepositoryMock.getInstance();
        for (Studente studente : repositoryMock.getStudenti()) {
            if ((cognome == null || studente.getCognome().equalsIgnoreCase(cognome))
                    && (nome == null || studente.getNome().equalsIgnoreCase(nome))
                    && (annoIscrizione == null || studente.getAnnoIscrizione() == annoIscrizione)) {
                studenti.add(studente);
            }
        }
        return studenti;
    }

    @Override
    public Studente findByMatricola(int matricola) throws DAOException {
        RepositoryMock repositoryMock = RepositoryMock.getInstance();
        for (Studente studente : repositoryMock.getStudenti()) {
            if (studente.getMatricola() == matricola) {
                return studente;
            }
        }
        return null;
    }

    @Override
    public Studente findById(Long id) throws DAOException {
        RepositoryMock repositoryMock = RepositoryMock.getInstance();
        return repositoryMock.getStudente(id);
    }

    @Override
    public List<Studente> findAll() throws DAOException {
        RepositoryMock repositoryMock = RepositoryMock.getInstance();
        return repositoryMock.getStudenti();
    }

    @Override
    public Studente makePersistent(Studente studente) throws DAOException {
        RepositoryMock repositoryMock = RepositoryMock.getInstance();
        repositoryMock.addStudente(studente);
        return studente;
    }

    @Override
    public void makeTransient(Studente entity) throws DAOException {
        RepositoryMock repositoryMock = RepositoryMock.getInstance();
        repositoryMock.removeStudente(entity);
    }

}
