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
                        resultSet.getString("codiceCoda"),
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

    public List<CodaImbarco> getByGateAndTratta(Gate gate) throws SQLException{
        List<CodaImbarco> list = new LinkedList();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = PGConnection.getConnection().prepareStatement("SELECT * FROM codaimbarco WHERE codicegate = ? AND numerovolo = ?");
            statement.setString(1, gate.getGateCode());
            statement.setString(2, gate.getTratta().getNumeroVolo());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(new CodaImbarco(
                        resultSet.getString("codiceCoda"),
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

    public void add(CodaImbarco coda, Gate gate) throws SQLException {
        PreparedStatement statement = null;

        try {
            statement = PGConnection.getConnection().prepareStatement(String.format("insert into codaimbarco values(?, ?, ?, '%s', ?, ?)", coda.getClasse().toString()));
            statement.setString(1, coda.getCodiceCoda());
            statement.setInt(2, coda.getTempoStimato());
            if (coda.getTempoEffettivo() != null)
                statement.setInt(3, coda.getTempoEffettivo());
            else statement.setNull(3, Types.INTEGER);
            statement.setString(4, gate.getGateCode());
            statement.setString(5, gate.getTratta().getNumeroVolo());
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
        }
    }
}
