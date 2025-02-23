package edu.example.aia.aia_management.bo.custom.impl;

import edu.example.aia.aia_management.bo.custom.PolicyBo;
import edu.example.aia.aia_management.dao.DAOFactory;
import edu.example.aia.aia_management.dao.custom.PolicyDao;
import edu.example.aia.aia_management.dto.PolicyDto;
import edu.example.aia.aia_management.entity.Policy;

import java.sql.SQLException;
import java.util.ArrayList;

public class PolicyBoImpl implements PolicyBo {

PolicyDao policyDao=(PolicyDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.POLICY);

@Override
    public boolean savePolicy(PolicyDto policy) throws SQLException, ClassNotFoundException {
//        String sql = "INSERT INTO policy (PolicyId, PolicyNumber, StartDate, EndDate, Premium, Coverage, ManagerId, UnderWriterId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//        return CrudUtil.execute(sql,
//                policyDto.getPolicyId(),
//                policyDto.getPolicyNumber(),
//                policyDto.getStartDate(),
//                policyDto.getEndDate(),
//                policyDto.getPremium(),
//                policyDto.getCoverage(),
//                policyDto.getManagerId(),
//                policyDto.getUnderWriterId()
//        );
        Policy policys=new Policy(policy.getPolicyId(),policy.getPolicyNumber(),policy.getStartDate(),policy.getEndDate(),policy.getPremium(),policy.getCoverage(),policy.getManagerId(),policy.getUnderWriterId());
        return policyDao. save(policys);
    }
@Override
    public boolean deletePolicy(int policyId) throws SQLException, ClassNotFoundException {
//        String sql = "DELETE FROM policy WHERE PolicyId = ?";
//        return CrudUtil.execute(sql, policyId);
   policyDao.delete(policyId);
   return true;
    }
@Override
    public boolean updatePolicy(PolicyDto policy) throws SQLException, ClassNotFoundException {
       // String sql = "UPDATE policy SET PolicyNumber = ?, StartDate = ?, EndDate = ?, Premium = ?, Coverage = ?, ManagerId = ?, UnderWriterId = ? WHERE PolicyId = ?";
       Policy policys=new Policy(policy.getPolicyId(),policy.getPolicyNumber(),policy.getStartDate(),policy.getEndDate(),policy.getPremium(),policy.getCoverage(),policy.getManagerId(),policy.getUnderWriterId());
       return policyDao.update(policys);
    }
@Override
    public ArrayList<PolicyDto> getAllPolicies() throws SQLException, ClassNotFoundException {
        ArrayList<Policy> policyArrayList =policyDao.getAll();
        ArrayList<PolicyDto>  policyDtos= new ArrayList<>();

//        while (rst.next()) {
//            PolicyDto policyDTO = new PolicyDto(
//                    rst.getInt("PolicyId"),
//                    rst.getString("PolicyNumber"),
//                    rst.getDate("StartDate"),
//                    rst.getDate("EndDate"),
//                    rst.getBigDecimal("Premium"),
//                    rst.getString("Coverage"),
//                    rst.getInt("ManagerId"),
//                    rst.getInt("UnderWriterId")
//            );
//            policies.add(policyDTO);
//        }
//        return policies;
    for (Policy policy:policyArrayList){
        policyDtos.add(new PolicyDto(policy.getPolicyId(),policy.getPolicyNumber(),policy.getStartDate(),policy.getEndDate(),policy.getPremium(),policy.getCoverage(),policy.getManagerId(),policy.getUnderWriterId()));
    }
    return policyDtos;
    }
    @Override
    public int getNextPolicyId() throws SQLException, ClassNotFoundException {
        // SQL query to fetch the maximum policy ID from the database
//        String query = "SELECT MAX(PolicyId) FROM policy"; // Adjust column and table names as needed
//        ResultSet rst = CrudUtil.execute(query);
//
//        // If there's a result, extract and increment the max policy ID
//        if (rst.next()) {
//            int maxPolicyId = rst.getInt(1); // Retrieve the maximum PolicyId
//            return maxPolicyId + 1; // Return the next PolicyId
//        }
//
//        // If no records exist, return 1 as the first policy ID
//        return 1;
    return policyDao.getNextId();
    }
    // In PolicyModel.java
    @Override
    public ArrayList<PolicyDto> searchPolicyById(int newValue) throws SQLException, ClassNotFoundException {
        //String query = "SELECT * FROM Policy WHERE PolicyId = ?";
        ArrayList<Policy> policies=policyDao.search(newValue);
        ArrayList<PolicyDto> policyDtos=new ArrayList<>();
        for (Policy policy:policies){
            PolicyDto policyDto=new PolicyDto();
            policyDto.setPolicyId(policy.getPolicyId());
            policyDto.setPolicyNumber(policy.getPolicyNumber());
            policyDto.setEndDate(policy.getEndDate());
            policyDto.setCoverage(policy.getCoverage());
            policyDto.setPremium(policy.getPremium());
            policyDto.setCoverage(policy.getCoverage());
            policyDto.setStartDate(policy.getStartDate());
            policyDto.setManagerId(policy.getManagerId());

        }
        return policyDtos;
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
//            }
//        }
//        return null; // If no matching policy is found
    }

}
