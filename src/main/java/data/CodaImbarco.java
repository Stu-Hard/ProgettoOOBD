package data;

import enumeration.CodeEnum;

public class CodaImbarco {
    private CodeEnum classe;
    private int tempoStimato;
    private int tempoEffettivo;
    private int passeggeri;

    public CodaImbarco(CodeEnum classe, int tempoStimato, int tempoEffettivo) {
        classe = classe;
        tempoStimato = tempoStimato;
        tempoEffettivo = tempoEffettivo;
    }

    public CodaImbarco(CodeEnum classe) {
        classe = classe;
        stimaTempo();
        tempoEffettivo = tempoStimato;
    }

    public CodeEnum getClasse() {
        return classe;
    }

    public int getTempoStimato() {
        return tempoStimato;
    }

    public int getTempoEffettivo() {
        return tempoEffettivo;
    }

    public int getPasseggeri() {
        return passeggeri;
    }

    public void setClasse(CodeEnum classe) {
        classe = classe;
    }

    public void stimaTempo() {
        tempoStimato = passeggeri*2;
    } // 2 minuti a passeggero. (forse un po' esagerato)

    public void setTempoEffettivo(int tempoEffettivo) {
        tempoEffettivo = tempoEffettivo;
    }

    public void setPasseggeri(int passeggeri) {
        this.passeggeri = passeggeri;
    }
}
