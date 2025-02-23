package edu.example.aia.aia_management.bo.custom.impl;

import edu.example.aia.aia_management.bo.custom.ManagerBo;
import edu.example.aia.aia_management.dao.DAOFactory;
import edu.example.aia.aia_management.dao.custom.ManagerDao;
import edu.example.aia.aia_management.dto.ManagerDto;
import edu.example.aia.aia_management.entity.Manager;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManagerBoImpl implements ManagerBo {

    ManagerDao managerDao=(ManagerDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.MANAGER);

    // Method to save a new manager
    @Override
    public boolean saveManager(ManagerDto manager) throws SQLException, ClassNotFoundException {
//        String sql = "INSERT INTO manager (ManagerId, name, email, phone) VALUES (?, ?, ?, ?)";
//        return CrudUtil.execute(sql,
//                manager.getManagerId(), // Explicit ManagerId
//                manager.getName(),
//                manager.getEmail(),
//                manager.getPhone()
        Manager managers=new Manager(manager.getManagerId(),manager.getName(),manager.getEmail(),manager.getPhone());
        return managerDao.save(managers);

    }

    // Method to update an existing manager
    @Override
    public boolean updateManager(ManagerDto manager) throws SQLException, ClassNotFoundException {
//        String sql = "UPDATE manager SET name = ?, email = ?, phone = ? WHERE managerId = ?";
//        return CrudUtil.execute(sql,
//                manager.getName(),
//                manager.getEmail(),
//                manager.getPhone(),
//                manager.getManagerId()
//        );
        Manager managers=new Manager(manager.getName(),manager.getEmail(),manager.getPhone(),manager.getManagerId());
        return managerDao.update(managers);
    }

    // Method to delete a manager by ID
    @Override
    public boolean deleteManager(int managerId) throws SQLException, ClassNotFoundException {
//        String sql = "DELETE FROM manager WHERE managerId = ?";
//        return CrudUtil.execute(sql, managerId);
        managerDao.delete(managerId);
        return true;
    }

    // Method to load all managers
    @Override
    public  ArrayList<ManagerDto>loadManagers() throws SQLException, ClassNotFoundException {
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
        ArrayList<Manager> managerArrayList=managerDao.getAll();
        ArrayList<ManagerDto> managerDtos=new ArrayList<>();

        for (Manager manager:managerArrayList){
            managerDtos.add(new ManagerDto(manager.getManagerId(),manager.getName(),manager.getEmail(),manager.getPhone()));

        }
        return managerDtos;
    }

    // Get the next manager ID
    @Override
    public int getNextManagerId() throws SQLException, ClassNotFoundException {
//        String sql = "SELECT MAX(managerId) AS maxId FROM manager";
//        ResultSet resultSet = CrudUtil.execute(sql);
//
//        if (resultSet.next()) {
//            int maxId = resultSet.getInt("maxId");
//            return String.valueOf(maxId + 1);  // Increment maxId by 1 for the next ID
//        } else {
//            return "1";  // Start with 1 if there are no manager records
//        }
        return managerDao.getNextId();
    }

    // Method to search a manager by ID
    @Override
    public ArrayList< ManagerDto> searchManagerById(String newValue) throws SQLException, ClassNotFoundException {
//        String sql = "SELECT managerId, name, email, phone FROM manager WHERE managerId = ?";
//        ResultSet rs = CrudUtil.execute(sql, managerId);  // Execute query using CrudUtil
//
//        if (rs.next()) {
//            return new ManagerDto(
//                    rs.getInt("managerId"),
//                    rs.getString("name"),
//                    rs.getString("email"),
//                    rs.getString("phone")
//            );
//        }
//        return null;  // No manager found
        ArrayList<Manager> managers=managerDao.search(newValue);
        ArrayList<ManagerDto> managerDtos=new ArrayList<>();

        for (Manager manager:managers){
            ManagerDto managerDto=new ManagerDto();
            managerDto.setManagerId(manager.getManagerId());
            managerDto.setName(manager.getName());
            managerDto.setEmail(manager.getEmail());
            managerDto.setPhone(manager.getPhone());
        }
        return managerDtos;
    }
}
