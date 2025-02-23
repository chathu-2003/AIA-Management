package edu.example.aia.aia_management.Model;

import edu.example.aia.aia_management.dto.CustomerDto;
import edu.example.aia.aia_management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {


    public boolean saveCustomer(CustomerDto customer) throws SQLException {
        String sql = "INSERT INTO CustomerDetails (CustomerId, Name, Address, Phone, Email, ManagerId) VALUES (?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql, customer.getCustomerId(), customer.getName(), customer.getAddress(), customer.getPhone(), customer.getEmail(), customer.getManagerId());
    }

    public boolean updateCustomer(CustomerDto customer) throws SQLException {
        String sql = "UPDATE CustomerDetails SET Name = ?, Address = ?, Phone = ?, Email = ?, ManagerId = ? WHERE CustomerId = ?";
        return CrudUtil.execute(sql, customer.getName(), customer.getAddress(), customer.getPhone(), customer.getEmail(), customer.getManagerId(), customer.getCustomerId());
    }

    public boolean deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM CustomerDetails WHERE CustomerId = ?";
        return CrudUtil.execute(sql, customerId);
    }

    public List<CustomerDto> getAllCustomers() throws SQLException {
        String sql = "SELECT * FROM CustomerDetails";
        ResultSet resultSet = CrudUtil.execute(sql);

        List<CustomerDto> customerList = new ArrayList<>();
        while (resultSet.next()) {
            CustomerDto customer = new CustomerDto(
                    resultSet.getInt("CustomerId"),
                    resultSet.getString("Name"),
                    resultSet.getString("Address"),
                    resultSet.getString("Phone"),
                    resultSet.getString("Email"),
                    resultSet.getInt("ManagerId")
            );
            customerList.add(customer);
        }
        return customerList;
    }
    public String getNextCustomer() throws SQLException {
        String sql = "SELECT MAX(CustomerId) AS maxId FROM CustomerDetails";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()) {
            int maxId = resultSet.getInt("maxId");
            return String.valueOf(maxId + 1); // Increment maxId by 1 for the next ID
        } else {
            return "1"; // Start with 1 if there are no customers
        }
    }
    public static CustomerDto searchCustomerById(int customerId) throws SQLException {
        String sql = "SELECT * FROM CustomerDetails WHERE customerId = ?";
        ResultSet resultSet = CrudUtil.execute(sql, customerId);

        if (resultSet.next()) {
            return new CustomerDto(
                    resultSet.getInt("customerId"),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getString("phone"),
                    resultSet.getString("email"),
                    resultSet.getInt("managerId")
            );
        }
        return null; // No customer found
    }

}
