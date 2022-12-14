package it.unibas.mediapesata.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.unibas.mediapesata.modello.dto.EsameDTO;
import it.unibas.mediapesata.service.ServiceEsami;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.time.LocalDate;
import java.util.List;

@Path("/esami")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "bearerAuth")
public class RisorsaEsami {

    @Inject
    private ServiceEsami serviceEsami;

    @GET
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
    public EsameDTO getEsame(
            @NotNull @PathParam("idEsame") long idEsame
    ) {
        return serviceEsami.getEsame(idEsame);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public long creaEsame(@NotNull @Valid EsameDTO esame) {
        return serviceEsami.creaEsame(esame);
    }

    @PUT
    @Path("/{idEsame}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void modificaEsame(
            @NotNull @PathParam("idEsame") long idEsame,
            @NotNull @Valid EsameDTO esame
    ) {
        serviceEsami.modificaEsame(idEsame, esame);
    }

    @DELETE
    @Path("/{idEsame}")
    public void eliminaEsame(
            @NotNull @PathParam("idEsame") long idEsame
    ) {
        serviceEsami.eliminaEsame(idEsame);
    }

}
