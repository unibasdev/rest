package it.unibas.mediapesata.persistenza.mock;

import it.unibas.mediapesata.modello.Studente;
import it.unibas.mediapesata.persistenza.DAOException;
import it.unibas.mediapesata.persistenza.IDAOStudente;
import java.util.ArrayList;
import java.util.List;

public class DAOStudenteMock extends DAOGenericoMock<Studente> implements IDAOStudente {

    @Override
    public List<Studente> findByCognomeNomeAnnoIscrizione(String cognome, String nome, Integer annoIscrizione) throws DAOException {
        List<Studente> tuttiGliStudenti = this.findAll();
        List<Studente> studentiFiltrati = new ArrayList<>();
        for (Studente studente : tuttiGliStudenti) {
            if ((cognome == null || studente.getCognome().equalsIgnoreCase(cognome))
                    && (nome == null || studente.getNome().equalsIgnoreCase(nome))
                    && (annoIscrizione == null || studente.getAnnoIscrizione() == annoIscrizione)) {
                studentiFiltrati.add(studente);
            }
        }
        return studentiFiltrati;
    }

    @Override
    public Studente findByMatricola(int matricola) throws DAOException {
        for (Studente studente : this.findAll()) {
            if (studente.getMatricola() == matricola) {
                return studente;
            }
        }
        return null;
    }

}
