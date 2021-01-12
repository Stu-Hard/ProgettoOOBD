package data;

public class Cliente {
    private String codiceFiscale;
    private String nome;
    private String cognome;
    private int eta;
    private String documento;
    private String passaporto = null;

    public Cliente(String codiceFiscale, String nome, String cognome, int eta, String documento, String passaporto) {
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.eta = eta;
        this.documento = documento;
        this.passaporto = passaporto;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public void setPassaporto(String passaporto) {
        this.passaporto = passaporto;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public int getEta() {
        return eta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getPassaporto() {
        return passaporto;
    }
}
