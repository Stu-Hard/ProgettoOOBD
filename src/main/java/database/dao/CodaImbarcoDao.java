package database.dao;

import data.*;
import database.PGConnection;
import enumeration.CodeEnum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CodaImbarcoDao {
    public List<CodaImbarco> getAll() throws SQLException {
        List<CodaImbarco> list = new ArrayList();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = PGConnection.getConnection().prepareStatement("SELECT * FROM codaimbarco");
            resultSet = statement.executeQuery();
            CompagniaDao cDao = new CompagniaDao();
            while (resultSet.next()) {
                Compagnia compagnia = cDao.getByNome(resultSet.getString("compagnia"));
                list.add(new CodaImbarco(
                        resultSet.getInt("codiceCoda"),
                        resultSet.getString("classe"),
                        resultSet.getInt("tempoStimato"),
                        resultSet.getInt("tempoEffettivo")
                ));
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        }

        return list;
    }

    public List<CodaImbarco> getByGateAndTratta(String gate, String tratta) throws SQLException{
        List<CodaImbarco> list = new LinkedList();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = PGConnection.getConnection().prepareStatement("SELECT * FROM codaimbarco WHERE codicegate = ? AND numerovolo = ?");
            statement.setString(1, gate);
            statement.setString(2, tratta);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(new CodaImbarco(
                        resultSet.getInt("codiceCoda"),
                        resultSet.getString("classe"),
                        resultSet.getInt("tempoStimato"),
                        resultSet.getInt("tempoEffettivo")
                ));
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        }
        return list;
    }

    public List<CodaImbarco> getByTratta(Tratta t) throws SQLException{
        List<CodaImbarco> list = new LinkedList();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = PGConnection.getConnection().prepareStatement("SELECT * FROM codaimbarco WHERE numerovolo = ?");
            statement.setString(1, t.getNumeroVolo());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(new CodaImbarco(
                        resultSet.getInt("codiceCoda"),
                        resultSet.getString("classe"),
                        resultSet.getInt("tempoStimato"),
                        resultSet.getInt("tempoEffettivo")
                ));
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        }
        return list;
    }

    public void addNew(CodeEnum coda, Tratta tratta) throws SQLException {
        PreparedStatement statement = null;

        try {
            statement = PGConnection.getConnection().prepareStatement(String.format("insert into codaimbarco(tempoStimato, classe, NumeroVolo) values(0, '%s', ?)", coda.toString()));
            statement.setString(1, tratta.getNumeroVolo());
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
        }
    }

    public void update(CodaImbarco coda) throws SQLException {
        PreparedStatement statement = null;

        try {
            statement = PGConnection.getConnection().prepareStatement("UPDATE codaimbarco SET tempoeffettivo = ?, codicegate = ? WHERE codicecoda = ?");
            statement.setInt(1, coda.getTempoEffettivo());
            statement.setString(2, coda.getCodiceGate());
            statement.setInt(3, coda.getCodiceCoda());
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
        }
    }
}
