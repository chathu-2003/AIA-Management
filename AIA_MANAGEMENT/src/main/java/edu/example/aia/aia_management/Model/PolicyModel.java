package edu.example.aia.aia_management.Model;

import edu.example.aia.aia_management.db.DBConection;
import edu.example.aia.aia_management.dto.PolicyDto;
import edu.example.aia.aia_management.util.CrudUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PolicyModel {

    public boolean savePolicy(PolicyDto policyDto) throws SQLException {
        String sql = "INSERT INTO policy (PolicyId, PolicyNumber, StartDate, EndDate, Premium, Coverage, ManagerId, UnderWriterId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql,
                policyDto.getPolicyId(),
                policyDto.getPolicyNumber(),
                policyDto.getStartDate(),
                policyDto.getEndDate(),
                policyDto.getPremium(),
                policyDto.getCoverage(),
                policyDto.getManagerId(),
                policyDto.getUnderWriterId()
        );
    }

    public boolean deletePolicy(int policyId) throws SQLException {
        String sql = "DELETE FROM policy WHERE PolicyId = ?";
        return CrudUtil.execute(sql, policyId);
    }

    public boolean updatePolicy(PolicyDto policyDto) throws SQLException {
        String sql = "UPDATE policy SET PolicyNumber = ?, StartDate = ?, EndDate = ?, Premium = ?, Coverage = ?, ManagerId = ?, UnderWriterId = ? WHERE PolicyId = ?";
        return CrudUtil.execute(sql,
                policyDto.getPolicyNumber(),
                policyDto.getStartDate(),
                policyDto.getEndDate(),
                policyDto.getPremium(),
                policyDto.getCoverage(),
                policyDto.getManagerId(),
                policyDto.getUnderWriterId(),
                policyDto.getPolicyId()
        );
    }

    public List<PolicyDto> getAllPolicies() throws SQLException {
        List<PolicyDto> policies = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT PolicyId, PolicyNumber, StartDate, EndDate, Premium, Coverage, ManagerId, UnderWriterId FROM policy");

        while (rst.next()) {
            PolicyDto policyDTO = new PolicyDto(
                    rst.getInt("PolicyId"),
                    rst.getString("PolicyNumber"),
                    rst.getDate("StartDate"),
                    rst.getDate("EndDate"),
                    rst.getBigDecimal("Premium"),
                    rst.getString("Coverage"),
                    rst.getInt("ManagerId"),
                    rst.getInt("UnderWriterId")
            );
            policies.add(policyDTO);
        }
        return policies;
    }
    public int getNextPolicyId() throws SQLException {
        // SQL query to fetch the maximum policy ID from the database
        String query = "SELECT MAX(PolicyId) FROM policy"; // Adjust column and table names as needed
        ResultSet rst = CrudUtil.execute(query);

        // If there's a result, extract and increment the max policy ID
        if (rst.next()) {
            int maxPolicyId = rst.getInt(1); // Retrieve the maximum PolicyId
            return maxPolicyId + 1; // Return the next PolicyId
        }

        // If no records exist, return 1 as the first policy ID
        return 1;
    }
    // In PolicyModel.java
    public PolicyDto searchPolicyById(int policyId) throws SQLException {
        String query = "SELECT * FROM Policy WHERE PolicyId = ?";
        try (Connection connection = DBConection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, policyId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new PolicyDto(
                        resultSet.getInt("PolicyId"),
                        resultSet.getString("PolicyNumber"),
                        resultSet.getDate("StartDate"),
                        resultSet.getDate("EndDate"),
                        resultSet.getBigDecimal("Premium"),
                        resultSet.getString("Coverage"),
                        resultSet.getInt("ManagerId"),
                        resultSet.getInt("UnderWriterId")
                );
            }
        }
        return null; // If no matching policy is found
    }

}
