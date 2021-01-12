package database.dao;

import data.Compagnia;
import data.Gate;
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
            statement = PGConnection.getConnection().prepareStatement("SELECT * FROM gate");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(new Gate(resultSet.getString("CodiceGate")));
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
}
