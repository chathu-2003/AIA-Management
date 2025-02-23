package edu.example.aia.aia_management.bo.custom.impl;

import edu.example.aia.aia_management.bo.custom.SupportTicketBo;
import edu.example.aia.aia_management.dao.DAOFactory;
import edu.example.aia.aia_management.dao.custom.SupportTickketDao;
import edu.example.aia.aia_management.dto.SupportTicketDto;
import edu.example.aia.aia_management.entity.SupportTicket;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupportTicketBoImpl implements SupportTicketBo {

SupportTickketDao supportTickketDao=(SupportTickketDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPORTTICKET);
    // Generate the next Support Ticket ID
    @Override
    public  int getNextSupportTicketId() throws SQLException {
//        String sql = "SELECT MAX(SupportTicketId) FROM SupportTicket";
//        ResultSet resultSet = CrudUtil.execute(sql);
//
//        if (resultSet.next()) {
//            // Get the highest current ID and increment by 1
//            int maxId = resultSet.getInt(1);
//            return maxId + 1;
//        } else {
//            // If there are no entries, start with ID 1
//            return 1;
        return supportTickketDao.getNextId();
    }

    // Save a new SupportTicket
    @Override
    public boolean saveSupportTicket(SupportTicketDto supportTicket) throws SQLException, ClassNotFoundException {
        //String sql = "INSERT INTO SupportTicket (SupportTicketId, CustomerId, IssueDescription, Status) VALUES (?, ?, ?, ?)";
        SupportTicket supporttickets=new SupportTicket(
                supportTicket.getSupportTicketId(),
                supportTicket.getCustomerId(),
                supportTicket.getIssueDescription(),
                supportTicket.getStatus());
        return supportTickketDao.save(supporttickets);
    }

    // Update an existing SupportTicket
    @Override
    public  boolean updateSupportTicket(SupportTicketDto supportTicket) throws SQLException, ClassNotFoundException {
       // String sql = "UPDATE SupportTicket SET CustomerId = ?, IssueDescription = ?, Status = ? WHERE SupportTicketId = ?";
        SupportTicket supportTicket1=new SupportTicket(
                supportTicket.getSupportTicketId(),
                supportTicket.getCustomerId(),
                supportTicket.getIssueDescription(),
                supportTicket.getStatus());
        return supportTickketDao.update(supportTicket1);
    }

    // Delete a SupportTicket by ID
    @Override
    public  boolean deleteSupportTicket(int supportTicketId) throws SQLException {
//        String sql = "DELETE FROM SupportTicket WHERE SupportTicketId = ?";
//        return CrudUtil.execute(sql, supportTicketId);
   supportTickketDao.delete(supportTicketId);
   return true;
    }

    // Retrieve all SupportTickets from the database
    @Override
    public ArrayList<SupportTicketDto> getAllSupportTickets() throws SQLException {
//        String sql = "SELECT * FROM SupportTicket";
//        ResultSet resultSet = CrudUtil.execute(sql);
//        List<SupportTicketDto> ticketList = new ArrayList<>();
//
//        while (resultSet.next()) {
//            SupportTicketDto supportTicket = new SupportTicketDto(
//                    resultSet.getInt("SupportTicketId"),
//                    resultSet.getInt("CustomerId"),
//                    resultSet.getString("IssueDescription"),
//                    resultSet.getString("Status")
//            );
//            ticketList.add(supportTicket);
//        }
//        return ticketList;
   ArrayList<SupportTicket> supportTicketArrayList=supportTickketDao.getAll();
   ArrayList<SupportTicketDto> supportTicketDtos=new ArrayList<>();

   for (SupportTicket supportTicket:supportTicketArrayList){
       supportTicketDtos.add(new SupportTicketDto(supportTicket.getSupportTicketId(),supportTicket.getCustomerId(),supportTicket.getIssueDescription(),supportTicket.getStatus()));


   }
   return supportTicketDtos;
    }

    // Search for a SupportTicket by ID
    @Override
    public ArrayList<SupportTicketDto> searchSupportTicketById(int supportTicketId) throws SQLException, ClassNotFoundException {
//        String sql = "SELECT * FROM SupportTicket WHERE SupportTicketId = ?";
//        ResultSet resultSet = CrudUtil.execute(sql, supportTicketId);
//
//        if (resultSet.next()) {
//            return new SupportTicketDto(
//                    resultSet.getInt("SupportTicketId"),
//                    resultSet.getInt("CustomerId"),
//                    resultSet.getString("IssueDescription"),
//                    resultSet.getString("Status")
//            );
//        }
//        return null; //
   ArrayList<SupportTicket> supportTickets=supportTickketDao.search(supportTicketId);
   ArrayList<SupportTicketDto> supportTicketDtos=new ArrayList<>();

   for (SupportTicket supportTicket:supportTickets){
       SupportTicketDto supportTicketDto= new SupportTicketDto();
       supportTicketDto.setSupportTicketId(supportTicket.getSupportTicketId());
       supportTicketDto.setCustomerId(supportTicket.getCustomerId());
       supportTicketDto.setIssueDescription(supportTicket.getIssueDescription());
       supportTicketDto.setStatus(supportTicket.getStatus());

   }
   return supportTicketDtos;
    }
}
