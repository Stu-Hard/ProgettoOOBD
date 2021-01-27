package data;

public class Cliente {
    private String codiceFiscale;
    private String nome;
    private String documento;


    public Cliente(String codiceFiscale, String nome, String documento) {
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.documento = documento;
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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
}
