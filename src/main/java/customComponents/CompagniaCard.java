package customComponents;

import data.Compagnia;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import java.io.IOException;


public class CompagniaCard extends Pane {
    private Compagnia compagnia;
    private Label nome, sigla, nazione,
            pesoMassimo, prezzoBagagli;
    private Circle iconCircle;

    public CompagniaCard(Compagnia compagnia) {
        this.compagnia = compagnia;
        try {
            loadComponents();
            setLabels();
            try {
                Image img = new Image(getClass().getResource("/img/icons/" + compagnia.getNome() + ".png").toString());
                iconCircle.setFill(new ImagePattern(img));
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLabels(){
        nome.setText(compagnia.getNome());
        sigla.setText("(" + compagnia.getSigla() + ")");
        nazione.setText(compagnia.getNazione());
        pesoMassimo.setText("Peso max: " + String.format("%.2f", compagnia.getPesoMassimo()) + "kg");
        prezzoBagagli.setText("Prezzo bagagli: " + String.format("%.2f", compagnia.getPrezzoBagagli()) + "$");
    }

    private void loadComponents() throws IOException {
        getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/CompagniaCard.fxml")));
        this.nome = ((Label) lookup("#nome"));
        this.sigla = ((Label) lookup("#sigla"));
        this.nazione = ((Label) lookup("#nazione"));
        this.pesoMassimo = ((Label) lookup("#pesoMassimo"));
        this.prezzoBagagli = (Label) lookup("#prezzoBagagli");
        this.iconCircle = (Circle) lookup("#iconCircle");
    }
}
