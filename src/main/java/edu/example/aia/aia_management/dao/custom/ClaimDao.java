package edu.example.aia.aia_management.dao.custom;

import edu.example.aia.aia_management.dao.CrudDAO;
import edu.example.aia.aia_management.dto.ClaimDto;
import edu.example.aia.aia_management.entity.Claim;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ClaimDao extends CrudDAO<Claim> {
  //  ArrayList<Claim> search(String newValue) throws SQLException, ClassNotFoundException;
}
