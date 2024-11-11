package it.unibas.mediapesata.modello.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UtenteDTO {

    private String email;
    private String nome;
    private String cognome;

}
