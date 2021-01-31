package controllers;

import com.jfoenix.controls.JFXTextField;
import data.Bagaglio;
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }




    public void canc(ActionEvent event) {
    }

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
               }else{

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
       }
    }
}
