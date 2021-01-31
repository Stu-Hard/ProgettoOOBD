package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import utility.WindowDragger;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMainPane extends WindowDragger implements Initializable {
    @FXML
    private VBox lpBox;
    @FXML
    private Pane contentPane;

    @FXML
    private JFXButton tratteBtn, gateBtn, checkInBtn,
            imbarcoBtn, compagnieBtn, aereiBtn,
            dipendentiBtn, statisticheBtn, tabelloneBtn;
    @FXML
    private Pane trattePane, gatePane, checkInPane,
            imbarcoPane, compagniePane, bagagliPane,
            dipendentiPane, statistichePane, tabellonePane;

    private ControllerTratte controllerTratte;
    private ControllerGate controllerGate;

    private ControllerDipendenti controllerDipendenti;
    private ControllerCompagnie controllerCompagnie;

    private ControllerStatistiche controllerStatistiche;


    public void close(MouseEvent e){
        Platform.exit();
    }
    public void closeButton(ActionEvent e){ Platform.exit(); }

    public void setFrame(MouseEvent e){
        for(Node i : lpBox.getChildren()){
            i.setStyle(i.getStyle().replace("-fx-background-color: #18283f;",
                    ""));
        }

        JFXButton b = (JFXButton) e.getSource();
        b.setStyle(b.getStyle() + "-fx-background-color: #18283f;");

        if (tratteBtn.equals(b)) {
            trattePane.toFront();
            controllerTratte.refresh();
        } else if (gateBtn.equals(b)) {
            gatePane.toFront();
            controllerGate.refresh();
        } else if (checkInBtn.equals(b)) {
            checkInPane.toFront();
        } else if (imbarcoBtn.equals(b)) {
            imbarcoPane.toFront();
        } else if (compagnieBtn.equals(b)) {
            compagniePane.toFront();
            controllerCompagnie.refresh();
        } else if (aereiBtn.equals(b)) {
            bagagliPane.toFront();
        } else if (dipendentiBtn.equals(b)) {
            dipendentiPane.toFront();
            controllerDipendenti.refresh();
        } else if (statisticheBtn.equals(b)) {
            statistichePane.toFront();
            controllerStatistiche.refresh();
        } else if (tabelloneBtn.equals(b)) {
            tabellonePane.toFront();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader tratteLoader = new FXMLLoader(getClass().getResource("/fxml/Tratte.fxml"));
            trattePane.getChildren().add(
                    tratteLoader.load()
            );
            controllerTratte = tratteLoader.getController();

            FXMLLoader gateLoader = new FXMLLoader(getClass().getResource("/fxml/Gate.fxml"));
            gatePane.getChildren().add(
                    gateLoader.load()
            );
            controllerGate = gateLoader.getController();

            checkInPane.getChildren().add(
                    FXMLLoader.load(getClass().getResource("/fxml/CheckIn.fxml"))
            );
            imbarcoPane.getChildren().add(
                    FXMLLoader.load(getClass().getResource("/fxml/Imbarco.fxml"))
            );

            FXMLLoader compagnieLoader = new FXMLLoader(getClass().getResource("/fxml/Compagnie.fxml"));
            compagniePane.getChildren().add(
                    compagnieLoader.load()
            );
            controllerCompagnie = compagnieLoader.getController();

            FXMLLoader dipendentiLoader = new FXMLLoader(getClass().getResource("/fxml/Dipendenti.fxml"));
            dipendentiPane.getChildren().add(
                    dipendentiLoader.load()
            );
            controllerDipendenti = dipendentiLoader.getController();

            FXMLLoader bagagliLoader = new FXMLLoader(getClass().getResource("/fxml/Bagagli.fxml"));
            bagagliPane.getChildren().add(
                    bagagliLoader.load()
            );
            FXMLLoader statisticheLoader = new FXMLLoader(getClass().getResource("/fxml/Statistiche.fxml"));
            statistichePane.getChildren().add(
                    statisticheLoader.load()
            );
            controllerStatistiche = statisticheLoader.getController();

            tabellonePane.getChildren().add(
                    FXMLLoader.load(getClass().getResource("/fxml/Tabellone.fxml"))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setOffset(MouseEvent e) {
        super.setOffset(e);
    }

    @Override
    public void moveWindow(MouseEvent e) {
        super.moveWindow(e);
    }
}
