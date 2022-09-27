package it.unibas.mediapesata.persistenza.mock;

import it.unibas.mediapesata.modello.Esame;
import it.unibas.mediapesata.modello.Studente;
import it.unibas.mediapesata.modello.Utente;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryMock {

    private static RepositoryMock singleton = new RepositoryMock();

    public static RepositoryMock getInstance() {
        return singleton;
    }

    private Map<Long, Utente> utenti = new HashMap<>();
    private Map<Long, Esame> esami = new HashMap<>();
    private Map<Long, Studente> studenti = new HashMap<>();
    private long prossimoId = 0;

    private RepositoryMock() {
        Utente u1 = new Utente("admin@unibas.it", "admin");
        addUtente(u1);
        Studente s1 = new Studente("Rossi", "Mario", 1234, 2022);
        Studente s2 = new Studente("Verdi", "Giuseppe", 4321, 2022);
        addStudente(s1);
        addStudente(s2);
        Esame e1 = new Esame("POO2", 30, true, 12, LocalDate.parse("2021-05-26"), s1);
        s1.getListaEsami().add(e1);
        Esame e2 = new Esame("Calcolo", 24, false, 6, LocalDate.parse("2021-10-10"), s1);
        s1.getListaEsami().add(e2);
        Esame e3 = new Esame("Inglese", 26, false, 3, LocalDate.parse("2021-10-20"), s2);
        s2.getListaEsami().add(e3);
        addEsame(e1);
        addEsame(e2);
        addEsame(e3);
    }
    
    public Utente getUtente(long id){
        return utenti.get(id);
    }
    
    public Esame getEsame(long id){
        return esami.get(id);
    }
    
    public Studente getStudente(long id){
        return studenti.get(id);
    }

    public List<Utente> getUtenti() {
        return new ArrayList<>(utenti.values());
    }

    public List<Esame> getEsami() {
        return new ArrayList<>(esami.values());
    }

    public List<Studente> getStudenti() {
        return new ArrayList<>(studenti.values());
    }

    public void addUtente(Utente utente) {
        utente.setId(getProssimoId());
        utenti.put(utente.getId(), utente);
    }

    public void addStudente(Studente studente) {
        studente.setId(getProssimoId());
        studenti.put(studente.getId(), studente);
    }

    public void addEsame(Esame esame) {
        esame.setId(getProssimoId());
        esami.put(esame.getId(), esame);
    }

    public void removeUtente(Utente utente) {
        utenti.remove(utente.getId());
    }

    public void removeStudente(Studente studente) {
        studenti.remove(studente.getId());
    }

    public void removeEsame(Esame esame) {
        esami.remove(esame.getId());
    }
    
    private long getProssimoId() {
        return prossimoId++;
    }

}
