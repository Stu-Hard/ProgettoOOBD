package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import customComponents.GateCard;
import data.Gate;
import data.Tratta;
import database.dao.GateDao;
import enumeration.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
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

    private List<GateCard> gateCardList = null; // lista che contiene tutti i gate.
                                        // N.B. gateCardList è diverso da flowPane.getChildren(). Indovinello: Perch'è sono diversi?
                                        // (Suggerimento) per diversi non si intende il tipo.
    @FXML
    private Label nessunGate;

    // Filtri sullo stato: occupato ecc...
    public void statusFilter(ActionEvent e){
        //GateStatus status;
        //JFXCheckBox source = (JFXCheckBox) e.getSource();
//
        //if (occupatiCheck.equals(source)) status = GateStatus.OCCUPATO;  //
        //else if (liberiCheck.equals(source)) status = GateStatus.LIBERO; // Decide qual'è lo stato da filtrare
        //else status = GateStatus.CHIUSO;                                 //
//
        //if (!(source).isSelected())
        //    flowPane.getChildren().removeIf(n -> ((GateCard) n).getStato() == status); // cancella se lo stato viene deselezionato
        //else
        //    gateCardList.forEach(n ->{
        //        if(n.getStato() == status)
        //            flowPane.getChildren().add(0, n); // aggiungi in cima se lo stato viene selezionato
        //    });
        //search(null); // "integra" il filtro con il testo sulla barra di ricerca
    }

    public void search(KeyEvent k){
        /*String searchMode = this.searchMode.getValue();
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
        }*/
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

        GateDao gateDao = new GateDao();
        List<Gate> gateList = null;
        try {
            gateList = gateDao.getGateCodes();
        } catch (SQLException e){
            e.printStackTrace();
        }

        if (gateList != null){
            gateList.forEach(g ->{
                flowPane.getChildren().add(new GateCard(g));
            });
        }

        scroll.setContent(flowPane);

        if(flowPane.getChildren().isEmpty()) nessunGate.setVisible(true);
        else nessunGate.setVisible(false);
    }
}
