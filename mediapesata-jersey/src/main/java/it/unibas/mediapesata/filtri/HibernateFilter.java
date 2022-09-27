package it.unibas.mediapesata.filtri;

import it.unibas.mediapesata.modello.Configurazione;
import it.unibas.mediapesata.enums.EStrategiaPersistenza;
import it.unibas.mediapesata.persistenza.hibernate.DAOUtilHibernate;
import jakarta.annotation.Priority;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@Priority(100)
public class HibernateFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private final static Logger logger = LoggerFactory.getLogger(HibernateFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if(!Configurazione.getInstance().getStrategiaDb().equals(EStrategiaPersistenza.DB_HIBERNATE)){
            return;
        }
        if (requestContext.getMethod().equalsIgnoreCase("OPTIONS")) {
            return;
        }
        logger.debug("Avvio la transazione");
        DAOUtilHibernate.beginTransaction();
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if(!Configurazione.getInstance().getStrategiaDb().equals(EStrategiaPersistenza.DB_HIBERNATE)){
            return;
        }
        if (requestContext.getMethod().equalsIgnoreCase("OPTIONS")) {
            return;
        }
        if (DAOUtilHibernate.getSessionFactory().getCurrentSession().getTransaction().isActive()) {
            logger.debug("Effettuo il commit della transazione");
            DAOUtilHibernate.commit();
        }
    }

}
