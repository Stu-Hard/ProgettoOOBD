package database.dao;

import data.Aeroporto;
import data.Compagnia;
import data.Tratta;
import database.PGConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrattaDao {
    public List<Tratta> getAllTratte() throws SQLException {
        List<Tratta> list = new ArrayList();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = PGConnection.getConnection().prepareStatement("SELECT * FROM tratta");
            resultSet = statement.executeQuery();
            AeroportoDao aDao = new AeroportoDao();
            CompagniaDao cDao = new CompagniaDao();

            while (resultSet.next()) {
                Aeroporto partenza = aDao.getByCodice(resultSet.getString("AeroportoPartenza"));
                Aeroporto arrivo = aDao.getByCodice(resultSet.getString("AeroportoArrivo"));
                Compagnia comp = cDao.getByNome(resultSet.getString("Compagnia"));
                Tratta tratta = new Tratta(
                    resultSet.getString("NumeroVolo"),
                    resultSet.getDate("DataPartenza").toLocalDate(),
                    resultSet.getTime("OraPartenza").toLocalTime(),
                    resultSet.getInt("DurataVolo"),
                    comp,
                    partenza,
                    arrivo
                );
                list.add(tratta);
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

    public void add(Tratta tratta) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = PGConnection.getConnection().prepareStatement("insert into tratta values" + tratta.toString());
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        }
    }
}
