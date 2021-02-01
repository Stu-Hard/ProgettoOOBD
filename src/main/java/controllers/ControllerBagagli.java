package controllers;

import com.jfoenix.controls.JFXTextField;
import data.Bagaglio;
import data.Biglietto;
import database.dao.BagaglioDao;
import database.dao.BigliettoDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerBagagli implements Initializable {
    /**
     * @param bagagliList e' il componente grafico dove verrano visulizzati i bagagli
     * @param hboxDati e' il componente grafico dove verrano visulizzati i Dati del biglietto e Cliente
     * @param vboxBagagli contiene bagagliList e il Text("Bagagli")
     * @param serarchBar e' la barra di ricerca
     * @param nessunBiglietto messaggi di "errore"
     * @param nBiglietto,nVolo,nome,documento,codiceFiscale dati del cliente e del suo biglietto
     * */
    @FXML
    private ListView<Bagaglio> bagagliList;
    ObservableList list = FXCollections.observableArrayList();
    @FXML
    private HBox hboxDati;
    @FXML
    private VBox vboxBagagli;
    @FXML
    private TextField searchBar;
    @FXML
    private Label nessunBiglietto;
    @FXML
    private Label nBiglietto, nVolo, nome, documento, codiceFiscale;

    /**
     * Inizializza il file .fxml
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hboxDati.setVisible(false);
    }

    /**
     * Nasconde gli elementi del file .fxml e setta la
     * barra di ricerca vuota, così da poter avere la finestra
     * allo stato iniziale
     */
    public void canc(ActionEvent event) {
        searchBar.setText("");
        hboxDati.setVisible(false);
        vboxBagagli.setVisible(false);
        nessunBiglietto.setVisible(false);
    }
    /**
     * cercaBagagli è una funzione che e' attivata dal JfxButton nel file
     * Bagagli.fxml, controlla se il contenuto della barra di ricerca e'
     * vuoto, in tal caso rende visibile il messaggio di ricerca nulla,
     * altrimenti controlla se esiste un biglietto nel DB con il codice inserito, in
     * caso positivo rende visibili i dati del biglietto e del cliente che
     * lo ha acquistato e i relativi bagagli imbarcati, altrimenti mostra
     * un messaggio di nessun biglietto corrispondente
     * */
    public void cercaBagagli(ActionEvent event) {

       if(!searchBar.getText().isEmpty()){
           Integer codiceBiglietto =  Integer.parseInt(searchBar.getText());
           BagaglioDao bgDao = new BagaglioDao();
           BigliettoDao bDao = new BigliettoDao();
           try {
               if(bDao.getBigliettoByCodice(codiceBiglietto) == null){
                   nessunBiglietto.setVisible(true);
                   nessunBiglietto.setText("Nessun biglietto corrispondente");
                   vboxBagagli.setVisible(false);
                   hboxDati.setVisible(false);
               }else{
                   Biglietto biglietto = bDao.getBigliettoByCodice(codiceBiglietto);
                   nBiglietto.setText(biglietto.getCodiceBiglietto().toString());
                   nVolo.setText(biglietto.getNumeroVolo());
                   nome.setText(biglietto.getCliente().getNome());
                   documento.setText(biglietto.getCliente().getDocumento());
                   codiceFiscale.setText(biglietto.getCliente().getCodiceFiscale());
                   hboxDati.setVisible(true);

                   bagagliList.getItems().clear();
                   bagagliList.getItems().addAll(bgDao.getBagagliByCodBiglietto(codiceBiglietto));
                   vboxBagagli.setVisible(true);
                   nessunBiglietto.setVisible(false);
               }
           } catch (SQLException sqlException) {
               sqlException.printStackTrace();
           }
       }else{
           nessunBiglietto.setText("Non hai cercato nulla");
           nessunBiglietto.setVisible(true);
           vboxBagagli.setVisible(false);
           hboxDati.setVisible(false);
       }
    }
}