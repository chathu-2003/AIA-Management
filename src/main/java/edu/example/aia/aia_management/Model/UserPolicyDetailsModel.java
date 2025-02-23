package edu.example.aia.aia_management.Model;

import edu.example.aia.aia_management.dto.UserPolicyDetailsDto;
import edu.example.aia.aia_management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserPolicyDetailsModel {

    // Save User Policy Details
    public static boolean saveUserPolicyDetails(UserPolicyDetailsDto userPolicyDetailsDto) throws SQLException {
        String sql = "INSERT INTO UserPolicyDetails (userId, policyId) VALUES (?, ?)";
        return CrudUtil.execute(sql, userPolicyDetailsDto.getUserId(), userPolicyDetailsDto.getPolicyId());
    }

    // Update User Policy Details
    public static boolean updateUserPolicyDetails(UserPolicyDetailsDto userPolicyDetailsDto) throws SQLException {
        String sql = "UPDATE UserPolicyDetails SET userId = ?, policyId = ? WHERE userPolicyDetailsId = ?";
        return CrudUtil.execute(sql, userPolicyDetailsDto.getUserId(), userPolicyDetailsDto.getPolicyId(), userPolicyDetailsDto.getUserPolicyDetailsId());
    }

    // Delete User Policy Details
    public static boolean deleteUserPolicyDetails(int userPolicyDetailsId) throws SQLException {
        String sql = "DELETE FROM UserPolicyDetails WHERE userPolicyDetailsId = ?";
        return CrudUtil.execute(sql, userPolicyDetailsId);
    }

    // Search User Policy Details by UserId
    public static List<UserPolicyDetailsDto> searchUserPolicyDetails(int userId) throws SQLException {
        String sql = "SELECT * FROM UserPolicyDetails WHERE userId = ?";
        ResultSet resultSet = CrudUtil.execute(sql, userId);
        List<UserPolicyDetailsDto> list = new ArrayList<>();

        while (resultSet.next()) {
            int userPolicyDetailsId = resultSet.getInt("userPolicyDetailsId");
            int policyId = resultSet.getInt("policyId");
            list.add(new UserPolicyDetailsDto(userPolicyDetailsId, userId, policyId));
        }

        return list;
    }

    // Get All User Policy Details
    public static List<UserPolicyDetailsDto> getAllUserPolicyDetails() throws SQLException {
        String sql = "SELECT * FROM UserPolicyDetails";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<UserPolicyDetailsDto> list = new ArrayList<>();

        while (resultSet.next()) {
            int userPolicyDetailsId = resultSet.getInt("userPolicyDetailsId");
            int userId = resultSet.getInt("userId");
            int policyId = resultSet.getInt("policyId");
            list.add(new UserPolicyDetailsDto(userPolicyDetailsId, userId, policyId));
        }

        return list;
    }
}
