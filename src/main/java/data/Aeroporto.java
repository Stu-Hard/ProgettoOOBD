package data;

import java.util.Objects;

public class Aeroporto {
    private String codiceICAO;
    private String nome;
    private String citta;

    public Aeroporto(String codiceICAO, String nome, String citta) {
        this.codiceICAO = codiceICAO;
        this.nome = nome;
        this.citta = citta;
    }

    @Override
    public String toString() {
        return citta + "-" + nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aeroporto aeroporto = (Aeroporto) o;
        return Objects.equals(codiceICAO, aeroporto.codiceICAO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codiceICAO, nome, citta);
    }

    public String getCodiceICAO() {
        return codiceICAO;
    }

    public String getNome() {
        return nome;
    }

    public String getCitta() {
        return citta;
    }

    public void setCodiceICAO(String codiceICAO) {
        this.codiceICAO = codiceICAO;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }
}
