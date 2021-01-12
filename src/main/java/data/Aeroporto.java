package data;

public class Aeroporto {
    private String codiceICAO;
    private String nome;
    private String citta;

    public Aeroporto(String codiceICAO, String nome, String citta) {
        this.codiceICAO = codiceICAO;
        this.nome = nome;
        this.citta = citta;
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
