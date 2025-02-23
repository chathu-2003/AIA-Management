package edu.example.aia.aia_management.bo.custom;

import edu.example.aia.aia_management.bo.SuperBo;
import edu.example.aia.aia_management.dto.SupportTicketDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupportTicketBo extends SuperBo {
    // Generate the next Support Ticket ID
    int getNextSupportTicketId() throws SQLException;

    // Save a new SupportTicket
    boolean saveSupportTicket(SupportTicketDto supportTicket) throws SQLException, ClassNotFoundException;

    // Update an existing SupportTicket
    boolean updateSupportTicket(SupportTicketDto supportTicket) throws SQLException, ClassNotFoundException;

    // Delete a SupportTicket by ID
    boolean deleteSupportTicket(int supportTicketId) throws SQLException;

    // Retrieve all SupportTickets from the database
    ArrayList<SupportTicketDto> getAllSupportTickets() throws SQLException;

    // Search for a SupportTicket by ID
    ArrayList<SupportTicketDto> searchSupportTicketById(int supportTicketId) throws SQLException, ClassNotFoundException;
}
