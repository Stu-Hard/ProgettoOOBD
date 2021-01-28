package controllers;

import com.jfoenix.controls.JFXButton;
import data.Dipendente;
import database.dao.DipendentiDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utility.WindowDragger;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerDipendentiDati extends WindowDragger implements Initializable {

    @FXML
    Label mail, nomeCognome, codiceImpiegato, password,ruolo, compagnia;
    @FXML
    JFXButton licenziaBtn;

    public String getCompagnia() {
        return compagnia.toString();
    }

    public void setCompagnia(String compagnia) {
        this.compagnia.setText(compagnia);
    }

    private Dipendente myDipendente;

    public void setMyDipendente(Dipendente myDipendente) {
        this.myDipendente = myDipendente;
    }

    @Override
    public void setOffset(MouseEvent e) {
        super.setOffset(e);
    }
    @Override
    public void moveWindow(MouseEvent e) {
        super.moveWindow(e);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void close(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void licenzia(ActionEvent event) throws IOException {
        //elimina dipendente
        try {
            new DipendentiDao().licenzia(this.myDipendente);
            close(event);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public Label getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail.setText(mail);
    }

    public Label getNomeCognome() {
        return nomeCognome;
    }

    public void setNomeCognome(String nomeCognome) {
        this.nomeCognome.setText(nomeCognome); ;
    }

    public Label getCodiceImpiegato() {
        return codiceImpiegato;
    }

    public void setCodiceImpiegato(String codiceImpiegato) {
        this.codiceImpiegato.setText(codiceImpiegato);
    }

    public Label getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password.setText(password);
    }

    public Label getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo.setText(ruolo);
    }



}
