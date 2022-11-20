package it.unibas.progettorest.filtri.eccezioni;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.unibas.progettorest.persistenza.hibernate.DAOUtilHibernate;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.server.ParamException;

@Slf4j
@Provider
//Il filtro e' necessario in quanto ParamException non verrebbe catturato dal GenericExceptionFilter, ma da un altro filtro piu' specifico di Jersey
public class ParamExceptionFilter implements ExceptionMapper<ParamException> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Response toResponse(ParamException ex) {
        try {
            if (DAOUtilHibernate.getSessionFactory().getCurrentSession().isOpen()) {
                log.debug("Effettuo il rollback della transazione");
                DAOUtilHibernate.rollback();
            }
        } catch (Exception e) {
            log.warn("Impossibile effettuare il rollback della transazione {}", e.getMessage(), e);
        }
        log.error("Errore durante la gestione della richiesta {}", ex.getMessage(), ex);
        ObjectNode json = mapper.createObjectNode();
        json.put("error", ex.getParameterName() + ": " + ex.getCause().getMessage());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(json.toPrettyString())
                .build();
    }

}
