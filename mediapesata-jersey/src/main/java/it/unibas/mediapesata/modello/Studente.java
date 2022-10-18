package it.unibas.mediapesata.modello;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Studente {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String nome;
    private String cognome;
    @Column(unique = true)
    private int matricola;
    private int annoIscrizione;
    @OneToMany(mappedBy = "studente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Esame> listaEsami = new ArrayList<Esame>();

    public Studente(String cognome, String nome, int matricola, int annoIscrizione) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
        this.annoIscrizione = annoIscrizione;
    }

    @Transient
    public double getMedia30mi() {
        if (this.listaEsami.isEmpty()) {
            throw new IllegalArgumentException("Non e' possibile calcolare la media di 0 esami");
        }
        int sommaVotiPesati = 0;
        int sommaCrediti = 0;
        for (Esame esame : this.listaEsami) {
            sommaVotiPesati += esame.getVoto() * esame.getCrediti();
            sommaCrediti += esame.getCrediti();
        }
        return ((double) sommaVotiPesati) / sommaCrediti;
    }

    @Transient
    public double getMedia110mi() {
        return getMedia30mi() / 30 * 110;
    }

    public void addEsame(Esame esame) {
        this.listaEsami.add(esame);
    }

    @Override
    public String toString() {
        return "[" + id + "] Cognome: " + getCognome() + " - Nome: " + getNome()
                + " - Matricola: " + getMatricola() + " - " + listaEsami.size() + " esami";
    }

}
