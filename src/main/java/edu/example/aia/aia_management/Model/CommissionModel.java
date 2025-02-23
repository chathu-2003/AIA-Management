package edu.example.aia.aia_management.Model;

import edu.example.aia.aia_management.dto.CommissionDTO;
import edu.example.aia.aia_management.util.CrudUtil;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommissionModel {

    // Save a new Commission
    public static boolean saveCommission(CommissionDTO commission) throws SQLException {
        String sql = "INSERT INTO Commission (CommissionId, AgentId, Amount, Date) VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql, commission.getCommissionId(), commission.getAgentId(),
                commission.getAmount(), commission.getDate());
    }

    // Update an existing Commission
    public static boolean updateCommission(CommissionDTO commission) throws SQLException {
        String sql = "UPDATE Commission SET AgentId = ?, Amount = ?, Date = ? WHERE CommissionId = ?";
        return CrudUtil.execute(sql, commission.getAgentId(), commission.getAmount(),
                commission.getDate(), commission.getCommissionId());
    }

    // Delete a Commission by ID
    public static boolean deleteCommission(int commissionId) throws SQLException {
        String sql = "DELETE FROM Commission WHERE CommissionId = ?";
        return CrudUtil.execute(sql, commissionId);
    }

    // Get all Commissions from the database
    public static List<CommissionDTO> getAllCommissions() throws SQLException {
        String sql = "SELECT * FROM Commission";
        ResultSet rs = CrudUtil.execute(sql);
        List<CommissionDTO> commissionList = new ArrayList<>();

        while (rs.next()) {
            commissionList.add(new CommissionDTO(
                    rs.getInt("CommissionId"),
                    rs.getInt("AgentId"),
                    rs.getBigDecimal("Amount"),
                    rs.getDate("Date").toLocalDate()
            ));
        }
        return commissionList;
    }
    public static CommissionDTO searchCommission(int commissionId) throws SQLException {
        String sql = "SELECT * FROM Commission WHERE CommissionId = ?";
        ResultSet resultSet = CrudUtil.execute(sql, commissionId);

        if (resultSet.next()) {
            int agentId = resultSet.getInt("AgentId");
            BigDecimal amount = resultSet.getBigDecimal("Amount");
            LocalDate date = resultSet.getDate("Date").toLocalDate();

            return new CommissionDTO(commissionId, agentId, amount, date);
        }
        return null; // Return null if no record is found
    }

}
