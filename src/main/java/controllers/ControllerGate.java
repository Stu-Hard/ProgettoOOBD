package controllers;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
import customComponents.GateCard;
import customComponents.GateCardPopup;
import customComponents.TrattaHbox;
import data.CodaImbarco;
import data.Gate;
import data.Tratta;
import database.dao.CodaImbarcoDao;
import database.dao.GateDao;
import database.dao.TrattaDao;
import enumeration.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

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
            }
        }

        if(flowPane.getChildren().isEmpty()) {
            nessunGate.setVisible(true);
        }else{
            nessunGate.setVisible(false);
        }*/
    }

    private void showImpostaTratta(GateCard gCard, GateCardPopup popup){
        JFXListView<TrattaHbox> l = new JFXListView<>();
        l.setPrefSize(820, 300);
        try {
            new TrattaDao().getAllTratte().forEach(t -> {
                l.getItems().add(new TrattaHbox(t));
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        JFXAlert<Void> alert = new JFXAlert(flowPane.getScene().getWindow());
        alert.setOverlayClose(true);
        alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        alert.setContent(l);
        alert.initModality(Modality.NONE);
        l.setOnMouseClicked( e1 -> {
            TrattaHbox t = l.getSelectionModel().getSelectedItem();
            if (t != null){
                gCard.getGate().setTratta(t.getTratta());
                // todo aggiorna la colonna gate in tratta nel db
                //gCard.updateLabels();
                //alert.hide();
                impostaCode(gCard, alert, popup);
            }
        });
        alert.showAndWait();
    }

    private void impostaCode(GateCard gCard, JFXAlert<Void> alert, GateCardPopup popup){
        VBox v = new VBox();
        VBox v2 = new VBox();
        JFXCheckBox diversamenteAbili = new JFXCheckBox("Diversamente abili");
        JFXCheckBox famiglie = new JFXCheckBox("Famiglie");
        JFXCheckBox business = new JFXCheckBox("Business");
        JFXCheckBox priority = new JFXCheckBox("Priority");
        JFXCheckBox economy = new JFXCheckBox("Economy");

        JFXButton conferma = new JFXButton("Conferma");
        conferma.setStyle("-fx-font-size: 15; -fx-background-radius: 0");

        v.getChildren().addAll(diversamenteAbili, famiglie, business, priority, economy);
        v.getChildren().forEach(n -> {
            n.setStyle("-fx-font-size: 15");
            ((JFXCheckBox) n).setSelected(true);
        });
        v.setSpacing(15);
        v.setPadding(new Insets(20));
        v2.getChildren().addAll(v, conferma);

        conferma.setOnAction(e -> {
            if (diversamenteAbili.isSelected()) gCard.getGate().addCoda(new CodaImbarco(CodeEnum.DIVERSAMENTE_ABILI));
            if (famiglie.isSelected()) gCard.getGate().addCoda(new CodaImbarco(CodeEnum.FAMIGLIE));
            if (business.isSelected()) gCard.getGate().addCoda(new CodaImbarco(CodeEnum.BUSINESS));
            if (priority.isSelected()) gCard.getGate().addCoda(new CodaImbarco(CodeEnum.PRIORITY));
            if (economy.isSelected()) gCard.getGate().addCoda(new CodaImbarco(CodeEnum.ECONOMY));

            try {
                new GateDao().update(gCard.getGate());
                CodaImbarcoDao codaImbarcoDao = new CodaImbarcoDao();
                gCard.getGate().getCodeImbarco().forEach(codaImbarco -> {
                    try {
                        codaImbarcoDao.add(codaImbarco, gCard.getGate());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
                gCard.updateLabels();
                popup.setOccupato();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            alert.hide();
        });
        alert.setContent(v2);
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


        try {
            CodaImbarcoDao codaImbarcoDao = new CodaImbarcoDao();
            new GateDao().getGateCodes().forEach(g ->{
                try {
                    if (g.getStatus() == GateStatus.OCCUPATO)
                        g.setCodeImbarco(codaImbarcoDao.getByGateAndTratta(g));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                GateCard gCard = new GateCard(g);
                GateCardPopup popup = new GateCardPopup(gCard);
                popup.setImpostaTratta(e -> showImpostaTratta(gCard, popup));
                flowPane.getChildren().add(gCard);
            });
        } catch (SQLException e){
            e.printStackTrace();
        }

        scroll.setContent(flowPane);

        nessunGate.setVisible(flowPane.getChildren().isEmpty());
    }
}
