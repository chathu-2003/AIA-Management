package edu.example.aia.aia_management.dao.custom.impl;

import edu.example.aia.aia_management.dao.SQlUtil;
import edu.example.aia.aia_management.dao.custom.PaymentDetailsDao;
import edu.example.aia.aia_management.dto.PaymentDetailsDto;
import edu.example.aia.aia_management.entity.PaymentDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDetailsDaoImpl implements PaymentDetailsDao {

    @Override
    public ArrayList<PaymentDetails> getAll() throws SQLException, ClassNotFoundException {
       // String sql = "SELECT * FROM PaymentDetails";
        ResultSet resultSet = SQlUtil.execute("SELECT * FROM PaymentDetails");
        ArrayList<PaymentDetails> paymentDetails=new ArrayList<>();

        while (resultSet.next()) {
            PaymentDetails paymentDetails1=new PaymentDetails(
                    resultSet.getInt("paymentId"),
                    resultSet.getInt("policyId"),
                    resultSet.getString("month"),
                    resultSet.getString("status")
            );
            paymentDetails.add(paymentDetails1);
        }
        return paymentDetails;
    }

    // Method to delete a payment detail by paymentId
    @Override
    public  boolean delete(int paymentId) throws SQLException, ClassNotFoundException {
//        String sql = "DELETE FROM PaymentDetails WHERE paymentId = ?";
//        return CrudUtil.execute(sql, paymentId);
    SQlUtil.execute("DELETE FROM PaymentDetails WHERE paymentId = ?",paymentId);
    return true;
    }

    public static List<PaymentDetailsDto> getPaymentDetailsByMonth(String selectedMonth) {
        return List.of();
    }
}
