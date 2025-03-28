package org.example.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnector {

    private static final String URL = "jdbc:mysql://localhost:3306/fpa_jensen";
    private static final String USER = "root";
    private static final String PASSWORD_ENV_VAR = "DB_PASSWORD";

    public static Connection getConnection() throws SQLException {
        String PASSWORD = System.getenv(PASSWORD_ENV_VAR);

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        return connection;
    }
}
