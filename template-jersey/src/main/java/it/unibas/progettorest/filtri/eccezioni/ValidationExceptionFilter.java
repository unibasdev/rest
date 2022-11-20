package it.unibas.progettorest.filtri.eccezioni;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.unibas.progettorest.enums.EStrategiaPersistenza;
import it.unibas.progettorest.modello.Configurazione;
import it.unibas.progettorest.persistenza.hibernate.DAOUtilHibernate;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Provider
public class ValidationExceptionFilter implements ExceptionMapper<ValidationException> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Response toResponse(ValidationException ex) {
        try {
            if (Configurazione.getInstance().getStrategiaDb().equals(EStrategiaPersistenza.DB_HIBERNATE) &&
                    DAOUtilHibernate.getSessionFactory().getCurrentSession().isOpen()) {
                log.debug("Effettuo il rollback della transazione");
                DAOUtilHibernate.rollback();
            }
        } catch (Exception e) {
            log.warn("Impossibile effettuare il rollback della transazione {}", e.getMessage(), e);
        }
        log.error("Errore durante la gestione della richiesta {}", ex.getMessage(), ex);
        ObjectNode json = mapper.createObjectNode();
        json.put("error", ex.getMessage());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(json.toPrettyString())
                .build();
    }

}
