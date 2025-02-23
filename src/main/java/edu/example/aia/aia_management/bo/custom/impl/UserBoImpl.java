package edu.example.aia.aia_management.bo.custom.impl;

import edu.example.aia.aia_management.bo.custom.UserBo;
import edu.example.aia.aia_management.dao.DAOFactory;
import edu.example.aia.aia_management.dao.custom.UserDao;
import edu.example.aia.aia_management.dto.UserDto;
import edu.example.aia.aia_management.entity.User;
import edu.example.aia.aia_management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserBoImpl implements UserBo {

    UserDao userDao=(UserDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

    // Method to save a new user to the database
    @Override
    public boolean saveUser(UserDto user) throws SQLException, ClassNotFoundException {
       // String sql = "INSERT INTO User (UserId, Password, Name, Email, PhoneNumber, Address) VALUES (?, ?, ?, ?, ?, ?)";

        User userS=new User(user.getUserId(), user.getPassword(), user.getName(), user.getEmail(), user.getPhoneNumber(), user.getAddress());
   return userDao.save(userS);
    }

    // Method to retrieve the username (Name) from the database by matching the username input
    public String getUserName(String username) throws SQLException {
        String sql = "SELECT Name FROM User WHERE Name = ?";
        ResultSet resultSet = CrudUtil.execute(sql, username);
        if (resultSet.next()) {
            return resultSet.getString("Name");
        }
        return null;
    }

    // Method to retrieve the user ID by username
    public String getUserId(String username) throws SQLException {
        String sql = "SELECT UserId FROM User WHERE Name = ?";
        ResultSet resultSet = CrudUtil.execute(sql, username);
        if (resultSet.next()) {
            return resultSet.getString("UserId");
        }
        return null;
    }

    // Method to retrieve the password for a given user ID
    public String getPassword(String userId) throws SQLException {
        String sql = "SELECT Password FROM User WHERE UserId = ?";
        ResultSet resultSet = CrudUtil.execute(sql, userId);
        if (resultSet.next()) {
            return resultSet.getString("Password");
        }
        return null;
    }
    @Override
    public int getNextUserId() throws SQLException, ClassNotFoundException {
//        String sql = "SELECT MAX(UserId) FROM User";
//        ResultSet resultSet = CrudUtil.execute(sql);
//
//        if (resultSet.next()) {
//            int maxId = resultSet.getInt(1);
//            return String.valueOf(maxId + 1);
//        } else {
//            // If no users exist, start with UserId 1
//            return "1";
//        }
        return userDao.getNextId();
    }
}
