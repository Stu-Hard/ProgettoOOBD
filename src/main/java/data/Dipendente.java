package data;

import enumeration.DipendentiEnum;

public class Dipendente {
    private String codiceImpiegato;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String ruolo;
    private String compagnia;

    public Dipendente(String codiceImpiegato, String nome, String cognome, String email, String password, String ruolo, String compagnia) {
        this.codiceImpiegato = codiceImpiegato;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
        this.compagnia = compagnia;
    }

    public String getCodiceImpiegato() {
        return codiceImpiegato;
    }

    public void setCodiceImpiegato(String codiceImpiegato) {
        this.codiceImpiegato = codiceImpiegato;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DipendentiEnum getRuolo() {
        if (ruolo.contains("Imbarco")) return DipendentiEnum.ADDETTO_IMBARCO;
        if (ruolo.contains("Amministratore")) return DipendentiEnum.AMMINISTRATORE;
        if (ruolo.contains("Check")) return DipendentiEnum.CHECK_IN;
        if (ruolo.contains("Ticket")) return DipendentiEnum.TICKET_AGENT;
        else return DipendentiEnum.RESPONSABILE_VOLI;
    }


    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public String getCompagnia() {
        return compagnia;
    }

    public void setCompagnia(String compagnia) {
        this.compagnia = compagnia;
    }
}
