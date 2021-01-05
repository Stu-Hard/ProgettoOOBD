package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Pane pannello;
    @FXML
    private VBox lpBox;
    @FXML
    private Pane contentPane;

    @FXML
    private Label tratteBtn, gateBtn, checkInBtn,
            imbarcoBtn, compagnieBtn, aereiBtn,
            impiegatiBtn, statisticheBtn, tabelloneBtn;
    @FXML
    private Pane trattePane, gatePane, checkInPane,
            imbarcoPane, compagniePane,  aereiPane,
            impiegatiPane, statistichePane, tabellonePane;

    static Label selectedFrame = null;


    private double xOffset = 0.0;
    private double yOffset = 0.0;

    public void setOffset(MouseEvent e){
        pannello.requestFocus();
        xOffset = pannello.getScene().getWindow().getX() - e.getScreenX();
        yOffset = pannello.getScene().getWindow().getY() - e.getScreenY();
    }
    public void moveWindow(MouseEvent e) {
        Window w = pannello.getScene().getWindow();
        w.setX(e.getScreenX() + xOffset);
        w.setY(e.getScreenY() + yOffset);
    }

    public void close(MouseEvent e){
        Platform.exit();
    }
    public void closeButton(ActionEvent e){ Platform.exit(); }

    public void setFrame(MouseEvent e){
        for(Node i : lpBox.getChildren()){
            i.setStyle(i.getStyle().replace("-fx-background-color: #18283f;",
                    ""));
        }

        Label b = (Label) e.getSource();
        b.setStyle(b.getStyle() + "-fx-background-color: #18283f;");
        selectedFrame = b;

        if (tratteBtn.equals(b)) {
            trattePane.toFront();
        } else if (gateBtn.equals(b)) {
            gatePane.toFront();
        } else if (checkInBtn.equals(b)) {
            checkInPane.toFront();
        } else if (imbarcoBtn.equals(b)) {
            imbarcoPane.toFront();
        } else if (compagnieBtn.equals(b)) {
            compagniePane.toFront();
        } else if (aereiBtn.equals(b)) {
            aereiPane.toFront();
        } else if (impiegatiBtn.equals(b)) {
            impiegatiPane.toFront();
        } else if (statisticheBtn.equals(b)) {
            statistichePane.toFront();
        } else if (tabelloneBtn.equals(b)) {
            tabellonePane.toFront();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            trattePane.getChildren().add(
                    FXMLLoader.load(getClass().getResource("/fxml/Tratte.fxml"))
            );
            gatePane.getChildren().add(
                    FXMLLoader.load(getClass().getResource("/fxml/Gate.fxml"))
            );
            checkInPane.getChildren().add(
                    FXMLLoader.load(getClass().getResource("/fxml/CheckIn.fxml"))
            );
            imbarcoPane.getChildren().add(
                    FXMLLoader.load(getClass().getResource("/fxml/Imbarco.fxml"))
            );
            compagniePane.getChildren().add(
                    FXMLLoader.load(getClass().getResource("/fxml/Compagnie.fxml"))
            );
            //aereiPane = FXMLLoader.load(getClass().getResource("fxml/Aerei.fxml"));
            //impiegatiPane.getChildren().add(
            //        FXMLLoader.load(getClass().getResource("/fxml/Impiegati.fxml"))
            //);
            //statistichePane = FXMLLoader.load(getClass().getResource("fxml/Tratte.fxml"));
            tabellonePane.getChildren().add(
                    FXMLLoader.load(getClass().getResource("/fxml/Tabellone.fxml"))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
