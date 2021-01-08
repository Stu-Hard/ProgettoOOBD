package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import customComponents.GateCard;
import enumeration.*;
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
import java.util.ResourceBundle;

public class ControllerGate implements Initializable {

    @FXML
    private ScrollPane scroll;
    private FlowPane flowPane;
    @FXML
    private JFXComboBox<String> searchMode;
    @FXML
    private JFXCheckBox occupatiCheck, liberiCheck, chiusiCheck;
    @FXML
    private TextField searchBar;
    @FXML
    private JFXButton cancelBtn;

    private List<GateCard> gateCardList; // lista che contiene tutti i gate.
                                        // N.B. gateCardList è diverso da flowPane.getChildren(). Indovinello: Perch'è sono diversi?
                                        // (Suggerimento) per diversi non si intende il tipo.
    @FXML
    private Label nessunGate;

    // Filtri sullo stato: occupato ecc...
    public void statusFilter(ActionEvent e){
        GateStatus status;
        JFXCheckBox source = (JFXCheckBox) e.getSource();

        if (occupatiCheck.equals(source)) status = GateStatus.OCCUPATO;  //
        else if (liberiCheck.equals(source)) status = GateStatus.LIBERO; // Decide qual'è lo stato da filtrare
        else status = GateStatus.CHIUSO;                                 //

        if (!(source).isSelected())
            flowPane.getChildren().removeIf(n -> ((GateCard) n).getStato() == status); // cancella se lo stato viene deselezionato
        else
            gateCardList.forEach(n ->{
                if(n.getStato() == status)
                    flowPane.getChildren().add(0, n); // aggiungi in cima se lo stato viene selezionato
            });
        search(null); // "integra" il filtro con il testo sulla barra di ricerca
    }

    public void search(KeyEvent k){
        String searchMode = this.searchMode.getValue();
        String text = searchBar.getText();
        switch (searchMode){
            case "Codice" -> { // non molto elegante, ma funziona...
                flowPane.getChildren().removeIf(node -> !((GateCard) node).getGateCode().contains(text)); // rimuovi se la card non contine il testo in searchBar
                gateCardList.forEach(node -> { //aggiunge i risultati validi che erano stati eliminati precedentemente (è utile quando viene cancellato un carattere)
                    if(node.getGateCode().contains(text) && !flowPane.getChildren().contains(node))
                        if ((occupatiCheck.isSelected() && node.getStato() == GateStatus.OCCUPATO) ||   //
                                (liberiCheck.isSelected() && node.getStato() == GateStatus.LIBERO) ||   // "integrazione" con i filtri sullo stato
                                (chiusiCheck.isSelected() && node.getStato() == GateStatus.CHIUSO))     //
                            flowPane.getChildren().add(0, node);
                });
            }
            case "Coda" -> { // da rifletterci su
                // TODO
            }
        }

        if(flowPane.getChildren().isEmpty()) {
            nessunGate.setVisible(true);
        }else{
            nessunGate.setVisible(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchMode.getItems().addAll("Codice", "Coda");
        searchMode.getSelectionModel().selectFirst();
        occupatiCheck.setSelected(true);
        liberiCheck.setSelected(true);
        chiusiCheck.setSelected(true);

        cancelBtn.setOnAction(e ->{ // Cancella il testo e annulla la ricerca
            searchBar.setText("");
            search(null);
        });

        // crea un pannello e lo inserisce in scroll
        flowPane = new FlowPane();
        flowPane.setHgap(20);
        flowPane.setVgap(15);
        flowPane.setPadding(new Insets(5, 10, 5, 20));
        flowPane.setStyle("-fx-background-color: transparent");

        gateCardList = new ArrayList<>();
        ///////// solo per prova!! è una specie di simulazione. da cancellare
        for (int i = 0; i < 10; i++) {
            GateCard gc;
            int r = (int) (Math.random()*4);
            int tempo = (int) (Math.random()*50);
            switch (r){
                case 0 -> gc = new GateCard("A"+i, "Napoli", "Londra", tempo, GateStatus.OCCUPATO);
                case 1 -> gc = new GateCard("A"+i, "Napoli", "Londra", tempo, GateStatus.LIBERO);
                case 2 -> gc = new GateCard("A"+i, "Napoli", "Londra", tempo, GateStatus.CHIUSO);
                case 3 -> gc = new GateCard("A"+i, "Napoli", "Londra", tempo, GateStatus.OCCUPATO);
                default -> throw new IllegalStateException("Unexpected value: " + r);
            }
            gateCardList.add(gc);
            flowPane.getChildren().add(gc);
        }
        for (int i = 0; i < 10; i++) {
            GateCard gc;
            int r = (int) (Math.random()*3);
            int tempo = (int) (Math.random()*50);
            switch (r){
                case 0 -> gc = new GateCard("B"+i,  "Londra","Napoli", tempo, GateStatus.OCCUPATO);
                case 1 -> gc = new GateCard("B"+i,  "Londra","Napoli", tempo, GateStatus.LIBERO);
                case 2 -> gc = new GateCard("B"+i,  "Londra","Napoli", tempo, GateStatus.CHIUSO);
                case 3 -> gc = new GateCard("B"+i,  "Londra","Napoli", tempo, GateStatus.CHIUSO);
                default -> throw new IllegalStateException("Unexpected value: " + r);
            }
            gateCardList.add(gc);
            flowPane.getChildren().add(gc);
        }
        GateCard card = new GateCard("B20",  "Londra","Napoli", 10, GateStatus.OCCUPATO);
        // prova dei metodi disable e enable coda. vedi l'ultima card.
        card.disableCode(CodeEnum.DIVERSAMENTE_ABILI, CodeEnum.ECONOMY, CodeEnum.BUSINESS, CodeEnum.PRIORITY, CodeEnum.FAMIGLIE);
        card.enableCode(CodeEnum.DIVERSAMENTE_ABILI, CodeEnum.BUSINESS);
        gateCardList.add(card);
        flowPane.getChildren().add(card);
        ////////// fine della simulazione

        scroll.setContent(flowPane);
    }
}
