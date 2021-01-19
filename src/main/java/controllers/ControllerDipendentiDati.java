package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utility.WindowDragger;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerDipendentiDati extends WindowDragger implements Initializable {

    @FXML
    Label mail, nomeCognome, codiceImpiegato, password,ruolo;

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
