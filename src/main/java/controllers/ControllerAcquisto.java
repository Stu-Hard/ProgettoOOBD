package controllers;

import com.jfoenix.controls.JFXButton;
import data.Tratta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import utility.WindowDragger;

public class ControllerAcquisto extends WindowDragger {
    private Tratta tratta;

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
    }

    @FXML
    public void close(ActionEvent e){
        ((JFXButton) e.getSource()).getScene().getWindow().hide();
    }

    @FXML
    public void buy(ActionEvent e){
        // todo crea un nuovo utente e lo inserisce nel db
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
