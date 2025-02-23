package edu.example.aia.aia_management.bo.custom.impl;

import edu.example.aia.aia_management.bo.custom.CustomerBo;
import edu.example.aia.aia_management.dao.DAOFactory;
import edu.example.aia.aia_management.dao.custom.CustomerDao;
import edu.example.aia.aia_management.dto.CustomerDto;
import edu.example.aia.aia_management.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBoImpl implements CustomerBo {

    CustomerDao customerDao=(CustomerDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);

    @Override
    public boolean saveCustomer(CustomerDto customer) throws SQLException, ClassNotFoundException {
//        String sql = "INSERT INTO CustomerDetails (CustomerId, Name, Address, Phone, Email, ManagerId) VALUES (?, ?, ?, ?, ?, ?)";
//        return CrudUtil.execute(sql, customer.getCustomerId(), customer.getName(), customer.getAddress(), customer.getPhone(), customer.getEmail(), customer.getManagerId());
        Customer customers=new Customer(customer.getCustomerId(),customer.getName(),customer.getAddress(),customer.getPhone(),customer.getEmail(),customer.getManagerId());
        return customerDao.save(customers);
    }
    @Override
    public boolean updateCustomer(CustomerDto customer) throws SQLException, ClassNotFoundException {
//        String sql = "UPDATE CustomerDetails SET Name = ?, Address = ?, Phone = ?, Email = ?, ManagerId = ? WHERE CustomerId = ?";
//        return CrudUtil.execute(sql, customer.getName(), customer.getAddress(), customer.getPhone(), customer.getEmail(), customer.getManagerId(), customer.getCustomerId());
   Customer customers=new Customer(customer.getName(),customer.getAddress(),customer.getPhone(),customer.getEmail(),customer.getManagerId(),customer.getCustomerId());
   return customerDao.update(customers);
    }
@Override
    public boolean deleteCustomer(int customerId) throws SQLException, ClassNotFoundException {
//        String sql = "DELETE FROM CustomerDetails WHERE CustomerId = ?";
//        return CrudUtil.execute(sql, customerId);
   customerDao.delete(customerId);
   return true;
    }
@Override
    public ArrayList<CustomerDto> getAllCustomers() throws SQLException,ClassNotFoundException {
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
        //      }
        //   return customerList;
        ArrayList<Customer> customerArrayList = customerDao.getAll();
        ArrayList<CustomerDto> customerDtos = new ArrayList<>();

        for (Customer customer : customerArrayList) {
            customerDtos.add(new CustomerDto(customer.getCustomerId(), customer.getName(), customer.getAddress(), customer.getPhone(), customer.getEmail(), customer.getManagerId()));

        }
        return customerDtos;
    }
   @Override
    public int getNextCustomer() throws SQLException, ClassNotFoundException {
//        String sql = "SELECT MAX(CustomerId) AS maxId FROM CustomerDetails";
//        ResultSet resultSet = CrudUtil.execute(sql);
//
//        if (resultSet.next()) {
//            int maxId = resultSet.getInt("maxId");
//            return String.valueOf(maxId + 1); // Increment maxId by 1 for the next ID
//        } else {
//            return "1"; // Start with 1 if there are no customers
//        }
        return customerDao.getNextId();
    }

    @Override
    public CustomerDto searchCustomerById(int customerId) throws SQLException {
        return null;
    }

    @Override
    public   ArrayList<CustomerDto> searchCustomerById(String newValue) throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers=customerDao.search(newValue);
        ArrayList<CustomerDto> customerDtos=new ArrayList<>();
        for (Customer customer:customers){
            CustomerDto customerDto=new CustomerDto();
            customerDto.setCustomerId(customer.getCustomerId());
            customerDto.setAddress(customer.getAddress());
            customerDto.setPhone(customer.getPhone());
            customerDto.setEmail(customer.getEmail());
            customerDto.setManagerId(customer.getManagerId());
        }
        return customerDtos;
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
