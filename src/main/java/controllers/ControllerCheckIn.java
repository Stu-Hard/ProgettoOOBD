package controllers;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCheckIn implements Initializable{

    @FXML
    Spinner<Integer> spinnerBagagli;
    @FXML
    JFXButton verificaButton, inviaButton, numeroBagagliButton, cancelBtn;
    @FXML
    HBox bagagliHbox, cartaImbarcoHbox;
    @FXML
    Label erroreLabel, nome, cognome, bagagliLabel;
    @FXML
    JFXTextField bigliettoTextField;

    String spinnerClass =  Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL;
    int initialValue = 0;
    int minValue = 0;
    int maxValue = 5;
    SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(minValue,maxValue,initialValue);

    public void verify(ActionEvent event) {

        if(bigliettoTextField.getText().equals("a")) {
            cartaImbarcoHbox.setVisible(true);               // dai il risultato
            spinnerBagagli.setVisible(true);
            bagagliLabel.setVisible(true);
            numeroBagagliButton.setVisible(true);
            erroreLabel.setVisible(false);
        }else{
            erroreLabel.setVisible(true);           // dai errore
            inviaButton.setVisible(false);
            bagagliHbox.setVisible(false);
            cartaImbarcoHbox.setVisible(false);
            spinnerBagagli.setVisible(false);
            bagagliLabel.setVisible(false);
            numeroBagagliButton.setVisible(false);

        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        spinnerBagagli.setValueFactory(svf);
        spinnerBagagli.getStyleClass().add(spinnerClass);

        erroreLabel.setVisible(false);
        inviaButton.setVisible(false);
        bagagliHbox.setVisible(false);
        cartaImbarcoHbox.setVisible(false);
        spinnerBagagli.setVisible(false);
        bagagliLabel.setVisible(false);
        numeroBagagliButton.setVisible(false);



        inviaButton.disableProperty().bind(new BooleanBinding() {
            {
                super.bind(
                        //todo abilita solo se ha inserito tutti i textField dei kg dei bagagli
                );
            }

            @Override
            protected boolean computeValue() {
                return false;
            }
        });
        verificaButton.disableProperty().bind(new BooleanBinding() {
            {
                super.bind(
                        bigliettoTextField.textProperty()
                );
            }

            @Override
            protected boolean computeValue() {
                return bigliettoTextField.getText().isEmpty();
            }
        });

    }



    public void invia(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CheckIn_ImbarcoConfirm.fxml"));
        Parent root = fxmlLoader.load();
        String bagagli = spinnerBagagli.getValue().toString();


        ControllerConfirmCheckImbarco controller = fxmlLoader.getController();
        controller.setImbarcoCheckLabel("CheckIn");
        controller.setIconCheckImbarco("BUG");
        controller.setBagagli(bagagli);
        controller.setPasseggero(nome.getText() +" "+ cognome.getText());


        JFXAlert<Void> alert = new JFXAlert(nome.getScene().getWindow());
        alert.setOverlayClose(true);
        alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        alert.setContent(root);
        alert.initModality(Modality.NONE);
        alert.showAndWait();

        erroreLabel.setVisible(false);
        inviaButton.setVisible(false);
        bagagliHbox.setVisible(false);
        cartaImbarcoHbox.setVisible(false);
        spinnerBagagli.setVisible(false);
        bagagliLabel.setVisible(false);
        numeroBagagliButton.setVisible(false);
    }

    public void imbarcaBagagli(ActionEvent event) {

        //todo imbarcarli con il codice..
        bagagliHbox.setVisible(true);
        inviaButton.setVisible(true);
    }


    public void restart(ActionEvent event) {
        bigliettoTextField.setText("");
        erroreLabel.setVisible(false);
        inviaButton.setVisible(false);
        bagagliHbox.setVisible(false);
        cartaImbarcoHbox.setVisible(false);
        spinnerBagagli.setVisible(false);
        bagagliLabel.setVisible(false);
        numeroBagagliButton.setVisible(false);

    }
}
