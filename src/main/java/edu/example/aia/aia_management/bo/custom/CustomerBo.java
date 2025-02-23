package edu.example.aia.aia_management.bo.custom;

import edu.example.aia.aia_management.bo.SuperBo;
import edu.example.aia.aia_management.dto.CustomerDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBo extends SuperBo {
    boolean saveCustomer(CustomerDto customer) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(CustomerDto customer) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(int customerId) throws SQLException, ClassNotFoundException;

    ArrayList<CustomerDto> getAllCustomers() throws SQLException,ClassNotFoundException;

    int getNextCustomer() throws SQLException, ClassNotFoundException;

    CustomerDto searchCustomerById(int customerId) throws SQLException;

    ArrayList<CustomerDto> searchCustomerById(String newValue) throws SQLException, ClassNotFoundException;
}
