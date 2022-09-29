 package it.unibas.mediapesata;

import it.unibas.mediapesata.modello.Esame;
import it.unibas.mediapesata.modello.Studente;
import static java.time.LocalDate.now;
import junit.framework.*;

public class TestStudente extends TestCase {

    private Studente studente;
    private Esame esame1;
    private Esame esame2;
    private Esame esame3;

    public void setUp() {
        studente = new Studente("Mario", "Rossi", 1234, 2022);
        esame1 = new Esame("Analisi", 24, false, 3, now(), studente);
        esame2 = new Esame("Geometria", 27, false, 9, now(), studente);
        esame3 = new Esame("Fisica", 30, true, 9, now(), studente);
    }

    public void testMediaPesata30esimi1() {
        studente.addEsame(esame1);
        studente.addEsame(esame2);
        assertEquals("media p. 1", 26.25, studente.getMedia30mi(), 0.01);
    }

    public void testMediaPesata30esimi2() {
        studente.addEsame(esame1);
        assertEquals("media p. 2", 24, studente.getMedia30mi(), 0.01);
    }

    public void testMediaPesata30esimi3() {
        studente.addEsame(esame1);
        studente.addEsame(esame2);
        studente.addEsame(esame3);
        assertEquals("media p. 3", 27.85, studente.getMedia30mi(), 0.01);
    }

    public void testMediaPesata110esimi1() {
        studente.addEsame(esame1);
        studente.addEsame(esame2);
        assertEquals("media p. 1", 96.25, studente.getMedia110mi(), 0.01);
    }

    public void testMediaPesata110esimi2() {
        studente.addEsame(esame1);
        assertEquals("media p. 2", 88, studente.getMedia110mi(), 0.01);
    }

    public void testMediaPesata110esimi3() {
        studente.addEsame(esame1);
        studente.addEsame(esame2);
        studente.addEsame(esame3);
        assertEquals("media p. 3", 102.14, studente.getMedia110mi(), 0.01);
    }

    public void testMediaPesataErrore() {
        try {
            studente.getMedia30mi();
            fail();
        } catch (Exception e) {
        }
    }

}
