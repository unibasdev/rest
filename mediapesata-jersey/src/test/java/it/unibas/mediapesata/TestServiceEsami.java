package it.unibas.mediapesata;

import it.unibas.mediapesata.enums.EStrategiaPersistenza;
import it.unibas.mediapesata.modello.Configurazione;
import it.unibas.mediapesata.modello.dto.EsameDTO;
import it.unibas.mediapesata.modello.dto.StudenteDTO;
import it.unibas.mediapesata.persistenza.hibernate.DAOUtilHibernate;
import it.unibas.mediapesata.service.ServiceEsami;
import it.unibas.mediapesata.service.ServiceStudenti;
import junit.framework.TestCase;

import java.time.LocalDate;

public class TestServiceEsami extends TestCase {

    private ServiceStudenti serviceStudenti = new ServiceStudenti();
    private ServiceEsami serviceEsami = new ServiceEsami();

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
        long idNuovoStudente = serviceStudenti.creaStudente(nuovoStudente, "-test-");
        EsameDTO nuovoEsame = new EsameDTO();
        nuovoEsame.setStudenteId(idNuovoStudente);
        nuovoEsame.setInsegnamento("-test-ins-");
        nuovoEsame.setVoto(30);
        nuovoEsame.setCrediti(6);
        nuovoEsame.setLode(true);
        nuovoEsame.setDataRegistrazione(LocalDate.now());
        int dimensioneIniziale = serviceEsami.getAllEsami(null, null, null, null, null).size();
        int dimensioneInizialeStudente = serviceStudenti.getEsami(idNuovoStudente).size();
        long idNuovoEsame = serviceEsami.creaEsame(nuovoEsame);
        int dimensionePostCreazione = serviceEsami.getAllEsami(null, null, null, null, null).size();
        int dimensionePostCreazioneStudente = serviceStudenti.getEsami(idNuovoStudente).size();
        assertEquals(dimensioneIniziale + 1, dimensionePostCreazione);
        assertEquals(dimensioneInizialeStudente + 1, dimensionePostCreazioneStudente);
        EsameDTO esameCreato = serviceEsami.getEsame(idNuovoEsame);
        assertNotNull(esameCreato);
        assertEquals(idNuovoEsame, esameCreato.getId().longValue());
        assertEquals(nuovoEsame.getInsegnamento(), esameCreato.getInsegnamento());
        assertEquals(nuovoEsame.getVoto(), esameCreato.getVoto());
        assertEquals(nuovoEsame.getCrediti(), esameCreato.getCrediti());
        assertEquals(nuovoEsame.isLode(), esameCreato.isLode());
        assertEquals(nuovoEsame.getDataRegistrazione(), esameCreato.getDataRegistrazione());
        nuovoEsame.setInsegnamento("-test-ins-MODIFICA-");
        nuovoEsame.setStudenteId(null);
        nuovoEsame.setVoto(26);
        nuovoEsame.setCrediti(9);
        nuovoEsame.setLode(false);
        nuovoEsame.setDataRegistrazione(LocalDate.now().minusDays(1));
        serviceEsami.modificaEsame(idNuovoEsame, nuovoEsame);
        EsameDTO esameModificato = serviceEsami.getEsame(idNuovoEsame);
        assertNotNull(esameModificato);
        assertEquals(idNuovoEsame, esameCreato.getId().longValue());
        assertEquals(nuovoEsame.getInsegnamento(), esameModificato.getInsegnamento());
        assertEquals(nuovoEsame.getVoto(), esameModificato.getVoto());
        assertEquals(nuovoEsame.getCrediti(), esameModificato.getCrediti());
        assertEquals(nuovoEsame.isLode(), esameModificato.isLode());
        assertEquals(nuovoEsame.getDataRegistrazione(), esameModificato.getDataRegistrazione());
        serviceEsami.eliminaEsame(idNuovoEsame);
        int dimensionePostCancellazione = serviceEsami.getAllEsami(null, null, null, null, null).size();
        int dimensionePostCancellazioneStudente = serviceStudenti.getEsami(idNuovoStudente).size();
        assertEquals(dimensioneInizialeStudente, dimensionePostCancellazioneStudente);
        assertEquals(dimensioneIniziale, dimensionePostCancellazione);
        serviceStudenti.eliminaStudente(idNuovoStudente);
        assertEquals(0, serviceStudenti.getAllStudenti(nuovoStudente.getCognome(), nuovoStudente.getNome(), nuovoStudente.getAnnoIscrizione()).size());
    }

}
