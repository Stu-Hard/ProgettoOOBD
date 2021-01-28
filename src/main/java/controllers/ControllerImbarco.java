package controllers;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
import data.Aeroporto;
import data.Biglietto;
import data.Cliente;
import data.Tratta;
import database.dao.BigliettoDao;
import database.dao.ClienteDao;
import database.dao.TrattaDao;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

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
    private JFXButton inviaButton, verificaButton;
    @FXML
    JFXTextField codiceTextField;
    @FXML
    Label nome, cognome, imbarcoCheckLabel, codiceBiglietto, tratta,
            classe, posto, gate, cf, documentoNumero;


    /*
    Valori per inizializzare lo spinner dei bagagli e la sua rispettiva inizializzazione
     */
    int initialValue = 0;
    int minValue = 0;
    int maxValue = 5;
    SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(minValue,maxValue,initialValue);
    //per settare le due frecce
    String spinnerClass =  Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL;

    //funzione per creare un codice del bagaglio in base a quanti sono
    int bagagli;
    public void createBagaglioCode(ActionEvent actionEvent) {

        bagagli = spinnerBagagli.getValue();
        if(!vboxLabel.getChildren().isEmpty()) {
          vboxLabel.getChildren().remove(0, vboxLabel.getChildren().size());
        }
        if(!(bagagli == 0)){
            for (int i = 0; i < bagagli; i++) {
                String randomString = generateRandomString(7, asciiChars);
                Label c = new Label(randomString);
                vboxLabel.getChildren().add(c);
            }
        }else{
            Label c = new Label("Nessun bagaglio");
            vboxLabel.getChildren().add(c);
        }
        inviaButton.setVisible(true);
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


    Biglietto biglietto = null;
    //dopo la verifica dei bagagli
    public void verificaBagagli(ActionEvent actionEvent) throws SQLException {

        //todo if codice è corretto allora..


        BigliettoDao bDao = new BigliettoDao();
        biglietto = bDao.getBigliettoByCodice(Integer.parseInt(codiceTextField.getText()));

        if(biglietto != null){

            TrattaDao trattaDao = new TrattaDao();
            Tratta trattastring = trattaDao.getByNumeroVolo(biglietto.getNumeroVolo());

            ClienteDao clienteDao = new ClienteDao();
            Cliente cliente = clienteDao.getClienteByCf(biglietto.getcF());

            Aeroporto partenza = trattastring.getAereoportoPartenza();
            Aeroporto arrivo = trattastring.getAereoportoArrivo();

            if (biglietto.isCheckIn() == true && trattastring.getGate() != null) {


                codiceBiglietto.setText(String.valueOf(biglietto.getCodiceBiglietto()));
                tratta.setText(partenza.getCitta() + " -> " + arrivo.getCitta());
                classe.setText(String.valueOf(biglietto.getClasse()));
                posto.setText(biglietto.getFila() +"-" +biglietto.getPosto());        // dai il risultato
                gate.setText(trattastring.getGate());
                cf.setText(biglietto.getcF());
                documentoNumero.setText(cliente.getCarta());
                nome.setText(cliente.getNome());
                cognome.setText(cliente.getCognome());

                hboxCodiciBagagli.setVisible(true);         //dai risultato
                labelBagagli.setVisible(true);
                bagagliButton.setVisible(true);
                spinnerBagagli.setVisible(true);
                hboxCartaImbarco.setVisible(true);
            }else if(biglietto.isCheckIn() == false){
                    erroreLabel.setText("Errore -> non e' ancora stato fatto il checkIn");
                    erroreLabel.setVisible(true);
            }else{
                erroreLabel.setText("Il gate non e' ancora aperto");
                erroreLabel.setVisible(true);

                codiceBiglietto.setText(String.valueOf(biglietto.getCodiceBiglietto()));
                tratta.setText(partenza.getCitta() + " -> " + arrivo.getCitta());
                classe.setText(String.valueOf(biglietto.getClasse()));
                posto.setText(biglietto.getFila() +"-" +biglietto.getPosto());        // dai il risultato
                gate.setText(trattastring.getGate());
                cf.setText(biglietto.getcF());
                documentoNumero.setText(cliente.getCarta());
                nome.setText(cliente.getNome());
                cognome.setText(cliente.getCognome());

                hboxCartaImbarco.setVisible(true);
            }
        }else{
            erroreLabel.setText("Errore --> non trovato");
            erroreLabel.setVisible(true);
        }
    }

    public void inviaCodes(ActionEvent actionEvent) throws IOException {
        List<Node> codiciBagagli = new ArrayList<>();
        if(!vboxLabel.getChildren().isEmpty() && !(spinnerBagagli.getValue() == 0)){
            for(int i = 0; i < vboxLabel.getChildren().size(); i++){
                codiciBagagli.add(vboxLabel.getChildren().get(i));
                System.out.println(codiciBagagli.get(i));
                //todo qui dovrebbe mandarli al Database
            }
        }

        /*message dialog controller*/

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CheckIn_ImbarcoConfirm.fxml"));
        AnchorPane root = fxmlLoader.load();

        Integer bagagliQuantita = codiciBagagli.size();

        ControllerConfirmCheckImbarco controller = fxmlLoader.getController();
        controller.setImbarcoCheckLabel("Imbarco");
        controller.setIconCheckImbarco("ANCHOR");
        controller.setBagagli(bagagliQuantita.toString());
        controller.setPasseggero(nome.getText() +" "+ cognome.getText());

        try {
            biglietto.setImbarcato(true);
            new BigliettoDao().update(biglietto);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        JFXAlert<Void> alert = new JFXAlert(vboxLabel.getScene().getWindow());
        alert.setOverlayClose(true);
        alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        alert.setContent(root);
        alert.initModality(Modality.NONE);
        alert.showAndWait();
        //stage.setScene(scene);
        //stage.show();

        /*message dialog controller*/

        //rende invisibili ogni volta gli elementi della finestra
        hboxCodiciBagagli.setVisible(false);
        labelBagagli.setVisible(false);
        bagagliButton.setVisible(false);
        spinnerBagagli.setVisible(false);
        hboxCartaImbarco.setVisible(false);
        codiceTextField.setText("");
        codiceTextField.requestFocus();
        inviaButton.setVisible(false);
        /* cancella i vecchi codici */
        if(!vboxLabel.getChildren().isEmpty()) {
            vboxLabel.getChildren().remove(0, vboxLabel.getChildren().size());
        }

    }

    /*cambia il colore quando ci entra il mouse */
    public void changeColor(MouseEvent mouseEvent) {
        if(mouseEvent.getSource() == verificaButton) {
            verificaButton.setStyle("-fx-background-color: #2c7ee2");
        }else if(mouseEvent.getSource() == inviaButton){
            inviaButton.setStyle("-fx-background-color: #00D600");
        }else if(mouseEvent.getSource() == bagagliButton){
            bagagliButton.setStyle("-fx-background-color:  #ff5858");
        }
    }
    /*cambia il colore quando ci esce il mouse */
    public void backingColor(MouseEvent mouseEvent) {
        if(mouseEvent.getSource() == verificaButton) {
        verificaButton.setStyle("-fx-background-color:  #1d487c");
        }else if(mouseEvent.getSource() == inviaButton){
            inviaButton.setStyle("-fx-background-color:  rgb(0,120,0)");
        }else if(mouseEvent.getSource() == bagagliButton){
            bagagliButton.setStyle("-fx-background-color:  #c93c3c");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        spinnerBagagli.setValueFactory(svf);
        spinnerBagagli.getStyleClass().add(spinnerClass);
        //mettere quasi tutti gli elementi invisibili perchè in teoria ancora non generati..
        hboxCodiciBagagli.setVisible(false);
        labelBagagli.setVisible(false);
        bagagliButton.setVisible(false);
        spinnerBagagli.setVisible(false);
        erroreLabel.setVisible(false);
        hboxCartaImbarco.setVisible(false);
        inviaButton.setVisible(false);


        verificaButton.disableProperty().bind(new BooleanBinding() {
            {
                super.bind(
                        codiceTextField.textProperty()
                );
            }

            @Override
            protected boolean computeValue() {
                return codiceTextField.getText().isEmpty();
            }
        });

    }

    //evento del cancelBtn
    public void restart(ActionEvent event) {
        codiceTextField.setText("");
        hboxCodiciBagagli.setVisible(false);
        labelBagagli.setVisible(false);
        bagagliButton.setVisible(false);
        spinnerBagagli.setVisible(false);
        erroreLabel.setVisible(false);
        hboxCartaImbarco.setVisible(false);
        inviaButton.setVisible(false);

    }
}
