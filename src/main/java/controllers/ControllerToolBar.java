package controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utility.WindowDragger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerToolBar extends WindowDragger implements Initializable {

    @FXML
    AnchorPane panel, container;

    @Override
    public void setOffset(MouseEvent e) {
        super.setOffset(e);
    }

    @Override
    public void moveWindow(MouseEvent e) {
        super.moveWindow(e);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AnchorPane mainPane = null;
        try {
            mainPane = FXMLLoader.load(getClass().getResource("/fxml/MainPane.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        container.getChildren().add(mainPane);
    }

    public void min(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void max(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setFullScreenExitHint("");
        if(stage.isFullScreen()){
            stage.setFullScreen(false);
        }else{
            stage.setFullScreen(true);
        }

    }

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
