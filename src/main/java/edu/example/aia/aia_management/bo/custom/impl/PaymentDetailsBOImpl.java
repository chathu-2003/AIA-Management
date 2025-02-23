package edu.example.aia.aia_management.bo.custom.impl;

import edu.example.aia.aia_management.bo.custom.PaymentDetailsBo;
import edu.example.aia.aia_management.dto.PaymentDetailsDto;
import edu.example.aia.aia_management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDetailsBOImpl implements PaymentDetailsBo {

    public  List<PaymentDetailsDto> getAllPaymentDetails() throws SQLException {
        String sql = "SELECT * FROM PaymentDetails";
        ResultSet resultSet = CrudUtil.execute(sql);

        List<PaymentDetailsDto> paymentDetailsList = new ArrayList<>();
        while (resultSet.next()) {
            paymentDetailsList.add(new PaymentDetailsDto(
                    resultSet.getInt("paymentId"),
                    resultSet.getInt("policyId"),
                    resultSet.getString("month"),
                    resultSet.getString("status")
            ));
        }
        return paymentDetailsList;
    }

    // Method to delete a payment detail by paymentId
    public static boolean delete(int paymentId) throws SQLException {
        String sql = "DELETE FROM PaymentDetails WHERE paymentId = ?";
        return CrudUtil.execute(sql, paymentId);
    }

    public  List<PaymentDetailsDto> getPaymentDetailsByMonth(String selectedMonth) {
        return List.of();
    }

    @Override
    public ArrayList<PaymentDetailsDto> getAllData() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(PaymentDetailsDto Dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(PaymentDetailsDto Dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public void delete(String id) throws SQLException, ClassNotFoundException {

    }

//    @Override
//    public void delete(String id) throws SQLException, ClassNotFoundException {
//
//    }

    @Override
    public String getNewId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public ArrayList<PaymentDetailsDto> searchItem(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean deletePaymentDetail(int paymentId) {
        return false;
    }

}
