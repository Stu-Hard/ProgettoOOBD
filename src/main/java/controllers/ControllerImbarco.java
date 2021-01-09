package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class ControllerImbarco implements Initializable {
    @FXML
    private VBox vboxLabel;
    @FXML
    private JFXButton bagagliButton;
    @FXML
    private Spinner<Integer> spinnerBagagli;
    @FXML
    private Label labelBagagli;
    @FXML
    private HBox hboxCodiciBagagli;
    @FXML
    private Label erroreLabel;
    @FXML
    private HBox hboxCartaImbarco;
    @FXML
    private JFXButton inviaButton;

    /*
    Valori per inizializzare lo spinner dei bagagli e la sua rispettiva inizializzazione
     */
    int initialValue = 0;
    int minValue = 0;
    int maxValue = 5;
    SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(minValue,maxValue,initialValue);



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        spinnerBagagli.setValueFactory(svf);
        //mettere quasi tutti gli elementi invisibili perchè in teoria ancora non generati..
        hboxCodiciBagagli.setVisible(false);
        labelBagagli.setVisible(false);
        bagagliButton.setVisible(false);
        spinnerBagagli.setVisible(false);
        erroreLabel.setVisible(false);
        hboxCartaImbarco.setVisible(false);
        inviaButton.setVisible(false);
    }


    //funzione per creare un codice del bagaglio in base a quanti sono
    int bagagli;
    public void createBagaglioCode(ActionEvent actionEvent) {

        bagagli = spinnerBagagli.getValue();
        if(!vboxLabel.getChildren().isEmpty()) {
          vboxLabel.getChildren().remove(0, vboxLabel.getChildren().size());
        }
            for (int i = 0; i < bagagli; i++) {
                String randomString = generateRandomString(7, asciiChars);
                Label c = new Label(randomString);
                vboxLabel.getChildren().add(c);
            }
    }


    /*START: funzione che restituisce una stringa random e attributi che lo permettono */
    String asciiUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String asciiLowerCase = asciiUpperCase.toLowerCase();
    String digits = "1234567890";
    String asciiChars = asciiUpperCase + asciiLowerCase + digits;

    private  String generateRandomString(int length, String seedChars) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        Random rand = new Random();
        while (i < length) {
            sb.append(seedChars.charAt(rand.nextInt(seedChars.length())));
            i++;
        }
        return sb.toString();
    }
    /*END: funzione che restituisce una stringa random e attributi che lo permettono */

    //dopo la verifica dei bagagli
    public void verificaBagagli(ActionEvent actionEvent) {

        // if codice è corretto allora..

        hboxCodiciBagagli.setVisible(true);
        labelBagagli.setVisible(true);
        bagagliButton.setVisible(true);
        spinnerBagagli.setVisible(true);
        hboxCartaImbarco.setVisible(true);
        inviaButton.setVisible(true);

        /* else
        * erroreLabel.setVisible(true)
        *
        * */
    }


}

