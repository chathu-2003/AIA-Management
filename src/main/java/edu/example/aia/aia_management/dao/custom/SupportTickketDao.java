package edu.example.aia.aia_management.dao.custom;

import edu.example.aia.aia_management.dao.CrudDAO;
import edu.example.aia.aia_management.entity.SupportTicket;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupportTickketDao extends CrudDAO<SupportTicket> {
    // Generate the next Support Ticket ID
    int getNextId() throws SQLException;

    // Save a new SupportTicket
    boolean save(SupportTicket supportTickets) throws SQLException, ClassNotFoundException;

    // Update an existing SupportTicket
    boolean update(SupportTicket supportTicket) throws SQLException, ClassNotFoundException;

    // Delete a SupportTicket by ID
    boolean delete(int supportTicketId) throws SQLException;

    // Retrieve all SupportTickets from the database
    ArrayList<SupportTicket> getAll() throws SQLException;

    // Search for a SupportTicket by ID
    ArrayList<SupportTicket> search(int newValue) throws SQLException, ClassNotFoundException;
}
