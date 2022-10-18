package it.unibas.mediapesata.persistenza;

import it.unibas.mediapesata.modello.Studente;

import java.util.List;


public interface IDAOStudente extends IDAOGenerico<Studente>{
    
    public List<Studente> findByCognomeNomeAnnoIscrizione (String cognome, String nome, Integer annoIscrizione) throws DAOException;
    public Studente findByMatricola (int matricola) throws DAOException;
    
}
