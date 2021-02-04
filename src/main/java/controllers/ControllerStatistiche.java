package controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSpinner;
import data.Gate;
import data.Tratta;
import database.dao.CodaImbarcoDao;
import database.dao.GateDao;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import utility.Refreshable;


import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;


public class ControllerStatistiche implements Initializable, Refreshable<Void> {

    @FXML
    CategoryAxis x;
    @FXML
    NumberAxis y;
    @FXML
    private BarChart<String,Number> barChart;
    @FXML
    JFXSpinner spinner;
    @FXML
    private BarChart<String,Number> barChart2;
    @FXML
    private BarChart<String,Number> barChart3;
    @FXML
    private JFXDatePicker dpkGiorno;
    @FXML
    JFXComboBox<Gate> gateComboBox;
    @FXML
    private JFXDatePicker dpkSettimana;
    @FXML
    private JFXComboBox<String> meseComboBox;
    @FXML
    private JFXComboBox<Integer> annoComboBox;

    int minutiGiornoStimato;
    int minutiGiornoEffettivo;
    int minutiSettimanaEffettivo;
    int minutiSettimanaStimato;
    int minutiMeseEffettivo;
    int minutiMeseStimato;

    // ?????
    public Task<List<Void>> refresh() {
        if (!spinner.isVisible()){
            spinner.setVisible(true);
            Task<List<Void>> task = new Task<>() {
                @Override
                protected List<Void> call() {
                  /*  try {

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }*/
                    return null;
                }
            };
            task.setOnSucceeded(e -> {

                spinner.setVisible(false);
            });
            Thread th = new Thread(task);
            th.setDaemon(true);
            th.start();
            return task;
        } else return null;
    }
    // ???????
    @Override
    public boolean isRefreshing() {
        return false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        barChart.setAnimated(false);
        barChart2.setAnimated(false);
        barChart3.setAnimated(false);
        //Mesi
        meseComboBox.getItems().add("Gennaio");
        meseComboBox.getItems().add("Febbraio");
        meseComboBox.getItems().add("Marzo");
        meseComboBox.getItems().add("Aprile");
        meseComboBox.getItems().add("Maggio");
        meseComboBox.getItems().add("Giugno");
        meseComboBox.getItems().add("Luglio");
        meseComboBox.getItems().add("Agosto");
        meseComboBox.getItems().add("Settembre");
        meseComboBox.getItems().add("Ottobre");
        meseComboBox.getItems().add("Novembre");
        meseComboBox.getItems().add("Dicembre");
        meseComboBox.getSelectionModel().select(LocalDate.now().getMonth().getValue()-1); //meno 1 perchè inizia da 0 l'array della combobox

        annoComboBox.getItems().add((LocalDate.now().getYear()));
        annoComboBox.getSelectionModel().selectFirst();
        //Giorno
        dpkGiorno.setValue(LocalDate.now());
        //settimana
        dpkSettimana.setValue(LocalDate.now());

        GateDao gateDao = new GateDao();
        try {
            gateDao.getGateCodes().forEach(g ->{
                gateComboBox.getItems().add(g);
            });
            gateComboBox.getSelectionModel().selectFirst();
            setGate(null);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }


    }

    public void setGate(ActionEvent event) {
        datePickGiorno(null);
        datePickSettimana(null);
        datePickMese(null);
    }

    public void datePickGiorno(ActionEvent event) {
        CodaImbarcoDao cDao = new CodaImbarcoDao();

        try {
            minutiGiornoStimato = cDao.minutiStimatiGiorno(dpkGiorno.getValue(), gateComboBox.getValue());
            minutiGiornoEffettivo = cDao.minutiEffettiviGiorno(dpkGiorno.getValue(), gateComboBox.getValue());
            barChart3.getData().clear();
            XYChart.Series<String,Number> utilizzoStimatoGiorno = new XYChart.Series<>();
            XYChart.Series<String,Number> utilizzoEffettivoGiorno = new XYChart.Series<>();

            utilizzoEffettivoGiorno.getData().add(new XYChart.Data<>(dpkGiorno.getValue().format(DateTimeFormatter.ofPattern("DD/MM/YY")), minutiGiornoEffettivo));
            utilizzoEffettivoGiorno.setName("Effettivo");
            utilizzoStimatoGiorno.getData().add(new XYChart.Data<>(dpkGiorno.getValue().format(DateTimeFormatter.ofPattern("DD/MM/YY")), minutiGiornoStimato));
            utilizzoStimatoGiorno.setName("Stimato");
            barChart3.getData().addAll(utilizzoEffettivoGiorno,utilizzoStimatoGiorno);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void datePickSettimana(ActionEvent event) {
        CodaImbarcoDao cDao = new CodaImbarcoDao();
            try {
                minutiSettimanaEffettivo = cDao.minutiEffettiviSettimana(dpkSettimana.getValue(), gateComboBox.getValue());
                minutiSettimanaStimato = cDao.minutiStimatiSettimana(dpkSettimana.getValue(), gateComboBox.getValue());
                barChart2.getData().clear();
                XYChart.Series<String,Number> utilizzoStimatoSettimana = new XYChart.Series<>();
                XYChart.Series<String,Number> utilizzoEffettivoSettimana = new XYChart.Series<>();

                utilizzoEffettivoSettimana.getData().add(new XYChart.Data<>(dpkSettimana.getValue().format(DateTimeFormatter.ofPattern("DD/MM/YY")), minutiSettimanaEffettivo));
                utilizzoEffettivoSettimana.setName("Effettivo");
                utilizzoStimatoSettimana.getData().add(new XYChart.Data<>(dpkSettimana.getValue().format(DateTimeFormatter.ofPattern("DD/MM/YY")), minutiSettimanaStimato));
                utilizzoStimatoSettimana.setName("Stimato");
                barChart2.getData().addAll(utilizzoEffettivoSettimana, utilizzoStimatoSettimana);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }

    public void datePickMese(ActionEvent event){
        CodaImbarcoDao cDao = new CodaImbarcoDao();
        try {
            minutiMeseEffettivo = cDao.minutiEffettiviMese(annoComboBox.getValue(),meseComboBox.getSelectionModel().getSelectedIndex()+1, gateComboBox.getValue());
            minutiMeseStimato = cDao.minutiStimatiMese(annoComboBox.getValue(),meseComboBox.getSelectionModel().getSelectedIndex()+1, gateComboBox.getValue());
            barChart.getData().clear();
            XYChart.Series<String,Number> utilizzoStimatoMese = new XYChart.Series<>();
            XYChart.Series<String,Number> utilizzoEffettivoMese = new XYChart.Series<>();

            utilizzoEffettivoMese.getData().add(new XYChart.Data<>(meseComboBox.getValue() + " " + annoComboBox.getValue().toString() , minutiMeseEffettivo));
            utilizzoEffettivoMese.setName("Effettivo");
            utilizzoStimatoMese.getData().add(new XYChart.Data<>(meseComboBox.getValue() + " " + annoComboBox.getValue().toString() , minutiMeseStimato));
            utilizzoStimatoMese.setName("Stimato");
            barChart.getData().addAll(utilizzoEffettivoMese, utilizzoStimatoMese);
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}




