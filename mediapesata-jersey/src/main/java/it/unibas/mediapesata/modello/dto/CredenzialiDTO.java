package it.unibas.mediapesata.modello.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CredenzialiDTO {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;

}
