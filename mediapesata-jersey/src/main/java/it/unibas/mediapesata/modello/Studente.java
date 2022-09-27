package it.unibas.mediapesata.modello;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Studente {

    private Long id;
    private String nome;
    private String cognome;
    private int matricola;
    private int annoIscrizione;
    private List<Esame> listaEsami = new ArrayList<Esame>();

    public Studente() {
    }

    public Studente(String cognome, String nome, int matricola, int annoIscrizione) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
        this.annoIscrizione = annoIscrizione;
    }
    

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Column(unique = true)
    public int getMatricola() {
        return this.matricola;
    }

    public void setMatricola(int matricola) {
        this.matricola = matricola;
    }

    public void addEsame(Esame esame) {
        this.listaEsami.add(esame);
    }

    public int getAnnoIscrizione() {
        return annoIscrizione;
    }

    public void setAnnoIscrizione(int annoIscrizione) {
        this.annoIscrizione = annoIscrizione;
    }

    @OneToMany(mappedBy = "studente", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Esame> getListaEsami() {
        return this.listaEsami;
    }

    public void setListaEsami(List<Esame> listaEsami) {
        this.listaEsami = listaEsami;
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

    @Override
    public String toString() {
        return "[" + id + "] Cognome: " + getCognome() + " - Nome: " + getNome()
                + " - Matricola: " + getMatricola() + " - " + listaEsami.size() + " esami";
    }

}
