package it.unibas.mediapesata.persistenza.hibernate;

import it.unibas.mediapesata.modello.Esame;
import it.unibas.mediapesata.persistenza.DAOException;
import it.unibas.mediapesata.persistenza.IDAOEsame;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAOEsameHibernate extends DAOGenericoHibernate<Esame> implements IDAOEsame {

    @Override
    public List<Esame> findByInsegnamentoVotoLodeCreditiDataRegistrazione(String insegnamento, Integer voto, Boolean lode, Integer crediti, LocalDate dataRegistrazione) throws DAOException {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<Esame> criteria = builder.createQuery(Esame.class);
        Root<Esame> root = criteria.from(Esame.class);
        criteria.select(root);
        List<Predicate> predicati = new ArrayList<>();
        if (insegnamento != null && !insegnamento.trim().isEmpty()) {
            predicati.add(builder.equal(root.get("insegnamento"), insegnamento));
        }
        if (voto != null) {
            predicati.add(builder.equal(root.get("voto"), voto));
        }
        if (lode != null) {
            predicati.add(builder.equal(root.get("lode"), lode));
        }
        if (crediti != null) {
            predicati.add(builder.equal(root.get("crediti"), crediti));
        }
        if (dataRegistrazione != null) {
            predicati.add(builder.equal(root.get("dataRegistrazione"), dataRegistrazione));
        }
        criteria.where(predicati.toArray(new Predicate[]{}));
        return getSession().createQuery(criteria).getResultList();
    }

}
