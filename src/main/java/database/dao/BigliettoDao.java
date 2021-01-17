package database.dao;

import data.Aeroporto;
import data.Biglietto;
import data.Compagnia;
import data.Gate;
import database.PGConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BigliettoDao {

    public List<Biglietto> getBiglietto() throws SQLException {
        List<Biglietto> list = new ArrayList();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = PGConnection.getConnection().prepareStatement("SELECT * FROM biglietto");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(new Biglietto(
                            resultSet.getString("codicebiglietto"),
                            resultSet.getInt("prezzo"),
                            resultSet.getString("fila"),
                            resultSet.getInt("posto"),
                            resultSet.getString("classe"),
                            resultSet.getBoolean("checkin"),
                            resultSet.getBoolean("imbarcato"),
                            resultSet.getString("numerovolo"),
                            resultSet.getString("cf")
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

    public Biglietto getBigliettoByCodice(String codice) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Biglietto biglietto = null;
        try {
            statement = PGConnection.getConnection().prepareStatement("SELECT * FROM biglietto WHERE codicebiglietto = ?");
            statement.setString(1, codice);
            resultSet = statement.executeQuery();
            if (resultSet.next())
                biglietto = new Biglietto(
                         resultSet.getString("codicebiglietto"),
                         resultSet.getInt("prezzo"),
                         resultSet.getString("fila"),
                         resultSet.getInt("posto"),
                         resultSet.getString("classe"),
                         resultSet.getBoolean("checkin"),
                         resultSet.getBoolean("imbarcato"),
                         resultSet.getString("numerovolo"),
                         resultSet.getString("cf")
                        );
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        }
        return biglietto;
    }


    public void update(Biglietto biglietto) throws SQLException{
        PreparedStatement statement = null;


        try {
            statement = PGConnection.getConnection().prepareStatement("UPDATE biglietto SET checkin = ?, imbarcato = ? WHERE codicebiglietto = ?");
            statement.setBoolean(1, biglietto.isCheckIn());
            statement.setBoolean(2, biglietto.isImbarcato());
            statement.setString(3, biglietto.getCodiceBiglietto());
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
        }
    }

    public void insert(Biglietto biglietto) throws SQLException{
        PreparedStatement statement = null;

        try {
            statement = PGConnection.getConnection().prepareStatement("INSERT INTO biglietto values (?,?,?,?,?,?,?,?,?)");
            statement.setString(1, biglietto.getCodiceBiglietto());
            statement.setDouble(2, biglietto.getPrezzo());
            statement.setString(3, biglietto.getFila());
            statement.setInt(4, biglietto.getPosto());
            statement.setString(5, biglietto.getClasse());
            statement.setBoolean(6, biglietto.isCheckIn());
            statement.setBoolean(7, biglietto.isImbarcato());
            statement.setString(8, biglietto.getNumeroVolo());
            statement.setString(9, biglietto.getcF());
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
        }
    }
}


