package data;

public class Cliente {
    private String codiceFiscale;
    private String nome;
    private String cognome;
    private String carta;
    private String passaporto = null;
    private String email;
    private int eta;


    public Cliente(String codiceFiscale, String nome, String cognome, String carta, String passaporto, String email, int eta) {
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.carta = carta;
        this.passaporto = passaporto;
        this.email = email;
        this.eta = eta;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCarta() {
        return carta;
    }

    public void setCarta(String carta) {
        this.carta = carta;
    }

    public String getPassaporto() {
        return passaporto;
    }

    public void setPassaporto(String passaporto) {
        this.passaporto = passaporto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEta() {
        return eta;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }
}
