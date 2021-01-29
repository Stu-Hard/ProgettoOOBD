package controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSpinner;
import customComponents.CompagniaCard;
import data.Aeroporto;
import data.Compagnia;
import database.dao.AeroportoDao;
import database.dao.CompagniaDao;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utility.CardRippler;


import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ControllerCompagnie implements Initializable {

    @FXML
    private ScrollPane scroll;
    @FXML
    private JFXComboBox<String> searchMode;
    @FXML
    private Label aeroportoGestito;

    private FlowPane flowPane;
    @FXML
    private JFXSpinner spinner;
    private List<CompagniaCard> localCompagnie;

    @FXML
    private void add(ActionEvent e){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CompagniaAdd.fxml"));
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

    public Task<List<Compagnia>> refresh() {
        if (!spinner.isVisible()){
            flowPane.getChildren().clear();
            spinner.setVisible(true);
            Task<List<Compagnia>> task = new Task<>() {
                @Override
                protected List<Compagnia> call() {
                    try {
                        return new CompagniaDao().getCompagnie();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    return null;
                }
            };
            task.setOnSucceeded(e -> {
                localCompagnie.clear();
                localCompagnie.addAll(task.getValue().stream().map(CompagniaCard::new).collect(Collectors.toList()));
                search(null);
                spinner.setVisible(false);
            });
            Thread th = new Thread(task);
            th.setDaemon(true);
            th.start();
            return task;
        } else return null;
    }

    private void search(KeyEvent k) {
        flowPane.getChildren().addAll(localCompagnie);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchMode.getItems().addAll("Nome", "ICAO", "Nazione");
        searchMode.getSelectionModel().selectFirst();

        flowPane = new FlowPane();
        flowPane.setHgap(20);
        flowPane.setVgap(15);
        flowPane.setPadding(new Insets(5, 10, 5, 20));
        flowPane.setStyle("-fx-background-color: transparent");

        try {
            Aeroporto a = new AeroportoDao().getAeroportoGestito();
            aeroportoGestito.setText(a.getCitta() + "-" + a.getNome() + " (" + a.getCodiceICAO() + ")");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        scroll.setContent(flowPane);
        localCompagnie = new LinkedList<>();
    }
}
