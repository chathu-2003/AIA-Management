package edu.example.aia.aia_management.bo.custom;

import edu.example.aia.aia_management.bo.SuperBo;
import edu.example.aia.aia_management.dto.InsurancetypeDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InsuranceTypeBo extends SuperBo{
    boolean addInsuranceType(InsurancetypeDto insuranceType) throws SQLException, ClassNotFoundException;

    boolean deleteInsuranceType(int typeId) throws SQLException;

    boolean updateInsuranceType(InsurancetypeDto insuranceType) throws SQLException, ClassNotFoundException;

    int getNextTypeId() throws SQLException;

    ArrayList< InsurancetypeDto> searchInsuranceTypeById(String newValue) throws SQLException, ClassNotFoundException;
}
