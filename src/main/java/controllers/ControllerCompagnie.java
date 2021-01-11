package controllers;

import com.jfoenix.controls.JFXComboBox;
import customComponents.CompagniaCard;
import data.Compagnia;
import database.dao.CompagniaDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import utility.CardRippler;


import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerCompagnie implements Initializable {

    @FXML
    private ScrollPane scroll;
    @FXML
    private JFXComboBox<String> searchMode;

    private FlowPane flowPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchMode.getItems().addAll("Nome", "ICAO", "Nazione");
        searchMode.getSelectionModel().selectFirst();

        flowPane = new FlowPane();
        flowPane.setHgap(20);
        flowPane.setVgap(15);
        flowPane.setPadding(new Insets(5, 10, 5, 20));
        flowPane.setStyle("-fx-background-color: transparent");

        CompagniaDao compagniaDao = new CompagniaDao();
        try {
            List<Compagnia> l = compagniaDao.getCompagnie();
            l.forEach(compagnia -> {
                CompagniaCard card = new CompagniaCard(compagnia);
                flowPane.getChildren().add( new CardRippler(card, 25));
            });
        } catch (NullPointerException | SQLException throwables) {
            throwables.printStackTrace();
        }
        scroll.setContent(flowPane);
    }
}
