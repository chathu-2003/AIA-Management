package edu.example.aia.aia_management.bo.custom;

import edu.example.aia.aia_management.bo.SuperBo;
import edu.example.aia.aia_management.dto.PaymentDetailsDto;
import edu.example.aia.aia_management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PaymentDetailsBo extends SuperBo {
    public ArrayList<PaymentDetailsDto> getAllData() throws SQLException, ClassNotFoundException ;
    public boolean save(PaymentDetailsDto Dto) throws SQLException, ClassNotFoundException;
    public boolean update(PaymentDetailsDto Dto) throws SQLException, ClassNotFoundException ;
    public boolean exist(String id) throws SQLException, ClassNotFoundException ;
    public void delete(String id) throws SQLException, ClassNotFoundException ;
    public String getNewId() throws SQLException, ClassNotFoundException;
    public ArrayList<PaymentDetailsDto> searchItem(String newValue) throws SQLException, ClassNotFoundException;
    public  List<PaymentDetailsDto> getPaymentDetailsByMonth(String selectedMonth);
    boolean deletePaymentDetail(int paymentId);
    public  List<PaymentDetailsDto> getAllPaymentDetails() throws SQLException ;

}
