package it.unibas.mediapesata.filtri;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class CorsFilter implements ContainerResponseFilter {

    private final static Logger logger = LoggerFactory.getLogger(CorsFilter.class);

    private static List<String> ALLOWED_ORIGINS = Arrays.asList(new String[]{
        "http://localhost:4200",
        "null",});

    @Override
    public void filter(ContainerRequestContext requestContext,
            ContainerResponseContext responseContext) throws IOException {
        logger.trace("Eseguo CORS filter");
        String origin = requestContext.getHeaderString("Origin");
        logger.debug("Ricevuta una richiesta dall'origin {}", origin);
        if (origin == null) {
            return;
        }
        if (ALLOWED_ORIGINS.contains(origin)) {
            logger.debug("Accetto l'origin {}", origin);
            responseContext.getHeaders().add("Access-Control-Allow-Origin", origin);
            responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
            responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
            responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        }
    }
}
