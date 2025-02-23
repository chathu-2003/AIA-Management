package edu.example.aia.aia_management.bo.custom;

import edu.example.aia.aia_management.bo.SuperBo;
import edu.example.aia.aia_management.dto.PaymentDto;
import edu.example.aia.aia_management.dto.PolicyDto;
import edu.example.aia.aia_management.dto.SavePaymentDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PaymentBo extends SuperBo{


    // Update payment details
    boolean updatePayment(PaymentDto payment) throws SQLException, ClassNotFoundException;

    // Delete payment by ID
    boolean deletePayment(int paymentId) throws SQLException, ClassNotFoundException;

    // Get all payments
    ArrayList<PaymentDto> getAllPayments() throws SQLException, ClassNotFoundException;

    // Get the next payment ID
    int getNextPaymentId() throws SQLException, ClassNotFoundException;

    List<PolicyDto> getAllPolicies() throws SQLException;

    public boolean savePayment(SavePaymentDto savePaymentDto) throws SQLException;
    }
