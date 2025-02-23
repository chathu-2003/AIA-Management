package edu.example.aia.aia_management.dao.custom;

import edu.example.aia.aia_management.dao.CrudDAO;
import edu.example.aia.aia_management.entity.Policy;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PolicyDao extends CrudDAO<Policy> {
    // In PolicyModel.java
    ArrayList<Policy> search(int newValue) throws SQLException, ClassNotFoundException;
}
