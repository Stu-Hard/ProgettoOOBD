package controllers;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;

import data.Biglietto;
import data.Cliente;
import data.Tratta;

import database.dao.BigliettoDao;

import enumeration.CodeEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;

import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;

import utility.Validators;
import utility.WindowDragger;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerAcquisto extends WindowDragger implements Initializable {
    private Tratta tratta;
    @FXML
    JFXTextField nome, cognome, riconoscimento, eta, cf, email, telefono;
    @FXML
    JFXComboBox<CodeEnum> classe;
    @FXML
    JFXComboBox<String> documento;
    @FXML
    JFXButton pagaBtn;

    double prezzo = 19.99;

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
    }

    public Tratta getTratta(){
        return this.tratta;
    }
    @FXML
    public void close(ActionEvent e){
        ((JFXButton) e.getSource()).getScene().getWindow().hide();
    }

    @FXML
    public void buy(ActionEvent e) throws  IOException {


        email.validate();
        riconoscimento.validate();
        eta.validate();
        nome.validate();
        cognome.validate();
        cf.validate();
        if (cf.validate() && riconoscimento.validate() && eta.validate() && nome.validate() && cognome.validate() && classe.getValue() != null) {
            Cliente cliente = new Cliente(getCf(), getNome() + "-" + getCognome(), getRiconoscimento());

            //todo fila e posto -> trigger
            Biglietto biglietto = new Biglietto(prezzo, 1, classe.getValue(), false, false, tratta.getNumeroVolo(), cliente);
            BigliettoDao bDao = new BigliettoDao();
            try {
                bDao.insert(biglietto);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
            close(e);



                //messaggio di conferma
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AcquistoBigliettoConfirm.fxml"));
                Parent root = fxmlLoader.load();

                JFXAlert<Void> alert = new JFXAlert(nome.getScene().getWindow());
                JFXDialogLayout layout = new JFXDialogLayout();
                layout.setHeading();
                alert.setOverlayClose(true);
                alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                alert.setContent(root);
                alert.initModality(Modality.NONE);
                alert.showAndWait();
                //messaggio di conferma


               /* MESSAGGIO DI ERRORE, NON VA
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AcquistoBigliettoError.fxml"));
                Parent root = fxmlLoader.load();

                JFXAlert<Void> alert = new JFXAlert(nome.getScene().getWindow());
                JFXDialogLayout layout = new JFXDialogLayout();
                layout.setHeading();
                alert.setOverlayClose(true);
                alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                alert.setContent(root);
                alert.initModality(Modality.NONE);
                alert.showAndWait();*/
            }
        }



    public String getNome() {
        return nome.getText();
    }

    public void setNome(String nome) {
        this.nome.setText(nome);
    }

    public String getCognome() {
        return cognome.getText();
    }

    public void setCognome(String cognome) {
        this.cognome.setText(cognome);
    }

    public String getRiconoscimento() {
        return riconoscimento.getText();
    }

    public void setRiconoscimento(String riconoscimento) {
        this.riconoscimento.setText(riconoscimento);
    }

    public int getEta() {
        Integer i = Integer.parseInt(eta.getText());
        return i;
    }

    public void setEta(String eta) {
        this.eta.setText(eta);
    }

    public String getCf() {
        return cf.getText();
    }

    public void setCf(String cf) {
        this.cf.setText(cf);
    }

    public String getEmail() {
        return email.getText();
    }

    public void setEmail(JFXTextField email) {
        this.email = email;
    }

    public JFXTextField getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono.setText(telefono);
    }

    public JFXComboBox getClasse() {
        return classe;
    }

    public void setClasse(JFXComboBox classe) {
        this.classe = classe;
    }

    public JFXComboBox getDocumento() {
        return documento;
    }

    public void setDocumento(JFXComboBox documento) {
        this.documento = documento;
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
        cf.getValidators().add(new Validators().createRequiredValidator("Non puo' essere vuota"));
        eta.getValidators().add(new Validators().createRequiredValidator(""));
        email.setValidators(new Validators().createEmailValidator("Email invalida"));
        nome.getValidators().add(new Validators().createRequiredValidator("Inserire nome"));
        cognome.getValidators().add(new Validators().createRequiredValidator("Inserire cognome"));
        riconoscimento.getValidators().add(new Validators().createRequiredValidator("Inserire n. documento"));

        classe.getItems().add(CodeEnum.ECONOMY);
        classe.getItems().add(CodeEnum.BUSINESS);
        classe.getItems().add(CodeEnum.FAMIGLIE);
        classe.getItems().add(CodeEnum.PRIORITY);
        classe.getItems().add(CodeEnum.DIVERSAMENTE_ABILI);

        documento.getItems().add("Patente");
        documento.getItems().add("Carta d'Identita'");
        documento.getItems().add("Passaporto");

        eta.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                eta.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        telefono.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                telefono.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

    }

    public void computePrezzo(ActionEvent event) {
        prezzo = 19.99;
        switch (classe.getValue()){
            case ECONOMY -> prezzo -= 5;
            case BUSINESS -> prezzo *= 1.5;
            case FAMIGLIE -> prezzo -= 1;
            case PRIORITY -> prezzo = prezzo*2 +1.99;
            case DIVERSAMENTE_ABILI -> prezzo /= 2;
        }
        pagaBtn.setText(String.format("%.2f$", prezzo));
    }
}
