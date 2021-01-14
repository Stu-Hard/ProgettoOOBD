package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PGConnection {
    static Connection connection = null;

    private PGConnection(){}

    public static Connection getConnection(){
        try {
            if (connection == null || connection.isClosed()){
                String url = "jdbc:postgresql://localhost:5432/" + System.getenv("DB");
                String user = System.getenv("user");
                String pass = System.getenv("pw");
                try {
                    Class.forName("org.postgresql.Driver");
                    connection = DriverManager.getConnection(url, user, pass);
                }
                catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }
}
