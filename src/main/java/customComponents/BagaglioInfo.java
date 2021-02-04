package customComponents;

import data.Bagaglio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class BagaglioInfo extends HBox {
    @FXML
    private Label peso, codiceBagaglio, prezzo;
    private Bagaglio bagaglio;

    public BagaglioInfo(Bagaglio bagaglio){
        this.bagaglio = bagaglio;
        try{
            getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/BagaglioInfo.fxml")));

            peso = (Label) lookup("#peso");
            codiceBagaglio = (Label) lookup("#codiceBagaglio");
            prezzo = (Label) lookup("#prezzo");
            setLabels();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setLabels() {
        peso.setText(String.valueOf(bagaglio.getPeso()));
        codiceBagaglio.setText(bagaglio.getCodiceBagaglio());
        prezzo.setText(String.valueOf(bagaglio.getPrezzo()));
    }
}
