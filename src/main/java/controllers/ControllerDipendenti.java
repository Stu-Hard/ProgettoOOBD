package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import customComponents.DipendentiCard;
import enumeration.DipendentiEnum;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;


import java.net.URL;
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
    private JFXComboBox<String> searchMode;

    private List<DipendentiCard> DipendentiList;
    private FlowPane flowPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            searchMode.getItems().addAll("Amministratori","Ticket Agent","Addetti all'Imbarco","Responsabili Voli", "Addetti al CheckIn");


            cancelBtn.setOnAction(e ->{ // Cancella il testo e annulla la ricerca
            searchBar.setText("");
        });

        //creazione del pannello da inserire in scrollPane
        flowPane = new FlowPane();
        flowPane.setHgap(20);
        flowPane.setVgap(15);
        flowPane.setPadding(new Insets(5, 10, 5, 20));
        flowPane.setStyle("-fx-background-color: transparent");

        DipendentiList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            DipendentiCard dipendentiCard;
            int r = (int) (Math.random()*4);
            switch (r){
                case 0 -> dipendentiCard = new DipendentiCard("Lele", DipendentiEnum.ADDETTO_IMBARCO);
                case 1 -> dipendentiCard = new DipendentiCard("Mamma frocia", DipendentiEnum.TICKET_AGENT);
                case 2 -> dipendentiCard = new DipendentiCard("Trota", DipendentiEnum.RESPONSABILE_VOLI);
                case 3 -> dipendentiCard = new DipendentiCard("Giada", DipendentiEnum.AMMINISTRATORE);
                default -> throw new IllegalStateException("Unexpected value: " + r);
            }
            DipendentiList.add(dipendentiCard);
            flowPane.getChildren().add(dipendentiCard);
        }
        scrollPane.setContent(flowPane);
    }
}
