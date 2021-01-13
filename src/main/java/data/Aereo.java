package data;

public class Aereo {
    private String codice;
    private Compagnia compagnia;
    private int file;
    private int colonne;

    public Aereo(String codice, Compagnia compagnia, int file, int colonne) {
        this.codice = codice;
        this.compagnia = compagnia;
        this.file = file;
        this.colonne = colonne;
    }

    @Override
    public String toString() {
        return  codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public void setCompagnia(Compagnia compagnia) {
        this.compagnia = compagnia;
    }

    public void setFile(int file) {
        this.file = file;
    }

    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    public String getCodice() {
        return codice;
    }

    public Compagnia getCompagnia() {
        return compagnia;
    }

    public int getFile() {
        return file;
    }

    public int getColonne() {
        return colonne;
    }
}
