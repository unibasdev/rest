package it.unibas.mediapesata.rest;

import it.unibas.mediapesata.modello.dto.UtenteDTO;
import it.unibas.mediapesata.service.ServiceUtenti;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/utenti")
@RequestScoped
@PermitAll
public class RisorsaUtenti {

    @Inject
    private ServiceUtenti serviceUtenti;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String login(
            @Valid UtenteDTO utente
    ) {
        return serviceUtenti.login(utente);
    }

}
