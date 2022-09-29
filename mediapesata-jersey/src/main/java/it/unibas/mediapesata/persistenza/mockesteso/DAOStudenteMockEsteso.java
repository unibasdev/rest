package it.unibas.mediapesata.persistenza.mockesteso;

import it.unibas.mediapesata.modello.Studente;
import it.unibas.mediapesata.persistenza.DAOException;
import it.unibas.mediapesata.persistenza.IDAOStudente;
import java.util.ArrayList;
import java.util.List;

public class DAOStudenteMockEsteso implements IDAOStudente {

    @Override
    public List<Studente> findByCognomeNomeAnnoIscrizione(String cognome, String nome, Integer annoIscrizione) throws DAOException {
        List<Studente> studenti = new ArrayList<>();
        RepositoryMockEsteso repositoryMock = RepositoryMockEsteso.getInstance();
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
        RepositoryMockEsteso repositoryMock = RepositoryMockEsteso.getInstance();
        for (Studente studente : repositoryMock.getStudenti()) {
            if (studente.getMatricola() == matricola) {
                return studente;
            }
        }
        return null;
    }

    @Override
    public Studente findById(Long id) throws DAOException {
        RepositoryMockEsteso repositoryMock = RepositoryMockEsteso.getInstance();
        return repositoryMock.getStudente(id);
    }

    @Override
    public List<Studente> findAll() throws DAOException {
        RepositoryMockEsteso repositoryMock = RepositoryMockEsteso.getInstance();
        return repositoryMock.getStudenti();
    }

    @Override
    public Studente makePersistent(Studente studente) throws DAOException {
        RepositoryMockEsteso repositoryMock = RepositoryMockEsteso.getInstance();
        repositoryMock.addStudente(studente);
        return studente;
    }

    @Override
    public void makeTransient(Studente entity) throws DAOException {
        RepositoryMockEsteso repositoryMock = RepositoryMockEsteso.getInstance();
        repositoryMock.removeStudente(entity);
    }

}
