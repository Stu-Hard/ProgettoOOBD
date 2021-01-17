package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import data.Tratta;
import database.dao.TrattaDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utility.WindowDragger;

import java.io.IOException;
import java.sql.SQLException;


public class ControllerTratteInfo extends WindowDragger {
    private Tratta tratta;

    @FXML
    private Label partenza, arrivo, compagnia, dataPartenza, durata,
    ritardo, gate, codiceAereo, passeggeri;
    @FXML
    private JFXCheckBox completata;
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
        durata.setText(tratta.getDurataVolo() + "''");
        ritardo.setText(tratta.getRitardo() + "''");
        if (tratta.getGate() != null)
            gate.setText(tratta.getGate());
        if (tratta.getAereo() != null)
            codiceAereo.setText(tratta.getAereo().getCodice());
        completata.setSelected(tratta.isConclusa());
        try {
            passeggeri.setText(String.valueOf(new TrattaDao().getPasseggeri(tratta)));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
