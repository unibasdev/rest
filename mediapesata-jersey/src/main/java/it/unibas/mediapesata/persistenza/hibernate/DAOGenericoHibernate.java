package it.unibas.mediapesata.persistenza.hibernate;

import it.unibas.mediapesata.persistenza.IDAOGenerico;
import it.unibas.mediapesata.persistenza.DAOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOGenericoHibernate<T> implements IDAOGenerico<T> {

    private final static Logger logger = LoggerFactory.getLogger(DAOGenericoHibernate.class);
    private Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public DAOGenericoHibernate() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    protected Class<T> getPersistentClass() {
        return persistentClass;
    }

    protected static Session getSession() throws DAOException {
        try {
            return DAOUtilHibernate.getCurrentSession();
        } catch (HibernateException ex) {
            logger.error("Error while opening session", ex);
            throw new DAOException(ex);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T makePersistent(T entity) throws DAOException {
        try {
            getSession().saveOrUpdate(entity);
        } catch (HibernateException ex) {
            logger.error("Error while making persistent object {}", entity, ex);
            throw new DAOException(ex);
        }
        return entity;
    }

    @Override
    public void makeTransient(T entity) throws DAOException {
        try {
            getSession().delete(entity);
        } catch (HibernateException ex) {
            logger.error("Error while making transient object {}", entity, ex);
            throw new DAOException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findById(Long id) throws DAOException {
        List<T> lista = findByEqual("id", id);
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll() throws DAOException {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(persistentClass);
        Root<T> root = criteria.from(persistentClass);
        criteria.select(root);
        return getSession().createQuery(criteria).getResultList();
    }

    public List<T> findByEqual(String attributeName, Object attributeValue) throws DAOException {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(persistentClass);
        Root<T> root = criteria.from(persistentClass);
        criteria.select(root);
        criteria.where(builder.equal(root.get(attributeName), attributeValue));
        return getSession().createQuery(criteria).getResultList();
    }

    public List<T> findByEqual(Map<String, Object> andSelections) throws DAOException {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(persistentClass);
        Root<T> root = criteria.from(persistentClass);
        criteria.select(root);
        List<Predicate> predicates = new ArrayList<Predicate>();
        for (String attributeName : andSelections.keySet()) {
            Object attributeValue = andSelections.get(attributeName);
            predicates.add(builder.equal(root.get(attributeName), attributeValue));
        }
        Predicate andPredicate = builder.and(predicates.toArray(new Predicate[]{}));
        criteria.where(andPredicate);
        return getSession().createQuery(criteria).getResultList();
    }

    public List<T> findByEqualIgnoreCase(String attributeName, String attributeValue) throws DAOException {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(persistentClass);
        Root<T> root = criteria.from(persistentClass);
        criteria.select(root);
        Expression<String> path = root.get(attributeName);
        Expression<String> upper = builder.upper(path);
        Predicate ctfPredicate = builder.equal(upper, attributeValue.toUpperCase());
        criteria.where(builder.and(ctfPredicate));
        return getSession().createQuery(criteria).getResultList();
    }

    @SuppressWarnings("unchecked")
    public T saveOrMerge(T obj, Long id) throws DAOException {
        try {
            T persistentObject = (T) getSession().get(persistentClass, id);
            if (persistentObject != null) {
                if (logger.isDebugEnabled()) logger.debug("Get ha trovato l'oggetto con id " + id);
                return persistentObject;
            } else {
                makePersistent(obj);
                return obj;
            }
        } catch (Exception ex) {
            logger.error("Error while saving object {}", obj, ex);
            throw new DAOException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public T merge(T obj) throws DAOException {
        try {
            T persistentObject = (T) getSession().merge(obj);
            return persistentObject;
        } catch (Exception ex) {
            logger.error("Error while merging object {}", obj, ex);
            throw new DAOException(ex);
        }
    }

}
