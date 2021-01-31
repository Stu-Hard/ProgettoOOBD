package data;

public class Bagaglio {
    private String codiceBagaglio ;
    private double peso;
    private double prezzo;
    private Biglietto biglietto;

    public Bagaglio(String codiceBagaglio, double peso, double prezzo, Biglietto biglietto) {
        this.codiceBagaglio = codiceBagaglio;
        this.peso = peso;
        this.prezzo = prezzo;
        this.biglietto = biglietto;
    }

    @Override
    public String toString() {
        return "Bagaglio{" +
                "codiceBagaglio='" + codiceBagaglio + '\'' +
                ", peso=" + peso +
                ", prezzo=" + prezzo +
                ", biglietto=" + biglietto +
                '}';
    }

    public String getCodiceBagaglio() {
        return codiceBagaglio;
    }

    public void setCodiceBagaglio(String codiceBagaglio) {
        this.codiceBagaglio = codiceBagaglio;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public Biglietto getBiglietto() {
        return biglietto;
    }

    public void setBiglietto(Biglietto biglietto) {
        this.biglietto = biglietto;
    }
}
