package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.awt.*;
import java.io.IOException;

public class ControllerLogin {
    @FXML
    private Pane pannello;

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

    public void login(ActionEvent e){
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
}
