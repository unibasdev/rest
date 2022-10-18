package it.unibas.mediapesata.modello.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudenteDTO {

    private Long id;
    @NotBlank
    private String nome;
    @NotBlank
    private String cognome;
    @NotNull
    private int matricola;
    @NotNull
    private int annoIscrizione;

}
