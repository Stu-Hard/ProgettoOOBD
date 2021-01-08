package data;

import java.util.Date;

public class Tratta {
    private String numeroVolo;
    private Date dataPartenza;
    private Date dataArrivo = null;
    private Date oraPartenza;
    private Date oraArrivo = null;
    private Date partenzaEffettiva = null;
    private Date arrivoEffettivo = null;
    private String compagnia; // qui dovrebbe essere Compagnie compagnia;
    private String gate; // come sopra da cambiare con Gate gate;
    // private AereoportoPartenza aereoporto...
    private String aereoportoPartenza;
    private String aereoportoArrivo;

    public Tratta(String numeroVolo, Date dataPartenza, Date dataArrivo,
                  Date oraPartenza, Date oraArrivo, Date partenzaEffettiva,
                  Date arrivoEffettivo, String compagnia, String gate,
                  String aereoportoPartenza, String getAereoportoArrivo) {
        this.numeroVolo = numeroVolo;
        this.dataPartenza = dataPartenza;
        this.dataArrivo = dataArrivo;
        this.oraPartenza = oraPartenza;
        this.oraArrivo = oraArrivo;
        this.partenzaEffettiva = partenzaEffettiva;
        this.arrivoEffettivo = arrivoEffettivo;
        this.compagnia = compagnia;
        this.gate = gate;
        this.aereoportoPartenza = aereoportoPartenza;
        this.aereoportoArrivo = getAereoportoArrivo;
    }

    public Tratta(String numeroVolo, Date dataPartenza, Date oraPartenza,
                  String compagnia, String gate, String aereoportoPartenza, String getAereoportoArrivo) {
        this.numeroVolo = numeroVolo;
        this.dataPartenza = dataPartenza;
        this.oraPartenza = oraPartenza;
        this.compagnia = compagnia;
        this.gate = gate;
        this.aereoportoPartenza = aereoportoPartenza;
        this.aereoportoArrivo = getAereoportoArrivo;
    }

    public void setNumeroVolo(String numeroVolo) {
        this.numeroVolo = numeroVolo;
    }

    public void setDataPartenza(Date dataPartenza) {
        this.dataPartenza = dataPartenza;
    }

    public void setDataArrivo(Date dataArrivo) {
        this.dataArrivo = dataArrivo;
    }

    public void setOraPartenza(Date oraPartenza) {
        this.oraPartenza = oraPartenza;
    }

    public void setOraArrivo(Date oraArrivo) {
        this.oraArrivo = oraArrivo;
    }

    public void setPartenzaEffettiva(Date partenzaEffettiva) {
        this.partenzaEffettiva = partenzaEffettiva;
    }

    public void setArrivoEffettivo(Date arrivoEffettivo) {
        this.arrivoEffettivo = arrivoEffettivo;
    }

    public void setCompagnia(String compagnia) {
        this.compagnia = compagnia;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public void setAereoportoPartenza(String aereoportoPartenza) {
        this.aereoportoPartenza = aereoportoPartenza;
    }

    public void setAereoportoArrivo(String aereoportoArrivo) {
        this.aereoportoArrivo = aereoportoArrivo;
    }

    public String getNumeroVolo() {
        return numeroVolo;
    }

    public Date getDataPartenza() {
        return dataPartenza;
    }

    public Date getDataArrivo() {
        return dataArrivo;
    }

    public Date getOraPartenza() {
        return oraPartenza;
    }

    public Date getOraArrivo() {
        return oraArrivo;
    }

    public Date getPartenzaEffettiva() {
        return partenzaEffettiva;
    }

    public Date getArrivoEffettivo() {
        return arrivoEffettivo;
    }

    public String getCompagnia() {
        return compagnia;
    }

    public String getGate() {
        return gate;
    }

    public String getAereoportoPartenza() {
        return aereoportoPartenza;
    }

    public String getAereoportoArrivo() {
        return aereoportoArrivo;
    }
}
