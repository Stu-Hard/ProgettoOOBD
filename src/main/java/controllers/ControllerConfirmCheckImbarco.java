package controllers;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import utility.WindowDragger;

public class ControllerConfirmCheckImbarco extends WindowDragger {

    @FXML
    Label passeggeroLabel, bagagliLabel, imbarcoCheckLabel;
@FXML
    FontAwesomeIcon iconCheckImbarco;


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

}
