package it.unibas.progettorest.filtri;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Provider
@Slf4j
public class CorsFilter implements ContainerResponseFilter {

    private static final List<String> ALLOWED_ORIGINS = Arrays.asList("http://localhost:4200",
            "null");

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {
        log.trace("Eseguo CORS filter");
        String origin = requestContext.getHeaderString("Origin");
        log.debug("Ricevuta una richiesta dall'origin {}", origin);
        if (origin == null) {
            return;
        }
        if (ALLOWED_ORIGINS.contains(origin)) {
            log.debug("Accetto l'origin {}", origin);
            responseContext.getHeaders().add("Access-Control-Allow-Origin", origin);
            responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
            responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
            responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        }
    }
}
