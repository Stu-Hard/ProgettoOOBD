package data;

import enumeration.CodeEnum;

public class Biglietto {
    private String codiceBiglietto;
    private double prezzo;
    private String fila;
    private int posto;
    private String classe;
    private boolean checkIn, imbarcato;
    private String numeroVolo;
    private String cF;

    public Biglietto() {
    }

    public Biglietto(String codiceBiglietto, double prezzo, String fila, int posto, String classe, boolean checkIn, boolean imbarcato, String numeroVolo, String cF) {
        this.codiceBiglietto = codiceBiglietto;
        this.prezzo = prezzo;
        this.fila = fila;
        this.posto = posto;
        this.classe = classe;
        this.checkIn = checkIn;
        this.imbarcato = imbarcato;
        this.numeroVolo = numeroVolo;
        this.cF = cF;
    }

    public String getCodiceBiglietto() {
        return codiceBiglietto;
    }

    public void setCodiceBiglietto(String codiceBiglietto) {
        this.codiceBiglietto = codiceBiglietto;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public int getPosto() {
        return posto;
    }

    public void setPosto(int posto) {
        this.posto = posto;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public boolean isCheckIn() {
        return checkIn;
    }

    public void setCheckIn(boolean checkIn) {
        this.checkIn = checkIn;
    }

    public boolean isImbarcato() {
        return imbarcato;
    }

    public void setImbarcato(boolean imbarcato) {
        this.imbarcato = imbarcato;
    }

    public String getNumeroVolo() {
        return numeroVolo;
    }

    public void setNumeroVolo(String numeroVolo) {
        this.numeroVolo = numeroVolo;
    }

    public String getcF() {
        return cF;
    }

    public void setcF(String cF) {
        this.cF = cF;
    }
}
