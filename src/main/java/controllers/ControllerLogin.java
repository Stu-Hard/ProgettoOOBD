package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utility.WindowDragger;

import java.io.IOException;


public class ControllerLogin extends WindowDragger { // per dubbi su windowDragger vai a utility.windowDragger
    @FXML
    private Pane pannello;

    public void Exit(MouseEvent e) {
        Platform.exit();
    }

    public void LoginAction(ActionEvent e) {
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

    public void EnterPressed(KeyEvent keyEvent) throws Exception{

        if(keyEvent.getCode() == KeyCode.ENTER){
            LoginAction(null);
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
