package edu.example.aia.aia_management.Model;

import edu.example.aia.aia_management.dto.ClaimDto;
import edu.example.aia.aia_management.util.CrudUtil;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClaimModel {

    // Save claim to the database
    public static boolean saveClaim(ClaimDto claim) throws SQLException {
        String sql = "INSERT INTO Claim (ClaimId, PolicyId, ClaimAmount, ClaimDate) VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql, claim.getClaimId(), claim.getPolicyId(), claim.getClaimAmount(), claim.getClaimDate());
    }

    // Update claim in the database
    public static boolean updateClaim(ClaimDto claim) throws SQLException {
        String sql = "UPDATE Claim SET PolicyId = ?, ClaimAmount = ?, ClaimDate = ? WHERE ClaimId = ?";
        return CrudUtil.execute(sql, claim.getPolicyId(), claim.getClaimAmount(), claim.getClaimDate(), claim.getClaimId());
    }

    // Delete claim from the database
    public static boolean deleteClaim(int claimId) throws SQLException {
        String sql = "DELETE FROM Claim WHERE ClaimId = ?";
        return CrudUtil.execute(sql, claimId);
    }

    // Retrieve all claims from the database
    public static List<ClaimDto> getAllClaims() throws SQLException {
        String sql = "SELECT * FROM Claim";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<ClaimDto> claims = new ArrayList<>();

        while (resultSet.next()) {
            claims.add(new ClaimDto(
                    resultSet.getInt("ClaimId"),
                    resultSet.getInt("PolicyId"),
                    resultSet.getBigDecimal("ClaimAmount"),
                    resultSet.getDate("ClaimDate")
            ));
        }
        return claims;
    }

    // Get the next Claim ID
    public static int getNextClaimId() throws SQLException {
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
    public static ClaimDto searchClaim(int claimId) throws SQLException {
        String sql = "SELECT * FROM Claim WHERE ClaimId = ?";
        ResultSet resultSet = CrudUtil.execute(sql, claimId);

        if (resultSet.next()) {
            int policyId = resultSet.getInt("PolicyId");
            BigDecimal claimAmount = resultSet.getBigDecimal("ClaimAmount");
            Date claimDate = resultSet.getDate("ClaimDate");
            return new ClaimDto(claimId, policyId, claimAmount, claimDate);
        }
        return null; // Return null if no claim is found
    }
}
