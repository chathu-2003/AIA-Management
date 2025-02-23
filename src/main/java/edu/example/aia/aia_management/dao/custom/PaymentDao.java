package edu.example.aia.aia_management.dao.custom;

import edu.example.aia.aia_management.dao.CrudDAO;
import edu.example.aia.aia_management.dto.PaymentDto;
import edu.example.aia.aia_management.entity.Payment;

import java.sql.SQLException;

public interface PaymentDao extends CrudDAO<Payment> {
    // Update payment details
    boolean update(PaymentDto payment) throws SQLException, ClassNotFoundException;
}
