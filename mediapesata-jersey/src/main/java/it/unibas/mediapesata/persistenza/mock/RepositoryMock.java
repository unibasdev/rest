package it.unibas.mediapesata.persistenza.mock;

import it.unibas.mediapesata.modello.Esame;
import it.unibas.mediapesata.modello.Studente;
import it.unibas.mediapesata.modello.Utente;

import java.time.LocalDate;

public class RepositoryMock extends RepositoryGenericoMock {

    private static final RepositoryMock singleton = new RepositoryMock();

    private RepositoryMock() {
        Utente u1 = new Utente("admin@unibas.it", "admin", "Pinco", "Palla");
        saveOrUpdate(u1);
        Studente s1 = new Studente("Rossi", "Mario", 1234, 2022);
        Studente s2 = new Studente("Verdi", "Giuseppe", 4321, 2022);
        saveOrUpdate(s1);
        saveOrUpdate(s2);
        Esame e1 = new Esame("POO2", 30, true, 12, LocalDate.parse("2021-05-26"), s1);
        s1.getListaEsami().add(e1);
        Esame e2 = new Esame("Calcolo", 24, false, 6, LocalDate.parse("2021-10-10"), s1);
        s1.getListaEsami().add(e2);
        Esame e3 = new Esame("Inglese", 26, false, 3, LocalDate.parse("2021-10-20"), s2);
        s2.getListaEsami().add(e3);
        saveOrUpdate(e1);
        saveOrUpdate(e2);
        saveOrUpdate(e3);
    }

    public static RepositoryMock getInstance() {
        return singleton;
    }

}
