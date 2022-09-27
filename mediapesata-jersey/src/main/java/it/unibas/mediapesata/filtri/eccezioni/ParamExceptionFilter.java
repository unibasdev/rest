package it.unibas.mediapesata.filtri.eccezioni;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.unibas.mediapesata.persistenza.hibernate.DAOUtilHibernate;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.jersey.server.ParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
//Il filtro e' necessario in quanto ParamException non verrebbe catturato dal GenericExceptionFilter, ma da un altro filtro piu' specifico di Jersey
public class ParamExceptionFilter implements ExceptionMapper<ParamException> {

    private final static Logger logger = LoggerFactory.getLogger(ParamExceptionFilter.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Response toResponse(ParamException ex) {
        try {
            if (DAOUtilHibernate.getSessionFactory().getCurrentSession().isOpen()) {
                logger.debug("Effettuo il rollback della transazione");
                DAOUtilHibernate.rollback();
            }
        } catch (Exception e) {
            logger.warn("Impossibile effettuare il rollback della transazione {}", e.getMessage(), e);
        }
        logger.error("Errore durante la gestione della richiesta {}", ex.getMessage(), ex);
        ObjectNode json = mapper.createObjectNode();
        json.put("error", ex.getParameterName() + ": " + ex.getCause().getMessage());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(json.toPrettyString())
                .build();
    }

}
