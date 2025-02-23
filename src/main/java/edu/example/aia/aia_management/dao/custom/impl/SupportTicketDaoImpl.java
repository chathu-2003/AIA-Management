package edu.example.aia.aia_management.dao.custom.impl;

import edu.example.aia.aia_management.dao.SQlUtil;
import edu.example.aia.aia_management.dao.custom.SupportTickketDao;
import edu.example.aia.aia_management.entity.SupportTicket;
import edu.example.aia.aia_management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class  SupportTicketDaoImpl implements SupportTickketDao{

    // Generate the next Support Ticket ID
    @Override
    public int getNextId() throws SQLException {
        String sql = "SELECT MAX(SupportTicketId) FROM SupportTicket";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()) {
            // Get the highest current ID and increment by 1
            int maxId = resultSet.getInt(1);
            return maxId + 1;
        } else {
            // If there are no entries, start with ID 1
            return 1;
        }
    }

    @Override
    public ArrayList<SupportTicket> search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }

    // Save a new SupportTicket
    @Override
    public  boolean save(SupportTicket supportTickets) throws SQLException, ClassNotFoundException {
     //   String sql = "INSERT INTO SupportTicket (SupportTicketId, CustomerId, IssueDescription, Status) VALUES (?, ?, ?, ?)";
        return SQlUtil.execute("INSERT INTO SupportTicket (SupportTicketId, CustomerId, IssueDescription, Status) VALUES (?, ?, ?, ?)",
                supportTickets.getSupportTicketId(),
                supportTickets.getCustomerId(),
                supportTickets.getIssueDescription(),
                supportTickets.getStatus());
    }

    // Update an existing SupportTicket
    @Override
    public  boolean update(SupportTicket supportTicket) throws SQLException, ClassNotFoundException {
     //   String sql = "UPDATE SupportTicket SET CustomerId = ?, IssueDescription = ?, Status = ? WHERE SupportTicketId = ?";
        return SQlUtil.execute("UPDATE SupportTicket SET CustomerId = ?, IssueDescription = ?, Status = ? WHERE SupportTicketId = ?",
                supportTicket.getCustomerId(),
                supportTicket.getIssueDescription(),
                supportTicket.getStatus(),
                supportTicket.getSupportTicketId());
    }

    // Delete a SupportTicket by ID
    @Override
    public  boolean delete(int supportTicketId) throws SQLException {
      //  String sql = "DELETE FROM SupportTicket WHERE SupportTicketId = ?";
         CrudUtil.execute("DELETE FROM SupportTicket WHERE SupportTicketId = ?", supportTicketId);
   return true;
    }

    // Retrieve all SupportTickets from the database
    @Override
    public ArrayList<SupportTicket> getAll() throws SQLException {
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
    ResultSet rst=CrudUtil.execute("SELECT * FROM SupportTicket");
    ArrayList<SupportTicket> supportTickets=new ArrayList<>();

    while (rst.next()){
        SupportTicket supportTicket=new SupportTicket(
                rst.getInt("SupportTicketId"),
                rst.getInt("CustomerId"),
                rst.getString("IssueDescription"),
                rst.getString("Status")
        );
        supportTickets.add(supportTicket);
    }
    return supportTickets;
    }

    // Search for a SupportTicket by ID
    @Override
    public ArrayList<SupportTicket> search(int newValue) throws SQLException, ClassNotFoundException {
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
   ResultSet rst =SQlUtil.execute("SELECT * FROM SupportTicket WHERE SupportTicketId = ?",newValue);
   ArrayList<SupportTicket> searchResults=new ArrayList<>();

   while (rst.next()){
       SupportTicket supportTicket=new SupportTicket(
               rst.getInt("SupportTicketId"),
               rst.getInt("CustomerId"),
               rst.getString("IssueDescription"),
               rst.getString("Status")
       );
        searchResults.add(supportTicket);
       }
   return searchResults;
    }
}