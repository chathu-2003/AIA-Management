package edu.example.aia.aia_management.bo.custom;

import edu.example.aia.aia_management.bo.SuperBo;
import edu.example.aia.aia_management.dto.CommissionDTO;
import edu.example.aia.aia_management.entity.Commission;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CommissionBO extends SuperBo {
    // Save a new Commission
    boolean saveCommission(CommissionDTO commission) throws SQLException, ClassNotFoundException;

    // Update an existing Commission
    boolean updateCommission(CommissionDTO commission) throws SQLException, ClassNotFoundException;

    // Delete a Commission by ID
    //boolean deleteCommission(Commission commissionId) throws SQLException;

    // Delete a Commission by ID
    boolean deleteCommission(int commissionId) throws SQLException, ClassNotFoundException;

    // Delete a Commission by ID
    boolean deleteCommission(Commission commissionId) throws SQLException, ClassNotFoundException;

    // Get all Commissions from the database
    ArrayList<CommissionDTO> getAllCommissions() throws SQLException, ClassNotFoundException;


    ArrayList<CommissionDTO> searchCommission(String newValue) throws SQLException, ClassNotFoundException;
}
