package database.dao;

import data.Aereo;
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
            AereoDao aereoDao = new AereoDao();

            while (resultSet.next()) {
                Aeroporto partenza = aDao.getByCodice(resultSet.getString("AeroportoPartenza"));
                Aeroporto arrivo = aDao.getByCodice(resultSet.getString("AeroportoArrivo"));
                Compagnia comp = cDao.getByNome(resultSet.getString("Compagnia"));
                Aereo aereo = aereoDao.getAereoByCode(resultSet.getString("CodiceAereo"));

                Tratta tratta = new Tratta(
                    resultSet.getString("NumeroVolo"),
                    resultSet.getDate("DataPartenza").toLocalDate(),
                    resultSet.getTime("OraPartenza").toLocalTime(),
                    resultSet.getInt("DurataVolo"),
                    resultSet.getInt("ritardo"),
                    resultSet.getBoolean("conclusa"),
                    resultSet.getString("CodiceGate"),
                    comp,
                    partenza,
                    arrivo,
                    aereo
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

        try {
            statement = PGConnection.getConnection().prepareStatement("insert into tratta values " + tratta.toString());
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
        }
    }

    public Tratta getByNumeroVolo(String numeroVolo) throws SQLException {
        Tratta t = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = PGConnection.getConnection().prepareStatement("SELECT * FROM tratta WHERE numerovolo = ?");
            statement.setString(1, numeroVolo);
            resultSet = statement.executeQuery();
            AeroportoDao aDao = new AeroportoDao();
            CompagniaDao cDao = new CompagniaDao();
            AereoDao aereoDao = new AereoDao();

            if (resultSet.next()) {
                Aeroporto partenza = aDao.getByCodice(resultSet.getString("AeroportoPartenza"));
                Aeroporto arrivo = aDao.getByCodice(resultSet.getString("AeroportoArrivo"));
                Compagnia comp = cDao.getByNome(resultSet.getString("Compagnia"));
                Aereo aereo = aereoDao.getAereoByCode(resultSet.getString("CodiceAereo"));

                t = new Tratta(
                        resultSet.getString("NumeroVolo"),
                        resultSet.getDate("DataPartenza").toLocalDate(),
                        resultSet.getTime("OraPartenza").toLocalTime(),
                        resultSet.getInt("DurataVolo"),
                        resultSet.getInt("ritardo"),
                        resultSet.getBoolean("conclusa"),
                        resultSet.getString("CodiceGate"),
                        comp,
                        partenza,
                        arrivo,
                        aereo
                );
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        }
        return t;
    }

    public void update(Tratta tratta) throws SQLException {
        PreparedStatement statement = null;

        try {
            statement = PGConnection.getConnection().prepareStatement("UPDATE tratta SET conclusa = ?, codicegate = ?, ritardo = ? WHERE numerovolo = ?");
            statement.setBoolean(1, tratta.isConclusa());
            statement.setString(2, tratta.getGate());
            statement.setInt(3, tratta.getRitardo());
            statement.setString(4, tratta.getNumeroVolo());
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
        }
    }

    public List<Tratta> getTratteAperte() throws SQLException {
        List<Tratta> list = new ArrayList();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = PGConnection.getConnection().prepareStatement("SELECT * FROM tratta WHERE NOT conclusa");
            resultSet = statement.executeQuery();
            AeroportoDao aDao = new AeroportoDao();
            CompagniaDao cDao = new CompagniaDao();
            AereoDao aereoDao = new AereoDao();

            while (resultSet.next()) {
                Aeroporto partenza = aDao.getByCodice(resultSet.getString("AeroportoPartenza"));
                Aeroporto arrivo = aDao.getByCodice(resultSet.getString("AeroportoArrivo"));
                Compagnia comp = cDao.getByNome(resultSet.getString("Compagnia"));
                Aereo aereo = aereoDao.getAereoByCode(resultSet.getString("CodiceAereo"));

                Tratta tratta = new Tratta(
                        resultSet.getString("NumeroVolo"),
                        resultSet.getDate("DataPartenza").toLocalDate(),
                        resultSet.getTime("OraPartenza").toLocalTime(),
                        resultSet.getInt("DurataVolo"),
                        resultSet.getInt("ritardo"),
                        resultSet.getBoolean("conclusa"),
                        resultSet.getString("CodiceGate"),
                        comp,
                        partenza,
                        arrivo,
                        aereo
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

    public int getPasseggeri(Tratta tratta)throws SQLException{
        int passeggeri = 0;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = PGConnection.getConnection().prepareStatement("SELECT * FROM passeggeritotali WHERE numerovolo = ?");
            statement.setString(1, tratta.getNumeroVolo());
            resultSet = statement.executeQuery();
            AeroportoDao aDao = new AeroportoDao();
            CompagniaDao cDao = new CompagniaDao();
            AereoDao aereoDao = new AereoDao();

            if (resultSet.next()){
                passeggeri = resultSet.getInt("Passeggeri");
            }
        } catch (SQLException | NullPointerException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        }

        return passeggeri;
    }
}
