package edu.example.aia.aia_management.dao.custom.impl;

import edu.example.aia.aia_management.dao.SQlUtil;
import edu.example.aia.aia_management.dao.custom.ClaimDao;
import edu.example.aia.aia_management.entity.Claim;
import edu.example.aia.aia_management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClaimDaoImpl implements ClaimDao {

    @Override
    public  boolean save(Claim claimEntity) throws SQLException, ClassNotFoundException {
//        String sql = "INSERT INTO Claim (ClaimId, PolicyId, ClaimAmount, ClaimDate) VALUES (?, ?, ?, ?)";
//        return CrudUtil.execute(sql, claim.getClaimId(), claim.getPolicyId(), claim.getClaimAmount(), claim.getClaimDate());
    return SQlUtil.execute("INSERT INTO Claim (ClaimId, PolicyId, ClaimAmount, ClaimDate) VALUES (?, ?, ?, ?)",
            claimEntity.getClaimId(),
            claimEntity.getPolicyId(),
            claimEntity.getClaimAmount(),
            claimEntity.getClaimDate()
        );
    }

    // Update claim in the database
    @Override
    public  boolean update(Claim claimEntity) throws SQLException, ClassNotFoundException {
//        String sql = "UPDATE Claim SET PolicyId = ?, ClaimAmount = ?, ClaimDate = ? WHERE ClaimId = ?";
//        return CrudUtil.execute(sql, claim.getPolicyId(), claim.getClaimAmount(), claim.getClaimDate(), claim.getClaimId());
   return SQlUtil.execute("UPDATE Claim SET PolicyId = ?, ClaimAmount = ?, ClaimDate = ? WHERE ClaimId = ?",
           claimEntity.getPolicyId(),
           claimEntity.getClaimAmount(),
           claimEntity.getClaimDate(),
           claimEntity.getClaimId()
          );
    }

    // Delete claim from the database
    @Override
    public  boolean delete(int claimId) throws SQLException,ClassNotFoundException {
//        String sql = "DELETE FROM Claim WHERE ClaimId = ?";
//        return CrudUtil.execute(sql, claimId);
        CrudUtil.execute("DELETE FROM Claim WHERE ClaimId = ?", claimId);
        return true;
    }



    @Override
    public  ArrayList<Claim> getAll() throws SQLException,ClassNotFoundException {
//        String sql = "SELECT * FROM Claim";
//        ResultSet resultSet = CrudUtil.execute(sql);
//        List<ClaimDto> claims = new ArrayList<>();
//
//        while (resultSet.next()) {
//            claims.add(new ClaimDto(
//                    resultSet.getInt("ClaimId"),
//                    resultSet.getInt("PolicyId"),
//                    resultSet.getBigDecimal("ClaimAmount"),
//                    resultSet.getDate("ClaimDate")
//            ));
//        }
//        return claims;
        ResultSet rst = SQlUtil.execute("SELECT * FROM Claim");
        ArrayList<Claim> claims=new ArrayList<>();

        while (rst.next()){
            Claim claim=new Claim(
                    rst.getInt("ClaimId"),
                    rst.getInt("PolicyId"),
                    rst.getBigDecimal("ClaimAmount"),
                    rst.getDate("ClaimDate")
            );
            claims.add(claim);
        }
        return claims;
    }

    // Get the next Claim ID
    @Override
    public int  getNextId() throws SQLException {
        String sql = "SELECT MAX(ClaimId) AS maxId FROM Claim";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()) {
            int maxId = resultSet.getInt("maxId");
            return maxId + 1; // Increment the highest ID by 1
        } else {
            return 1; // Start from 1 if no IDs are found
        }
    }

    // Search for a claim by ClaimId
//    public  ClaimDto search(int claimId) throws SQLException {
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


    @Override
    public ArrayList<Claim> search(String newValue) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQlUtil.execute("SELECT * FROM Claim WHERE ClaimId = ?",newValue);
        ArrayList<Claim> searchResults = new ArrayList<>();
        while (rst.next()){
            Claim claim=new Claim(
                    rst.getInt("ClaimId"),
                    rst.getInt("PolicyId"),
                    rst.getBigDecimal("ClaimAmount"),
                    rst.getDate("ClaimDate")
            );
            searchResults.add(claim);
        }
        return searchResults;
    }
}
