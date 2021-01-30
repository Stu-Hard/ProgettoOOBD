package controllers;

import com.jfoenix.controls.*;
import data.*;
import database.dao.*;
import enumeration.CodeEnum;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import utility.IdFactory;
import utility.WindowDragger;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class ControllerTratteAdd extends WindowDragger implements Initializable {
    @FXML
    public JFXCheckBox diversamenteAbili;
    @FXML
    public JFXCheckBox famiglie;
    @FXML
    public JFXCheckBox business;
    @FXML
    public JFXCheckBox priorty;
    @FXML
    public JFXCheckBox economy;
    @FXML
    JFXComboBox<Compagnia> compagnia;
    @FXML
    JFXComboBox<Aeroporto> partenza, arrivo;

    @FXML
    JFXComboBox<Gate> gate;
    @FXML
    JFXCheckBox conclusa;
    @FXML
    Label gateLbl;
    @FXML
    JFXDatePicker data;
    @FXML
    JFXTimePicker ora;
    @FXML
    JFXTextField durata, posti;

    @FXML
    JFXButton conferma;

    Aeroporto aeroportoGestito;

    @FXML
    public void compagniaAction(ActionEvent e){

    }

    @FXML
    public void disableGate(ActionEvent e){
        gate.setDisable(!conclusa.isSelected());
        gateLbl.setDisable(gate.isDisable());
        if (gate.isDisable()){
            gate.getSelectionModel().clearSelection();

        }
    }

    @FXML
    public void conferma(ActionEvent e){
        Tratta tratta = new Tratta(
                new IdFactory().newNumeroVolo(compagnia.getValue()),
                data.getValue(),
                ora.getValue(),
                Integer.parseInt(durata.getText()),
                0,
                conclusa.isSelected(),
                (conclusa.isSelected())? gate.getValue().getGateCode(): null,
                compagnia.getValue(),
                partenza.getValue(),
                arrivo.getValue(),
                Integer.parseInt(posti.getText())
        );
        try {
            new TrattaDao().add(tratta);
            CodaImbarcoDao cDao = new CodaImbarcoDao();
            if (diversamenteAbili.isSelected()) cDao.addNew(CodeEnum.DIVERSAMENTE_ABILI, tratta);
            if (famiglie.isSelected()) cDao.addNew(CodeEnum.FAMIGLIE, tratta);
            if (business.isSelected()) cDao.addNew(CodeEnum.BUSINESS, tratta);
            if (priorty.isSelected()) cDao.addNew(CodeEnum.PRIORITY, tratta);
            if (economy.isSelected()) cDao.addNew(CodeEnum.ECONOMY, tratta);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ((Node) e.getSource()).getScene().getWindow().hide();
    }

    public void annulla(ActionEvent e){
        ((Node) e.getSource()).getScene().getWindow().hide();
    }

    public void controlloAeroporto(ActionEvent e){
        if (e.getSource().equals(partenza)){
            if (!partenza.getValue().equals(aeroportoGestito)){
                arrivo.getSelectionModel().select(aeroportoGestito);
                conclusa.setSelected(true);
                conclusa.setDisable(true);
                gateLbl.setDisable(true);
                gate.setDisable(true);
            }
        } else {
            if (!arrivo.getValue().equals(aeroportoGestito)){
                partenza.getSelectionModel().select(aeroportoGestito);
                conclusa.setSelected(false);
                conclusa.setDisable(false);
            }
        }
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
                        partenza.valueProperty(),
                        arrivo.valueProperty(),
                        data.valueProperty(),
                        arrivo.valueProperty(),
                        durata.textProperty(),
                        gate.valueProperty(),
                        conclusa.selectedProperty(),
                        posti.textProperty(),
                        diversamenteAbili.selectedProperty(),
                        famiglie.selectedProperty(),
                        economy.selectedProperty(),
                        business.selectedProperty(),
                        priorty.selectedProperty());
            }
            @Override
            protected boolean computeValue() {
                return compagnia.getValue() == null ||
                        partenza.getValue() == null ||
                        arrivo.getValue() == null ||
                        data.getValue() == null ||
                        ora.getValue() == null ||
                        arrivo.getValue() == null ||
                        durata.getText().isEmpty() ||
                        (conclusa.isSelected() && gate.getValue() == null) ||
                        posti.getText().isEmpty() ||
                        !(
                                diversamenteAbili.isSelected() ||
                                famiglie.isSelected() ||
                                priorty.isSelected() ||
                                business.isSelected() ||
                                economy.isSelected()
                        );
            }
        });

        durata.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                durata.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        posti.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                posti.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        try {
            aeroportoGestito = new AeroportoDao().getAeroportoGestito();
            compagnia.getItems().addAll(new CompagniaDao().getCompagnie());
            List<Aeroporto> a = new AeroportoDao().getAeroporti();
            partenza.getItems().addAll(a);
            arrivo.getItems().addAll(a);
            gate.getItems().addAll(new GateDao().getGateCodes());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
