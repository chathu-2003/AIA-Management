package edu.example.aia.aia_management.Model;

import edu.example.aia.aia_management.dto.PaymentDto;
import edu.example.aia.aia_management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentModel {

    // Save payment details
    public boolean savePayment(PaymentDto payment) throws SQLException {
        String sql = "INSERT INTO payment (paymentId, policyId, amount, paymentMethod) VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql, payment.getPaymentId(), payment.getPolicyId(), payment.getAmount(), payment.getPaymentMethod());
    }

    // Update payment details
    public boolean updatePayment(PaymentDto payment) throws SQLException {
        String sql = "UPDATE payment SET policyId=?, amount=?, paymentMethod=? WHERE paymentId=?";
        return CrudUtil.execute(sql, payment.getPolicyId(), payment.getAmount(), payment.getPaymentMethod(), payment.getPaymentId());
    }

    // Delete payment by ID
    public boolean deletePayment(int paymentId) throws SQLException {
        String sql = "DELETE FROM payment WHERE paymentId=?";
        return CrudUtil.execute(sql, paymentId);
    }

    // Get all payments
    public List<PaymentDto> getAllPayments() throws SQLException {
        String sql = "SELECT * FROM payment";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<PaymentDto> paymentList = new ArrayList<>();

        while (resultSet.next()) {
            PaymentDto payment = new PaymentDto(
                    resultSet.getInt("paymentId"),
                    resultSet.getInt("policyId"),
                    resultSet.getBigDecimal("amount"),
                    resultSet.getString("paymentMethod")
            );
            paymentList.add(payment);
        }
        return paymentList;
    }

    // Get the next payment ID
    public String getNextPaymentId() throws SQLException {
        String sql = "SELECT MAX(paymentId) AS maxId FROM payment";  // Ensure 'payment' table is the correct name
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()) {
            int maxId = resultSet.getInt("maxId");
            return String.valueOf(maxId + 1); // Increment maxId by 1 for the next ID
        } else {
            return "1"; // Start with 1 if there are no payment records
        }
    }

    // Get payment by ID
    public PaymentDto getPaymentById(int paymentId) throws SQLException {
        String sql = "SELECT * FROM payment WHERE paymentId = ?";  // Adjust the query based on your table and column names
        ResultSet resultSet = CrudUtil.execute(sql, paymentId);

        if (resultSet.next()) {
            return new PaymentDto(
                    resultSet.getInt("paymentId"),
                    resultSet.getInt("policyId"),
                    resultSet.getBigDecimal("amount"),
                    resultSet.getString("paymentMethod")
            );
        }
        return null;  // Return null if no payment is found with the given ID
    }
}
