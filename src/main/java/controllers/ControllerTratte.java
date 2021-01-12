package controllers;

import com.jfoenix.controls.*;
import customComponents.TrattaHbox;
import data.Tratta;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
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

    @FXML
    public void mouseClick(MouseEvent e){
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
            Label elimina = new Label("Elimina");
            JFXListView<Label> l = new JFXListView();
            l.getItems().add(elimina);
            JFXPopup popup = new JFXPopup(l);
            popup.show(listView.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void add(ActionEvent e){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchMode.getItems().addAll("Partenza", "Arrivo", "Compagnia", "NumeroVolo");
        searchMode.getSelectionModel().selectFirst();

        Random r = new Random();

        tratteHboxList = new ArrayList();

        //for (int i = 0; i < 15; i++) {
        //    Tratta tratta = new Tratta("C23FAS2G",
        //            LocalDate.of(r.nextInt(151)+ 1900, r.nextInt(12)+1, r.nextInt(28)+1),
        //            LocalTime.of(r.nextInt(24), r.nextInt(59)),
        //            30,
        //            "Vueling",
        //            "Napoli",
        //            "Barcellona");
        //    TrattaHbox t = new TrattaHbox(tratta);
        //    tratteHboxList.add(t);
        //    listView.getItems().add(t);
        //    //box.getChildren().add(t);
        //}
        //scroll.setContent(listView);
    }
}
