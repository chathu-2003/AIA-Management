package edu.example.aia.aia_management.bo.custom.impl;

import edu.example.aia.aia_management.bo.custom.UserPolicyDetailsBo;
import edu.example.aia.aia_management.dao.DAOFactory;
import edu.example.aia.aia_management.dao.custom.UserPolicyDetailsDao;
import edu.example.aia.aia_management.dto.UserPolicyDetailsDto;
import edu.example.aia.aia_management.entity.UserPolicyDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserPolicyDetailsBoImpl implements UserPolicyDetailsBo {

    UserPolicyDetailsDao userPolicyDetailsDao = (UserPolicyDetailsDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USERPOLICYDETAILS);

@Override
    public  boolean saveUserPolicyDetails(UserPolicyDetailsDto userPolicyDetailsDto) throws SQLException, ClassNotFoundException {
//        String sql = "INSERT INTO UserPolicyDetails (userId, policyId) VALUES (?, ?)";
//        return CrudUtil.execute(sql, userPolicyDetailsDto.getUserId(), userPolicyDetailsDto.getPolicyId());
        UserPolicyDetails userPolicyDetails=new UserPolicyDetails(userPolicyDetailsDto.getUserPolicyDetailsId(),userPolicyDetailsDto.getUserId(),userPolicyDetailsDto.getPolicyId());
   return userPolicyDetailsDao.save(userPolicyDetails);
    }

    // Update User Policy Details
    @Override
    public  boolean updateUserPolicyDetails(UserPolicyDetailsDto userPolicyDetailsDto) throws SQLException, ClassNotFoundException {
//        String sql = "UPDATE UserPolicyDetails SET userId = ?, policyId = ? WHERE userPolicyDetailsId = ?";
//        return CrudUtil.execute(sql, userPolicyDetailsDto.getUserId(), userPolicyDetailsDto.getPolicyId(), userPolicyDetailsDto.getUserPolicyDetailsId());
        UserPolicyDetails userPolicyDetails=new UserPolicyDetails(userPolicyDetailsDto.getUserPolicyDetailsId(),userPolicyDetailsDto.getUserId(),userPolicyDetailsDto.getPolicyId());
        return userPolicyDetailsDao.update(userPolicyDetails);
    }

    // Delete User Policy Details
    @Override
    public  boolean deleteUserPolicyDetails(int userPolicyDetailsId) throws SQLException, ClassNotFoundException {
//        String sql = "DELETE FROM UserPolicyDetails WHERE userPolicyDetailsId = ?";
//        return CrudUtil.execute(sql, userPolicyDetailsId);
        userPolicyDetailsDao.delete(userPolicyDetailsId);
        return true;
}

    // Search User Policy Details by UserId
    @Override
    public  ArrayList<UserPolicyDetailsDto> searchUserPolicyDetails(int userId) throws SQLException, ClassNotFoundException {
//        String sql = "SELECT * FROM UserPolicyDetails WHERE userId = ?";
//        ResultSet resultSet = CrudUtil.execute(sql, userId);
//        List<UserPolicyDetailsDto> list = new ArrayList<>();
        ArrayList<UserPolicyDetails> userPolicyDetails = userPolicyDetailsDao.search(userId);
        ArrayList<UserPolicyDetailsDto> userpolicydtos=new ArrayList<>();
       for (UserPolicyDetails userPolicyDetails1:userPolicyDetails){
           UserPolicyDetailsDto userPolicyDetailsDto=new UserPolicyDetailsDto();
           userPolicyDetailsDto.setUserId(userPolicyDetails1.getUserId());
           userPolicyDetailsDto.setUserPolicyDetailsId(userPolicyDetails1.getUserPolicyDetailsId());
           userPolicyDetailsDto.setPolicyId(userPolicyDetails1.getPolicyId());
           userpolicydtos.add(userPolicyDetailsDto);
       }

        return  userpolicydtos;
    }

    // Get All User Policy Details
    @Override
    public  ArrayList<UserPolicyDetailsDto> getAllUserPolicyDetails() throws SQLException, ClassNotFoundException {
//        String sql = "SELECT * FROM UserPolicyDetails";
//        ResultSet resultSet = CrudUtil.execute(sql);
//        List<UserPolicyDetailsDto> list = new ArrayList<>();

        ArrayList<UserPolicyDetails> userPolicyDetails = userPolicyDetailsDao.getAll();
        ArrayList<UserPolicyDetailsDto> userpolicydtos=new ArrayList<>();
        for (UserPolicyDetails userPolicyDetails1:userPolicyDetails){
           userpolicydtos.add(new UserPolicyDetailsDto(userPolicyDetails1.getUserPolicyDetailsId(),userPolicyDetails1.getUserId(),userPolicyDetails1.getPolicyId()));

        }
        return userpolicydtos;
    }
}