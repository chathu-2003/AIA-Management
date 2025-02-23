package edu.example.aia.aia_management.Model;

import edu.example.aia.aia_management.dto.ManagerDto;
import edu.example.aia.aia_management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManagerModel {

    // Method to save a new manager
    public boolean saveManager(ManagerDto manager) throws SQLException {
        String sql = "INSERT INTO manager (ManagerId, name, email, phone) VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql,
                manager.getManagerId(), // Explicit ManagerId
                manager.getName(),
                manager.getEmail(),
                manager.getPhone()
        );
    }

    // Method to update an existing manager
    public boolean updateManager(ManagerDto manager) throws SQLException {
        String sql = "UPDATE manager SET name = ?, email = ?, phone = ? WHERE managerId = ?";
        return CrudUtil.execute(sql,
                manager.getName(),
                manager.getEmail(),
                manager.getPhone(),
                manager.getManagerId()
        );
    }

    // Method to delete a manager by ID
    public boolean deleteManager(int managerId) throws SQLException {
        String sql = "DELETE FROM manager WHERE managerId = ?";
        return CrudUtil.execute(sql, managerId);
    }

    // Method to load all managers
    public List<ManagerDto> loadManagers() throws SQLException {
        String sql = "SELECT managerId, name, email, phone FROM manager";
        ResultSet resultSet = CrudUtil.execute(sql);  // Assuming CrudUtil handles ResultSet directly
        List<ManagerDto> managers = new ArrayList<>();

        while (resultSet.next()) {
            managers.add(new ManagerDto(
                    resultSet.getInt("managerId"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("phone")
            ));
        }
        return managers;
    }

    // Get the next manager ID
    public String getNextManagerId() throws SQLException {
        String sql = "SELECT MAX(managerId) AS maxId FROM manager";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()) {
            int maxId = resultSet.getInt("maxId");
            return String.valueOf(maxId + 1);  // Increment maxId by 1 for the next ID
        } else {
            return "1";  // Start with 1 if there are no manager records
        }
    }

    // Method to search a manager by ID
    public ManagerDto searchManagerById(int managerId) throws SQLException {
        String sql = "SELECT managerId, name, email, phone FROM manager WHERE managerId = ?";
        ResultSet rs = CrudUtil.execute(sql, managerId);  // Execute query using CrudUtil

        if (rs.next()) {
            return new ManagerDto(
                    rs.getInt("managerId"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone")
            );
        }
        return null;  // No manager found
    }
}
