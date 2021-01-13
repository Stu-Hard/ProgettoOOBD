package controllers;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
import customComponents.GateCard;
import customComponents.TrattaHbox;
import data.Gate;
import data.Tratta;
import database.dao.GateDao;
import database.dao.TrattaDao;
import enumeration.*;
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
            new GateDao().getGateCodes().forEach(g ->{
                GateCard gCard = new GateCard(g);
                JFXPopup popup = new JFXPopup();

                VBox v = new VBox();

                JFXButton chiudi = new JFXButton("Chiudi");
                chiudi.setStyle("-fx-background-radius: 0");
                JFXButton libera = new JFXButton("Libera");
                libera.setStyle("-fx-background-radius: 0");
                JFXButton impostaTratta = new JFXButton("Imposta Tratta");
                impostaTratta.setStyle("-fx-background-radius: 0");

                chiudi.setPrefWidth(100);
                libera.setPrefWidth(100);
                impostaTratta.setPrefWidth(100);

                JFXButton terminaImbarco = new JFXButton("Termina imbarco");
                terminaImbarco.setStyle("-fx-background-radius: 0");
                //terminaImbarco.setPrefWidth(100);

                chiudi.setOnAction(e -> {
                    gCard.getGate().close();
                    gCard.updateLabels();
                    impostaTratta.setDisable(true);
                });

                libera.setOnAction(e ->{
                    gCard.getGate().open();
                    gCard.updateLabels();
                    impostaTratta.setDisable(false);
                });

                impostaTratta.setOnAction(e -> {
                    JFXListView<TrattaHbox> l = new JFXListView<>();
                    try {
                        new TrattaDao().getAllTratte().forEach(t -> {
                            l.getItems().add(new TrattaHbox(t));
                        });
                        l.setPrefSize(820, 300);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    JFXAlert<Void> alert = new JFXAlert(gCard.getScene().getWindow());
                    alert.setOverlayClose(true);
                    alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                    alert.setContent(l);
                    alert.initModality(Modality.NONE);
                    l.setOnMouseClicked( e1 -> {
                        TrattaHbox t = l.getSelectionModel().getSelectedItem();
                        if (t != null){
                            gCard.getGate().setTratta(t.getTratta());
                            // todo aggiorna la colonna gate in tratta nel db
                            gCard.updateLabels();
                            alert.hide();
                            v.getChildren().removeAll(chiudi, libera, impostaTratta);
                            v.getChildren().add(terminaImbarco);
                        }
                    });
                    alert.showAndWait();
                });

                terminaImbarco.setOnAction(e ->{
                    gCard.getGate().end();
                    gCard.updateLabels();
                    v.getChildren().remove(terminaImbarco);
                    v.getChildren().addAll(chiudi, libera, impostaTratta);
                });

                v.getChildren().addAll(
                        chiudi,
                        libera,
                        impostaTratta
                );

                popup.setPopupContent(v);
                gCard.setOnMouseClicked(m ->{
                    if (m.getButton() == MouseButton.SECONDARY){
                        popup.show(gCard.getScene().getWindow(), m.getSceneX(), m.getSceneY(), JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, 0, 0);
                    }
                });
                flowPane.getChildren().add(gCard);



            });
        } catch (SQLException e){
            e.printStackTrace();
        }

        scroll.setContent(flowPane);

        nessunGate.setVisible(flowPane.getChildren().isEmpty());
    }
}
