package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
    JFXButton verificaButton, inviaButton, numeroBagagliButton;
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

    }

    public void invia(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CheckIn_ImbarcoConfirm.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
        String bagagli = spinnerBagagli.getValue().toString();
        ControllerConfirmCheckImbarco controller = fxmlLoader.getController();
        controller.setBagagli(bagagli);
        controller.setPasseggero(nome.getText() +" "+ cognome.getText());

        erroreLabel.setVisible(false);
        inviaButton.setVisible(false);
        bagagliHbox.setVisible(false);
        cartaImbarcoHbox.setVisible(false);
        spinnerBagagli.setVisible(false);
        bagagliLabel.setVisible(false);
        numeroBagagliButton.setVisible(false);
    }

    public void imbarcaBagagli(ActionEvent event) {
        bagagliHbox.setVisible(true);
        inviaButton.setVisible(true);
    }
}
