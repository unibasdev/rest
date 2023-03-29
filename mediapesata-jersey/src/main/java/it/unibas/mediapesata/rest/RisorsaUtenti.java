package it.unibas.mediapesata.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.unibas.mediapesata.modello.dto.UtenteDTO;
import it.unibas.mediapesata.service.ServiceUtenti;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

@Path("/utenti")
@RequestScoped
public class RisorsaUtenti {

    @Inject
    private ServiceUtenti serviceUtenti;

    @Context
    private SecurityContext securityContext;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public String login(
            @Valid UtenteDTO utente
    ) {
        return serviceUtenti.login(utente);
    }

    @GET
    @Path("/me")
    @Produces(MediaType.APPLICATION_JSON)
    @SecurityRequirement(name = "bearerAuth")
    public UtenteDTO me() {
        return serviceUtenti.me(securityContext.getUserPrincipal().getName());
    }

}
