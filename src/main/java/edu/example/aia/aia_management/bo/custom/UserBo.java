package edu.example.aia.aia_management.bo.custom;

import edu.example.aia.aia_management.bo.SuperBo;
import edu.example.aia.aia_management.dto.UserDto;

import java.sql.SQLException;

public interface UserBo extends SuperBo {
    // Method to save a new user to the database
    boolean saveUser(UserDto user) throws SQLException, ClassNotFoundException;

    int getNextUserId() throws SQLException, ClassNotFoundException;
}
