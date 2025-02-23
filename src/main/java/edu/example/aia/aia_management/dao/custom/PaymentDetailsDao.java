package edu.example.aia.aia_management.dao.custom;

import edu.example.aia.aia_management.entity.PaymentDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentDetailsDao {
    ArrayList<PaymentDetails> getAll() throws SQLException, ClassNotFoundException;

    // Method to delete a payment detail by paymentId
    boolean delete(int paymentId) throws SQLException, ClassNotFoundException;
}
