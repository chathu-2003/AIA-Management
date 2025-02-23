package edu.example.aia.aia_management.dao.custom.impl;

import edu.example.aia.aia_management.dao.SQlUtil;
import edu.example.aia.aia_management.dao.custom.ManagerDao;
import edu.example.aia.aia_management.dto.ManagerDto;
import edu.example.aia.aia_management.entity.Manager;
import edu.example.aia.aia_management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManagerDaoImpl implements ManagerDao {

    // Method to save a new manager
    @Override
    public boolean save(Manager managerEntity) throws SQLException, ClassNotFoundException {
//        String sql = "INSERT INTO manager (ManagerId, name, email, phone) VALUES (?, ?, ?, ?)";
//        return CrudUtil.execute(sql,
//                manager.getManagerId(), // Explicit ManagerId
//                manager.getName(),
//                manager.getEmail(),
//                manager.getPhone()
//        );
        return SQlUtil.execute("INSERT INTO manager (ManagerId, name, email, phone) VALUES (?, ?, ?, ?)",
                managerEntity.getManagerId(),managerEntity.getName(),managerEntity.getEmail(),managerEntity.getPhone());
    }

    // Method to update an existing manager
    @Override
    public boolean update(Manager managerEntity) throws SQLException, ClassNotFoundException {
//        String sql = "UPDATE manager SET name = ?, email = ?, phone = ? WHERE managerId = ?";
       return SQlUtil.execute( "UPDATE manager SET name = ?, email = ?, phone = ? WHERE managerId = ?",
                managerEntity.getName(),
                managerEntity.getEmail(),
                managerEntity.getPhone(),
                managerEntity.getManagerId()
        );
    }

    // Method to delete a manager by ID
    @Override
    public boolean delete(int managerId) throws SQLException {
//        String sql = "DELETE FROM manager WHERE managerId = ?";
//        return CrudUtil.execute(sql, managerId);
        CrudUtil.execute( "DELETE FROM manager WHERE managerId = ?",managerId);
        return true;
    }

    // Method to load all managers
    @Override
    public ArrayList<Manager> getAll() throws SQLException {
//        String sql = "SELECT managerId, name, email, phone FROM manager";
//        ResultSet resultSet = CrudUtil.execute(sql);  // Assuming CrudUtil handles ResultSet directly
//        List<ManagerDto> managers = new ArrayList<>();
//
//        while (resultSet.next()) {
//            managers.add(new ManagerDto(
//                    resultSet.getInt("managerId"),
//                    resultSet.getString("name"),
//                    resultSet.getString("email"),
//                    resultSet.getString("phone")
//            ));
//        }
//        return managers;
    ResultSet rst=CrudUtil.execute("SELECT managerId, name, email, phone FROM manager");
    ArrayList<Manager> managers=new ArrayList<>();

    while (rst.next()){
        Manager manager=new Manager(
                      rst.getInt("managerId"),
                      rst.getString("name"),
                      rst.getString("email"),
                      rst.getString("phone")
        );
        managers.add(manager);
    }
        return managers;
    }

    // Get the next manager ID
    @Override
    public int getNextId() throws SQLException {
        String sql = "SELECT MAX(managerId) AS maxId FROM manager";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()) {
            int maxId = resultSet.getInt("maxId");
            return maxId+1;  // Increment maxId by 1 for the next ID
        } else {
            return 1;  // Start with 1 if there are no manager records
        }
    }

    // Method to search a manager by ID
    @Override
    public ArrayList<Manager> search(String newValue) throws SQLException, ClassNotFoundException {
        ResultSet rst=SQlUtil.execute( "SELECT managerId, name, email, phone FROM manager WHERE managerId = ?",newValue);
        ArrayList<Manager> searchResults=new ArrayList<>();

        while (rst.next()){
            Manager manager=new Manager(
                      rst.getInt("managerId"),
                      rst.getString("name"),
                      rst.getString("email"),
                      rst.getString("phone")
            );
             searchResults.add(manager);
        }
             return searchResults;

//        String sql = "SELECT managerId, name, email, phone FROM manager WHERE managerId = ?";
//        ResultSet rs = CrudUtil.execute(sql, managerId);  // Execute query using CrudUtil

//        if (rs.next()) {
//            return new ManagerDto(
//                    rs.getInt("managerId"),
//                    rs.getString("name"),
//                    rs.getString("email"),
//                    rs.getString("phone")
//            );

//        }
//        return null;  // No manager found
    }
}
