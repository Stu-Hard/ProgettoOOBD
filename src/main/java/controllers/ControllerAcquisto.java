package controllers;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;

import data.Biglietto;
import data.Cliente;
import data.Tratta;

import database.dao.BigliettoDao;

import database.dao.CodaImbarcoDao;
import enumeration.CodeEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;

import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;

import javafx.stage.Window;
import utility.Validators;
import utility.WindowDragger;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerAcquisto extends WindowDragger implements Initializable {
    private Tratta tratta;
    @FXML
    JFXTextField nome, cognome, riconoscimento, cf;
    @FXML
    JFXComboBox<CodeEnum> classe;
    @FXML
    JFXComboBox<String> documento;
    @FXML
    JFXButton pagaBtn;

    Window mainWindow;

    double prezzo = 19.99;

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
        try {
            new CodaImbarcoDao().getByTratta(tratta).forEach(c -> classe.getItems().add(c.getClasse()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(!classe.getItems().isEmpty())
            classe.getSelectionModel().selectFirst();
        computePrezzo(null);
    }

    public Tratta getTratta(){
        return this.tratta;
    }
    @FXML
    public void close(ActionEvent e){
        ((JFXButton) e.getSource()).getScene().getWindow().hide();
    }

    @FXML
    public void buy(ActionEvent e) {
        if (cf.validate() && riconoscimento.validate()  && nome.validate() && cognome.validate() && classe.getValue() != null) {
            Cliente cliente = new Cliente(getCf(), getNome() + "-" + getCognome(), getRiconoscimento());

            Biglietto biglietto = new Biglietto(prezzo, classe.getValue(), false, false, tratta.getNumeroVolo(), cliente);
            BigliettoDao bDao = new BigliettoDao();

            FXMLLoader fxmlLoader;
            try {
                bDao.insert(biglietto);
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AcquistoBigliettoConfirm.fxml"));

            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AcquistoBigliettoError.fxml"));
            }
            try {
                Parent root = fxmlLoader.load();
                JFXAlert alert = new JFXAlert(mainWindow);
                JFXDialogLayout layout = new JFXDialogLayout();
                layout.setHeading();
                alert.setOverlayClose(true);
                alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                alert.setContent(root);
                alert.initModality(Modality.NONE);
                alert.showAndWait();
            } catch (IOException ioException){
                ioException.printStackTrace();
            }
                close(e);
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

    public String getCf() {
        return cf.getText();
    }

    public void setCf(String cf) {
        this.cf.setText(cf);
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

    public void setMainWindow(Window mainWindow) {
        this.mainWindow = mainWindow;
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
        nome.getValidators().add(new Validators().createRequiredValidator("Inserire nome"));
        cognome.getValidators().add(new Validators().createRequiredValidator("Inserire cognome"));
        riconoscimento.getValidators().add(new Validators().createRequiredValidator("Inserire n. documento"));

        documento.getItems().add("Patente");
        documento.getItems().add("Carta d'Identita'");
        documento.getItems().add("Passaporto");
        documento.getSelectionModel().selectFirst();

        computePrezzo(null);

    }

    public void computePrezzo(ActionEvent event) {
        prezzo = 19.99;
        if (!classe.getItems().isEmpty())
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
