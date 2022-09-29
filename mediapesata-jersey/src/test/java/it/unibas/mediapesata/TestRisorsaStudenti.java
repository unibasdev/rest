package it.unibas.mediapesata;

import it.unibas.mediapesata.enums.EStrategiaPersistenza;
import it.unibas.mediapesata.modello.Configurazione;
import it.unibas.mediapesata.modello.dto.StudenteDTO;
import it.unibas.mediapesata.persistenza.hibernate.DAOUtilHibernate;
import it.unibas.mediapesata.service.ServiceStudenti;
import junit.framework.TestCase;

public class TestRisorsaStudenti extends TestCase {

    private ServiceStudenti serviceStudenti = new ServiceStudenti();

    @Override
    protected void setUp() throws Exception {
        if (Configurazione.getInstance().getStrategiaDb().equals(EStrategiaPersistenza.DB_HIBERNATE)) {
            DAOUtilHibernate.beginTransaction();
        }
    }

    @Override
    protected void tearDown() throws Exception {
        if (Configurazione.getInstance().getStrategiaDb().equals(EStrategiaPersistenza.DB_HIBERNATE)) {
            DAOUtilHibernate.rollback();
        }
    }

    public void testCRUDStudente() {
        StudenteDTO nuovoStudente = new StudenteDTO();
        nuovoStudente.setCognome("Test-COGNOME");
        nuovoStudente.setNome("Test-NOME");
        nuovoStudente.setMatricola(12345);
        nuovoStudente.setAnnoIscrizione(2022);
        assertEquals(0, serviceStudenti.getAllStudenti(nuovoStudente.getCognome(), nuovoStudente.getNome(), nuovoStudente.getAnnoIscrizione()).size());
        int dimensioneIniziale = serviceStudenti.getAllStudenti(null, null, null).size();
        long idNuovoStudente = serviceStudenti.creaStudente(nuovoStudente, "-test-");
        int dimensionePostCreazione = serviceStudenti.getAllStudenti(null, null, null).size();
        assertEquals(dimensioneIniziale + 1, dimensionePostCreazione);
        assertEquals(1, serviceStudenti.getAllStudenti(nuovoStudente.getCognome(), nuovoStudente.getNome(), nuovoStudente.getAnnoIscrizione()).size());
        StudenteDTO studenteCreato = serviceStudenti.getStudente(idNuovoStudente);
        assertNotNull(studenteCreato);
        assertEquals(idNuovoStudente, studenteCreato.getId().longValue());
        assertEquals(nuovoStudente.getCognome(), studenteCreato.getCognome());
        assertEquals(nuovoStudente.getNome(), studenteCreato.getNome());
        assertEquals(nuovoStudente.getAnnoIscrizione(), studenteCreato.getAnnoIscrizione());
        nuovoStudente.setCognome("Test-COGNOME-Modifica");
        nuovoStudente.setNome("Test-NOME-Modifica");
        nuovoStudente.setMatricola(54321);
        serviceStudenti.modificaStudente(idNuovoStudente, nuovoStudente);
        StudenteDTO studenteModificato = serviceStudenti.getStudente(idNuovoStudente);
        assertNotNull(studenteModificato);
        assertEquals(idNuovoStudente, studenteModificato.getId().longValue());
        assertEquals(nuovoStudente.getCognome(), studenteModificato.getCognome());
        assertEquals(nuovoStudente.getNome(), studenteModificato.getNome());
        assertEquals(nuovoStudente.getAnnoIscrizione(), studenteModificato.getAnnoIscrizione());
        serviceStudenti.eliminaStudente(idNuovoStudente);
        assertEquals(0, serviceStudenti.getAllStudenti(nuovoStudente.getCognome(), nuovoStudente.getNome(), nuovoStudente.getAnnoIscrizione()).size());
        int dimensionePostCancellazione = serviceStudenti.getAllStudenti(null, null, null).size();
        assertEquals(dimensioneIniziale, dimensionePostCancellazione);
    }

}
