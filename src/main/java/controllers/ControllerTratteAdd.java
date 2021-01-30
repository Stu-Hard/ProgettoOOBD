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
import javafx.scene.input.MouseEvent;
import utility.IdFactory;
import utility.WindowDragger;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class ControllerTratteAdd extends WindowDragger implements Initializable {
    @FXML
    public JFXCheckBox
            diversamenteAbili,
            famiglie,
            business,
            priorty,
            economy;

    @FXML
    JFXComboBox<Compagnia> compagnia;
    @FXML
    JFXComboBox<Aeroporto> partenza, arrivo;
    @FXML
    JFXDatePicker data;
    @FXML
    JFXTimePicker ora;
    @FXML
    JFXTextField durata, posti;
    @FXML
    JFXCheckBox conclusa;

    @FXML
    JFXButton conferma;

    Aeroporto aeroportoGestito;
    List<Aeroporto> aeroporti;

    @FXML
    public void compagniaAction(ActionEvent e){

    }

    @FXML
    public void disableCode(ActionEvent e){
        if (conclusa.isSelected()) {
            diversamenteAbili.setSelected(false);
            famiglie.setSelected(false);
            business.setSelected(false);
            priorty.setSelected(false);
            economy.setSelected(false);
        } else {
            diversamenteAbili.setSelected(true);
            famiglie.setSelected(true);
            business.setSelected(true);
            priorty.setSelected(true);
            economy.setSelected(true);
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
                false,
                null,
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
        /*if (e.getSource().equals(partenza)){
            if (partenza.getValue() != null){
                arrivo.getItems().addAll(aeroporti.stream().filter(a -> arrivo.getItems().contains(a)).collect(Collectors.toList()));
                arrivo.getItems().remove(partenza.getValue());
            }
        } else{
            if (arrivo.getValue() != null){
                partenza.getItems().addAll(aeroporti.stream().filter(a -> partenza.getItems().contains(a)).collect(Collectors.toList()));
                partenza.getItems().remove(arrivo.getValue());
            }
        }*/
        if (partenza.getValue() != null && arrivo.getValue() != null){
            if (!partenza.getValue().equals(aeroportoGestito) && !arrivo.getValue().equals(aeroportoGestito)){
                if (((Node) e.getSource()).getId().equals(partenza.getId())) arrivo.getSelectionModel().select(aeroportoGestito);
                else partenza.getSelectionModel().select(aeroportoGestito);
            }else if (partenza.getValue().equals(arrivo.getValue())){
                if (((Node) e.getSource()).getId().equals(partenza.getId())) {
                    arrivo.getSelectionModel().clearSelection();
                }
                else partenza.getSelectionModel().clearSelection();
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
                        posti.textProperty(),
                        diversamenteAbili.selectedProperty(),
                        famiglie.selectedProperty(),
                        economy.selectedProperty(),
                        business.selectedProperty(),
                        conclusa.selectedProperty(),
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
                        posti.getText().isEmpty() ||
                        (!conclusa.isSelected() &&
                        !(
                                (diversamenteAbili.isSelected() ||
                                famiglie.isSelected() ||
                                priorty.isSelected() ||
                                business.isSelected() ||
                                economy.isSelected())
                        ));
            }
        });

        diversamenteAbili.disableProperty().bind(conclusa.selectedProperty());
        famiglie.disableProperty().bind(conclusa.selectedProperty());
        business.disableProperty().bind(conclusa.selectedProperty());
        priorty.disableProperty().bind(conclusa.selectedProperty());
        economy.disableProperty().bind(conclusa.selectedProperty());


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
            aeroporti = new AeroportoDao().getAeroporti();
            partenza.getItems().addAll(aeroporti);
            arrivo.getItems().addAll(aeroporti);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
