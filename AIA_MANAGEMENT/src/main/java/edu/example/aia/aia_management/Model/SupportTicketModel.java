package edu.example.aia.aia_management.Model;

import edu.example.aia.aia_management.dto.SupportTicketDto;
import edu.example.aia.aia_management.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupportTicketModel {

    // Generate the next Support Ticket ID
    public static int getNextSupportTicketId() throws SQLException {
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

    // Save a new SupportTicket
    public static boolean saveSupportTicket(SupportTicketDto supportTicket) throws SQLException {
        String sql = "INSERT INTO SupportTicket (SupportTicketId, CustomerId, IssueDescription, Status) VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql,
                supportTicket.getSupportTicketId(),
                supportTicket.getCustomerId(),
                supportTicket.getIssueDescription(),
                supportTicket.getStatus());
    }

    // Update an existing SupportTicket
    public static boolean updateSupportTicket(SupportTicketDto supportTicket) throws SQLException {
        String sql = "UPDATE SupportTicket SET CustomerId = ?, IssueDescription = ?, Status = ? WHERE SupportTicketId = ?";
        return CrudUtil.execute(sql,
                supportTicket.getCustomerId(),
                supportTicket.getIssueDescription(),
                supportTicket.getStatus(),
                supportTicket.getSupportTicketId());
    }

    // Delete a SupportTicket by ID
    public static boolean deleteSupportTicket(int supportTicketId) throws SQLException {
        String sql = "DELETE FROM SupportTicket WHERE SupportTicketId = ?";
        return CrudUtil.execute(sql, supportTicketId);
    }

    // Retrieve all SupportTickets from the database
    public static List<SupportTicketDto> getAllSupportTickets() throws SQLException {
        String sql = "SELECT * FROM SupportTicket";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<SupportTicketDto> ticketList = new ArrayList<>();

        while (resultSet.next()) {
            SupportTicketDto supportTicket = new SupportTicketDto(
                    resultSet.getInt("SupportTicketId"),
                    resultSet.getInt("CustomerId"),
                    resultSet.getString("IssueDescription"),
                    resultSet.getString("Status")
            );
            ticketList.add(supportTicket);
        }
        return ticketList;
    }

    // Search for a SupportTicket by ID
    public static SupportTicketDto searchSupportTicketById(int supportTicketId) throws SQLException {
        String sql = "SELECT * FROM SupportTicket WHERE SupportTicketId = ?";
        ResultSet resultSet = CrudUtil.execute(sql, supportTicketId);

        if (resultSet.next()) {
            return new SupportTicketDto(
                    resultSet.getInt("SupportTicketId"),
                    resultSet.getInt("CustomerId"),
                    resultSet.getString("IssueDescription"),
                    resultSet.getString("Status")
            );
        }
        return null; // Return null if no match is found
    }
}
