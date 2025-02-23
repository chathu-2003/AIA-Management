package edu.example.aia.aia_management.bo.custom.impl;

import edu.example.aia.aia_management.bo.custom.PaymentBo;
import edu.example.aia.aia_management.dao.DAOFactory;
import edu.example.aia.aia_management.dao.custom.PaymentDao;
import edu.example.aia.aia_management.db.DBConection;
import edu.example.aia.aia_management.dto.PaymentDetailsDto;
import edu.example.aia.aia_management.dto.PaymentDto;
import edu.example.aia.aia_management.dto.PolicyDto;
import edu.example.aia.aia_management.dto.SavePaymentDto;
import edu.example.aia.aia_management.entity.Payment;
import edu.example.aia.aia_management.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBo {

    PaymentDao paymentDao = (PaymentDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);



    // Save payment details
    public boolean savePayment(SavePaymentDto savePaymentDto) throws SQLException {
        Connection connection = DBConection.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatementOrderDetails = null;

        try {
            // Start transaction
            connection.setAutoCommit(false);

            // Insert payment data
            String sql = "INSERT INTO payment (PaymentId, Amount, PaymentMethod) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            for (PaymentDto paymentDto : savePaymentDto.getPayment()) {
                preparedStatement.setInt(1, paymentDto.getPaymentId());
                preparedStatement.setBigDecimal(2, paymentDto.getAmount());
                preparedStatement.setString(3, paymentDto.getPaymentMethod());

                if (preparedStatement.executeUpdate() == 0) {
                    System.out.println("Payment failed");
                    connection.rollback();
                    return false;
                }
            }

            // Insert payment details
            String sqlPaymentDetails = "INSERT INTO paymentdetails VALUES (?, ?, ?, ?)";
            preparedStatementOrderDetails = connection.prepareStatement(sqlPaymentDetails);

            for (PaymentDetailsDto paymentDetailsDto : savePaymentDto.getPaymentDetails()) {
                preparedStatementOrderDetails.setInt(1, paymentDetailsDto.getPaymentId());
                preparedStatementOrderDetails.setInt(2, paymentDetailsDto.getPolicyId());
                preparedStatementOrderDetails.setString(3, paymentDetailsDto.getStatus());
                preparedStatementOrderDetails.setString(4, paymentDetailsDto.getMonth());

                if (preparedStatementOrderDetails.executeUpdate() == 0) {
                    System.out.println("PaymentDetails failed");
                    connection.rollback();
                    return false;
                }
            }

            // Commit transaction
            connection.commit();
            return true;

        } catch (Exception e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (preparedStatementOrderDetails != null) preparedStatementOrderDetails.close();
                connection.setAutoCommit(true); // Reset auto-commit mode
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Update payment details
    public boolean updatePayment(PaymentDto payment) throws SQLException {
        String sql = "UPDATE payment SET amount = ?, paymentMethod = ? WHERE paymentId = ?";
        return CrudUtil.execute(sql, payment.getAmount(), payment.getPaymentMethod(), payment.getPaymentId());
    }

    // Delete payment by ID
    public boolean deletePayment(int paymentId) throws SQLException {
        String sql = "DELETE FROM payment WHERE paymentId = ?";
        return CrudUtil.execute(sql, paymentId);
    }

    // Get all payments
    @Override
    public ArrayList<PaymentDto> getAllPayments() throws SQLException, ClassNotFoundException {
//        String sql = "SELECT * FROM payment";
//        ResultSet resultSet = CrudUtil.execute(sql);
        ArrayList<Payment> paymentArrayList=paymentDao.getAll();
        ArrayList<PaymentDto> paymentDtos = new ArrayList<>();

 for (Payment payment:paymentArrayList){
     paymentDtos.add(new PaymentDto(payment.getPaymentId(),payment.getAmount(),payment.getPaymentMethod()));
 }
   return paymentDtos;


    }

    // Get the next payment ID
    public int getNextPaymentId() throws SQLException {
        String sql = "SELECT MAX(paymentId) AS maxId FROM payment";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()) {
            int maxId = resultSet.getInt("maxId");
            return maxId + 1; // Increment maxId by 1 for the next ID
        } else {
            return 1; // Start with 1 if there are no payment records
        }
    }

//    @Override
//    public List<PolicyDto> getAllPolicies() {
//        return List.of();
//    }
public List<PolicyDto> getAllPolicies() throws SQLException {
    List<PolicyDto> policies = new ArrayList<>();
    ResultSet rst = CrudUtil.execute("SELECT PolicyId, PolicyNumber, StartDate, EndDate, Premium, Coverage, ManagerId, UnderWriterId FROM policy");

    while (rst.next()) {
        PolicyDto policyDTO = new PolicyDto(
                rst.getInt("PolicyId"),
                rst.getString("PolicyNumber"),
                rst.getDate("StartDate"),
                rst.getDate("EndDate"),
                rst.getBigDecimal("Premium"),
                rst.getString("Coverage"),
                rst.getInt("ManagerId"),
                rst.getInt("UnderWriterId")
        );
        policies.add(policyDTO);
    }
    return policies;
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
}
