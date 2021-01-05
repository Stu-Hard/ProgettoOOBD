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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button bottone;
    @FXML
    private Pane pannello;
    @FXML
    private VBox lpBox;

    private AnchorPane contentPane;
    @FXML
    private Pane contentPane2;

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

    public void enterTransition(MouseEvent e){
        Label l = (Label) e.getSource();
        l.setStyle(l.getStyle().replace("-fx-background-color: #1746d4;",
                "-fx-background-color: #163eb8;"));
    }
    public void exitTransition(MouseEvent e){
        Label l = (Label) e.getSource();
        if (l != selectedFrame)
            l.setStyle(l.getStyle().replace("-fx-background-color: #163eb8;",
                "-fx-background-color: #1746d4;"));
    }
    public void setFrame(MouseEvent e){
        for(Node i : lpBox.getChildren()){
            i.setStyle(i.getStyle().replace("-fx-background-color: #163eb8;",
                    "-fx-background-color: #1746d4;"));
        }

        Label b = (Label) e.getSource();
        b.setStyle(b.getStyle()
                .replace("-fx-background-color: #1746d4;",
                        "-fx-background-color: #163eb8;"));
        selectedFrame = b;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       try {
           contentPane = FXMLLoader.load(Controller.class.getResource("fxml/Tratte.fxml"));
           contentPane2.getChildren().add(contentPane);
       } catch (IOException ex) {
           ex.printStackTrace();
       }
    }
}
