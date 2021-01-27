package database.dao;

import data.*;
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

                list.add(new Biglietto(
                            resultSet.getInt("codicebiglietto"),
                            resultSet.getInt("prezzo"),
                            resultSet.getInt("posto"),
                            c,
                            resultSet.getBoolean("checkin"),
                            resultSet.getBoolean("imbarcato"),
                            resultSet.getString("numerovolo"),
                            new Cliente(
                                    resultSet.getString("cf"),
                                    resultSet.getString("NomeCliente"),
                                    resultSet.getString("Documento")
                            )
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
                        resultSet.getInt("posto"),
                        c,
                        resultSet.getBoolean("checkin"),
                        resultSet.getBoolean("imbarcato"),
                        resultSet.getString("numerovolo"),
                        new Cliente(
                                resultSet.getString("cf"),
                                resultSet.getString("NomeCliente"),
                                resultSet.getString("Documento")
                        )
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
        int codiceCoda = new CodaImbarcoDao().getByBiglietto(biglietto).getCodiceCoda();
        try {
            statement = PGConnection.getConnection().prepareStatement("INSERT INTO biglietto(prezzo, posto, checkin, imbarcato, numerovolo, nomecliente, documento, codicecoda, cf) values (?,?,?,?,?,?,?,?,?)");
            statement.setDouble(1, biglietto.getPrezzo());
            statement.setInt(2, biglietto.getPosto());
            statement.setBoolean(3, biglietto.isCheckIn());
            statement.setBoolean(4, biglietto.isImbarcato());
            statement.setString(5, biglietto.getNumeroVolo());
            statement.setString(6, biglietto.getCliente().getNome());
            statement.setString(7, biglietto.getCliente().getDocumento());
            statement.setInt(8, codiceCoda);
            statement.setString(9, biglietto.getCliente().getCodiceFiscale());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
        }
    }
}


