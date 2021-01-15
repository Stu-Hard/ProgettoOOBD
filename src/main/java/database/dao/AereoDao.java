package database.dao;

import data.Aereo;
import data.Compagnia;
import database.PGConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AereoDao {
    public List<Aereo> getAerei() throws SQLException {
        List<Aereo> list = new ArrayList();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = PGConnection.getConnection().prepareStatement("SELECT * FROM aereo");
            resultSet = statement.executeQuery();
            CompagniaDao cDao = new CompagniaDao();
            while (resultSet.next()) {
                Compagnia compagnia = cDao.getByNome(resultSet.getString("compagnia"));
                list.add(new Aereo(
                        resultSet.getString("CodiceAereo"),
                        compagnia,
                        resultSet.getInt("file"),
                        resultSet.getInt("colonne")
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

    public List<Aereo> getAereiByCompagnia(Compagnia compagnia) throws SQLException {
        List<Aereo> list = new ArrayList();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = PGConnection.getConnection().prepareStatement("SELECT * FROM aereo WHERE compagnia = ?");
            statement.setString(1, compagnia.getNome());

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(new Aereo(
                        resultSet.getString("CodiceAereo"),
                        compagnia,
                        resultSet.getInt("file"),
                        resultSet.getInt("colonne")
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

    public Aereo getAereoByCode(String codiceAereo) throws SQLException {
        Aereo aereo = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = PGConnection.getConnection().prepareStatement("SELECT * FROM aereo WHERE codiceaereo = ?");
            statement.setString(1, codiceAereo);
            resultSet = statement.executeQuery();
            CompagniaDao compDao = new CompagniaDao();
            if (resultSet.next()) {
                aereo = new Aereo(
                        resultSet.getString("CodiceAereo"),
                        compDao.getByNome(resultSet.getString("compagnia")),
                        resultSet.getInt("file"),
                        resultSet.getInt("colonne")
                );
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        }
        return aereo;
    }
}
