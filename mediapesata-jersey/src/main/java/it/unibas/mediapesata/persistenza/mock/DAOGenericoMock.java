package it.unibas.mediapesata.persistenza.mock;

import it.unibas.mediapesata.persistenza.IDAOGenerico;
import it.unibas.mediapesata.persistenza.DAOException;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOGenericoMock<T> implements IDAOGenerico<T> {

    private final static Logger logger = LoggerFactory.getLogger(DAOGenericoMock.class);
    private Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public DAOGenericoMock() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    protected Class<T> getPersistentClass() {
        return persistentClass;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T makePersistent(T entity) throws DAOException {
        RepositoryMock repositoryMock = RepositoryMock.getInstance();
        repositoryMock.addBean(entity);
        return entity;
    }

    @Override
    public void makeTransient(T entity) throws DAOException {
        RepositoryMock repositoryMock = RepositoryMock.getInstance();
        repositoryMock.removeBean(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findById(Long id) throws DAOException {
        RepositoryMock repositoryMock = RepositoryMock.getInstance();
        return repositoryMock.getBean(id, persistentClass);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll() throws DAOException {
        RepositoryMock repositoryMock = RepositoryMock.getInstance();
        return repositoryMock.getBeans(persistentClass);
    }

}
