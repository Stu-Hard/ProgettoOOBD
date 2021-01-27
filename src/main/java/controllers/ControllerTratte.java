package controllers;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
import customComponents.TrattaHbox;
import data.Tratta;
import database.dao.TrattaDao;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import javax.swing.*;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class ControllerTratte implements Initializable {

    @FXML
    private JFXComboBox<String> searchMode;
    @FXML
    private JFXDatePicker dpk1, dpk2;
    @FXML
    private JFXButton cancelBtn;
    @FXML
    private TextField searchBar;
    @FXML
    private JFXSpinner spinner;

    @FXML
    private JFXListView<TrattaHbox> listView;
    private List<TrattaHbox> tratteHboxList;

    public void search(KeyEvent k){
        //String searchMode = this.searchMode.getValue();
        //String text = searchBar.getText();
        //switch (searchMode){
        //    case "Partenza" -> { // non molto elegante, ma funziona...
        //        box.getChildren().removeIf(node -> !((TrattaHbox) node).getTratta().getAereoportoPartenza().contains(text)); // rimuovi se la card non contine il testo in searchBar
        //        tratteHboxList.forEach(node -> { //aggiunge i risultati validi che erano stati eliminati precedentemente (è utile quando viene cancellato un carattere)
        //            if(node.getTratta().getAereoportoPartenza().contains(text) && !box.getChildren().contains(node))
        //                box.getChildren().add(0, node);
        //        });
        //    }
        //    case "Arrivo" -> {
        //        box.getChildren().removeIf(node -> !((TrattaHbox) node).getTratta().getAereoportoArrivo().contains(text)); // rimuovi se la card non contine il testo in searchBar
        //        tratteHboxList.forEach(node -> { //aggiunge i risultati validi che erano stati eliminati precedentemente (è utile quando viene cancellato un carattere)
        //            if(node.getTratta().getAereoportoArrivo().contains(text) && !box.getChildren().contains(node))
        //                box.getChildren().add(0, node);
        //        }); }
        //    case "Compagnia" -> {
        //        box.getChildren().removeIf(node -> !((TrattaHbox) node).getTratta().getCompagnia().contains(text)); // rimuovi se la card non contine il testo in searchBar
        //        tratteHboxList.forEach(node -> { //aggiunge i risultati validi che erano stati eliminati precedentemente (è utile quando viene cancellato un carattere)
        //            if(node.getTratta().getCompagnia().contains(text) && !box.getChildren().contains(node))
        //                box.getChildren().add(0, node);
        //        });
        //    }
        //    case "NumeroVolo" -> {
        //        box.getChildren().removeIf(node -> !((TrattaHbox) node).getTratta().getNumeroVolo().contains(text)); // rimuovi se la card non contine il testo in searchBar
        //        tratteHboxList.forEach(node -> { //aggiunge i risultati validi che erano stati eliminati precedentemente (è utile quando viene cancellato un carattere)
        //            if(node.getTratta().getNumeroVolo().contains(text) && !box.getChildren().contains(node))
        //                box.getChildren().add(0, node);
        //        });
        //    }
        //}
    }

    public void datePick(ActionEvent e){ // è una prova solo per il dpk a sinistra
        //box.getChildren().removeIf(node ->
        //        ((TrattaHbox) node)
        //        .getTratta()
        //        .getDataPartenza()
        //        .isBefore(((JFXDatePicker) e.getSource()).getValue())
        //);
    }

    public void canc(ActionEvent e){
        searchBar.setText("");
        search(null);
    }

    @FXML
    public void mouseClick(MouseEvent e){
        if (listView.getSelectionModel().getSelectedItem() == null) return;
        Tratta tratta = listView.getSelectionModel().getSelectedItem().getTratta();
        if (e.getButton() == MouseButton.PRIMARY) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TratteInfo.fxml"));
                Parent parent = fxmlLoader.load();
                ControllerTratteInfo controller = fxmlLoader.getController();
                controller.setTratta(tratta);

                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.TRANSPARENT);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } else if(e.getButton() == MouseButton.SECONDARY){
            JFXButton elimina = new JFXButton("Elimina");
            elimina.setStyle("-fx-background-radius: 0; -fx-font-size: 18; -fx-background-color: red; -fx-text-fill: white");
            JFXPopup popup = new JFXPopup(elimina);
            popup.show(listView.getScene().getWindow(), e.getSceneX(), e.getSceneY(), JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, 0, 0);
        }
    }

    @FXML
    private void add(ActionEvent e){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TratteAdd.fxml"));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.showAndWait();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void refresh() {
        if (!spinner.isVisible()){
            listView.getItems().clear();
            spinner.setVisible(true);
            Task<List<Tratta>> task = new Task<>() {
                @Override
                protected List<Tratta> call() {
                    try {
                        return new TrattaDao().getTratteWithDate(dpk1.getValue(), dpk2.getValue());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    return null;
                }
            };
            task.setOnSucceeded(e -> {
                listView.getItems().addAll(task.getValue().stream().map(TrattaHbox::new).toArray(TrattaHbox[]::new));
                spinner.setVisible(false);
            });
            Thread th = new Thread(task);
            th.setDaemon(true);
            th.start();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchMode.getItems().addAll("Partenza", "Arrivo", "Compagnia", "NumeroVolo");
        searchMode.getSelectionModel().selectFirst();
        tratteHboxList = new ArrayList();
    }
}
