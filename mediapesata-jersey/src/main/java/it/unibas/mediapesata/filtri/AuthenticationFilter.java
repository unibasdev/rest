package it.unibas.mediapesata.filtri;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.unibas.mediapesata.modello.Utente;
import it.unibas.mediapesata.persistenza.DAOFactory;
import it.unibas.mediapesata.persistenza.IDAOUtente;
import it.unibas.mediapesata.util.JWTUtil;
import jakarta.annotation.Priority;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    @Context
    private ResourceInfo resourceInfo;
    private static final String AUTHENTICATION_SCHEME = "Bearer";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final IDAOUtente daoUtente =DAOFactory.getInstance().getDAOUtente();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if(requestContext.getUriInfo().getPath().startsWith("openapi.") || requestContext.getUriInfo().getPath().startsWith("application.wadl")){
            return;
        }
        if (requestContext.getMethod().equalsIgnoreCase("OPTIONS")) {
            return;
        }
        Class<?> resourceClass = resourceInfo.getResourceClass();
        Method method = resourceInfo.getResourceMethod();
        if (resourceClass.isAnnotationPresent(PermitAll.class) || method.isAnnotationPresent(PermitAll.class)) {
            logger.debug("Accesso ad una risorsa pubblica");
            return;
        }
        String intestazioneAutorizzazione = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (!isTokenAutorizzazionePresente(intestazioneAutorizzazione)) {
            interrompiRichiesta("Token di autorizzazione non presente", requestContext);
            return;
        }
        String token = intestazioneAutorizzazione.substring(AUTHENTICATION_SCHEME.length()).trim();
        try {
            String emailUtente = JWTUtil.verificaToken(token);
            logger.debug("Utente verificato: {}", emailUtente);
            Utente utente = daoUtente.findByEmail(emailUtente);
            if (utente == null) {
                interrompiRichiesta("Utente " + emailUtente + " non trovato", requestContext);
                return;
            }
            SecurityContext originalSecurityContext = requestContext.getSecurityContext();
            requestContext.setSecurityContext(new AppSecurityContext(emailUtente, originalSecurityContext.isSecure(), originalSecurityContext.getAuthenticationScheme()));
        } catch (Exception e) {
            logger.error("Errore durante la validazione del token {}", e.getMessage(), e);
            interrompiRichiesta("Token di autorizzazione non valido", requestContext);
        }
    }

    private boolean isTokenAutorizzazionePresente(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void interrompiRichiesta(String messaggio, ContainerRequestContext requestContext) {
        ObjectNode json = mapper.createObjectNode();
        json.put("error", messaggio);
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .entity(json.toPrettyString())
                        .build());
    }

    class AppSecurityContext implements SecurityContext {

        private String username;
        private boolean secure;
        private String authenticationScheme;

        public AppSecurityContext(String username, boolean secure, String authenticationScheme) {
            this.username = username;
            this.secure = secure;
            this.authenticationScheme = authenticationScheme;
        }

        @Override
        public Principal getUserPrincipal() {
            return new Principal() {
                @Override
                public String getName() {
                    return username;
                }
            };
        }

        @Override
        public boolean isSecure() {
            return secure;
        }

        @Override
        public String getAuthenticationScheme() {
            return authenticationScheme;
        }

        @Override
        public boolean isUserInRole(String role) {
            return true;
        }

    }
}
