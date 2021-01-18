package data;

import enumeration.CodeEnum;

public class Biglietto {
    private Integer codiceBiglietto;
    private double prezzo;
    private int fila;
    private int posto;
    private CodeEnum classe;
    private boolean checkIn, imbarcato;
    private String numeroVolo;
    private String cF;

    public Biglietto() {
    }

    public Biglietto(Integer codiceBiglietto, double prezzo, int fila, int posto, CodeEnum classe, boolean checkIn, boolean imbarcato, String numeroVolo, String cF) {
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

    public Biglietto(double prezzo, int fila, int posto, CodeEnum classe, boolean checkIn, boolean imbarcato, String numeroVolo, String cF) {
        this.prezzo = prezzo;
        this.fila = fila;
        this.posto = posto;
        this.classe = classe;
        this.checkIn = checkIn;
        this.imbarcato = imbarcato;
        this.numeroVolo = numeroVolo;
        this.cF = cF;
    }

    public Integer getCodiceBiglietto() {
        return codiceBiglietto;
    }

    public void setCodiceBiglietto(Integer codiceBiglietto) {
        this.codiceBiglietto = codiceBiglietto;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getPosto() {
        return posto;
    }

    public void setPosto(int posto) {
        this.posto = posto;
    }

    public CodeEnum getClasse() {
        return classe;
    }

    public void setClasse(CodeEnum classe) {
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
