package edu.example.aia.aia_management.dao.custom;

import edu.example.aia.aia_management.dao.CrudDAO;
import edu.example.aia.aia_management.dto.InsurancetypeDto;
import edu.example.aia.aia_management.entity.Insurancetype;

import java.sql.SQLException;

public interface InusrancetypeDao extends CrudDAO<Insurancetype> {
    boolean save(Insurancetype insuranceType) throws SQLException, ClassNotFoundException;

    boolean delete(int typeId) throws SQLException;

    boolean update(Insurancetype insuranceType) throws SQLException, ClassNotFoundException;

    int getNextId() throws SQLException;
}
