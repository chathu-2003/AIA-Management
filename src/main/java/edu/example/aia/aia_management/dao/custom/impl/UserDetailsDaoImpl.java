package edu.example.aia.aia_management.dao.custom.impl;

import edu.example.aia.aia_management.dao.SQlUtil;
import edu.example.aia.aia_management.dao.custom.UserPolicyDetailsDao;
import edu.example.aia.aia_management.dto.UserPolicyDetailsDto;
import edu.example.aia.aia_management.entity.UserPolicyDetails;
import edu.example.aia.aia_management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDetailsDaoImpl implements UserPolicyDetailsDao {

    @Override
    public  boolean save(UserPolicyDetails userPolicyDetailsEntity) throws SQLException, ClassNotFoundException {
    //    String sql = "INSERT INTO UserPolicyDetails (userId, policyId) VALUES (?, ?)";
        return SQlUtil.execute("INSERT INTO UserPolicyDetails (userId, policyId) VALUES (?, ?)", userPolicyDetailsEntity.getUserId(), userPolicyDetailsEntity.getPolicyId());
    }

    // Update User Policy Details
    @Override
    public  boolean update(UserPolicyDetails userPolicyDetailsEntity) throws SQLException, ClassNotFoundException {
       // String sql = "UPDATE UserPolicyDetails SET userId = ?, policyId = ? WHERE userPolicyDetailsId = ?";
        return SQlUtil.execute( "UPDATE UserPolicyDetails SET userId = ?, policyId = ? WHERE userPolicyDetailsId = ?", userPolicyDetailsEntity.getUserId(), userPolicyDetailsEntity.getPolicyId(), userPolicyDetailsEntity.getUserPolicyDetailsId());
    }

    // Delete User Policy Details
    @Override
    public  boolean delete(int userPolicyDetailsId) throws SQLException {
//        String sql = "DELETE FROM UserPolicyDetails WHERE userPolicyDetailsId = ?";
//        return CrudUtil.execute(sql, userPolicyDetailsId);
    CrudUtil.execute("DELETE FROM UserPolicyDetails WHERE userPolicyDetailsId = ?",userPolicyDetailsId);
    return true;
    }

    @Override
    public int getNextId() throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public ArrayList<UserPolicyDetails> search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }

    // Search User Policy Details by UserId
    @Override
    public ArrayList<UserPolicyDetails> search(int userId) throws SQLException, ClassNotFoundException {
//        String sql = "SELECT * FROM UserPolicyDetails WHERE userId = ?";
//        ResultSet resultSet = CrudUtil.execute(sql, userId);
//        List<UserPolicyDetailsDto> list = new ArrayList<>();
//
//        while (resultSet.next()) {
//            int userPolicyDetailsId = resultSet.getInt("userPolicyDetailsId");
//            int policyId = resultSet.getInt("policyId");
//            list.add(new UserPolicyDetailsDto(userPolicyDetailsId, userId, policyId));
//        }
//
//        return list;
    ResultSet rst=SQlUtil.execute("SELECT * FROM UserPolicyDetails WHERE userId = ?",userId);
    ArrayList<UserPolicyDetails> searchResults=new ArrayList<>();
    while (rst.next()){
        UserPolicyDetails userPolicyDetails=new UserPolicyDetails(
                rst.getInt("userId"),
                rst.getInt("policyId"),
                rst.getInt("userPolicyDetailsId")
        );
        searchResults.add(userPolicyDetails);
    }
    return searchResults;
    }
    // Get All User Policy Details
    @Override
    public ArrayList <UserPolicyDetails> getAll() throws SQLException {
       // String sql = "SELECT * FROM UserPolicyDetails";
        ResultSet rst = CrudUtil.execute("SELECT * FROM UserPolicyDetails");
       ArrayList <UserPolicyDetails> userPolicyDetails = new ArrayList<>();

        while (rst.next()) {
            UserPolicyDetails userPolicyDetails1 = new UserPolicyDetails(
             rst.getInt("userId"), rst.getInt("userPolicyDetailsId"),
              rst.getInt("policyId")
            );
            userPolicyDetails.add(userPolicyDetails1);
           // list.add(new UserPolicyDetailsDto(userPolicyDetailsId, userId, policyId));
        }

        return userPolicyDetails;
    }
}
