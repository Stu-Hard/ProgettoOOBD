package database.dao;

import data.Compagnia;
import database.PGConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompagniaDao {
    public List<Compagnia> getCompagnie() throws SQLException {
        List<Compagnia> list = new ArrayList();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = PGConnection.getConnection().prepareStatement("SELECT * FROM compagnia");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(new Compagnia(
                        resultSet.getString("nome"),
                        resultSet.getString("sigla"),
                        resultSet.getString("nazione"),
                        resultSet.getFloat("pesomassimo"),
                        resultSet.getFloat("prezzobagagli")
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
}
