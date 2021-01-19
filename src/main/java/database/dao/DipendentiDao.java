package database.dao;

import data.Cliente;
import data.Compagnia;
import data.Dipendente;
import database.PGConnection;

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
                list.add(new Dipendente(
                        resultSet.getString("codiceImpiegato"),
                        resultSet.getString("nome"),
                        resultSet.getString("cognome"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("ruolo"),
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
            statement = PGConnection.getConnection().prepareStatement("INSERT INTO dipendente values (?,?,?,?,?,'"+dipendente.getRuolo()+"')");
            statement.setString(1, dipendente.getNome());
            statement.setString(2, dipendente.getCognome());
            statement.setString(3, dipendente.getEmail());
            statement.setString(4, dipendente.getPassword());

            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
        }
    }
}
