package customComponents;

import data.Gate;
import data.Tratta;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import enumeration.CodeEnum;
import enumeration.GateStatus;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

// Non definitivo, bisognerebbe mettere un costruttore tramite la classe Gate. Per esperimenti va più che bene.
public class GateCard extends Pane{
    private Gate gate;

    private Label gateCode, partenza, arrivo, stato, tempo; // le label principali
    private Pane trattaPane; // pannello con partenza arrivo e tempo
    public VBox code; // pannello con code di imbarco

    // tempo stimato di imbarco in minuti
    public void setTempo(Integer tempo) {
        if (tempo == null)
            this.tempo.setText("Tempo stimato: --''");
        else
            this.tempo.setText("Tempo stimato: " + tempo + "''");
    }

    // setta lo stato del gate vedi enumeration.GateStatus
    public void setStato(GateStatus stato) {
        switch (stato){
            case CHIUSO -> { // se chiuso allora sfondo grigio e disabilita
                this.stato.setText("Chiuso");
                setStyle("-fx-background-color: #e0e0e0; -fx-background-radius: 25px; -fx-opacity: 0.4");
                ((FontAwesomeIcon) this.stato.getGraphic()).setIcon(FontAwesomeIconName.MINUS_CIRCLE);
                trattaPane.setVisible(false);
                code.setVisible(false);
            }
            case LIBERO -> { // se libero sfondo grigio e abilita
                this.stato.setText("Libero");
                setStyle("-fx-background-color: #e0e0e0; -fx-background-radius: 25px; -fx-opacity: 1");
                ((FontAwesomeIcon) this.stato.getGraphic()).setIcon(FontAwesomeIconName.CHECK);
                trattaPane.setVisible(false);
                code.setVisible(false);
            }
            case OCCUPATO -> { // se occupato sfondo giallo (si può cambiare volendo magari anche mettendo un gradient...) e visualizza le label
                this.stato.setText("Occupato");
                setStyle("-fx-background-color: yellow; -fx-background-radius: 25px; -fx-opacity: 1");
                ((FontAwesomeIcon) this.stato.getGraphic()).setIcon(FontAwesomeIconName.CLOCK_ALT);
                trattaPane.setVisible(true);
                code.setVisible(true);
            }
        }
    }

    // ablilita le code di imbarco
    public void enableCode(CodeEnum... code){
        for (CodeEnum c : code)
            (lookup("#" + c.toString())).setDisable(false);
    }
    // disabilita le code
    public void disableCode(CodeEnum ... code){
        for (CodeEnum c : code)
            (lookup("#" + c.toString())).setDisable(true);
    }

    // costruttore con pià informazioni
    public GateCard(Gate gate){
        try {
            this.gate = gate;
            loadComponents();
            updateLabels();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateLabels(){
        gateCode.setText(gate.getGateCode());
        if (gate.getStatus() == GateStatus.OCCUPATO) {
            partenza.setText(gate.getTratta().getAereoportoPartenza().getCitta());
            arrivo.setText(gate.getTratta().getAereoportoArrivo().getCitta());
        }
        setStato(gate.getStatus());
        setTempo(null); // tempo stimato per l'imbarco.
    }

    // carica i componenti dal file fxml
    private void loadComponents() throws IOException{
        getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/GateCard.fxml")));
        this.gateCode = ((Label) lookup("#gateCode")); // "lockup" serve per ricercare i componenti tramite l'id
        this.partenza = ((Label) lookup("#partenza"));
        this.arrivo = ((Label) lookup("#arrivo"));
        this.tempo = ((Label) lookup("#tempo"));
        this.stato = (Label) lookup("#stato");
        this.trattaPane = (Pane) lookup("#trattaPane");
        this.code = (VBox) lookup("#code");
    }

    public Gate getGate() {
        return gate;
    }
}
