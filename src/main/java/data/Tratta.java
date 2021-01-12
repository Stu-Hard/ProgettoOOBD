package data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Tratta {
    private String numeroVolo;
    private LocalDate dataPartenza;
    private LocalDate dataArrivo = null;
    private LocalTime oraPartenza;
    private LocalTime oraArrivo = null;
    private int ritardo = 0;
    private String compagnia; // qui dovrebbe essere Compagnie compagnia;
    private String gate = null; // come sopra da cambiare con Gate gate;
    // private AereoportoPartenza aereoporto...
    private String aereoportoPartenza;
    private String aereoportoArrivo;
    private String aereo = null;
    private int passeggeri = 0;
    public DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/uuuu");
    public DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");


    // quando una tratta viene creata non si sa a prescindere il ritardo, il gate ecc...
    public Tratta(String numeroVolo, LocalDate dataPartenza, LocalTime oraPartenza,
                  String compagnia, String gate, String aereoportoPartenza, String getAereoportoArrivo) {
        this.numeroVolo = numeroVolo;
        this.dataPartenza = dataPartenza;
        this.oraPartenza = oraPartenza;
        this.compagnia = compagnia;
        this.gate = gate;
        this.aereoportoPartenza = aereoportoPartenza;
        this.aereoportoArrivo = getAereoportoArrivo;
    }

    public String getDataPartenzaFormatted(){
        return dateFormat.format(dataPartenza);
    }
    public String getDataArrivoFormatted(){
        return dateFormat.format(dataArrivo);
    }
    public String getOraPartenzaFormatted(){
        return timeFormat.format(oraPartenza);
    }
    public String getOraArrivoFormatted(){
        return timeFormat.format(oraArrivo);
    }

    public String getAereo() {
        return aereo;
    }

    public void setAereo(String aereo) {
        this.aereo = aereo;
    }

    public int getRitardo() {
        return ritardo;
    }

    public void setRitardo(int ritardo) {
        this.ritardo = ritardo;
    }

    public void setNumeroVolo(String numeroVolo) {
        this.numeroVolo = numeroVolo;
    }

    public void setDataPartenza(LocalDate dataPartenza) {
        this.dataPartenza = dataPartenza;
    }

    public void setDataArrivo(LocalDate dataArrivo) {
        this.dataArrivo = dataArrivo;
    }

    public void setOraPartenza(LocalTime oraPartenza) {
        this.oraPartenza = oraPartenza;
    }

    public void setOraArrivo(LocalTime oraArrivo) {
        this.oraArrivo = oraArrivo;
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

    public LocalDate getDataPartenza() {
        return dataPartenza;
    }

    public LocalDate getDataArrivo() {
        return dataArrivo;
    }

    public LocalTime getOraPartenza() {
        return oraPartenza;
    }

    public LocalTime getOraArrivo() {
        return oraArrivo;
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

    public int getPasseggeri() {
        return passeggeri;
    }
}
