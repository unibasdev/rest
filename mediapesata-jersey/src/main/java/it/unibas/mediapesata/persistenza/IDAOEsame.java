package it.unibas.mediapesata.persistenza;

import it.unibas.mediapesata.modello.Esame;

import java.time.LocalDate;
import java.util.List;

public interface IDAOEsame extends IDAOGenerico<Esame> {

    List<Esame> findByInsegnamentoVotoLodeCreditiDataRegistrazione(String insegnamento, Integer voto, Boolean lode, Integer crediti, LocalDate dataRegistrazione) throws DAOException;

}
