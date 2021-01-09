package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import customComponents.TrattaHbox;
import data.Tratta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;


import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

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

    public void datePick(ActionEvent e){ // è una prova solo per il dpk a sinistra
        box.getChildren().removeIf(node ->
                ((TrattaHbox) node)
                .getTratta()
                .getDataPartenza()
                .isBefore(((JFXDatePicker) e.getSource()).getValue())
        );
    }

    public void canc(ActionEvent e){
        searchBar.setText("");
        search(null);
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
                    LocalDate.of(r.nextInt(151)+ 1900, r.nextInt(12)+1, r.nextInt(28)+1),
                    LocalTime.of(r.nextInt(24), r.nextInt(59)),
                    "Vueling",
                    "A"+r.nextInt(31),
                    "Napoli",
                    "Barcellona");
            TrattaHbox t = new TrattaHbox(tratta);
            tratteHboxList.add(t);
            box.getChildren().add(t);
        }
        scroll.setContent(box);
    }
}
