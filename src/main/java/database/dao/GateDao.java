package database.dao;

import data.Compagnia;
import data.Gate;
import data.Tratta;
import database.PGConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GateDao {
    public List<Gate> getGateCodes() throws SQLException {
        List<Gate> list = new ArrayList();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = PGConnection.getConnection().prepareStatement("SELECT * FROM gate ORDER BY codicegate");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Tratta tratta = new TrattaDao().getByNumeroVolo(resultSet.getString("tratta"));
                list.add(new Gate(resultSet.getString("CodiceGate"), tratta));
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

    public void update(Gate gate) throws SQLException{
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = PGConnection.getConnection().prepareStatement(String.format("UPDATE gate SET stato = '%s', tratta = ? WHERE codicegate = ?", gate.getStatus().toString()));
            statement.setString(1, gate.getTratta().getNumeroVolo());
            statement.setString(2, gate.getGateCode());
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
        }
    }
}
