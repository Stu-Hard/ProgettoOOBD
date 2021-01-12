package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import customComponents.DipendentiCard;
import customComponents.GateCard;
import enumeration.DipendentiEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

    private List<DipendentiCard> DipendentiList;
    private FlowPane flowPane;




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

                flowPane.getChildren().removeIf(node -> !(((DipendentiCard) node).getGerarchia() == DipendentiEnum.AMMINISTRATORE));
                break;
            case("Addetti al CheckIn"):


                flowPane.getChildren().removeIf(node -> !(((DipendentiCard) node).getGerarchia() == DipendentiEnum.CHECK_IN));
                break;
            case("Ticket Agent"):


                flowPane.getChildren().removeIf(node -> !(((DipendentiCard) node).getGerarchia() == DipendentiEnum.TICKET_AGENT));
                break;
            case("Addetti all'Imbarco"):


                flowPane.getChildren().removeIf(node -> !(((DipendentiCard) node).getGerarchia() == DipendentiEnum.ADDETTO_IMBARCO));
                break;
            case("Responsabili Voli"):

                flowPane.getChildren().removeIf(node -> !(((DipendentiCard) node).getGerarchia() == DipendentiEnum.RESPONSABILE_VOLI));
                break;

        }
    }

    public void sceltaDipendenti(ActionEvent actionEvent) {
        String scelta = this.dipendentiType.getValue();
        filtroLavoro(scelta);
        search(null);
    }
}
