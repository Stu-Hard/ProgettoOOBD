package controllers;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerTabellone implements Initializable {

    @FXML
    private VBox voloBox;
    @FXML
    private VBox destinazioneBox;
    @FXML
    private VBox partenzaBox;
    @FXML
    private VBox statoBox;

    @FXML
    private Button partenzeBtn, arriviBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Quando viene premuto il pulsante partenze setta lo sfondo nero su partenze e lo sfondo bianco su arrivi
        partenzeBtn.setOnAction(e -> {
            partenzeBtn.setStyle(
                    partenzeBtn.getStyle()
                            .replace("-fx-background-color: f0f0f0", "-fx-background-color: black")
                            .replace("-fx-text-fill: black", "-fx-text-fill: white")
            );
            ((FontAwesomeIcon) partenzeBtn.getGraphic()).setFill(Color.WHITE);
            arriviBtn.setStyle(
                    arriviBtn.getStyle()
                            .replace("-fx-background-color: black", "-fx-background-color: f0f0f0")
                            .replace("-fx-text-fill: white", "-fx-text-fill: black")
            );
            ((FontAwesomeIcon) arriviBtn.getGraphic()).setFill(Color.BLACK);
        });

        // come sopra ma al contrario
        arriviBtn.setOnAction(e -> {
            arriviBtn.setStyle(
                    arriviBtn.getStyle()
                            .replace("-fx-background-color: f0f0f0", "-fx-background-color: black")
                            .replace("-fx-text-fill: black", "-fx-text-fill: white")
            );
            ((FontAwesomeIcon) arriviBtn.getGraphic()).setFill(Color.WHITE);
            partenzeBtn.setStyle(
                    partenzeBtn.getStyle()
                            .replace("-fx-background-color: black", "-fx-background-color: f0f0f0")
                            .replace("-fx-text-fill: white", "-fx-text-fill: black")
            );
            ((FontAwesomeIcon) partenzeBtn.getGraphic()).setFill(Color.BLACK);
        });
    }

   /* @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("ok");
        for (int i = 0; i < 5; i++) {
            Label l = new Label("ciao");
            l.setPrefSize(217, 30);
            l.setStyle("-fx-background-color: white; -fx-spacing: 2");

            voloBox.getChildren().add(l);
        }
        for (int i = 0; i < 5; i++) {
            Label l = new Label("ciao");
            l.setPrefSize(217, 30);
            l.setStyle("-fx-background-color: white; -fx-spacing: 2");

            destinazioneBox.getChildren().add(l);
        }for (int i = 0; i < 5; i++) {
            Label l = new Label("ciao");
            l.setPrefSize(217, 30);
            l.setStyle("-fx-background-color: white; -fx-spacing: 2");

            partenzaBox.getChildren().add(l);
        }for (int i = 0; i < 5; i++) {
            Label l = new Label("ciao");
            l.setPrefSize(217, 30);
            l.setStyle("-fx-background-color: white; -fx-spacing: 2");

            statoBox.getChildren().add(l);
        }


    }*/

}
