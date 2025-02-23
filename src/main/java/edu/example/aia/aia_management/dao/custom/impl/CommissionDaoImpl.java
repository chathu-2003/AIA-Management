package edu.example.aia.aia_management.dao.custom.impl;

import edu.example.aia.aia_management.dao.SQlUtil;
import edu.example.aia.aia_management.dao.custom.CommissionDao;
import edu.example.aia.aia_management.dto.CommissionDTO;
import edu.example.aia.aia_management.entity.Commission;
import edu.example.aia.aia_management.util.CrudUtil;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommissionDaoImpl implements CommissionDao {

    @Override
    public  boolean save(Commission commissionEntity) throws SQLException, ClassNotFoundException {
//        String sql = "INTO Commission (CommissionId, AgentId, Amount, Date) VALUES (?, ?, ?, ?)";
//        return CrudUtil.execute(sql, commission.getCommissionId(), commission.getAgentId(),
             //   commission.getAmount(), commission.getDate());
        return SQlUtil.execute("INTO Commission (CommissionId, AgentId, Amount, Date) VALUES (?, ?, ?, ?)",
                commissionEntity.getCommissionId(),
                commissionEntity.getAgentId(),
                commissionEntity.getAmount(),
                commissionEntity.getDate());
    }

    // Update an existing Commission
    @Override
    public  boolean update(Commission commissionEntity) throws SQLException, ClassNotFoundException {
       // String sql = "UPDATE Commission SET AgentId = ?, Amount = ?, Date = ? WHERE CommissionId = ?";
        //return CrudUtil.execute(sql, commission.getAgentId(), commission.getAmount(),
               // commission.getDate(), commission.getCommissionId());
        return SQlUtil.execute("UPDATE Commission SET AgentId = ?, Amount = ?, Date = ? WHERE CommissionId = ?",
              commissionEntity.getAgentId(),
              commissionEntity.getAmount(),
              commissionEntity.getDate(),
              commissionEntity.getCommissionId()
        );
    }

    // Delete a Commission by ID
    @Override
    public  boolean delete(int commissionId) throws SQLException {
       // String sql = "DELETE FROM Commission WHERE CommissionId = ?";
        //return CrudUtil.execute(sql, commissionId);
        CrudUtil.execute("DELETE FROM Commission WHERE CommissionId = ?");
        return true;
    }

    @Override
    public int getNextId() throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public ArrayList<Commission> getAll() throws SQLException,ClassNotFoundException{
        ResultSet rst=CrudUtil.execute("SELECT * FROM Commission");
        ArrayList<Commission> commissions=new ArrayList<>();

        while (rst.next()){
             Commission commission=new Commission(
                     rst.getInt("CommissionId"),
                     rst.getInt("AgentId"),
                     rst.getBigDecimal("Amount"),
                     rst.getDate("Date").toLocalDate()
            );
             commissions.add(commission);
        }
        return commissions;
    }

//    // Get all Commissions from the database
//    public static List<Commission> getAll() throws SQLException {
//        String sql = "SELECT * FROM Commission";
//        ResultSet rs = CrudUtil.execute(sql);
//        List<CommissionDTO> commissionList = new ArrayList<>();
//
//        while (rs.next()) {
//            commissionLi  st.add(new CommissionDTO(
//                    rs.getInt("CommissionId"),
//                    rs.getInt("AgentId"),
//                    rs.getBigDecimal("Amount"),
//                    rs.getDate("Date").toLocalDate()
//            ));
//        }
//        return commissionList;
//    }
    public ArrayList <Commission> search(String newValue) throws SQLException, ClassNotFoundException {
//        String sql = "SELECT * FROM Commission WHERE CommissionId = ?";
//        ResultSet resultSet = CrudUtil.execute(sql, commissionId);
//
//        if (resultSet.next()) {
//            int agentId = resultSet.getInt("AgentId");
//            BigDecimal amount = resultSet.getBigDecimal("Amount");
//            LocalDate date = resultSet.getDate("Date").toLocalDate();
//
//            return new CommissionDTO(commissionId, agentId, amount, date);
//        }
//        return null; // Return null if no record is found
        ResultSet rst=SQlUtil.execute("SELECT * FROM Commission WHERE CommissionId = ?",newValue);
        ArrayList<Commission> searchResults=new ArrayList<>();
        while (rst.next()){
            Commission commission=new Commission(
                    rst.getInt("CommissionId"),
                    rst.getInt("AgentId"),
                    rst.getBigDecimal("Amount"),
                    rst.getDate("Date").toLocalDate()
            );
            searchResults.add(commission);
        }
        return searchResults;
    }

}
