package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerTratte implements Initializable {

    @FXML
    private JFXComboBox<String> searchMode;
    private FlowPane scrollPan;
    @FXML
    private ScrollPane scroll;

    public void close(MouseEvent e){
        Platform.exit();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchMode.getItems().addAll( "Citta'", "Compagnia");
        scrollPan = new FlowPane();
        scrollPan.setHgap(20);
        scrollPan.setVgap(15);
        scrollPan.setStyle("-fx-background-color: transparent");
        for (int i = 0; i < 20; i++) {
            JFXButton l =  new JFXButton("ciao");
            l.setPrefSize(260, 200);

            if (i%2 == 0)
                l.setStyle("-fx-background-color: red; -fx-background-radius: 25;");
            else
                l.setStyle("-fx-background-color: blue; -fx-background-radius: 25;");
            scrollPan.getChildren().add(l);
        }
        scroll.setContent(scrollPan);
    }
}
