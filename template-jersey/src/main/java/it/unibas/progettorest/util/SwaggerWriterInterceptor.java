package it.unibas.progettorest.util;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Provider
@Slf4j
public class SwaggerWriterInterceptor implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if(requestContext.getMethod().equals("GET")
                && responseContext.getStatus() == 200
                && responseContext.getEntity() != null
                && (responseContext.getEntity() instanceof String)
                && requestContext.getUriInfo().getPath().equals("openapi.json")){
            String openAPIContent = responseContext.getEntity().toString();
            String backendBaseURL = requestContext.getUriInfo().getBaseUri().toString();
            if(backendBaseURL.contains("/api/")){
                backendBaseURL = backendBaseURL.substring(0, backendBaseURL.indexOf("/api/"));
            }
            openAPIContent = openAPIContent.replace("BACKEND_BASE_URL", backendBaseURL);
            responseContext.setEntity(openAPIContent);
        }
    }
}
