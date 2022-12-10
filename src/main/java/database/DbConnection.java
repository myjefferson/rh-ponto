package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    public static Connection getConnectionSqlite() throws SQLException {
        Connection connection;
        connection = DriverManager.getConnection("jdbc:sqlite:rh_ponto.db");
        return connection;
    }
}
