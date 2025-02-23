package edu.example.aia.aia_management.Model;

import edu.example.aia.aia_management.dto.InsurancetypeDto;
import edu.example.aia.aia_management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InsuranceTypeModel {

    public boolean addInsuranceType(InsurancetypeDto insuranceType) throws SQLException {
        String sql = "INSERT INTO InsuranceType (TypeId, PolicyId, TypeName, Description) VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql, insuranceType.getTypeId(), insuranceType.getPolicyid(), insuranceType.getTypename(), insuranceType.getDescription());
    }

    public boolean deleteInsuranceType(int typeId) throws SQLException {
        String sql = "DELETE FROM InsuranceType WHERE TypeId = ?";
        return CrudUtil.execute(sql, typeId);
    }

    public boolean updateInsuranceType(InsurancetypeDto insuranceType) throws SQLException {
        String sql = "UPDATE InsuranceType SET PolicyId = ?, TypeName = ?, Description = ? WHERE TypeId = ?";
        return CrudUtil.execute(sql, insuranceType.getPolicyid(), insuranceType.getTypename(), insuranceType.getDescription(), insuranceType.getTypeId());
    }

    public List<InsurancetypeDto> getAllInsuranceTypes() throws SQLException {
        String sql = "SELECT * FROM InsuranceType";
        ResultSet resultSet = CrudUtil.execute(sql);

        List<InsurancetypeDto> insuranceTypes = new ArrayList<>();
        while (resultSet.next()) {
            InsurancetypeDto insuranceType = new InsurancetypeDto();
            insuranceType.setTypeId(resultSet.getInt("TypeId"));
            insuranceType.setPolicyid(resultSet.getInt("PolicyId"));
            insuranceType.setTypename(resultSet.getString("TypeName"));
            insuranceType.setDescription(resultSet.getString("Description"));
            insuranceTypes.add(insuranceType);
        }
        return insuranceTypes;
    }

    // New method to check if an insurance type exists by typeId
    public boolean doesInsuranceTypeExist(int typeId) throws SQLException {
        String sql = "SELECT TypeId FROM InsuranceType WHERE TypeId = ?";
        ResultSet resultSet = CrudUtil.execute(sql, typeId);
        return resultSet.next(); // If resultSet has any rows, the typeId exists
    }
    public String getNextTypeId() throws SQLException {
        String sql = "SELECT MAX(typeId) AS maxId FROM InsuranceType";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()) {
            int maxId = resultSet.getInt("maxId");
            return String.valueOf(maxId + 1); // Increment maxId by 1 for the next ID
        } else {
            return "1"; // Start with 1 if there are no types
        }
    }
    public InsurancetypeDto searchInsuranceTypeById(int typeId) throws SQLException {
        String sql = "SELECT * FROM InsuranceType WHERE TypeId = ?";
        ResultSet resultSet = CrudUtil.execute(sql, typeId);

        if (resultSet.next()) {
            return new InsurancetypeDto(
                    resultSet.getInt("TypeId"),
                    resultSet.getInt("PolicyId"),
                    resultSet.getString("TypeName"),
                    resultSet.getString("Description")
            );
        }
        return null; // Return null if no matching record is found
    }

}
