package edu.example.aia.aia_management.dao.custom.impl;

import edu.example.aia.aia_management.dao.SQlUtil;
import edu.example.aia.aia_management.dao.custom.PaymentDao;
import edu.example.aia.aia_management.db.DBConection;
import edu.example.aia.aia_management.dto.PaymentDetailsDto;
import edu.example.aia.aia_management.dto.PaymentDto;
import edu.example.aia.aia_management.dto.SavePaymentDto;
import edu.example.aia.aia_management.entity.Payment;
import edu.example.aia.aia_management.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDaoImpl implements PaymentDao{

    public boolean save(SavePaymentDto savePaymentDto) throws SQLException {
        Connection connection = DBConection.getInstance().getConnection();

        try {
            // Start a database transaction
            connection.setAutoCommit(false);

            // Flag to track if all PaymentDto objects were saved successfully
            boolean isPaymentSaved = true;

            String sql = "INSERT INTO payment (PaymentId,Amount, PaymentMethod) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (PaymentDto paymentDto : savePaymentDto.getPayment()) {
                preparedStatement.setInt(1, paymentDto.getPaymentId());
                preparedStatement.setBigDecimal(2, paymentDto.getAmount());
                preparedStatement.setString(3, paymentDto.getPaymentMethod());

                if (!(preparedStatement.executeUpdate() > 0)) {
                    isPaymentSaved = false;
                }
            }

            // Proceed to save payment details only if payment data was successfully inserted
            if (isPaymentSaved) {
                boolean isPaymentDetailsSaved = true;

                // Insert payment details into the paymentdetails table
                String sqlPaymentDetails = "INSERT INTO paymentdetails (PaymentId, PolicyId, status, Month) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatementOrderDetails = connection.prepareStatement(sqlPaymentDetails);

                for (PaymentDetailsDto paymentDetailsDto : savePaymentDto.getPaymentDetails()) {
                    preparedStatementOrderDetails.setInt(1, paymentDetailsDto.getPaymentId());
                    preparedStatementOrderDetails.setInt(2, paymentDetailsDto.getPolicyId());
                    preparedStatementOrderDetails.setString(3, paymentDetailsDto.getStatus());
                    preparedStatementOrderDetails.setString(4, paymentDetailsDto.getMonth());

                    // If any payment details insert fails, set flag to false
                    if (!(preparedStatementOrderDetails.executeUpdate() > 0)) {
                        isPaymentDetailsSaved = false;
                    }
                };

                // Commit the transaction only if all operations were successful
                if (isPaymentDetailsSaved) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            } else {
                connection.rollback();
                return false;
            }
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
            return false;
        } finally {
            connection.setAutoCommit(true); // Reset auto-commit mode
        }
    }

 @Override
    public boolean update(Payment payment) throws SQLException, ClassNotFoundException {
//        String sql = "UPDATE payment SET amount = ?, paymentMethod = ? WHERE paymentId = ?";
//        return CrudUtil.execute(sql, payment.getAmount(), payment.getPaymentMethod(), payment.getPaymentId());
        return SQlUtil.execute("UPDATE payment SET amount = ?, paymentMethod = ? WHERE paymentId = ?",
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentId()
        );
    }

    // Delete payment by ID
    @Override
    public boolean delete(int paymentId) throws SQLException {
//        String sql = "DELETE FROM payment WHERE paymentId = ?";
//        return CrudUtil.execute(sql, paymentId);
        CrudUtil.execute( "DELETE FROM payment WHERE paymentId = ?" , paymentId);
        return true;
    }

    // Get all payments
   @Override
    public ArrayList<Payment> getAll() throws SQLException, ClassNotFoundException {
//        while (resultSet.next()) {
//            PaymentDto payment = new PaymentDto(
//                    resultSet.getInt("paymentId"),
//                    resultSet.getBigDecimal("amount"),
//                    resultSet.getString("paymentMethod")
//            );
//            paymentList.add(payment);
//        }
//        return paymentList;
//        String sql = "SELECT * FROM payment";
//        ResultSet resultSet = CrudUtil.execute(sql);
//        ArrayList<PaymentDto> paymentList = new ArrayList<>();

        ResultSet rst = SQlUtil.execute("SELECT * FROM payment");
        ArrayList<Payment> payments = new ArrayList<>();
        while (rst.next()) {
            Payment payment = new Payment(
                    rst.getInt("paymentId"),
                    rst.getBigDecimal("amount"),
                    rst.getString("paymentMethod")
            );
            payments.add(payment);
        }
        return payments;
    }

    @Override
    public boolean save(Payment Dto) throws SQLException, ClassNotFoundException {
        return false;
    }



    // Get the next payment ID
    public int getNextId() throws SQLException {
        String sql = "SELECT MAX(paymentId) AS maxId FROM payment";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()) {
            int maxId = resultSet.getInt("maxId");
            return maxId + 1; // Increment maxId by 1 for the next ID
        } else {
            return 1; // Start with 1 if there are no payment records
        }
    }

    @Override
    public ArrayList<Payment> search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }

    // Get payment by ID
    public PaymentDto getPaymentById(int paymentId) throws SQLException {
        String sql = "SELECT * FROM payment WHERE paymentId = ?";
        ResultSet resultSet = CrudUtil.execute(sql, paymentId);

        if (resultSet.next()) {
            return new PaymentDto(
                    resultSet.getInt("paymentId"),
                    resultSet.getBigDecimal("amount"),
                    resultSet.getString("paymentMethod")
            );
        }
        return null;  // Return null if no payment is found with the given ID
    }


    @Override
    public boolean update(PaymentDto payment) throws SQLException, ClassNotFoundException {
        return false;
    }
}
