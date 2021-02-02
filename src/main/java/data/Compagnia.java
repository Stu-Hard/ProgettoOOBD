package data;

import java.util.Objects;

public class Compagnia {
    private String nome;
    private String sigla;
    private String nazione;
    private float prezzoBagagli;
    private float pesoMassimo;
    private int numeroTratte = 0;

    public Compagnia(String nome, String sigla, String nazione, float prezzoBagagli, float pesoMassimo) {
        this.nome = nome;
        this.sigla = sigla;
        this.nazione = nazione;
        this.prezzoBagagli = prezzoBagagli;
        this.pesoMassimo = pesoMassimo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compagnia compagnia = (Compagnia) o;
        return Objects.equals(nome, compagnia.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, sigla, nazione, prezzoBagagli, pesoMassimo, numeroTratte);
    }

    @Override
    public String toString() {
        return nome + "(" + sigla + ")";
    }

    public String getNome() {
        return nome;
    }

    public String getSigla() {
        return sigla;
    }

    public String getNazione() {
        return nazione;
    }

    public Float getPrezzoBagagli() {
        return prezzoBagagli;
    }

    public int computeNumeroTratte() { // calcola quante tratte ha fatto una determinata compagnia (da fare con il db)
        return numeroTratte;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public void setNazione(String nazione) {
        this.nazione = nazione;
    }

    public void setPrezzoBagagli(Float prezzoBagagli) {
        this.prezzoBagagli = prezzoBagagli;
    }

    public Float getPesoMassimo() {
        return pesoMassimo;
    }

    public void setPesoMassimo(Float pesoMassimo) {
        this.pesoMassimo = pesoMassimo;
    }
}
