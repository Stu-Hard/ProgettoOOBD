package data;

import enumeration.CodeEnum;
import enumeration.GateStatus;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class Gate {
    private String gateCode;
    private GateStatus status = GateStatus.LIBERO; // stato del gate.
    private Tratta tratta = null; // Tratta che sta gestendo al momento.
    private List<CodaImbarco> codeImbarco = new LinkedList();

    public Gate(String gateCode, Tratta tratta, List<CodaImbarco> codeImbarco, Boolean isClosed) {
        this.gateCode = gateCode;
        if (isClosed) status = GateStatus.CHIUSO;
        setTratta(tratta, codeImbarco);
    }

    public Gate(String gateCode, Boolean isClosed){
        this.gateCode = gateCode;
        if (isClosed) status = GateStatus.CHIUSO;
    }

    @Override
    public String toString() {
        return gateCode;
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
        if (status != GateStatus.OCCUPATO) status = GateStatus.LIBERO;
    }
    public Pair<Tratta, List<CodaImbarco>> end(){ // conclude l'imbarco
        Tratta t = null;
        List<CodaImbarco> code = null;
        if (status == GateStatus.OCCUPATO) {
            status = GateStatus.LIBERO;
            tratta.concludi();
            t = tratta;
            code = codeImbarco;
            codeImbarco.forEach(c -> c.setTempoEffettivo(c.getTempoStimato() + tratta.getRitardo())); // setta il tempo effettivo. da cambiare todo

            tratta = null;
            codeImbarco.clear();
        }
        return new Pair(t, code);
    }

    public void setTratta(Tratta tratta, List<CodaImbarco> codeImbarco) {
        if (status == GateStatus.LIBERO && tratta != null) {
            tratta.setGate(gateCode);
            this.tratta = tratta;
            this.codeImbarco = codeImbarco;
            status = GateStatus.OCCUPATO;
        }
    }

    public void addCoda(CodaImbarco codaImbarco) {
        codeImbarco.add(codaImbarco);
    }

    public void setCodeImbarco(List<CodaImbarco> codeImbarco) {
        this.codeImbarco = codeImbarco;
    }

    public List<CodaImbarco> getCodeImbarco() {
        return codeImbarco;
    }

    public CodeEnum[] getCodeArray() {
        //CodeEnum[] codeEnums = new CodeEnum[codeImbarco.size()];
        return codeImbarco.stream().map(c -> c.getClasse()).toArray(CodeEnum[]::new);
    }
}
