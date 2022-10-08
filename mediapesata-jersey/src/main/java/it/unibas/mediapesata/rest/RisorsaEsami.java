package it.unibas.mediapesata.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.unibas.mediapesata.modello.dto.EsameDTO;
import it.unibas.mediapesata.service.ServiceEsami;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.List;

@Path("/esami")
@Produces(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "bearerAuth")
public class RisorsaEsami {

    @Inject
    private ServiceEsami serviceEsami;

    @GET
    //    @Operation(summary = "Filtra esami",tags = "Esami")
    //    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    public List<EsameDTO> getAllEsami(
            @QueryParam("insegnamento") String insegnamento,
            @Min(18) @Max(30) @QueryParam("voto") Integer voto,
            @QueryParam("lode") Boolean lode,
            @Min(1) @QueryParam("crediti") Integer crediti,
            @QueryParam("dataRegistrazione") LocalDate dataRegistrazione
    ) {
        return serviceEsami.getAllEsami(insegnamento, voto, lode, crediti, dataRegistrazione);
    }

    @GET
    @Path("/{idEsame}")
    //    @Operation(summary = "Ottieni i dettagli di un singolo esame",tags = "Esami")
    //    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    public EsameDTO getEsame(
            @NotNull @PathParam("idEsame") long idEsame
    ) {
        return serviceEsami.getEsame(idEsame);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    //    @Operation(summary = "Crea un nuovo esame",tags = "Esami")
    //    @ApiResponse(responseCode = "201", description = "OK")
    public long creaEsame(@NotNull @Valid EsameDTO esame) {
        return serviceEsami.creaEsame(esame);
    }

    @PUT
    @Path("/{idEsame}")
    @Consumes(MediaType.APPLICATION_JSON)
    //    @Operation(summary = "Modifica l'esame",tags = "Esami")
    //    @ApiResponse(responseCode = "201", description = "OK")
    public void modificaEsame(
            @NotNull @PathParam("idEsame") long idEsame,
            @NotNull @Valid EsameDTO esame
    ) {
        serviceEsami.modificaEsame(idEsame, esame);
    }

    @DELETE
    @Path("/{idEsame}")
    //    @Operation(summary = "Elimina l'esame",tags = "Esami")
    //    @ApiResponse(responseCode = "201", description = "OK")
    public void eliminaEsame(
            @NotNull @PathParam("idEsame") long idEsame
    ) {
        serviceEsami.eliminaEsame(idEsame);
    }

}
