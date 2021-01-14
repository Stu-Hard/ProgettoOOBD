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
        codiceCoda = randomStr();
        this.classe = classe;
        passeggeri = 0;
        tempoStimato = 0;
        tempoEffettivo = null;
    }

    public String randomStr() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString.substring(0, 8);
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
