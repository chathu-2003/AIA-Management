package edu.example.aia.aia_management.dao.custom.impl;

import edu.example.aia.aia_management.dao.SQlUtil;
import edu.example.aia.aia_management.dao.custom.CustomerDao;
import edu.example.aia.aia_management.dto.CustomerDto;
import edu.example.aia.aia_management.entity.Customer;
import edu.example.aia.aia_management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDaoImpl implements CustomerDao {

@Override
    public boolean save(Customer customerEntity) throws SQLException, ClassNotFoundException {
       // String sql = "INSERT INTO CustomerDetails (CustomerId, Name, Address, Phone, Email, ManagerId) VALUES (?, ?, ?, ?, ?, ?)";
      //  return CrudUtil.execute(sql, customer.getCustomerId(), customer.getName(), customer.getAddress(), customer.getPhone(), customer.getEmail(), customer.getManagerId());
    return SQlUtil.execute("INSERT INTO CustomerDetails (CustomerId, Name, Address, Phone, Email, ManagerId) VALUES (?, ?, ?, ?, ?, ?)",
            customerEntity.getCustomerId(),
            customerEntity.getName(),
            customerEntity.getAddress(),
            customerEntity.getPhone(),
            customerEntity.getEmail(),
            customerEntity.getManagerId());

    }
@Override
    public boolean update(Customer customerEnititi) throws SQLException, ClassNotFoundException {
//        String sql = "UPDATE CustomerDetails SET Name = ?, Address = ?, Phone = ?, Email = ?, ManagerId = ? WHERE CustomerId = ?";
//        return CrudUtil.execute(sql, customer.getName(), customer.getAddress(), customer.getPhone(), customer.getEmail(), customer.getManagerId(), customer.getCustomerId());
    return SQlUtil.execute( "UPDATE CustomerDetails SET Name = ?, Address = ?, Phone = ?, Email = ?, ManagerId = ? WHERE CustomerId = ?",
            customerEnititi.getName(),
            customerEnititi.getAddress(),
            customerEnititi.getPhone(),
            customerEnititi.getEmail(),
            customerEnititi.getManagerId(),
            customerEnititi.getCustomerId());
}
@Override
    public boolean delete(int customerId) throws SQLException {
//        String sql = "DELETE FROM CustomerDetails WHERE CustomerId = ?";
//        return CrudUtil.execute(sql, customerId);
   CrudUtil.execute("DELETE FROM CustomerDetails WHERE CustomerId = ?",customerId);
   return true;
    }
@Override
    public  ArrayList<Customer> getAll() throws SQLException {
//        String sql = "SELECT * FROM CustomerDetails";
//        ResultSet resultSet = CrudUtil.execute(sql);
//
//        List<CustomerDto> customerList = new ArrayList<>();
//        while (resultSet.next()) {
//            CustomerDto customer = new CustomerDto(
//                    resultSet.getInt("CustomerId"),
//                    resultSet.getString("Name"),
//                    resultSet.getString("Address"),
//                    resultSet.getString("Phone"),
//                    resultSet.getString("Email"),
//                    resultSet.getInt("ManagerId")
//            );
//            customerList.add(customer);
//        }
//        return customerList;
   ResultSet rst =CrudUtil.execute("SELECT * FROM CustomerDetails");
   ArrayList<Customer> customers=new ArrayList<>();

   while (rst.next()){
       Customer customer=new Customer(
                    rst.getInt("CustomerId"),
                    rst.getString("Name"),
                    rst.getString("Address"),
                    rst.getString("Phone"),
                    rst.getString("Email"),
                    rst.getInt("ManagerId")
       );
       customers.add(customer);
   }
   return customers;
}

    public int getNextId() throws SQLException {
        String sql = "SELECT MAX(CustomerId) AS maxId FROM CustomerDetails";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()) {
            int maxId = resultSet.getInt("maxId");
            return maxId+ 1; // Increment maxId by 1 for the next ID
        } else {
            return 1; // Start with 1 if there are no customers
        }
    }

    public ArrayList<Customer> search(String newValue) throws SQLException, ClassNotFoundException {
      ResultSet rst =SQlUtil.execute("SELECT * FROM CustomerDetails WHERE customerId = ?",newValue);
      ArrayList<Customer> searchResults=new ArrayList<>();
      while (rst.next()){
        Customer customer=new Customer(
                    rst.getInt("customerId"),
                    rst.getString("name"),
                    rst.getString("address"),
                    rst.getString("phone"),
                    rst.getString("email"),
                    rst.getInt("managerId")
            );
          searchResults.add(customer);
      }
      return searchResults;

    //        String sql = "SELECT * FROM CustomerDetails WHERE customerId = ?";
//        ResultSet resultSet = CrudUtil.execute(sql, customerId);
//
//        if (resultSet.next()) {
//            return new CustomerDto(
//                    resultSet.getInt("customerId"),
//                    resultSet.getString("name"),
//                    resultSet.getString("address"),
//                    resultSet.getString("phone"),
//                    resultSet.getString("email"),
//                    resultSet.getInt("managerId")
//            );
//        }
//        return null; // No customer found
    }
}