package controllers;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import utility.WindowDragger;

public class ControllerConfirmCheckImbarco extends WindowDragger {

    @FXML
    JFXButton okButton, closeButton;
    @FXML
    Label passeggeroLabel, bagagliLabel, imbarcoCheckLabel;
@FXML
    FontAwesomeIcon iconCheckImbarco;
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

    public void setImbarcoCheckLabel(String checkOrImb){
        this.imbarcoCheckLabel.setText(checkOrImb);
    }

    public void setIconCheckImbarco(String name){
        this.iconCheckImbarco.setIconName(name);
        if(name == "BUG"){
            iconCheckImbarco.setFill(Color.valueOf("#146b25"));
        }else if(name == "ANCHOR"){
            iconCheckImbarco.setFill(Color.valueOf("#223edd"));
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
