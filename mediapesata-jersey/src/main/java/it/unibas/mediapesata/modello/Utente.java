package it.unibas.mediapesata.modello;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;

    public Utente(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
