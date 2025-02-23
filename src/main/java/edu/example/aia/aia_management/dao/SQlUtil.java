package edu.example.aia.aia_management.dao;

import edu.example.aia.aia_management.db.DBConection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQlUtil {
    public static <T> T execute(String sql, Object... objects) throws SQLException, ClassNotFoundException {
        Connection connection = DBConection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        for (int i = 0;i < objects.length;i++){
            pstm.setObject(i+1,objects[i]);
        }
        if (sql.startsWith("SELECT")){
            return (T) (pstm.executeQuery());
        }else {
            return (T) (Boolean) (pstm.executeUpdate()>0);
        }
    }
}
