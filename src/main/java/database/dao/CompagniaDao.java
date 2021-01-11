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
        PreparedStatement statement = PGConnection.getConnection().prepareStatement("SELECT * FROM compagnia");
        ResultSet resultSet = statement.executeQuery();
        List<Compagnia> list = new ArrayList();

        while (resultSet.next()){
            list.add(new Compagnia(
                    resultSet.getString("nome"),
                    resultSet.getString("sigla"),
                    resultSet.getString("nazione"),
                    resultSet.getFloat("pesomassimo"),
                    resultSet.getFloat("prezzobagagli")
            ));
        }

        return list;
    }
}
