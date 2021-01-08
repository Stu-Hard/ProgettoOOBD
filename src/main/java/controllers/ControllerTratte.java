package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import customComponents.TrattaHbox;
import data.Tratta;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;


import java.net.URL;
import java.util.*;

// TODO la ricerca tramite la data e volendo anche in base al gate...
public class ControllerTratte implements Initializable {

    @FXML
    private ScrollPane scroll;
    @FXML
    private JFXComboBox<String> searchMode;
    @FXML
    private JFXDatePicker dpk1, dpk2;
    @FXML
    private JFXButton cancelBtn;
    @FXML
    private TextField searchBar;

    private VBox box;
    private List<TrattaHbox> tratteHboxList;

    public void search(KeyEvent k){
        String searchMode = this.searchMode.getValue();
        String text = searchBar.getText();
        switch (searchMode){
            case "Partenza" -> { // non molto elegante, ma funziona...
                box.getChildren().removeIf(node -> !((TrattaHbox) node).getTratta().getAereoportoPartenza().contains(text)); // rimuovi se la card non contine il testo in searchBar
                tratteHboxList.forEach(node -> { //aggiunge i risultati validi che erano stati eliminati precedentemente (è utile quando viene cancellato un carattere)
                    if(node.getTratta().getAereoportoPartenza().contains(text) && !box.getChildren().contains(node))
                        box.getChildren().add(0, node);
                });
            }
            case "Arrivo" -> {
                box.getChildren().removeIf(node -> !((TrattaHbox) node).getTratta().getAereoportoArrivo().contains(text)); // rimuovi se la card non contine il testo in searchBar
                tratteHboxList.forEach(node -> { //aggiunge i risultati validi che erano stati eliminati precedentemente (è utile quando viene cancellato un carattere)
                    if(node.getTratta().getAereoportoArrivo().contains(text) && !box.getChildren().contains(node))
                        box.getChildren().add(0, node);
                }); }
            case "Compagnia" -> {
                box.getChildren().removeIf(node -> !((TrattaHbox) node).getTratta().getCompagnia().contains(text)); // rimuovi se la card non contine il testo in searchBar
                tratteHboxList.forEach(node -> { //aggiunge i risultati validi che erano stati eliminati precedentemente (è utile quando viene cancellato un carattere)
                    if(node.getTratta().getCompagnia().contains(text) && !box.getChildren().contains(node))
                        box.getChildren().add(0, node);
                });
            }
            case "NumeroVolo" -> {
                box.getChildren().removeIf(node -> !((TrattaHbox) node).getTratta().getNumeroVolo().contains(text)); // rimuovi se la card non contine il testo in searchBar
                tratteHboxList.forEach(node -> { //aggiunge i risultati validi che erano stati eliminati precedentemente (è utile quando viene cancellato un carattere)
                    if(node.getTratta().getNumeroVolo().contains(text) && !box.getChildren().contains(node))
                        box.getChildren().add(0, node);
                });
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchMode.getItems().addAll("Partenza", "Arrivo", "Compagnia", "NumeroVolo");
        searchMode.getSelectionModel().selectFirst();

        Random r = new Random();
        box = new VBox(5);
        box.setPadding(new Insets(5, 0, 5, 0));

        tratteHboxList = new ArrayList();

        for (int i = 0; i < 15; i++) {
            Tratta tratta = new Tratta("C23FAS2G",
                    new Date(r.nextLong()),
                    new Date(r.nextLong()),
                    "Vueling",
                    "A"+r.nextInt(31),
                    "Napoli",
                    "Barcellona");
            TrattaHbox t = new TrattaHbox(tratta);
            tratteHboxList.add(t);
            box.getChildren().add(t);
        }
        Tratta t1 = new Tratta("C23FAS2G",
                new Date(r.nextLong()),
                new Date(r.nextLong()),
                "Alitalia",
                "A"+r.nextInt(31),
                "Napoli",
                "Milano");
        Tratta t2 = new Tratta("AS21R542",
                new Date(r.nextLong()),
                new Date(r.nextLong()),
                "Ryanair",
                "C"+r.nextInt(31),
                "Londra",
                "Napoli");
        Tratta t3 = new Tratta("33AS7DY3",
                new Date(r.nextLong()),
                new Date(r.nextLong()),
                "Easyget",
                "B"+r.nextInt(31),
                "Napoli",
                "Dublino");
        TrattaHbox tt1 = new TrattaHbox(t1);
        TrattaHbox tt2 = new TrattaHbox(t2);
        TrattaHbox tt3 = new TrattaHbox(t3);
        tratteHboxList.add(tt1);
        tratteHboxList.add(tt2);
        tratteHboxList.add(tt3);
        box.getChildren().addAll(tt1,tt2,tt3);
        scroll.setContent(box);
    }
}
