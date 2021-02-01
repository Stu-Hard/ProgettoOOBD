package controllers;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import data.*;
import database.dao.BagaglioDao;
import database.dao.BigliettoDao;
import database.dao.TrattaDao;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import org.w3c.dom.Text;
import utility.IdFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class ControllerCheckIn implements Initializable{

    @FXML
    Spinner<Integer> spinnerBagagli;
    @FXML
    JFXButton verificaButton, inviaButton, numeroBagagliButton, cancelBtn;
    @FXML
    HBox bagagliHbox, cartaImbarcoHbox;
    @FXML
    Label erroreLabel, nome, cognome, bagagliLabel, codiceBiglietto,
            tratta, classe, posto, gate, cf, documentoNumero, numeroVolo;
    @FXML
    JFXTextField bigliettoTextField;
    @FXML
    VBox nBagagli, nPeso, datiCheckIn;

    String spinnerClass =  Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL;
    int initialValue = 0;
    int minValue = 0;
    int maxValue = 5;
    SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(minValue,maxValue,initialValue);
    Biglietto biglietto = null;

    public void verify(ActionEvent event) throws SQLException {

            BigliettoDao bDao = new BigliettoDao();
                //todo forse bisogna cambiare int in String e quindi generare codici biglietti.
                biglietto = bDao.getBigliettoByCodice(Integer.parseInt(bigliettoTextField.getText()));
                if(biglietto != null) {

                        TrattaDao trattaDao = new TrattaDao();

                        Tratta trattastring = trattaDao.getByNumeroVolo(biglietto.getNumeroVolo());

                        Aeroporto partenza = trattastring.getAereoportoPartenza();
                        Aeroporto arrivo = trattastring.getAereoportoArrivo();

                        codiceBiglietto.setText(String.valueOf(biglietto.getCodiceBiglietto()));
                        tratta.setText(partenza.getCitta() + " -> " + arrivo.getCitta());
                        classe.setText(String.valueOf(biglietto.getClasse()));
                        posto.setText(biglietto.getPosto() + "");        // dai il risultato
                        gate.setText(trattastring.getGate());
                        cf.setText(biglietto.getCliente().getCodiceFiscale());
                        documentoNumero.setText(biglietto.getCliente().getDocumento());
                        nome.setText(biglietto.getCliente().getNome().split("-")[0]);
                        cognome.setText(biglietto.getCliente().getNome().split("-")[1]);
                        numeroVolo.setText(biglietto.getNumeroVolo());

                        if(trattastring.isConclusa()){
                            erroreLabel.setText("Errore -> tratta conclusa.");
                            erroreLabel.setVisible(true);

                            cartaImbarcoHbox.setVisible(true);
                        }else {
                            if (!biglietto.isCheckIn()) {
                                cartaImbarcoHbox.setVisible(true);
                                spinnerBagagli.setVisible(true);
                                bagagliLabel.setVisible(true);                                 // rendi visibile
                                numeroBagagliButton.setVisible(true);
                                erroreLabel.setVisible(false);
                            } else {
                                erroreLabel.setText("Errore -> gia' fatto checkIn");
                                erroreLabel.setVisible(true);

                                cartaImbarcoHbox.setVisible(true);
                            }
                        }
                } else {
                    erroreLabel.setText("Errore -> biglietto non trovato");
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


        try {
            biglietto.setCheckIn(true);
            new BigliettoDao().update(biglietto);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        JFXAlert<Void> alert = new JFXAlert(nome.getScene().getWindow());
        alert.setOverlayClose(true);
        alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        alert.setContent(root);
        alert.initModality(Modality.NONE);
        alert.showAndWait();

        for(int i = 0; i < spinnerBagagli.getValue(); i++){
            BagaglioDao bgDao = new BagaglioDao();
            try {
                bgDao.insert(new Bagaglio(new IdFactory().randomString(8), Float.parseFloat(tfList.get(i).getText()), new BigliettoDao().getCompagnia(biglietto).getPrezzoBagagli()*Float.parseFloat(tfList.get(i).getText()),biglietto));
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }

        bigliettoTextField.setText("");
        erroreLabel.setVisible(false);
        inviaButton.setVisible(false);
        bagagliHbox.setVisible(false);
        cartaImbarcoHbox.setVisible(false);
        spinnerBagagli.setVisible(false);
        bagagliLabel.setVisible(false);
        numeroBagagliButton.setVisible(false);
    }

    List<TextField> tfList = new LinkedList();

    public void imbarcaBagagli(ActionEvent event) {
        Integer bagagli = spinnerBagagli.getValue();
        if(!(nBagagli.getChildren().isEmpty() && nPeso.getChildren().isEmpty())){
            nBagagli.getChildren().remove(0, nBagagli.getChildren().size());
            nPeso.getChildren().remove(0, nPeso.getChildren().size());
        }
        tfList.clear();
        for(int i = 1; i <= bagagli; i++){
            nBagagli.getChildren().add(new Label(i + " bagaglio"));
            TextField tf = new TextField();
            tf.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    tf.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });
            tfList.add(tf);
            nPeso.getChildren().add(tf);
        }

        inviaButton.disableProperty().bind(new BooleanBinding() {
            {
                tfList.forEach(i -> super.bind(i.textProperty()));
            }

            @Override
            protected boolean computeValue() {
                Boolean temp = false;
                for (TextField tf: tfList) {
                    if (tf.getText().isEmpty()){
                        temp = true;
                    }
                }
                return temp;
            }
        });

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
