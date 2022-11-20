package it.unibas.progettorest.persistenza.mock;

import it.unibas.progettorest.persistenza.DAOException;

import java.lang.reflect.Field;
import java.util.*;

public class RepositoryGenericoMock {

    private final Map<Class, Map<Long, Object>> beans = new HashMap<>();
    private long prossimoId = 1;

    @SuppressWarnings("unchecked")
    public <T> T findById(long id, Class<T> classe) {
        return (T) beans.get(classe).get(id);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findAll(Class<T> classe) {
        return new ArrayList<>((Collection<? extends T>) beans.get(classe).values());
    }

    public <T> void saveOrUpdate(T bean) {
        try {
            Class classe = bean.getClass();
            Field field = classe.getDeclaredField("id");
            field.setAccessible(true);
            Long id = (Long) field.get(bean);
            if (id == null || id == 0) {
                id = getProssimoId();
                field.set(bean, id);
            }
            Map<Long, Object> mappaBeans = beans.get(classe);
            if (mappaBeans == null) {
                mappaBeans = new HashMap<>();
                beans.put(classe, mappaBeans);
            }
            mappaBeans.put(id, bean);
        } catch (Exception ex) {
            throw new DAOException("Impossibile cambiare il valore dell'id del bean " + bean);
        }
    }

    public <T> void delete(T bean) {
        try {
            Class classe = bean.getClass();
            Field field = classe.getDeclaredField("id");
            field.setAccessible(true);
            Map<Long, Object> mappaBeans = beans.get(classe);
            long id = (long) field.get(bean);
            mappaBeans.remove(id);
        } catch (Exception ex) {
            throw new DAOException("Impossibile leggere il valore dell'id del bean " + bean);
        }
    }

    private long getProssimoId() {
        return prossimoId++;
    }

}
