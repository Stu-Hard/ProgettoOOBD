package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import data.Biglietto;
import data.CodaImbarco;
import data.Tratta;
import database.dao.BigliettoDao;
import database.dao.CodaImbarcoDao;
import database.dao.TrattaDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import utility.WindowDragger;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;


public class ControllerTratteInfo extends WindowDragger {
    private Tratta tratta;

    @FXML
    private Label partenza, arrivo, compagnia, dataPartenza, durata,
    ritardo, gate, numeroVolo, passeggeri, posti;
    @FXML
    private JFXCheckBox completata;
    @FXML
    private JFXButton closeBtn;
    private Window mainWindow;
    @FXML
    private JFXButton acquistaBtn;
    @FXML
    private AnchorPane codePane;

    @FXML
    private void close(ActionEvent e){
        closeBtn.getScene().getWindow().hide();
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
    }

    @FXML
    public void buyTicket(ActionEvent e){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/BigliettiAcquisto.fxml"));
            Parent parent = fxmlLoader.load();
            ControllerAcquisto controller = fxmlLoader.getController();
            controller.setTratta(tratta);
            controller.setMainWindow(mainWindow);
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
        numeroVolo.setText(tratta.getNumeroVolo());
        completata.setSelected(tratta.isConclusa());
        try {
            passeggeri.setText(new TrattaDao().getPasseggeri(tratta) + "");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        posti.setText(tratta.getPosti() + "");

        try {
            List<CodaImbarco> l = new CodaImbarcoDao().getByTratta(tratta);
            l.forEach(c -> {
                Label lbl1 = (Label) codePane.lookup("#" + c.getClasse() + "1");
                Label lbl11 = (Label) codePane.lookup("#" + c.getClasse() + "11");
                Label lbl111 = (Label) codePane.lookup("#" + c.getClasse() + "111");
                if (c.getOraApertura() != null)
                    lbl1.setText(c.getOraApertura().format(DateTimeFormatter.ofPattern("HH:mm")));
                lbl11.setText(c.getTempoStimato() + "");
                lbl111.setText(((c.getTempoEffettivo() != null)?c.getTempoEffettivo():"-") + "");
            });
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

    public void setMainWindow(Window window) {
        this.mainWindow = window;
    }

    public void initialize(Tratta t) {
        setTratta(t);
        setLabels();
        acquistaBtn.setDisable(completata.isSelected() || Integer.parseInt(passeggeri.getText()) >= tratta.getPosti());
    }
}
