package edu.example.aia.aia_management.dao.custom.impl;

import edu.example.aia.aia_management.dao.SQlUtil;
import edu.example.aia.aia_management.dao.custom.InusrancetypeDao;
import edu.example.aia.aia_management.dto.InsurancetypeDto;
import edu.example.aia.aia_management.entity.Insurancetype;
import edu.example.aia.aia_management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InsurancetypeDaoImpl implements InusrancetypeDao {

 @Override
    public boolean save(Insurancetype insuranceTypeEntity) throws SQLException, ClassNotFoundException {
//        String sql = "INSERT INTO InsuranceType (TypeId, PolicyId, TypeName, Description) VALUES (?, ?, ?, ?)";
//        return CrudUtil.execute(sql, insuranceType.getTypeId(), insuranceType.getPolicyid(), insuranceType.getTypename(), insuranceType.getDescription());
  return SQlUtil.execute("INSERT INTO InsuranceType (TypeId, PolicyId, TypeName, Description) VALUES (?, ?, ?, ?)",
          insuranceTypeEntity.getTypeId(),
          insuranceTypeEntity.getPolicyid(),
          insuranceTypeEntity.getTypename(),
          insuranceTypeEntity.getDescription()
       );
    }
 @Override
    public boolean delete(int typeId) throws SQLException {
//        String sql = "DELETE FROM InsuranceType WHERE TypeId = ?";
//        return CrudUtil.execute(sql, typeId);
   CrudUtil.execute("DELETE FROM InsuranceType WHERE TypeId = ?",typeId);
   return true;
    }
@Override
    public boolean update(Insurancetype insuranceTypeEntity) throws SQLException, ClassNotFoundException {
//        String sql = "UPDATE InsuranceType SET PolicyId = ?, TypeName = ?, Description = ? WHERE TypeId = ?";
//        return CrudUtil.execute(sql, insuranceType.getPolicyid(), insuranceType.getTypename(), insuranceType.getDescription(), insuranceType.getTypeId());
    return SQlUtil.execute( "UPDATE InsuranceType SET PolicyId = ?, TypeName = ?, Description = ? WHERE TypeId = ?",
         insuranceTypeEntity.getPolicyid(),
         insuranceTypeEntity.getTypename(),
         insuranceTypeEntity.getDescription(),
         insuranceTypeEntity.getTypeId()
    );
}
@Override
    public  ArrayList<Insurancetype> getAll() throws SQLException {
    ResultSet rst=CrudUtil.execute("SELECT * FROM InsuranceType");
    ArrayList<Insurancetype> insurancetypes=new ArrayList<>();

    while (rst.next()){
        Insurancetype insurancetype=new Insurancetype(
                rst.getInt("TypeId"),
                rst.getInt("PolicyId"),
                rst.getString("TypeName"),
                rst.getString("Description")
        );
        insurancetypes.add(insurancetype);
    }
    return insurancetypes;
//        String sql = "SELECT * FROM InsuranceType";
//        ResultSet resultSet = CrudUtil.execute(sql);
//
//        List<InsurancetypeDto> insuranceTypes = new ArrayList<>();
//        while (resultSet.next()) {
//            InsurancetypeDto insuranceType = new InsurancetypeDto();
//            insuranceType.setTypeId(resultSet.getInt("TypeId"));
//            insuranceType.setPolicyid(resultSet.getInt("PolicyId"));
//            insuranceType.setTypename(resultSet.getString("TypeName"));
//            insuranceType.setDescription(resultSet.getString("Description"));
//            insuranceTypes.add(insuranceType);
//        }
//        return insuranceTypes;
    }

    // New method to check if an insurance type exists by typeId
    public boolean doesInsuranceTypeExist(int typeId) throws SQLException {
        String sql = "SELECT TypeId FROM InsuranceType WHERE TypeId = ?";
        ResultSet resultSet = CrudUtil.execute(sql, typeId);
        return resultSet.next(); // If resultSet has any rows, the typeId exists
    }
    @Override
    public int getNextId() throws SQLException {
        String sql = "SELECT MAX(typeId) AS maxId FROM InsuranceType";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()) {
            int maxId = resultSet.getInt("maxId");
            return maxId+ 1; // Increment maxId by 1 for the next ID
        } else {
            return 1; // Start with 1 if there are no types
        }
    }

    @Override
    public ArrayList<Insurancetype> search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
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
