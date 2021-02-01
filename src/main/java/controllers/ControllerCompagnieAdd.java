package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import customComponents.Toast;
import data.Compagnia;
import database.dao.CompagniaDao;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import utility.WindowDragger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerCompagnieAdd extends WindowDragger implements Initializable {
    @FXML
    private JFXTextField nome, sigla, nazione, pesoMassimo, prezzoBagagli;

    @FXML
    private JFXButton conferma;
    @FXML
    private Circle img;
    private File imageFile;
    @FXML
    private Label trascinaLbl;

    private Pane mainPane;

    public void setMainPane(Pane mainPane) {
        this.mainPane = mainPane;
    }

    @FXML
    public void conferma(ActionEvent e){

        Toast toast = new Toast(mainPane);
        try {
            Compagnia compagnia = new Compagnia(
                    nome.getText(),
                    sigla.getText(),
                    nazione.getText(),
                    Float.parseFloat(prezzoBagagli.getText()),
                    Float.parseFloat(pesoMassimo.getText())
            );
            new CompagniaDao().insert(compagnia);
            toast.show("Compagnia aggiunta con successo");
        } catch (SQLException | NumberFormatException throwables) {
            throwables.printStackTrace();
            toast.show("Errore compagnia non aggiunta");
        }

        if (imageFile != null){
            try {
                URI dest = new File(  "src/main/resources/img/icons/" + nome.getText() + ".png").toURI();
                Files.copy(
                        Paths.get(imageFile.getAbsolutePath()),
                        Paths.get(dest),
                        StandardCopyOption.COPY_ATTRIBUTES
                );
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        ((Node) e.getSource()).getScene().getWindow().hide();
    }

    @FXML
    public void annulla(ActionEvent e){
        ((Node) e.getSource()).getScene().getWindow().hide();
    }




    @Override
    public void setOffset(MouseEvent e) {
        super.setOffset(e);
    }

    @Override
    public void moveWindow(MouseEvent e) {
        super.moveWindow(e);
    }

    @FXML
    public void onDragOver(DragEvent e){
        if (e.getDragboard().hasFiles()){
            e.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    public void fileDropped(DragEvent e){
        List<File> f = e.getDragboard().getFiles();
        if (!f.isEmpty()){
            try {
                imageFile = f.get(0);
                Image image = new Image(new FileInputStream(imageFile));
                img.setFill(new ImagePattern(image));
                img.setVisible(true);
                trascinaLbl.setVisible(false);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conferma.disableProperty().bind(new BooleanBinding() {
            {
                bind(nome.textProperty(),
                        sigla.textProperty(),
                        nazione.textProperty(),
                        pesoMassimo.textProperty(),
                        prezzoBagagli.textProperty());
            }
            @Override
            protected boolean computeValue() {
                return nome.getText().isEmpty() ||
                        sigla.getText().isEmpty() ||
                        nazione.getText().isEmpty() ||
                        pesoMassimo.getText().isEmpty() ||
                        prezzoBagagli.getText().isEmpty();
            }
        });
        sigla.textProperty().addListener((observable, oldValue, newValue) -> {
            if (sigla.getText().length() <= 3)
                sigla.setText(newValue.toUpperCase());
            else
                sigla.setText(sigla.getText().substring(0, 3));
        });
    }
}
