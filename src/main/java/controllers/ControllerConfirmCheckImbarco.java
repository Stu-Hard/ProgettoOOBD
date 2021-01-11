package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import utility.WindowDragger;

public class ControllerConfirmCheckImbarco extends WindowDragger {

    @FXML
    JFXButton okButton, closeButton;
    @FXML
    Label passeggeroLabel, bagagliLabel;

    @FXML
    private void close(ActionEvent e){
        closeButton.getScene().getWindow().hide();
    }

    public void setPasseggero(String passeggero){
        this.passeggeroLabel.setText(passeggero);
    }

    public void setBagagli(String bagagli){
        this.bagagliLabel.setText(bagagli);
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
