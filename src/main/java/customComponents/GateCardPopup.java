package customComponents;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import database.dao.TrattaDao;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

import java.sql.SQLException;

public class GateCardPopup extends JFXPopup {

    private JFXButton chiudi;
    private JFXButton libera;
    private JFXButton impostaTratta;
    private JFXButton terminaImbarco;
    private VBox v;

    public GateCardPopup(GateCard gCard){
        v = new VBox();

        chiudi = new JFXButton("Chiudi");
        chiudi.setStyle("-fx-background-radius: 0");
        libera = new JFXButton("Libera");
        libera.setStyle("-fx-background-radius: 0");
        impostaTratta = new JFXButton("Imposta Tratta");
        impostaTratta.setStyle("-fx-background-radius: 0");

        chiudi.setPrefWidth(100);
        libera.setPrefWidth(100);
        impostaTratta.setPrefWidth(100);

        terminaImbarco = new JFXButton("Termina imbarco");
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

        terminaImbarco.setOnAction(e ->{
            gCard.getGate().end();
            gCard.updateLabels();
            setLibero();
        });

        setLibero();

        setPopupContent(v);
        gCard.setOnMouseClicked(m ->{
            if (m.getButton() == MouseButton.SECONDARY){
                show(gCard.getScene().getWindow(), m.getSceneX(), m.getSceneY(), JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, 0, 0);
            }
        });
    }

    public void setOccupato(){
        v.getChildren().clear();
        v.getChildren().add(terminaImbarco);
    }
    public void setLibero(){
        v.getChildren().clear();
        v.getChildren().addAll(
                chiudi,
                libera,
                impostaTratta
        );
    }

    public VBox getVBox() { return v; }

    public void setImpostaTratta(EventHandler<ActionEvent> e) {
        impostaTratta.setOnAction(e);
    }
}
