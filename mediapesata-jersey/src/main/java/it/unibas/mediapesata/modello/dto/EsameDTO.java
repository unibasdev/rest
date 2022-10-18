package it.unibas.mediapesata.modello.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EsameDTO {

    private Long id;
    private Long studenteId;
    @NotNull
    private String insegnamento;
    @Min(18)
    @Max(30)
    private int voto;
    private boolean lode;
    @Min(1)
    private int crediti;
    @NotNull
    private LocalDate dataRegistrazione;

}
