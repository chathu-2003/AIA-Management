package edu.example.aia.aia_management.bo.custom;

import edu.example.aia.aia_management.bo.SuperBo;
import edu.example.aia.aia_management.dto.ManagerDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ManagerBo extends SuperBo {
    // Method to save a new manager
    boolean saveManager(ManagerDto manager) throws SQLException, ClassNotFoundException;

    // Method to update an existing manager
    boolean updateManager(ManagerDto manager) throws SQLException, ClassNotFoundException;

    // Method to delete a manager by ID
    boolean deleteManager(int managerId) throws SQLException, ClassNotFoundException;

    // Method to load all managers
    ArrayList<ManagerDto> loadManagers() throws SQLException, ClassNotFoundException;

    // Get the next manager ID
    int getNextManagerId() throws SQLException, ClassNotFoundException;

    // Method to search a manager by ID
    ArrayList< ManagerDto> searchManagerById(String newValue) throws SQLException, ClassNotFoundException;
}
