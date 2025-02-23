package edu.example.aia.aia_management.bo.custom;

import edu.example.aia.aia_management.bo.SuperBo;
import edu.example.aia.aia_management.dto.ClaimDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ClaimBO extends SuperBo {
    boolean saveClaim(ClaimDto claim) throws SQLException, ClassNotFoundException;

    boolean updateClaim(ClaimDto claim) throws SQLException, ClassNotFoundException;

    boolean deleteClaim(int claimId) throws SQLException, ClassNotFoundException;

    ArrayList<ClaimDto> getAllClaims() throws SQLException,ClassNotFoundException;

    int getNextClaimId() throws SQLException, ClassNotFoundException;

   // ArrayList<ClaimDto> searchClaim(int newValue) throws SQLException, ClassNotFoundException;

    ArrayList<ClaimDto> searchClaim(String newValue) throws SQLException, ClassNotFoundException;

    // Search for a claim by ClaimId
  //  ArrayList<ClaimDto> searchClaim(String newValue) throws SQLException, ClassNotFoundException;
}
