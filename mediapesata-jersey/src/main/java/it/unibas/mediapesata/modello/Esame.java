package it.unibas.mediapesata.modello;

import java.time.LocalDate;
import static java.time.format.DateTimeFormatter.ofLocalizedDate;
import java.time.format.FormatStyle;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Esame {

    private Long id;
    private String insegnamento;
    private int voto;
    private boolean lode;
    private int crediti;
    private LocalDate dataRegistrazione;
    private Studente studente;

    public Esame() {
    }

    public Esame(String insegnamento, int voto, boolean lode, int crediti, LocalDate dataRegistrazione, Studente studente) {
        this.insegnamento = insegnamento;
        this.voto = voto;
        this.lode = lode;
        this.crediti = crediti;
        this.dataRegistrazione = dataRegistrazione;
        this.studente = studente;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getInsegnamento() {
        return this.insegnamento;
    }

    public int getVoto() {
        return this.voto;
    }

    public int getCrediti() {
        return this.crediti;
    }

    public boolean isLode() {
        return this.lode;
    }

    public void setInsegnamento(String insegnamento) {
        this.insegnamento = insegnamento;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public void setCrediti(int crediti) {
        this.crediti = crediti;
    }

    public void setLode(boolean lode) {
        this.lode = lode;
    }

    public LocalDate getDataRegistrazione() {
        return dataRegistrazione;
    }

    public void setDataRegistrazione(LocalDate dataRegistrazione) {
        this.dataRegistrazione = dataRegistrazione;
    }

    @Transient
    public String getTestoDataRegistrazione() {
        return dataRegistrazione.format(ofLocalizedDate(FormatStyle.FULL));
    }

    @ManyToOne()
    public Studente getStudente() {
        return studente;
    }

    public void setStudente(Studente studente) {
        this.studente = studente;
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
