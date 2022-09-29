package it.unibas.mediapesata.persistenza.mock;

import it.unibas.mediapesata.modello.Esame;
import it.unibas.mediapesata.persistenza.DAOException;
import it.unibas.mediapesata.persistenza.IDAOEsame;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAOEsameMock extends DAOGenericoMock<Esame> implements IDAOEsame {

    @Override
    public List<Esame> findByInsegnamentoVotoLodeCreditiDataRegistrazione(String insegnamento,
            Integer voto, Boolean lode, Integer crediti, LocalDate dataRegistrazione) throws DAOException {
        List<Esame> tuttiGliEsami = this.findAll();
        List<Esame> esamiFiltrati = new ArrayList<>();
        for (Esame esame : tuttiGliEsami) {
            if ((insegnamento == null || esame.getInsegnamento().equalsIgnoreCase(insegnamento))
                    && (voto == null || esame.getVoto() == voto)
                    && (lode == null || esame.isLode() == lode)
                    && (crediti == null || esame.getCrediti() == crediti)
                    && (dataRegistrazione == null || esame.getDataRegistrazione().equals(dataRegistrazione))) {
                esamiFiltrati.add(esame);
            }
        }
        return esamiFiltrati;
    }

}
