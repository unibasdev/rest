package it.unibas.mediapesata.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.unibas.mediapesata.enums.ETipoMediaPesata;
import it.unibas.mediapesata.modello.dto.EsameDTO;
import it.unibas.mediapesata.modello.dto.StudenteDTO;
import it.unibas.mediapesata.service.ServiceStudenti;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@Path("/studenti")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "bearerAuth")
public class RisorsaStudenti {

    @Inject
    private ServiceStudenti serviceStudenti;

    @Context
    private SecurityContext securityContext;

    @GET
    public List<StudenteDTO> getAllStudenti(
            @QueryParam("cognome") String cognome,
            @QueryParam("nome") String nome,
            @QueryParam("annoIscrizione") @Positive Integer annoIscrizione
    ) {
        return serviceStudenti.getAllStudenti(cognome, nome, annoIscrizione);
    }

    @GET
    @Path("/{idStudente}")
    public StudenteDTO getStudente(
            @NotNull @PathParam("idStudente") long idStudente
    ) {
        return serviceStudenti.getStudente(idStudente);
    }

    @GET
    @Path("/{idStudente}/esami")
    public List<EsameDTO> getEsami(
            @NotNull @PathParam("idStudente") long idStudente
    ) {
        return serviceStudenti.getEsami(idStudente);
    }

    @GET
    @Path("/{idStudente}/mediapesata")
    @Produces(MediaType.TEXT_PLAIN)
    public double getMediaPesata(
            @NotNull @PathParam("idStudente") long idStudente,
            @DefaultValue("MEDIA_110") @QueryParam("tipoMediaPesata") ETipoMediaPesata tipoMediaPesata
    ) {
        return serviceStudenti.getMediaPesata(idStudente, tipoMediaPesata);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public long creaStudente(@NotNull @Valid StudenteDTO studente) {
        return serviceStudenti.creaStudente(studente, securityContext.getUserPrincipal().getName());
    }

    @PUT
    @Path("/{idStudente}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void modificaStudente(
            @NotNull @PathParam("idStudente") long idStudente,
            @NotNull @Valid StudenteDTO studente
    ) {
        serviceStudenti.modificaStudente(idStudente, studente);
    }

    @DELETE
    @Path("/{idStudente}")
    public void eliminaStudente(
            @NotNull @PathParam("idStudente") long idStudente
    ) {
        serviceStudenti.eliminaStudente(idStudente);
    }

}
