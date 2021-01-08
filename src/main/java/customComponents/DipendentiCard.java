package customComponents;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import enumeration.DipendentiEnum;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;

public class DipendentiCard extends AnchorPane {
    private FontAwesomeIcon utente;
    private JFXButton bottoneUtente;
    private Label gerarchia;
    public AnchorPane pannello;

    public FontAwesomeIcon getUtente() {
        return utente;
    }

    public void setUtente(FontAwesomeIcon utente) {
        this.utente = utente;
    }

    public String getBottoneUtente() {
        //per sapere il nome del dipendente
        return bottoneUtente.getText().toUpperCase();

    }

    public void setBottoneUtente(String bottoneNome) {
        this.bottoneUtente.setText(bottoneNome);
    }



    public DipendentiEnum getGerarchia(){
        if(gerarchia.getText().contains("Addetto")) return DipendentiEnum.ADDETTO_IMBARCO;
        else if(gerarchia.getText().contains("Amministratore")) return DipendentiEnum.AMMINISTRATORE;
        else if(gerarchia.getText().contains("Check")) return DipendentiEnum.CHECK_IN;
        else if(gerarchia.getText().contains("Ticket")) return DipendentiEnum.TICKET_AGENT;
        else return DipendentiEnum.RESPONSABILE_VOLI;
    }





    public void setGerarchia(DipendentiEnum gerarchia) throws IOException {
        switch (gerarchia) {
            case AMMINISTRATORE:
                this.gerarchia.setText("Amministratore");
                setStyle("-fx-background-color: #1d3791; -fx-background-radius: 25px;");
                break;
            case CHECK_IN:
                this.gerarchia.setText("Addetto al Check In");
                setStyle("-fx-background-color: #1d915f; -fx-background-radius: 25px;");
                break;
            case ADDETTO_IMBARCO:
                this.gerarchia.setText("Addetto all'Imbarco");
                setStyle("-fx-background-color: #be1c27; -fx-background-radius: 25px;");
                break;
            case TICKET_AGENT:
                this.gerarchia.setText("Ticket Agents");
                setStyle("-fx-background-color: #19a91b; -fx-background-radius: 25px;");
                break;
            case RESPONSABILE_VOLI:
                this.gerarchia.setText("Responsabile Voli");
                setStyle("-fx-background-color: #d26619; -fx-background-radius: 25px;");
                break;
        }


    }
    //Costruttore della DipendentiCard

    public DipendentiCard(String bottoneUtente, DipendentiEnum gerarchia) {
        try{
            caricaComponenti();
            setBottoneUtente(bottoneUtente);
            setGerarchia(gerarchia);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


      private void caricaComponenti() throws IOException {
          getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/DipendentiCard.fxml")));
          this.pannello = ((AnchorPane) lookup("#pannello"));
          this.gerarchia = ((Label) lookup("#gerarchia"));
          this.bottoneUtente = ((JFXButton) lookup("#bottoneUtente"));
          this.utente = ((FontAwesomeIcon) lookup("#utente"));
      }


}
