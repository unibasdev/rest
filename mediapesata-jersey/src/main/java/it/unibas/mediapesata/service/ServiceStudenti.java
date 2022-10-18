package it.unibas.mediapesata.service;

import it.unibas.mediapesata.enums.ETipoMediaPesata;
import it.unibas.mediapesata.modello.Esame;
import it.unibas.mediapesata.modello.Studente;
import it.unibas.mediapesata.modello.dto.EsameDTO;
import it.unibas.mediapesata.modello.dto.StudenteDTO;
import it.unibas.mediapesata.persistenza.DAOFactory;
import it.unibas.mediapesata.persistenza.IDAOStudente;
import it.unibas.mediapesata.util.Mapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ApplicationScoped
public class ServiceStudenti {

    private final static IDAOStudente daoStudente = DAOFactory.getInstance().getDAOStudente();

    public List<StudenteDTO> getAllStudenti(String cognome, String nome, Integer annoIscrizione) {
        List<Studente> studenti = daoStudente.findByCognomeNomeAnnoIscrizione(cognome, nome, annoIscrizione);
        List<StudenteDTO> studentiDTO = Mapper.map(studenti, StudenteDTO.class);
        log.debug("Studenti trovati: {}", studentiDTO.size());
        return studentiDTO;
    }

    public StudenteDTO getStudente(long idStudente) {
        Studente studente = daoStudente.findById(idStudente);
        if (studente == null) {
            throw new EntityNotFoundException("Studente con id " + idStudente + " non trovato");
        }
        StudenteDTO studenteDTO = Mapper.map(studente, StudenteDTO.class);
        log.debug("Studente trovato: {}", studenteDTO);
        return studenteDTO;
    }

    public List<EsameDTO> getEsami(long idStudente) {
        Studente studente = daoStudente.findById(idStudente);
        if (studente == null) {
            throw new EntityNotFoundException("Studente con id " + idStudente + " non trovato");
        }
        log.debug("Esami: {}", studente.getListaEsami());
        List<EsameDTO> esamiDTO = new ArrayList<>();
        for (Esame esame : studente.getListaEsami()) {
            EsameDTO esameDTO = Mapper.map(esame, EsameDTO.class);
            esameDTO.setStudenteId(esame.getStudente().getId());
            esamiDTO.add(esameDTO);
        }
        return esamiDTO;
    }

    public double getMediaPesata(long idStudente, ETipoMediaPesata tipoMediaPesata) {
        log.debug("Calcolo la medi pesata dello studente con id: {}", idStudente);
        Studente studente = daoStudente.findById(idStudente);
        if (studente == null) {
            throw new EntityNotFoundException("Studente con id " + idStudente + " non trovato");
        }
        double mediaPesata;
        if (tipoMediaPesata.equals(ETipoMediaPesata.MEDIA_110)) {
            mediaPesata = studente.getMedia110mi();
        } else {
            mediaPesata = studente.getMedia30mi();
        }
        return mediaPesata;
    }

    public long creaStudente(StudenteDTO studenteDTO, String utente) {
        if (studenteDTO.getId() != null) {
            throw new IllegalArgumentException("Lo studente nella richiesta deve avere id null");
        }
        Studente studenteEsistente = daoStudente.findByMatricola(studenteDTO.getMatricola());
        if (studenteEsistente != null) {
            throw new IllegalArgumentException("Studente con matricola " + studenteDTO.getMatricola() + " gia' esistente");
        }
        Studente studente = Mapper.map(studenteDTO, Studente.class);
        daoStudente.makePersistent(studente);
        log.debug("L'utente {} ha creato lo studente {} {}", utente, studente.getId(), studente);
        return studente.getId();
    }

    public void modificaStudente(long idStudente, StudenteDTO studenteDTO) {
        if (studenteDTO.getId() != null && !studenteDTO.getId().equals(idStudente)) {
            throw new IllegalArgumentException("Lo studente nella richiesta deve avere id " + idStudente);
        }
        Studente studente = daoStudente.findById(idStudente);
        if (studente == null) {
            throw new EntityNotFoundException("Studente con id " + idStudente + " non trovato");
        }
        log.debug("Studente prima della modifica: {}", studente);
        Mapper.map(studenteDTO, studente);
        log.debug("Studente dopo la modifica: {}", studente);
    }

    public void eliminaStudente(long idStudente) {
        Studente studente = daoStudente.findById(idStudente);
        if (studente == null) {
            throw new EntityNotFoundException("Studente con id " + idStudente + " non trovato");
        }
        daoStudente.makeTransient(studente);
    }
}
