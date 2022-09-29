package it.unibas.mediapesata.persistenza.hibernate;

import it.unibas.mediapesata.modello.Utente;
import it.unibas.mediapesata.persistenza.DAOException;
import it.unibas.mediapesata.persistenza.IDAOUtente;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class DAOUtenteHibernate extends DAOGenericoHibernate<Utente> implements IDAOUtente {

    @Override
    public Utente findByEmail(String email) throws DAOException {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<Utente> criteria = builder.createQuery(Utente.class);
        Root<Utente> root = criteria.from(Utente.class);
        criteria.select(root);
        Predicate predicatoEmail = builder.equal(root.get("email"), email);
        criteria.where(predicatoEmail);
        List<Utente> risultato = getSession().createQuery(criteria).getResultList();
        if (risultato.isEmpty()) {
            return null;
        }
        return risultato.get(0);
    }

}
