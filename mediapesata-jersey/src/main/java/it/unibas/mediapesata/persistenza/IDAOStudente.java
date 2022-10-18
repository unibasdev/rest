package it.unibas.mediapesata.persistenza;

import it.unibas.mediapesata.modello.Studente;

import java.util.List;


public interface IDAOStudente extends IDAOGenerico<Studente> {

    List<Studente> findByCognomeNomeAnnoIscrizione(String cognome, String nome, Integer annoIscrizione) throws DAOException;

    Studente findByMatricola(int matricola) throws DAOException;

}
