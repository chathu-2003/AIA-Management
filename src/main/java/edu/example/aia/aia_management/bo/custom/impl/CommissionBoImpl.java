package edu.example.aia.aia_management.bo.custom.impl;

import edu.example.aia.aia_management.bo.custom.CommissionBO;
import edu.example.aia.aia_management.dao.DAOFactory;
import edu.example.aia.aia_management.dao.custom.CommissionDao;
import edu.example.aia.aia_management.dto.CommissionDTO;
import edu.example.aia.aia_management.entity.Commission;

import java.sql.SQLException;
import java.util.ArrayList;

public class CommissionBoImpl implements CommissionBO {

    CommissionDao commissionDao=(CommissionDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.COMMISSION);

    // Save a new Commission
    @Override
    public  boolean saveCommission(CommissionDTO commission) throws SQLException, ClassNotFoundException {
       // String sql = "INSERT INTO Commission (CommissionId, AgentId, Amount, Date) VALUES (?, ?, ?, ?)";
       // return CrudUtil.execute(sql, commission.getCommissionId(), commission.getAgentId(),
             //   commission.getAmount(), commission.getDate());
        Commission commissions=new Commission(commission.getCommissionId(),commission.getAgentId(),commission.getAmount(),commission.getDate());
        return commissionDao.save(commissions);
    }

    // Update an existing Commission
    @Override
    public  boolean updateCommission(CommissionDTO commission) throws SQLException, ClassNotFoundException {
//        String sql = "UPDATE Commission SET AgentId = ?, Amount = ?, Date = ? WHERE CommissionId = ?";
//        return CrudUtil.execute(sql, commission.getAgentId(), commission.getAmount(),
//                commission.getDate(), commission.getCommissionId());
        Commission commissions=new Commission(commission.getAgentId(),commission.getAmount(),commission.getDate(),commission.getCommissionId());
        return commissionDao.save(commissions);
    }

    @Override
    public boolean deleteCommission(int commissionId) throws SQLException, ClassNotFoundException {
        return false;
    }

    // Delete a Commission by ID
    @Override
    public  boolean deleteCommission(Commission commissionId) throws SQLException, ClassNotFoundException {
       // String sql = "DELETE FROM Commission WHERE CommissionId = ?";
        //return CrudUtil.execute(sql, commissionId);
        commissionDao.save(commissionId);
        return true;
    }

    // Get all Commissions from the database
    @Override
    public   ArrayList<CommissionDTO>getAllCommissions() throws SQLException, ClassNotFoundException {
//        String sql = "SELECT * FROM Commission";
//        ResultSet rs = CrudUtil.execute(sql);
//        List<CommissionDTO> commissionList = new ArrayList<>();
//
//        while (rs.next()) {
//            commissionList.add(new CommissionDTO(
//                    rs.getInt("CommissionId"),
//                    rs.getInt("AgentId"),
//                    rs.getBigDecimal("Amount"),
//                    rs.getDate("Date").toLocalDate()
//            ));
//        }
//        return commissionList;
        ArrayList<Commission> commissionArrayList=commissionDao.getAll();
        ArrayList<CommissionDTO> commissionDTOS=new ArrayList<>();

        for (Commission commission:commissionArrayList){
            commissionDTOS.add(new CommissionDTO(commission.getCommissionId(),commission.getAgentId(),commission.getAmount(),commission.getDate()));
        }
        return commissionDTOS;
    }

    @Override
    public ArrayList<CommissionDTO> searchCommission(String newValue) throws SQLException, ClassNotFoundException {
//        // Return null if no record is found String sql = "SELECT * FROM Commission WHERE CommissionId = ?";
////        ResultSet resultSet = CrudUtil.execute(sql, commissionId);
////
////        if (resultSet.next()) {
////            int agentId = resultSet.getInt("AgentId");
////            BigDecimal amount = resultSet.getBigDecimal("Amount");
////            LocalDate date = resultSet.getDate("Date").toLocalDate();
////
////            return new CommissionDTO(commissionId, agentId, amount, date);
////        }
////        return null;
        ArrayList<Commission> commissions=commissionDao.search(newValue);
        ArrayList<CommissionDTO> commissionDTOS=new ArrayList<>();
        for (Commission commission: commissions){
            CommissionDTO commissionDTO=new CommissionDTO();
            commissionDTO.getAgentId();
            commissionDTO.getAmount();
            commissionDTO.getDate();
        }
        return commissionDTOS;
    }

}
