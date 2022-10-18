package it.unibas.mediapesata.modello;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.FormatStyle;

import static java.time.format.DateTimeFormatter.ofLocalizedDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Esame {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String insegnamento;
    private int voto;
    private boolean lode;
    private int crediti;
    private LocalDate dataRegistrazione;
    @ManyToOne()
    private Studente studente;

    public Esame(String insegnamento, int voto, boolean lode, int crediti, LocalDate dataRegistrazione, Studente studente) {
        this.insegnamento = insegnamento;
        this.voto = voto;
        this.lode = lode;
        this.crediti = crediti;
        this.dataRegistrazione = dataRegistrazione;
        this.studente = studente;
    }

    @Transient
    public String getTestoDataRegistrazione() {
        return dataRegistrazione.format(ofLocalizedDate(FormatStyle.FULL));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[" + id + "] Esame di ").append(this.insegnamento).append(" (").append(this.crediti).append(" CFU) - voto: ").append(this.voto);
        if (this.lode) {
            sb.append(" e lode");
        }
        sb.append(" registrato il ").append(getTestoDataRegistrazione());
        return sb.toString();
    }


}
