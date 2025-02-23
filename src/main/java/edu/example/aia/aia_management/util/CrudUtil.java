package edu.example.aia.aia_management.util;

import edu.example.aia.aia_management.db.DBConection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {

    public static <T> T execute(String sql, Object... obj) throws SQLException {
        Connection connection = DBConection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement(sql);

        for (int i = 0; i < obj.length; i++) { 
            pst.setObject(i + 1, obj[i]);
        }

        if (sql.trim().toLowerCase().startsWith("select")) {
            ResultSet resultSet = pst.executeQuery();
            return (T) resultSet;
        } else {
            int i = pst.executeUpdate();
            return (T) (Boolean) (i > 0);
        }
    }
}
