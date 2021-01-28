package database.dao;

import data.Dipendente;
import database.PGConnection;
import enumeration.DipendentiEnum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DipendentiDao {

    public List<Dipendente> getDipendenti() throws SQLException {
        List<Dipendente> list = new ArrayList();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = PGConnection.getConnection().prepareStatement("SELECT * FROM dipendente");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                DipendentiEnum c;
                String ruolo = resultSet.getString(("ruolo"));
                if(ruolo.contains("Amministratore")){
                    c = DipendentiEnum.Amministratore;
                }else if(ruolo.contains("Check")){
                    c = DipendentiEnum.AddettoCheckIn;
                }else if(ruolo.contains("Imbarco")){
                    c = DipendentiEnum.AddettoImbarco;
                }else if(ruolo.contains("Ticket")){
                    c = DipendentiEnum.TicketAgent;
                }else{
                    c = DipendentiEnum.ResponsabileVoli;
                }

                list.add(new Dipendente(
                        resultSet.getString("codiceImpiegato"),
                        resultSet.getString("nome"),
                        resultSet.getString("cognome"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        c,
                        resultSet.getString("compagnia")
                ));

            }
        } catch (SQLException | NullPointerException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        }

        return list;
    }


    public void insert(Dipendente dipendente) throws SQLException{
        PreparedStatement statement = null;
        try {
            statement = PGConnection.getConnection().prepareStatement("INSERT INTO dipendente (nome, cognome, email, password, ruolo, compagnia) values (?,?,?,?,'"+dipendente.getRuolo()+"', ?)");
            statement.setString(1, dipendente.getNome());
            statement.setString(2, dipendente.getCognome());
            statement.setString(3, dipendente.getEmail());
            statement.setString(4, dipendente.getPassword());
            statement.setString(5, dipendente.getCompagnia());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
        }
    }

    public void licenzia(Dipendente dipendente) throws SQLException{
        PreparedStatement statement = null;
        try {
            statement = PGConnection.getConnection().prepareStatement("DELETE FROM dipendente WHERE codiceimpiegato = ?");
            statement.setInt(1, Integer.parseInt(dipendente.getCodiceImpiegato()));
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
        }
    }
}
