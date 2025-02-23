package edu.example.aia.aia_management.bo.custom.impl;

import edu.example.aia.aia_management.bo.custom.InsuranceTypeBo;
import edu.example.aia.aia_management.dao.DAOFactory;
import edu.example.aia.aia_management.dao.custom.InusrancetypeDao;
import edu.example.aia.aia_management.dto.InsurancetypeDto;
import edu.example.aia.aia_management.entity.Insurancetype;
import edu.example.aia.aia_management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InsuranceTypeBoImpl implements InsuranceTypeBo {

InusrancetypeDao inusrancetypeDao=(InusrancetypeDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INUSRANCETYPE);
    @Override
public boolean addInsuranceType(InsurancetypeDto insuranceType) throws SQLException, ClassNotFoundException {
//        String sql = "INSERT INTO InsuranceType (TypeId, PolicyId, TypeName, Description) VALUES (?, ?, ?, ?)";
//        return CrudUtil.execute(sql, insuranceType.getTypeId(), insuranceType.getPolicyid(), insuranceType.getTypename(), insuranceType.getDescription());
        Insurancetype insurancetypes=new Insurancetype(insuranceType.getTypeId(),insuranceType.getPolicyid(),insuranceType.getTypename(),insuranceType.getDescription());
        return inusrancetypeDao.save(insurancetypes);
    }
@Override
    public boolean deleteInsuranceType(int typeId) throws SQLException {
//        String sql = "DELETE FROM InsuranceType WHERE TypeId = ?";
//        return CrudUtil.execute(sql, typeId);
        inusrancetypeDao.delete(typeId);
        return true;
    }
@Override
    public boolean updateInsuranceType(InsurancetypeDto insuranceType) throws SQLException, ClassNotFoundException {
//        String sql = "UPDATE InsuranceType SET PolicyId = ?, TypeName = ?, Description = ? WHERE TypeId = ?";
//        return CrudUtil.execute(sql, insuranceType.getPolicyid(), insuranceType.getTypename(), insuranceType.getDescription(), insuranceType.getTypeId());
   Insurancetype insurancetypes=new Insurancetype(insuranceType.getTypeId(),insuranceType.getPolicyid(),insuranceType.getTypename(),insuranceType.getDescription());
   return inusrancetypeDao.update(insurancetypes);
    }

    public ArrayList<InsurancetypeDto> getAllInsuranceTypes() throws SQLException, ClassNotFoundException {
        ArrayList<Insurancetype> insurancetypeArrayList=inusrancetypeDao.getAll();
        ArrayList<InsurancetypeDto> insurancetypeDtos=new ArrayList<>();

        for (Insurancetype insurancetype:insurancetypeArrayList){
            insurancetypeDtos.add(new InsurancetypeDto(insurancetype.getTypeId(),insurancetype.getPolicyid(),insurancetype.getTypename(),insurancetype.getDescription()));
        }
        return insurancetypeDtos;
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
    public int getNextTypeId() throws SQLException {
//        String sql = "SELECT MAX(typeId) AS maxId FROM InsuranceType";
//        ResultSet resultSet = CrudUtil.execute(sql);
//
//        if (resultSet.next()) {
//            int maxId = resultSet.getInt("maxId");
//            return String.valueOf(maxId + 1); // Increment maxId by 1 for the next ID
//        } else {
//            return "1"; // Start with 1 if there are no types
//        }
        return inusrancetypeDao.getNextId();
    }
    @Override
    public ArrayList< InsurancetypeDto> searchInsuranceTypeById(String newValue) throws SQLException, ClassNotFoundException {
        ArrayList<Insurancetype> insurancetypes=inusrancetypeDao.search(newValue);
        ArrayList<InsurancetypeDto> insurancetypeDtos=new ArrayList<>();

        for (Insurancetype insurancetype:insurancetypes){
            InsurancetypeDto insurancetypeDto=new InsurancetypeDto();
            insurancetypeDto.setTypeId(insurancetype.getTypeId());
            insurancetypeDto.setPolicyid(insurancetype.getPolicyid());
            insurancetypeDto.setTypename(insurancetype.getTypename());
            insurancetypeDto.setDescription(insurancetype.getDescription());
        }
        return insurancetypeDtos;
//        String sql = "SELECT * FROM InsuranceType WHERE TypeId = ?";
//        ResultSet resultSet = CrudUtil.execute(sql, typeId);
//
//        if (resultSet.next()) {
//            return new InsurancetypeDto(
//                    resultSet.getInt("TypeId"),
//                    resultSet.getInt("PolicyId"),
//                    resultSet.getString("TypeName"),
//                    resultSet.getString("Description")
//            );
//        }
//        return null; // Return null if no matching record is found
    }

}
