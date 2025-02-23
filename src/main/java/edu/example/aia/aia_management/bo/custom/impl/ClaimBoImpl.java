package edu.example.aia.aia_management.bo.custom.impl;

import edu.example.aia.aia_management.bo.custom.ClaimBO;
import edu.example.aia.aia_management.dao.DAOFactory;
import edu.example.aia.aia_management.dao.custom.ClaimDao;
import edu.example.aia.aia_management.dto.ClaimDto;
import edu.example.aia.aia_management.entity.Claim;


import java.sql.SQLException;
import java.util.ArrayList;

public class ClaimBoImpl implements ClaimBO {

ClaimDao claimDao = (ClaimDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CLAIM);

    // Save claim to the database
    @Override
    public  boolean saveClaim(ClaimDto claim) throws SQLException, ClassNotFoundException {
//        String sql = "INSERT INTO Claim (ClaimId, PolicyId, ClaimAmount, ClaimDate) VALUES (?, ?, ?, ?)";
//        return CrudUtil.execute(sql, claim.getClaimId(), claim.getPolicyId(), claim.getClaimAmount(), claim.getClaimDate());
   Claim claims = new Claim(claim.getClaimId(),claim.getPolicyId(),claim.getClaimAmount(),claim.getClaimDate());
   return claimDao.save(claims);
    }

    @Override

    public  boolean updateClaim(ClaimDto claim) throws SQLException, ClassNotFoundException {
//        String sql = "UPDATE Claim SET PolicyId = ?, ClaimAmount = ?, ClaimDate = ? WHERE ClaimId = ?";
//        return CrudUtil.execute(sql, claim.getPolicyId(), claim.getClaimAmount(), claim.getClaimDate(), claim.getClaimId());
        Claim claims = new Claim(claim.getClaimId(),claim.getPolicyId(),claim.getClaimAmount(),claim.getClaimDate());
        return claimDao.update(claims);
    }

    // Delete claim from the database
    @Override
    public  boolean deleteClaim(int claimId) throws SQLException, ClassNotFoundException {
//        String sql = "DELETE FROM Claim WHERE ClaimId = ?";
//        return CrudUtil.execute(sql, claimId);
        claimDao.delete(claimId);
        return true;
    }

    // Retrieve all claims from the database
    //public  List<ClaimDto> getAllClaims() throws SQLException {
//        String sql = "SELECT * FROM Claim";
//        ResultSet resultSet = CrudUtil.execute(sql);
//        List<ClaimDto> claims = new ArrayList<>();

//        while (resultSet.next()) {
//            claims.add(new ClaimDto(
//                    resultSet.getInt("ClaimId"),
//                    resultSet.getInt("PolicyId"),
//                    resultSet.getBigDecimal("ClaimAmount"),
//                    resultSet.getDate("ClaimDate")
//            ));
//        }
//        return claims;
    //}
@Override
    public ArrayList<ClaimDto>getAllClaims() throws SQLException,ClassNotFoundException{
        ArrayList<Claim> claimList =claimDao.getAll();
        ArrayList<ClaimDto> claimDtos=new ArrayList<>();

        for (Claim claim: claimList){
            claimDtos.add(new ClaimDto(claim.getClaimId(),claim.getPolicyId(),claim.getClaimAmount(),claim.getClaimDate()));

        }
        return claimDtos;
    }


    // Get the next Claim ID
    @Override
    public  int getNextClaimId() throws SQLException, ClassNotFoundException {
//        String sql = "SELECT MAX(ClaimId) AS maxId FROM Claim";
//        ResultSet resultSet = CrudUtil.execute(sql);
//
//        if (resultSet.next()) {
//            int maxId = resultSet.getInt("maxId");
//            return maxId + 1; // Increment the highest ID by 1
//        } else {
//            return 1; // Start from 1 if no IDs are found
//        }
        return claimDao.getNextId();
    }


    @Override
    public ArrayList<ClaimDto> searchClaim(String newValue) throws SQLException, ClassNotFoundException {
        ArrayList<Claim> claims = claimDao.search(newValue);
        ArrayList<ClaimDto> customerDTOS=new ArrayList<>();
        for (Claim claim :claims){
            ClaimDto claimDto=new ClaimDto();
            claimDto.setPolicyId(claim.getPolicyId());
            claimDto.setClaimAmount(claim.getClaimAmount());
            claimDto.setClaimDate(claim.getClaimDate());
            customerDTOS.add(claimDto);
        }
        return customerDTOS;
    }
    }

    // Search for a claim by ClaimId

  //  public ArrayList<ClaimDto> searchClaim(String newValue) throws SQLException, ClassNotFoundException {
//        String sql = "SELECT * FROM Claim WHERE ClaimId = ?";
//        ResultSet resultSet = CrudUtil.execute(sql, claimId);
//
//        if (resultSet.next()) {
//            int policyId = resultSet.getInt("PolicyId");
//            BigDecimal claimAmount = resultSet.getBigDecimal("ClaimAmount");
//            Date claimDate = resultSet.getDate("ClaimDate");
//            return new ClaimDto(claimId, policyId, claimAmount, claimDate);
//        }
//        return null; // Return null if no claim is found
//    }


