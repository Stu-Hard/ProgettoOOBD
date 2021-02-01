package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import data.Dipendente;
import database.dao.CompagniaDao;
import database.dao.DipendentiDao;
import enumeration.DipendentiEnum;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utility.Validators;
import utility.WindowDragger;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerDipendentiAdd extends WindowDragger implements Initializable {
    @FXML
    JFXTextField nome, cognome, email, password;
    @FXML
    JFXButton addDipendente, annullaBtn;
    @FXML
    JFXComboBox<DipendentiEnum> ruolo;
    @FXML
    JFXComboBox<String> compagnia;


    public void close(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void moveWindow(MouseEvent mouseEvent) {
        super.moveWindow(mouseEvent);
    }

    @Override
    public void setOffset(MouseEvent mouseEvent) {
        super.setOffset(mouseEvent);
    }

    public void add(ActionEvent event) {

        if(email.validate()){
            //qui si può allora aggiungere il Dipendente
            try {
                new DipendentiDao().insert(new Dipendente(null, nome.getText(), cognome.getText(), email.getText(), password.getText(), ruolo.getValue(), new CompagniaDao().getByNome(compagnia.getValue())));
                //aggiungere un messaggio di conferma(?)
                close(event);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ruolo.getItems().add(DipendentiEnum.TicketAgent);
        ruolo.getItems().add(DipendentiEnum.Amministratore);
        ruolo.getItems().add(DipendentiEnum.AddettoImbarco);
        ruolo.getItems().add(DipendentiEnum.ResponsabileVoli);
        ruolo.getItems().add(DipendentiEnum.AddettoCheckIn);
        ruolo.getSelectionModel().selectFirst();

        // todo da cambiare
        compagnia.getItems().add("Alitalia");//
        compagnia.getItems().add("Ryanair");//
        compagnia.getItems().add("Easyjet");//
        compagnia.getItems().add("Vueling");//
        compagnia.getSelectionModel().selectFirst();//

        addDipendente.disableProperty().bind(new BooleanBinding() {
            {
                super.bind( nome.textProperty(),
                cognome.textProperty(),
                email.textProperty(),
                password.textProperty()
                );
            }
            @Override
            protected boolean computeValue() {
                return (nome.getText().isEmpty() ||
                        cognome.getText().isEmpty() ||
                        email.getText().isEmpty() ||
                        password.getText().isEmpty()
                );
            }
        });

        email.setValidators(new Validators().createEmailValidator("Email Invalida"));
    }
}
