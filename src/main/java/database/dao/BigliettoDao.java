package database.dao;

import data.Aeroporto;
import data.Biglietto;
import data.Compagnia;
import data.Gate;
import database.PGConnection;
import enumeration.CodeEnum;

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
                CodeEnum c = CodeEnum.ECONOMY;
                String classe = resultSet.getString(("classe"));
                if(classe.contains("ECONOMY")){
                    c = CodeEnum.ECONOMY;
                }else if(classe.contains("PRIORITY")){
                    c = CodeEnum.PRIORITY;
                }else if(classe.contains("BUSINESS")){
                    c = CodeEnum.BUSINESS;
                }else if(classe.contains("FAMIGLIE")){
                    c = CodeEnum.FAMIGLIE;
                }else{
                    c = CodeEnum.DIVERSAMENTE_ABILI;
                }

                list.add(new Biglietto(
                            resultSet.getInt("codicebiglietto"),
                            resultSet.getInt("prezzo"),
                            resultSet.getInt("fila"),
                            resultSet.getInt("posto"),
                            c,
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

    public Biglietto getBigliettoByCodice(int codice) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Biglietto biglietto = null;
        try {
            statement = PGConnection.getConnection().prepareStatement("SELECT * FROM biglietto WHERE codicebiglietto = ?");
            statement.setInt(1, codice);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                CodeEnum c;
                String classe = resultSet.getString(("classe"));
                if(classe.contains("ECONOMY")){
                    c = CodeEnum.ECONOMY;
                }else if(classe.contains("PRIORITY")){
                    c = CodeEnum.PRIORITY;
                }else if(classe.contains("BUSINESS")){
                    c = CodeEnum.BUSINESS;
                }else if(classe.contains("FAMIGLIE")){
                    c = CodeEnum.FAMIGLIE;
                }else{
                    c = CodeEnum.DIVERSAMENTE_ABILI;
                }

                biglietto = new Biglietto(
                        resultSet.getInt("codicebiglietto"),
                        resultSet.getInt("prezzo"),
                        resultSet.getInt("fila"),
                        resultSet.getInt("posto"),
                        c,
                        resultSet.getBoolean("checkin"),
                        resultSet.getBoolean("imbarcato"),
                        resultSet.getString("numerovolo"),
                        resultSet.getString("cf")
                );
            }
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
            statement.setInt(3, biglietto.getCodiceBiglietto());
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
            statement = PGConnection.getConnection().prepareStatement("INSERT INTO biglietto( prezzo, fila, posto, classe, checkin, imbarcato, numerovolo, cf) values (?,?,?,'"+biglietto.getClasse()+"',?,?,?,?)");
            statement.setDouble(1, biglietto.getPrezzo());
            statement.setInt(2, biglietto.getFila());
            statement.setInt(3, biglietto.getPosto());
            statement.setBoolean(4, biglietto.isCheckIn());
            statement.setBoolean(5, biglietto.isImbarcato());
            statement.setString(6, biglietto.getNumeroVolo());
            statement.setString(7, biglietto.getcF());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
        }
    }

    /*public Integer getCodiceByBiglietto(Biglietto biglietto) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = PGConnection.getConnection().prepareStatement("SELECT biglietto.codicebiglietto FROM biglietto WHERE " +
                    "prezzo = ?" +
                    "AND fila = ?" +
                    "AND posto = ?" +
                    "AND classe = '"+ biglietto.getClasse()+ "'" +
                    "AND biglietto.checkin = ?" +
                    "AND imbarcato = ?" +
                    "AND numerovolo = ?" +
                    "AND cf = ?");

            statement.setDouble(1,biglietto.getPrezzo());
            statement.setInt(2,biglietto.getFila());
            statement.setInt(3, biglietto.getPosto());
            statement.setBoolean(4, biglietto.isCheckIn());
            statement.setBoolean(5, biglietto.isImbarcato());
            statement.setString(6, biglietto.getNumeroVolo());
            statement.setString(7, biglietto.getcF());

            resultSet = statement.executeQuery();
            if  (resultSet.next()) {
                return resultSet.getInt("codicebiglietto");
            }
        } catch (SQLException | NullPointerException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        }

        return null;
    }*/
}


