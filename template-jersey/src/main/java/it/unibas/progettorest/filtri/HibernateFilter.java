package it.unibas.progettorest.filtri;

import it.unibas.progettorest.enums.EStrategiaPersistenza;
import it.unibas.progettorest.modello.Configurazione;
import it.unibas.progettorest.persistenza.hibernate.DAOUtilHibernate;
import jakarta.annotation.Priority;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Provider
@Priority(100)
@Slf4j
public class HibernateFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (!Configurazione.getInstance().getStrategiaDb().equals(EStrategiaPersistenza.DB_HIBERNATE)) {
            return;
        }
        if (requestContext.getMethod().equalsIgnoreCase("OPTIONS")) {
            return;
        }
        log.debug("Avvio la transazione");
        DAOUtilHibernate.beginTransaction();
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if (!Configurazione.getInstance().getStrategiaDb().equals(EStrategiaPersistenza.DB_HIBERNATE)) {
            return;
        }
        if (requestContext.getMethod().equalsIgnoreCase("OPTIONS")) {
            return;
        }
        if (DAOUtilHibernate.getSessionFactory().getCurrentSession().getTransaction().isActive()) {
            log.debug("Effettuo il commit della transazione");
            DAOUtilHibernate.commit();
        }
    }

}
