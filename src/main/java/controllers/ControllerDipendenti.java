package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSpinner;
import customComponents.DipendentiCard;
import data.Dipendente;
import database.dao.DipendentiDao;
import enumeration.DipendentiEnum;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerDipendenti implements Initializable {

    @FXML
    private TextField searchBar;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private JFXButton cancelBtn;
    @FXML
    private JFXComboBox<String> dipendentiType;
    @FXML
    JFXSpinner spinner;
    private List<DipendentiCard> DipendentiList;
    private FlowPane flowPane;
    @FXML
    JFXButton addBtn;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dipendentiType.getItems().addAll("Dipendenti","Amministratori","Ticket Agent","Addetti all'Imbarco","Responsabili Voli", "Addetti al CheckIn");
        dipendentiType.getSelectionModel().selectFirst();

        // Cancella il testo e annulla la ricerca
        cancelBtn.setOnAction(e ->{
            searchBar.setText("");
            search(null);
        });

        //creazione del pannello da inserire in scrollPane
        flowPane = new FlowPane();
        flowPane.setHgap(20);
        flowPane.setVgap(15);
        flowPane.setPadding(new Insets(5, 10, 5, 20));
        flowPane.setStyle("-fx-background-color: transparent");

        DipendentiList = new ArrayList<>();

        scrollPane.setContent(flowPane);

    }



    public void search(KeyEvent keyEvent) {

        String testo = searchBar.getText().toUpperCase();//perchè così in qualunque modo inserisci il nome lo trova *guarda commento di sotto

        flowPane.getChildren().removeIf(node -> !((DipendentiCard) node).getBottoneUtente().contains(testo)); //getBottoneUtente() restituisce una stringa in UpperCase
        DipendentiList.forEach(node -> {
            if(node.getBottoneUtente().contains(testo) && !flowPane.getChildren().contains(node)){
                    flowPane.getChildren().add(node);
            }
        });
        filtroLavoro(dipendentiType.getValue());
    }

    public void filtroLavoro(String scelta){
        switch (scelta){
            case("Amministratori"):

                flowPane.getChildren().removeIf(node -> !(((DipendentiCard) node).getGerarchia() == DipendentiEnum.Amministratore));
                break;
            case("Addetti al CheckIn"):

                flowPane.getChildren().removeIf(node -> !(((DipendentiCard) node).getGerarchia() == DipendentiEnum.AddettoCheckIn));
                break;
            case("Ticket Agent"):


                flowPane.getChildren().removeIf(node -> !(((DipendentiCard) node).getGerarchia() == DipendentiEnum.TicketAgent));
                break;
            case("Addetti all'Imbarco"):


                flowPane.getChildren().removeIf(node -> !(((DipendentiCard) node).getGerarchia() == DipendentiEnum.AddettoImbarco));
                break;
            case("Responsabili Voli"):

                flowPane.getChildren().removeIf(node -> !(((DipendentiCard) node).getGerarchia() == DipendentiEnum.ResponsabileVoli));
                break;

        }
    }

    public void sceltaDipendenti(ActionEvent actionEvent) {
        String scelta = this.dipendentiType.getValue();
        filtroLavoro(scelta);
        search(null);
    }

    public void addDipendente(ActionEvent event) throws IOException {
        //crea scheda per inserire utente
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DipendentiAdd.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }


    public void refresh() {
        if (!spinner.isVisible()){
            flowPane.getChildren().clear();
            spinner.setVisible(true);
            Task<List<Dipendente>> task = new Task<>() {
                @Override
                protected List<Dipendente> call() {
                    try {
                        return new DipendentiDao().getDipendenti();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    return null;
                }
            };
            task.setOnSucceeded(e -> {
                DipendentiList.clear();
                task.getValue().stream().distinct().forEach(d -> DipendentiList.add(new DipendentiCard(d)));
                search(null);
                //con questo non funziona, chissa (?)
                //flowPane.getChildren().addAll(task.getValue().stream().map(DipendentiCard::new).toArray(DipendentiCard[]::new));
                spinner.setVisible(false);
            });

            Thread th = new Thread(task);
            th.setDaemon(true);
            th.start();
        }
    }
}
