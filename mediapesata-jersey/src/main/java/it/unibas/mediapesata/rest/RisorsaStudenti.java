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
//    @Operation(summary = "Filtra studenti",tags = "Studenti")
//    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    public List<StudenteDTO> getAllStudenti(
            @QueryParam("cognome") String cognome,
            @QueryParam("nome") String nome,
            @QueryParam("annoIscrizione") Integer annoIscrizione
    ) {
        return serviceStudenti.getAllStudenti(cognome, nome, annoIscrizione);
    }

    @GET
    @Path("/{idStudente}")
//    @Operation(summary = "Ottieni i dettagli di un singolo studente",tags = "Studenti")
//    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    public StudenteDTO getStudente(
            @NotNull @PathParam("idStudente") long idStudente
    ) {
        return serviceStudenti.getStudente(idStudente);
    }

    @GET
    @Path("/{idStudente}/esami")
//    @Operation(summary = "Ottieni la lista degli esami di un singolo studente",tags = "Studenti")
//    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    public List<EsameDTO> getEsami(
            @NotNull @PathParam("idStudente") long idStudente
    ) {
        return serviceStudenti.getEsami(idStudente);
    }

    @GET
    @Path("/{idStudente}/mediapesata")
    @Produces(MediaType.TEXT_PLAIN)
//    @Operation(summary = "Calcola la media pesata degli esami di uno studente",tags = "Studenti")
//    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    public double getMediaPesata(
            @NotNull @PathParam("idStudente") long idStudente,
            @DefaultValue("MEDIA_110") @QueryParam("tipoMediaPesata") ETipoMediaPesata tipoMediaPesata
    ) {
        return serviceStudenti.getMediaPesata(idStudente, tipoMediaPesata);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
//    @Operation(summary = "Crea un nuovo studente",tags = "Studenti")
//    @ApiResponse(responseCode = "201", description = "OK")
    public long creaStudente(@NotNull @Valid StudenteDTO studente) {
        return serviceStudenti.creaStudente(studente, securityContext.getUserPrincipal().getName());
    }

    @PUT
    @Path("/{idStudente}")
    @Consumes(MediaType.APPLICATION_JSON)
//    @Operation(summary = "Modifica lo studente", tags = "Studenti")
//    @ApiResponse(responseCode = "201", description = "OK")
    public void modificaStudente(
            @NotNull @PathParam("idStudente") long idStudente,
            @NotNull @Valid StudenteDTO studente
    ) {
        serviceStudenti.modificaStudente(idStudente, studente);
    }

    @DELETE
    @Path("/{idStudente}")
//    @Operation(summary = "Elimina lo studente", tags = "Studenti")
//    @ApiResponse(responseCode = "201", description = "OK")
    public void eliminaStudente(
            @NotNull @PathParam("idStudente") long idStudente
    ) {
        serviceStudenti.eliminaStudente(idStudente);
    }

}
