package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSpinner;
import customComponents.CompagniaCard;
import data.Aeroporto;
import data.Compagnia;
import data.Dipendente;
import database.dao.AeroportoDao;
import database.dao.CompagniaDao;
import enumeration.DipendentiEnum;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utility.CardRippler;
import utility.Refreshable;
import utility.UserRestricted;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ControllerCompagnie implements Initializable, Refreshable<Compagnia>, UserRestricted {

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
    private AnchorPane mainPane;
    @FXML
    private TextField searchBar;
    private Dipendente loggedUser;
    @FXML
    private JFXButton addBtn;


    @FXML
    private void add(ActionEvent e){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CompagniaAdd.fxml"));
            Parent parent = fxmlLoader.load();
            ControllerCompagnieAdd controller = fxmlLoader.getController();
            controller.setMainPane(mainPane);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.showAndWait();
            refresh();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void canc(ActionEvent e){
        searchBar.setText("");
        searchMode.getSelectionModel().selectFirst();
        refresh();
    }

    public boolean isRefreshing(){
        return spinner.isVisible();
    }

    public Task<List<Compagnia>> refresh() {
        if (!isRefreshing()){
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

    public void search(KeyEvent k) {
        String searchMode = this.searchMode.getValue();
        String text = searchBar.getText();
        flowPane.getChildren().clear();
        flowPane.getChildren().addAll(localCompagnie);
        switch (searchMode){
            case "Nome" -> flowPane.getChildren().removeIf(node -> !((CompagniaCard) node).getCompagnia().getNome().toUpperCase().contains(text.toUpperCase())); // rimuovi se la card non contine il testo in searchBar
            case "ICAO" -> flowPane.getChildren().removeIf(node -> !((CompagniaCard) node).getCompagnia().getSigla().toUpperCase().contains(text.toUpperCase()));
            case "Nazione" -> flowPane.getChildren().removeIf(node -> !((CompagniaCard) node).getCompagnia().getNazione().toUpperCase().contains(text.toUpperCase()));
        }
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

    @Override
    public void setLoggedUser(Dipendente loggedUser) {
        this.loggedUser = loggedUser;
        if (loggedUser.getRuolo() == DipendentiEnum.Amministratore && loggedUser.getCompagnia() == null){
            addBtn.setVisible(true);
        } else {
            addBtn.setVisible(false);
        }
    }
}
