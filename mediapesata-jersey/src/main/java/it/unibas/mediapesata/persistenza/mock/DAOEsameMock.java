package it.unibas.mediapesata.persistenza.mock;

import it.unibas.mediapesata.modello.Esame;
import it.unibas.mediapesata.persistenza.DAOException;
import it.unibas.mediapesata.persistenza.IDAOEsame;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAOEsameMock implements IDAOEsame {

    @Override
    public List<Esame> findByInsegnamentoVotoLodeCreditiDataRegistrazione(String insegnamento,
            Integer voto, Boolean lode, Integer crediti, LocalDate dataRegistrazione) throws DAOException {
        List<Esame> esami = new ArrayList<>();
        RepositoryMock repositoryMock = RepositoryMock.getInstance();
        for (Esame esame : repositoryMock.getEsami()) {
            if ((insegnamento == null || esame.getInsegnamento().equalsIgnoreCase(insegnamento))
                    && (voto == null || esame.getVoto() == voto)
                    && (lode == null || esame.isLode() == lode)
                    && (crediti == null || esame.getCrediti() == crediti)
                    && (dataRegistrazione == null || esame.getDataRegistrazione().equals(dataRegistrazione))) {
                esami.add(esame);
            }
        }
        return esami;
    }

    @Override
    public Esame findById(Long id) throws DAOException {
        RepositoryMock repositoryMock = RepositoryMock.getInstance();
        return repositoryMock.getEsame(id);
    }

    @Override
    public List<Esame> findAll() throws DAOException {
        RepositoryMock repositoryMock = RepositoryMock.getInstance();
        return repositoryMock.getEsami();
    }

    @Override
    public Esame makePersistent(Esame esame) throws DAOException {
        RepositoryMock repositoryMock = RepositoryMock.getInstance();
        repositoryMock.addEsame(esame);
        return esame;
    }

    @Override
    public void makeTransient(Esame esame) throws DAOException {
        RepositoryMock repositoryMock = RepositoryMock.getInstance();
        repositoryMock.removeEsame(esame);
        esame.getStudente().getListaEsami().remove(esame);
    }

}
