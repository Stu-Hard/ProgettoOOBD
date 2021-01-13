package controllers;

import com.jfoenix.controls.JFXButton;
import data.Tratta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utility.WindowDragger;

import java.io.IOException;


public class ControllerTratteInfo extends WindowDragger {
    private Tratta tratta;

    @FXML
    private Label partenza, arrivo, compagnia, dataPartenza, dataArrivo,
    ritardo, gate, aereo, passeggeri;
    @FXML
    private JFXButton closeBtn;

    @FXML
    private void close(ActionEvent e){
        closeBtn.getScene().getWindow().hide();
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
        setLabels();
    }

    @FXML
    public void buyTicket(ActionEvent e){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/BigliettiAcquisto.fxml"));
            Parent parent = fxmlLoader.load();
            ControllerAcquisto controller = fxmlLoader.getController();
            controller.setTratta(tratta);

            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);

            ((JFXButton) e.getSource()).getScene().getWindow().hide();
            stage.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


    }

    private void setLabels(){
        partenza.setText(tratta.getAereoportoPartenza().getCitta());
        arrivo.setText(tratta.getAereoportoArrivo().getCitta());
        compagnia.setText(tratta.getCompagnia().getNome());
        dataPartenza.setText(tratta.getDataPartenzaFormatted() + " " +  tratta.getOraPartenzaFormatted());
        //if(tratta.getDataArrivo() != null && tratta.getOraArrivo() != null)
        //    dataArrivo.setText(tratta.getDataArrivoFormatted() + " " + tratta.getOraArrivoFormatted());
        ritardo.setText(tratta.getRitardo() + "''");
        if (tratta.getGate() != null)
            gate.setText(tratta.getGate());
        if (tratta.getAereo() != null)
            aereo.setText(tratta.getAereo().getCodice());
        //passeggeri.setText(String.valueOf(tratta.getPasseggeri()));
    }

    @Override
    public void setOffset(MouseEvent e) {
        super.setOffset(e);
    }

    @Override
    public void moveWindow(MouseEvent e) {
        super.moveWindow(e);
    }
}
