package it.unibas.mediapesata.persistenza;

import java.util.List;

public interface IDAOGenerico<T> {
    
    public T findById(Long id) throws DAOException;
    
    public List<T> findAll() throws DAOException;
    
    public T makePersistent(T entity) throws DAOException;
    
    public void makeTransient(T entity) throws DAOException;
    
}
