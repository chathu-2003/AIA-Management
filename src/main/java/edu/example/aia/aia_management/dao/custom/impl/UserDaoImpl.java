package edu.example.aia.aia_management.dao.custom.impl;

import edu.example.aia.aia_management.dao.SQlUtil;
import edu.example.aia.aia_management.dao.custom.UserDao;
import edu.example.aia.aia_management.dto.UserDto;
import edu.example.aia.aia_management.entity.User;
import edu.example.aia.aia_management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDaoImpl implements UserDao {

    @Override
    public ArrayList<User> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    // Method to save a new user to the database
    @Override
    public boolean save(User userEntity) throws SQLException, ClassNotFoundException {
//        String sql = "INSERT INTO User (UserId, Password, Name, Email, PhoneNumber, Address) VALUES (?, ?, ?, ?, ?, ?)";
       return SQlUtil.execute("INSERT INTO User (UserId, Password, Name, Email, PhoneNumber, Address) VALUES (?, ?, ?, ?, ?, ?)", userEntity.getUserId(), userEntity.getPassword(), userEntity.getName(), userEntity.getEmail(), userEntity.getPhoneNumber(), userEntity.getAddress());
    }

    @Override
    public boolean update(User Dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException, ClassNotFoundException {
        return false;
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
    public int getNextId() throws SQLException {
        String sql = "SELECT MAX(UserId) FROM User";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()) {
            int maxId = resultSet.getInt(1);
            return maxId+ 1;
        } else {
            // If no users exist, start with UserId 1
            return 1;
        }
    }

    @Override
    public ArrayList<User> search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }
}
