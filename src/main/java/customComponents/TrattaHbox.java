package customComponents;

import data.Tratta;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class TrattaHbox extends HBox {
    private Tratta tratta;
    private Label partenza;
    private Label arrivo;
    private Label numeroVolo;
    private Label dataPartenza;
    private Label oraPartenza;
    private Label oraArrivo;
    private Label gate;
    private Label compagnia;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");

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
            oraArrivo = (Label) lookup("#oraArrivo");
            gate = (Label) lookup("#gate");
            compagnia = (Label) lookup("#compagnia");

            partenza.setText(tratta.getAereoportoPartenza());
            arrivo.setText(tratta.getAereoportoArrivo());
            numeroVolo.setText(tratta.getNumeroVolo());
            dataPartenza.setText(dateFormat.format(tratta.getDataPartenza()));
            oraPartenza.setText(timeFormat.format(tratta.getOraPartenza()));
            if (tratta.getOraArrivo() != null)
                oraArrivo.setText(timeFormat.format(tratta.getOraArrivo()));
            gate.setText(tratta.getGate());
            compagnia.setText(tratta.getCompagnia());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
