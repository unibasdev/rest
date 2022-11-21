package it.unibas.mediapesata.service;

import it.unibas.mediapesata.modello.Esame;
import it.unibas.mediapesata.modello.Studente;
import it.unibas.mediapesata.modello.dto.EsameDTO;
import it.unibas.mediapesata.persistenza.DAOFactory;
import it.unibas.mediapesata.persistenza.IDAOEsame;
import it.unibas.mediapesata.persistenza.IDAOStudente;
import it.unibas.mediapesata.util.Mapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ApplicationScoped
public class ServiceEsami {

    private final static IDAOStudente daoStudente = DAOFactory.getInstance().getDAOStudente();
    private final static IDAOEsame daoEsame = DAOFactory.getInstance().getDAOEsame();

    public List<EsameDTO> getAllEsami(String insegnamento, Integer voto, Boolean lode, Integer crediti, LocalDate dataRegistrazione) {
        List<Esame> esami = daoEsame.findByInsegnamentoVotoLodeCreditiDataRegistrazione(insegnamento, voto, lode, crediti, dataRegistrazione);
        List<EsameDTO> esamiDTO = new ArrayList<>();
        for (Esame esame : esami) {
            EsameDTO esameDTO = Mapper.map(esame, EsameDTO.class);
            esameDTO.setStudenteId(esame.getStudente().getId());
            esamiDTO.add(esameDTO);
        }
        log.debug("Esami trovati: {}", esamiDTO.size());
        return esamiDTO;
    }

    public EsameDTO getEsame(long idEsame) {
        Esame esame = daoEsame.findById(idEsame);
        if (esame == null) {
            throw new IllegalArgumentException("Esame con id " + idEsame + " non trovato");
        }
        EsameDTO esameDTO = Mapper.map(esame, EsameDTO.class);
        esameDTO.setStudenteId(esame.getStudente().getId());
        log.debug("Esame trovato: {}", esameDTO);
        return esameDTO;
    }

    public long creaEsame(EsameDTO esameDTO) {
        if (esameDTO.getId() != null) {
            throw new IllegalArgumentException("L'esame nella richiesta deve avere id null");
        }
        if (esameDTO.getStudenteId() == null) {
            throw new IllegalArgumentException("L'esame nella richiesta deve avere studenteId");
        }
        Studente studente = daoStudente.findById(esameDTO.getStudenteId());
        if (studente == null) {
            throw new EntityNotFoundException("Studente con id " + esameDTO.getStudenteId() + " non trovato");
        }
        Esame nuovoEsame = Mapper.map(esameDTO, Esame.class);
        nuovoEsame.setStudente(studente);
        studente.getListaEsami().add(nuovoEsame);
        daoEsame.makePersistent(nuovoEsame);
        log.debug("Salvato l'esame {} {}", nuovoEsame.getId(), nuovoEsame);
        return nuovoEsame.getId();
    }

    public void modificaEsame(long idEsame, EsameDTO esameDTO) {
        if (esameDTO.getId() != null && !esameDTO.getId().equals(idEsame)) {
            throw new IllegalArgumentException("L'esame nella richiesta deve avere id " + idEsame);
        }
        if (esameDTO.getStudenteId() != null) {
            throw new IllegalArgumentException("L'esame nella richiesta deve avere studenteId nullo");
        }
        Esame esame = daoEsame.findById(idEsame);
        if (esame == null) {
            throw new IllegalArgumentException("Esame con id " + idEsame + " non trovato");
        }
        Mapper.map(esameDTO, esame);
    }

    public void eliminaEsame(long idEsame) {
        Esame esame = daoEsame.findById(idEsame);
        if (esame == null) {
            throw new IllegalArgumentException("Esame con id " + idEsame + " non trovato");
        }
        Studente studente = esame.getStudente();
        daoEsame.makeTransient(esame);
        studente.getListaEsami().remove(esame);
        daoStudente.makePersistent(studente);
    }
}
