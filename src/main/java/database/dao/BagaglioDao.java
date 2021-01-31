package database.dao;

import data.Aeroporto;
import data.Bagaglio;
import data.Biglietto;
import data.Compagnia;
import database.PGConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BagaglioDao {

    public List<Bagaglio> getBagagli(Biglietto biglietto) throws SQLException {
        List<Bagaglio> list = new ArrayList();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = PGConnection.getConnection().prepareStatement("SELECT * FROM bagaglio WHERE codicebiglietto = ?");
            statement.setInt(1,biglietto.getCodiceBiglietto());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(new Bagaglio(
                        resultSet.getString("codicebagaglio"),
                        resultSet.getDouble("peso"),
                        resultSet.getDouble("prezzo"),
                        biglietto
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

    public void insert(Bagaglio bagaglio) throws SQLException {

        PreparedStatement statement = null;
        try {
            statement = PGConnection.getConnection().prepareStatement("INSERT INTO bagaglio(codicebagaglio, peso, prezzo, codicebiglietto ) values (?,?,?,?)");
            statement.setString(1, bagaglio.getCodiceBagaglio());
            statement.setDouble(2, bagaglio.getPeso());
            statement.setDouble(3, bagaglio.getPrezzo());
            statement.setInt(4, bagaglio.getBiglietto().getCodiceBiglietto());

            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
        }
    }

    public List<Bagaglio> getBagagliByCodBiglietto(Integer codiceBiglietto) throws SQLException {
        List<Bagaglio> list = new ArrayList();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = PGConnection.getConnection().prepareStatement("SELECT * FROM bagaglio WHERE codicebiglietto = ?");
            statement.setInt(1, codiceBiglietto);
            resultSet = statement.executeQuery();
            BigliettoDao bDao = new BigliettoDao();
            Biglietto biglietto = bDao.getBigliettoByCodice(codiceBiglietto);
            while (resultSet.next()) {
                list.add(new Bagaglio(
                        resultSet.getString("codicebagaglio"),
                        resultSet.getDouble("peso"),
                        resultSet.getDouble("prezzo"),
                        biglietto
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


}
