package data;

import enumeration.CodeEnum;

import java.util.Random;

public class CodaImbarco {
    private String codiceCoda;
    private CodeEnum classe;
    private Integer tempoStimato;
    private Integer tempoEffettivo;
    private int passeggeri;

    public CodaImbarco(String codiceCoda, String classe, int tempoStimato, int tempoEffettivo) {
        this.codiceCoda = codiceCoda;
        setClasse(classe);
        this.tempoStimato = tempoStimato;
        this.tempoEffettivo = tempoEffettivo;
    }

    public CodaImbarco(CodeEnum classe) {
        this.classe = classe;
        passeggeri = 0;
        tempoStimato = 0;
        tempoEffettivo = null;
    }

    public String getCodiceCoda() {
        return codiceCoda;
    }

    public CodeEnum getClasse() {
        return classe;
    }

    public int getTempoStimato() {
        return tempoStimato;
    }

    public Integer getTempoEffettivo() {
        return tempoEffettivo;
    }

    public int getPasseggeri() {
        return passeggeri;
    }

    public void setClasse(CodeEnum classe) {
        this.classe = classe;
    }

    public void setClasse(String s){
        if (CodeEnum.DIVERSAMENTE_ABILI.toString().equals(s)) classe = CodeEnum.DIVERSAMENTE_ABILI;
        else if (CodeEnum.FAMIGLIE.toString().equals(s)) classe = CodeEnum.FAMIGLIE;
        else if (CodeEnum.BUSINESS.toString().equals(s)) classe = CodeEnum.BUSINESS;
        else if (CodeEnum.PRIORITY.toString().equals(s)) classe = CodeEnum.PRIORITY;
        else if (CodeEnum.ECONOMY.toString().equals(s)) classe = CodeEnum.ECONOMY;
        else classe = CodeEnum.ECONOMY;
    }

    public void stimaTempo() {
        tempoStimato = passeggeri*2;
    } // 2 minuti a passeggero. (forse un po' esagerato)

    public void setTempoEffettivo(int tempoEffettivo) {
        this.tempoEffettivo = tempoEffettivo;
    }

    public void setPasseggeri(int passeggeri) {
        this.passeggeri = passeggeri;
    }
}
