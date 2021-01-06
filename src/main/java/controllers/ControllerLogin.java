package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utility.WindowDragger;

import java.io.IOException;


public class ControllerLogin extends WindowDragger { // per dubbi su windowDragger vai a utility.windowDragger
    @FXML
    private Pane pannello;

    public void close(MouseEvent e) {
        Platform.exit();
    }

    public void login(ActionEvent e) {
        try {
            Stage mainStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainPane.fxml"));
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            pannello.getScene().getWindow().hide();
            mainStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void setOffset(MouseEvent e) {
        super.setOffset(e);
    } // override dei metodi di windowDragger

    @Override
    public void moveWindow(MouseEvent e) {
        super.moveWindow(e);
    } // override dei metodi di windowDragger
}
