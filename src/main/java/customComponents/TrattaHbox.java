package customComponents;

import com.jfoenix.controls.JFXButton;
import controllers.ControllerTratte;
import controllers.ControllerTratteInfo;
import data.Tratta;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utility.WindowDragger;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class TrattaHbox extends HBox {
    private Tratta tratta;
    private Label partenza;
    private Label arrivo;
    private Label numeroVolo;
    private Label dataPartenza;
    private Label oraPartenza;
    private Label ritardo;
    private Label gate;
    private Label compagnia;

    public Tratta getTratta() {
        return tratta;
    }

    public TrattaHbox(Tratta tratta) {
        this.tratta = tratta;
        try {
            getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/TratteHbox.fxml")));

            partenza = (Label) lookup("#partenza");
            arrivo = (Label) lookup("#arrivo");
            numeroVolo = (Label) lookup("#numeroVolo");
            dataPartenza = (Label) lookup("#dataPartenza");
            oraPartenza = (Label) lookup("#oraPartenza");
            ritardo = (Label) lookup("#ritardo");
            gate = (Label) lookup("#gate");
            compagnia = (Label) lookup("#compagnia");

            partenza.setText(tratta.getAereoportoPartenza());
            arrivo.setText(tratta.getAereoportoArrivo());
            numeroVolo.setText(tratta.getNumeroVolo());
            dataPartenza.setText(tratta.getDataPartenzaFormatted());
            oraPartenza.setText(tratta.getOraPartenzaFormatted());
            ritardo.setText(tratta.getRitardo() + "''");
            gate.setText(tratta.getGate());
            compagnia.setText(tratta.getCompagnia());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
