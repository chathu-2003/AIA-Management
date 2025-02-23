package edu.example.aia.aia_management.dao.custom;

import edu.example.aia.aia_management.dao.CrudDAO;
import edu.example.aia.aia_management.entity.UserPolicyDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserPolicyDetailsDao extends CrudDAO<UserPolicyDetails> {
    // Search User Policy Details by UserId
    ArrayList<UserPolicyDetails> search(int userId) throws SQLException, ClassNotFoundException;
}
