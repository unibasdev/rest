package it.unibas.mediapesata.persistenza.hibernate;

import it.unibas.mediapesata.persistenza.IDAOStudente;
import it.unibas.mediapesata.modello.Studente;
import it.unibas.mediapesata.persistenza.DAOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class DAOStudenteHibernate extends DAOGenericoHibernate<Studente> implements IDAOStudente {

    public DAOStudenteHibernate() {
        super(Studente.class);
    }

    @Override
    public Studente findByMatricola(int matricola) throws DAOException {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<Studente> criteria = builder.createQuery(Studente.class);
        Root<Studente> root = criteria.from(Studente.class);
        criteria.select(root);
        Predicate predicatoMatricola = builder.equal(root.get("matricola"), matricola);
        criteria.where(predicatoMatricola);
        List<Studente> risultato = getSession().createQuery(criteria).getResultList();
        if (risultato.isEmpty()) {
            return null;
        }
        return risultato.get(0);
    }

    @Override
    public List<Studente> findByCognomeNomeAnnoIscrizione(String cognome, String nome, Integer annoIscrizione) throws DAOException {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<Studente> criteria = builder.createQuery(Studente.class);
        Root<Studente> root = criteria.from(Studente.class);
        criteria.select(root);
        List<Predicate> predicati = new ArrayList<>();
        if (cognome != null && !cognome.trim().isEmpty()) {
            predicati.add(builder.equal(root.get("cognome"), cognome));
        }
        if (nome != null && !nome.trim().isEmpty()) {
            predicati.add(builder.equal(root.get("nome"), nome));
        }
        if (annoIscrizione != null) {
            predicati.add(builder.equal(root.get("annoIscrizione"), annoIscrizione));
        }
        criteria.where(predicati.toArray(new Predicate[]{}));
        return getSession().createQuery(criteria).getResultList();
    }

}
