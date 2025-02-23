package edu.example.aia.aia_management.db;

import lombok.Getter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class DBConection {
    private static DBConection dbConnection;

    private final Connection connection;

    private DBConection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/insurance","root","IjseQ1234");
    }
    public static DBConection getInstance() throws SQLException {
        if (dbConnection == null) {
            dbConnection = new DBConection();
        }
        return dbConnection;
    }

}
