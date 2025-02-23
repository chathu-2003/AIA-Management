package edu.example.aia.aia_management.dao.custom.impl;

import edu.example.aia.aia_management.dao.SQlUtil;
import edu.example.aia.aia_management.dao.custom.PolicyDao;
import edu.example.aia.aia_management.entity.Policy;
import edu.example.aia.aia_management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PolicyDaoImpl implements PolicyDao {

    @Override
    public boolean save(Policy policyEntity) throws SQLException, ClassNotFoundException {
        //String sql = "INSERT INTO policy (PolicyId, PolicyNumber, StartDate, EndDate, Premium, Coverage, ManagerId, UnderWriterId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return SQlUtil.execute("INSERT INTO policy (PolicyId, PolicyNumber, StartDate, EndDate, Premium, Coverage, ManagerId, UnderWriterId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                policyEntity.getPolicyId(),
                policyEntity.getPolicyNumber(),
                policyEntity.getStartDate(),
                policyEntity.getEndDate(),
                policyEntity.getPremium(),
                policyEntity.getCoverage(),
                policyEntity.getManagerId(),
                policyEntity.getUnderWriterId()
        );
    }

    @Override
    public boolean delete(int policyId) throws SQLException {
        //String sql = "DELETE FROM policy WHERE PolicyId = ?";
        CrudUtil.execute( "DELETE FROM policy WHERE PolicyId = ?",policyId);
        return true;
    }
@Override
    public boolean update(Policy policyEntity) throws SQLException, ClassNotFoundException {
        //String sql = "UPDATE policy SET PolicyNumber = ?, StartDate = ?, EndDate = ?, Premium = ?, Coverage = ?, ManagerId = ?, UnderWriterId = ? WHERE PolicyId = ?";
        return SQlUtil.execute("UPDATE policy SET PolicyNumber = ?, StartDate = ?, EndDate = ?, Premium = ?, Coverage = ?, ManagerId = ?, UnderWriterId = ? WHERE PolicyId = ?",
                policyEntity.getPolicyNumber(),
                policyEntity.getStartDate(),
                policyEntity.getEndDate(),
                policyEntity.getPremium(),
                policyEntity.getCoverage(),
                policyEntity.getManagerId(),
                policyEntity.getUnderWriterId(),
                policyEntity.getPolicyId()
        );
    }

    @Override
    public ArrayList<Policy> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQlUtil.execute("SELECT PolicyId, PolicyNumber, StartDate, EndDate, Premium, Coverage, ManagerId, UnderWriterId FROM policy");
        ArrayList<Policy> policies = new ArrayList<>();

        while (rst.next()) {
            Policy policy = new Policy(
                    rst.getInt("PolicyId"),
                    rst.getString("PolicyNumber"),
                    rst.getDate("StartDate"),
                    rst.getDate("EndDate"),
                    rst.getBigDecimal("Premium"),
                    rst.getString("Coverage"),
                    rst.getInt("ManagerId"),
                    rst.getInt("UnderWriterId")
            );
            policies.add(policy);
        }
        return policies;
    }

    @Override
    public int getNextId() throws SQLException {
        // SQL query to fetch the maximum policy ID from the database
        String sql = "SELECT MAX(PolicyId) FROM policy"; // Adjust column and table names as needed
        ResultSet rst = CrudUtil.execute(sql);

        // If there's a result, extract and increment the max policy ID
        if (rst.next()) {
            int maxPolicyId = rst.getInt(1); // Retrieve the maximum PolicyId
            return maxPolicyId + 1; // Return the next PolicyId
        }

        // If no records exist, return 1 as the first policy ID
        return 1;
    }

    @Override
    public ArrayList<Policy> search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }

    // In PolicyModel.java
    @Override
    public ArrayList<Policy> search(int newValue) throws SQLException, ClassNotFoundException {
//        String query = "SELECT * FROM Policy WHERE PolicyId = ?";
//        try (Connection connection = DBConection.getInstance().getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//
//            preparedStatement.setInt(1, policyId);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                return new PolicyDto(
//                        resultSet.getInt("PolicyId"),
//                        resultSet.getString("PolicyNumber"),
//                        resultSet.getDate("StartDate"),
//                        resultSet.getDate("EndDate"),
//                        resultSet.getBigDecimal("Premium"),
//                        resultSet.getString("Coverage"),
//                        resultSet.getInt("ManagerId"),
//                        resultSet.getInt("UnderWriterId")
//                );

  //  }
  //      }
   //     return null; // If no matching policy is found
   ResultSet rst =SQlUtil.execute("SELECT * FROM Policy WHERE PolicyId = ?",newValue);
   ArrayList<Policy> searchResults=new ArrayList<>();
   while (rst.next()){
       Policy policy=new Policy(
               rst.getInt("PolicyId"),
               rst.getString("PolicyNumber"),
               rst.getDate("StartDate"),
               rst.getDate("EndDate"),
               rst.getBigDecimal("Premium"),
               rst.getString("Coverage"),
               rst.getInt("ManagerId"),
               rst.getInt("UnderWriterId")
       );
       searchResults.add(policy);
   }
   return searchResults;
    }
}

