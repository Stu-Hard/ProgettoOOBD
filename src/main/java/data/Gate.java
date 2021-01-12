package data;

import enumeration.GateStatus;

import java.util.List;
import java.util.Random;

public class Gate {
    private String gateCode;
    private GateStatus status = GateStatus.LIBERO; // stato del gate.
    private Tratta tratta = null; // Tratta che sta gestendo al momento.
    private List<CodaImbarco> codeImbarco = null;

    public Gate(String gateCode, Tratta tratta) {
        this.gateCode = gateCode;
        setTratta(tratta);
    }

    public Gate(String gateCode, Boolean isClosed){
        this.gateCode = gateCode;
        if (isClosed) status = GateStatus.CHIUSO;
    }

    public Gate(String gateCode){
        this.gateCode = gateCode;
    }

    public GateStatus getStatus() {
        return status;
    }

    public Tratta getTratta() {
        return tratta;
    }

    public String getGateCode() {
        return gateCode;
    }

    public void setGateCode(String gateCode) {
        this.gateCode = gateCode;
    }

    public void close() {
        if (status != GateStatus.OCCUPATO) status = GateStatus.CHIUSO;
    }
    public void open(){
        if (status != GateStatus.OCCUPATO) status = GateStatus.CHIUSO;
    }
    public void end(){ // conclude l'imbarco
        if (status == GateStatus.OCCUPATO) {
            status = GateStatus.LIBERO;
            tratta = null;
            codeImbarco.forEach(c -> c.setTempoEffettivo(c.getTempoStimato() + (new Random().nextInt(10))));
            // todo aggiorna nel db le code
        }
    }

    public void setTratta(Tratta tratta) {
        if (status == GateStatus.LIBERO) {
            this.tratta = tratta;
            status = GateStatus.OCCUPATO;
        }
    }
}
