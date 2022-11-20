package it.unibas.progettorest.filtri;

import jakarta.annotation.Priority;
import jakarta.ws.rs.container.*;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@Provider
@Priority(1) //I filtri con i valori più bassi saranno eseguiti per primi
@PreMatching //In questo modo vengono intercettate anche le richieste che non corrispondono a risorse esistenti
public class LoggerFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        log.trace("Ricevuta richiesta \n\t[{}] {}\n\tQuery String: {}\n\tIntestazioni: {}",
                requestContext.getMethod(),
                requestContext.getUriInfo().getPath(),
                requestContext.getUriInfo().getQueryParameters(),
                requestContext.getHeaders());
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        log.trace("Generata risposta per la richiesta \n\t[{}] {} - Stato: {}",
                requestContext.getMethod(),
                requestContext.getUriInfo().getPath(),
                responseContext.getStatus());
    }

}
