package controllers;

import com.jfoenix.controls.*;
import data.Aereo;
import data.Aeroporto;
import data.Compagnia;
import data.Tratta;
import database.dao.AereoDao;
import database.dao.AeroportoDao;
import database.dao.CompagniaDao;
import database.dao.TrattaDao;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import utility.WindowDragger;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;


public class ControllerTratteAdd extends WindowDragger implements Initializable {

    @FXML
    JFXComboBox<Compagnia> compagnia;
    @FXML
    JFXComboBox<Aeroporto> partenza, arrivo;
    @FXML
    JFXComboBox<Aereo> aerei;

    @FXML
    JFXDatePicker data;
    @FXML
    JFXTimePicker ora;
    @FXML
    JFXTextField durata;

    @FXML
    JFXButton conferma;

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

        return generatedString.substring(0, 4);
    }

    @FXML
    public void compagniaAction(ActionEvent e){
        aerei.getItems().remove(0, aerei.getItems().size());
        try {
            aerei.getItems().addAll(new AereoDao().getAereiByCompagnia(compagnia.getValue()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void conferma(ActionEvent e){
        Tratta tratta = new Tratta(
                compagnia.getValue().getSigla() + randomStr(),
                data.getValue(),
                ora.getValue(),
                Integer.parseInt(durata.getText()),
                0,
                false,
                compagnia.getValue(),
                partenza.getValue(),
                arrivo.getValue(),
                aerei.getValue()
        );
        System.out.println(tratta);
        try {
            new TrattaDao().add(tratta);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ((Node) e.getSource()).getScene().getWindow().hide();
    }

    public void annulla(ActionEvent e){
        ((Node) e.getSource()).getScene().getWindow().hide();
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
        conferma.disableProperty().bind(new BooleanBinding() {
            {
                super.bind(
                        compagnia.valueProperty(),
                        aerei.valueProperty(),
                        partenza.valueProperty(),
                        arrivo.valueProperty(),
                        data.valueProperty(),
                        arrivo.valueProperty(),
                        durata.textProperty());
            }
            @Override
            protected boolean computeValue() {
                return compagnia.getValue() == null ||
                        aerei.getValue() == null ||
                        partenza.getValue() == null ||
                        arrivo.getValue() == null ||
                        data.getValue() == null ||
                        ora.getValue() == null ||
                        arrivo.getValue() == null ||
                        durata.getText().isEmpty();
            }
        });

        durata.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                durata.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        try {
            compagnia.getItems().addAll(new CompagniaDao().getCompagnie());
            List<Aeroporto> a = new AeroportoDao().getAeroporti();
            partenza.getItems().addAll(a);
            arrivo.getItems().addAll(a);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
