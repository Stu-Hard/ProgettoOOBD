package database.dao;

import data.Biglietto;
import data.Cliente;
import database.PGConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {

    public List<Cliente> getCliente() throws SQLException {
        List<Cliente> list = new ArrayList();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = PGConnection.getConnection().prepareStatement("SELECT * FROM cliente");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(new Cliente(
                        resultSet.getString("cf"),
                        resultSet.getString("nome"),
                        resultSet.getString("cognome"),
                        resultSet.getString("carta"),
                        resultSet.getString("email"),
                        resultSet.getInt("eta")
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

    public Cliente getClienteByCf(String codiceFiscale) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Cliente cliente = null;
        try {
            statement = PGConnection.getConnection().prepareStatement("SELECT * FROM cliente WHERE cf = ?");
            statement.setString(1, codiceFiscale);
            resultSet = statement.executeQuery();
            if (resultSet.next())
                cliente = new Cliente(
                        resultSet.getString("cf"),
                        resultSet.getString("nome"),
                        resultSet.getString("cognome"),
                        resultSet.getString("carta"),
                        resultSet.getString("email"),
                        resultSet.getInt("eta")
                );

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        }
        return cliente;
    }

    public void insert(Cliente cliente) throws SQLException{
        PreparedStatement statement = null;

        try {
            statement = PGConnection.getConnection().prepareStatement("INSERT INTO cliente values (?,?,?,?,?,?)");
            statement.setString(1, cliente.getCodiceFiscale());
            statement.setString(2, cliente.getNome());
            statement.setString(3, cliente.getCognome());
            statement.setString(4, cliente.getCarta());
            statement.setString(5, cliente.getEmail());
            statement.setInt(6, cliente.getEta());

            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (PGConnection.getConnection() != null) PGConnection.getConnection().close();
            if (statement != null) statement.close();
        }
    }
}
