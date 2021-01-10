package controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRippler;
import customComponents.CompagniaCard;
import data.Compagnia;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import utility.CardRippler;


import java.net.URL;
import java.util.Random;
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

        Random r = new Random();
        for (int i = 0; i < 20; i++) {
            Compagnia c;
            if (i%4 == 0)
                c = new Compagnia("Ryanair", "RYR", "Irlanda", r.nextFloat()*10, r.nextFloat()*20);
            else if (i%4 == 1)
                c = new Compagnia("Vueling", "VLG", "Spagna", r.nextFloat()*10, r.nextFloat()*20);
            else if (i%4 == 2)
                c = new Compagnia("Alitalia", "AZA", "Italia", r.nextFloat()*10, r.nextFloat()*20);
            else
                c = new Compagnia("Easyjet", "EZS", "Svizzera", r.nextFloat()*10, r.nextFloat()*20);

            CompagniaCard card = new CompagniaCard(c);
            JFXRippler cardRippler = new CardRippler(card, 25);
            flowPane.getChildren().add(cardRippler);
        }
        scroll.setContent(flowPane);
    }
}
