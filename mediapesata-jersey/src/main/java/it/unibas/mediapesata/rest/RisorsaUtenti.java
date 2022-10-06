package it.unibas.mediapesata.rest;

import it.unibas.mediapesata.modello.dto.UtenteDTO;
import it.unibas.mediapesata.service.ServiceLogin;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("utenti")
@PermitAll
public class RisorsaUtenti {

    private ServiceLogin serviceLogin = new ServiceLogin();

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    //    @Operation(summary = "Effettua il login sull'applicazione",tags = "Login")
    //    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    public String login(
            @Valid UtenteDTO utente
    ) {
        String token = serviceLogin.login(utente);
        return token;
    }

}
