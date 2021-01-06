package customComponents;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import enumeration.CodeEnum;
import enumeration.GateStatus;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class GateCard extends Pane{
    private Label gateCode, partenza, arrivo, stato, tempo; // le label principali
    private Pane trattaPane; // pannello con partenza arrivo e tempo
    public VBox code; // pannello con code di imbarco

    // setter e getter del testo delle label
    public String getGateCode() {
        return gateCode.getText();
    }

    public String getPartenza() {
        return partenza.getText();
    }

    public String getArrivo() {
        return arrivo.getText();
    }

    public GateStatus getStato() {
        if(stato.getText().contains("Occupato")) return GateStatus.OCCUPATO;
        else if(stato.getText().contains("Libero")) return GateStatus.LIBERO;
        else return GateStatus.CHIUSO;
    }

    public String getTempo() {
        return tempo.getText();
    }

    public void setGateCode(String gateCode) {
        this.gateCode.setText(gateCode);
    }

    public void setPartenza(String partenza) {
        this.partenza.setText(partenza);
    }

    public void setArrivo(String arrivo) {
        this.arrivo.setText(arrivo);
    }
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
                setStyle("-fx-background-color: #e0e0e0; -fx-background-radius: 25px;");
                setDisable(true);
                ((FontAwesomeIcon) this.stato.getGraphic()).setIcon(FontAwesomeIconName.MINUS_CIRCLE);
            }
            case LIBERO -> { // se libero sfondo grigio e abilita
                this.stato.setText("Libero");
                setDisable(false);
                setStyle("-fx-background-color: #e0e0e0; -fx-background-radius: 25px;");
                ((FontAwesomeIcon) this.stato.getGraphic()).setIcon(FontAwesomeIconName.CHECK);
            }
            case OCCUPATO -> { // se occupato sfondo giallo (si può cambiare volendo magari anche mettendo un gradient...) e visualizza le label
                this.stato.setText("Occupato");
                setDisable(false);
                setStyle("-fx-background-color: yellow; -fx-background-radius: 25px;");
                ((FontAwesomeIcon) this.stato.getGraphic()).setIcon(FontAwesomeIconName.CLOCK_ALT);
            }
        }

        if (stato == GateStatus.CHIUSO || stato == GateStatus.LIBERO){
            trattaPane.setVisible(false);
            code.setVisible(false);
        }
    }

    // ablilita le code di imbarco
    public void enableCode(CodeEnum... code){
        for (CodeEnum c : code)
            ((Label) lookup("#" + c.toString())).setDisable(false);
    }
    // disabilita le code
    public void disableCode(CodeEnum ... code){
        for (CodeEnum c : code)
            ((Label) lookup("#" + c.toString())).setDisable(true);
    }


    // costruttore con sola chiave primaria. Inizializza il gate come libero
    public GateCard(String gateCode){
        try {
            loadComponents(); // carica i componenti dal file fxml
            setGateCode(gateCode);
            setStato(GateStatus.LIBERO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // costruttore con pià informazioni
    public GateCard(String gateCode, String partenza,
                    String arrivo, int tempo, GateStatus stato){
        try {
            loadComponents();
            setGateCode(gateCode);
            setPartenza(partenza);
            setArrivo(arrivo);
            setTempo(tempo);
            setStato(stato);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
