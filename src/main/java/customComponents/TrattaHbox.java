package customComponents;

import com.jfoenix.controls.JFXButton;
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
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/uuuu");
    private DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

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
            dataPartenza.setText(dateFormat.format(tratta.getDataPartenza()));
            oraPartenza.setText(timeFormat.format(tratta.getOraPartenza()));
            ritardo.setText(tratta.getRitardo() + "''");
            gate.setText(tratta.getGate());
            compagnia.setText(tratta.getCompagnia());
        } catch (IOException e) {
            e.printStackTrace();
        }

        setOnMouseClicked(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TratteInfo.fxml"));
                fxmlLoader.setController(new WindowDragger());
                Parent parent = fxmlLoader.load();

                ((Label) parent.lookup("#partenza")).setText(tratta.getAereoportoPartenza());
                ((Label) parent.lookup("#arrivo")).setText(tratta.getAereoportoArrivo());
                ((Label) parent.lookup("#compagnia")).setText(tratta.getCompagnia());
                ((Label) parent.lookup("#dataPartenza")).setText(dataPartenza.getText() + " " +  oraPartenza.getText());
                if(tratta.getDataArrivo() != null && tratta.getOraArrivo() != null)
                    ((Label) parent.lookup("#dataArrivo")).setText(dateFormat.format(tratta.getDataArrivo()) + " " + timeFormat.format(tratta.getOraArrivo()));
                ((Label) parent.lookup("#ritardo")).setText(tratta.getRitardo() + "''");
                if (tratta.getGate() != null)
                    ((Label) parent.lookup("#gate")).setText(tratta.getGate());
                if (tratta.getAereo() != null)
                    ((Label) parent.lookup("#aereo")).setText(tratta.getAereo());
                ((Label) parent.lookup("#passeggeri")).setText(String.valueOf(tratta.getPasseggeri()));

                JFXButton closeBtn = (JFXButton) parent.lookup("#closeBtn");
                closeBtn.setOnAction(e -> closeBtn.getScene().getWindow().hide());

                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.TRANSPARENT);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
