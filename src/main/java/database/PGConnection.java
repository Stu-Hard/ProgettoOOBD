package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PGConnection {
    static Connection connection = null;

    private PGConnection(){}

    public static Connection getConnection(){
        if (connection == null){
            String url = "jdbc:postgresql://localhost:5432/GoAirlines";
            String user = "postgres";
            String pass = "pgPassword";
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(url, user, pass);
            }
            catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
