package it.unibas.mediapesata.filtri;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@PreMatching //In questo modo vengono intercettate anche le richieste che non corrispondono a risorse esistenti
public class LoggerFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private final static Logger logger = LoggerFactory.getLogger(LoggerFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        logger.trace("Ricevuta richiesta \n\t[{}] {}\n\tQuery String: {}\n\tIntestazioni: {}",
                requestContext.getMethod(),
                requestContext.getUriInfo().getPath(),
                requestContext.getUriInfo().getQueryParameters(),
                requestContext.getHeaders());
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        logger.trace("Generata risposta per la richiesta \n\t[{}] {} - Stato: {}",
                requestContext.getMethod(),
                requestContext.getUriInfo().getPath(),
                responseContext.getStatus());
    }

}
