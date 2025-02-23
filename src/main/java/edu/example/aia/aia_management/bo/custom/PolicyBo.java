package edu.example.aia.aia_management.bo.custom;

import edu.example.aia.aia_management.bo.SuperBo;
import edu.example.aia.aia_management.dto.PolicyDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PolicyBo extends SuperBo {
    boolean savePolicy(PolicyDto policy) throws SQLException, ClassNotFoundException;

    boolean deletePolicy(int policyId) throws SQLException, ClassNotFoundException;

    boolean updatePolicy(PolicyDto policy) throws SQLException, ClassNotFoundException;

    ArrayList<PolicyDto> getAllPolicies() throws SQLException, ClassNotFoundException;

    int getNextPolicyId() throws SQLException, ClassNotFoundException;

    // In PolicyModel.java
    ArrayList<PolicyDto> searchPolicyById(int newValue) throws SQLException, ClassNotFoundException;
}
