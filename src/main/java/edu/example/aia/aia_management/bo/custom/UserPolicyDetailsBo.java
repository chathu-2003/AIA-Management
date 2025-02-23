package edu.example.aia.aia_management.bo.custom;

import edu.example.aia.aia_management.bo.SuperBo;
import edu.example.aia.aia_management.dto.UserPolicyDetailsDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserPolicyDetailsBo extends SuperBo {
    boolean saveUserPolicyDetails(UserPolicyDetailsDto userPolicyDetailsDto) throws SQLException, ClassNotFoundException;

    // Update User Policy Details
    boolean updateUserPolicyDetails(UserPolicyDetailsDto userPolicyDetailsDto) throws SQLException, ClassNotFoundException;

    // Delete User Policy Details
    boolean deleteUserPolicyDetails(int userPolicyDetailsId) throws SQLException, ClassNotFoundException;

    // Search User Policy Details by UserId
    ArrayList<UserPolicyDetailsDto> searchUserPolicyDetails(int userId) throws SQLException, ClassNotFoundException;

    // Get All User Policy Details
    ArrayList<UserPolicyDetailsDto> getAllUserPolicyDetails() throws SQLException, ClassNotFoundException;
}
