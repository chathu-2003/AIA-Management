//package edu.example.aia.aia_management.bo.custom.impl;
//
//import com.example.layeredarchitecture.bo.custom.impl.PlaceOrderBo;
//import com.example.layeredarchitecture.dao.DAOFactory;
//import com.example.layeredarchitecture.dao.custom.CustomerDAO;
//import com.example.layeredarchitecture.dao.custom.ItemDAO;
//import com.example.layeredarchitecture.dao.custom.OrderDetailDAO;
//import com.example.layeredarchitecture.dao.custom.OrdersDAO;
//import com.example.layeredarchitecture.db.DBConnection;
//import com.example.layeredarchitecture.dto.CustomerDTO;
//import com.example.layeredarchitecture.dto.ItemDTO;
//import com.example.layeredarchitecture.dto.OrderDTO;
//import com.example.layeredarchitecture.dto.OrderDetailDTO;
//import com.example.layeredarchitecture.entity.Customer;
//import com.example.layeredarchitecture.entity.Item;
//import com.example.layeredarchitecture.entity.Order;
//import com.example.layeredarchitecture.entity.OrderDetail;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//public class PlaceOrderBOImpl implements PlaceOrderBo {
//    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);
//    ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ITEM);
//    OrdersDAO ordersDAO = (OrdersDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER);
//    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER_DETAIL);
//    @Override
//    public ArrayList<CustomerDTO> searchCustomer(String newValue) throws SQLException, ClassNotFoundException {
//        ArrayList<Customer> customers = customerDAO.search(newValue);
//        ArrayList<CustomerDTO> customerDTOs = new ArrayList<>();
//        for (Customer customer : customers) {
//            CustomerDTO customerDTO = new CustomerDTO();
//            customerDTO.setId(customer.getId());
//            customerDTO.setName(customer.getName());
//            customerDTOs.add(customerDTO);
//        }
//        return customerDTOs;
//    }
//
//    @Override
//    public ArrayList<ItemDTO> searchItem(String newItemCode) throws SQLException, ClassNotFoundException {
//        ArrayList<Item> items = itemDAO.search(newItemCode);
//        ArrayList<ItemDTO> itemDtos = new ArrayList<>();
//        for (Item item : items) {
//            ItemDTO itemDto = new ItemDTO();
//            itemDto.setCode((item.getCode()));
//            itemDto.setDescription(item.getDescription());
//            itemDto.setUnitPrice(item.getUnitPrice());
//            itemDto.setQtyOnHand(item.getQtyOnHand());
//            itemDtos.add(itemDto);
//        }
//        return itemDtos;
//    }
//
//    @Override
//    public boolean existItemId(String code) throws SQLException, ClassNotFoundException {
//        return itemDAO.existId(code);
//    }
//
//    @Override
//    public boolean existCustomerId(String code) throws SQLException, ClassNotFoundException {
//        return customerDAO.existId(code);
//    }
//
//    @Override
//    public String getNewOrderId() throws SQLException, ClassNotFoundException {
//        return ordersDAO.getNewId();
//    }
//
//    @Override
//    public ArrayList<ItemDTO> getAllItemData() throws SQLException, ClassNotFoundException {
//        ArrayList<Item> items = itemDAO.getAllData();
//        ArrayList<ItemDTO> itemDtos = new ArrayList<>();
//        for (Item item : items) {
//            ItemDTO itemDto = new ItemDTO();
//            itemDto.setCode((item.getCode()));
//            itemDto.setDescription(item.getDescription());
//            itemDto.setUnitPrice(item.getUnitPrice());
//            itemDto.setQtyOnHand(item.getQtyOnHand());
//            itemDtos.add(itemDto);
//        }
//        return itemDtos;
//    }
//
//    public boolean saveOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) {
//        /*Transaction*/
//        Connection connection = null;
//        try {
//            connection = DBConnection.getDbConnection().getConnection();
////            PreparedStatement stm = connection.prepareStatement("SELECT oid FROM `Orders` WHERE oid=?");
////            stm.setString(1, orderId);
//            /*if order id already exist*/
//            ArrayList<OrderDTO> rst = search(orderId);
//            OrderDTO orderDTO = new OrderDTO();
//
//            if (!rst.isEmpty()) {
//                System.out.println("No order found");
//                return false;
//            }
//
//            connection.setAutoCommit(false);
////            stm = connection.prepareStatement("INSERT INTO `Orders` (oid, date, customerID) VALUES (?,?,?)");
////            stm.setString(1, orderId);
////            stm.setDate(2, Date.valueOf(orderDate));
////            stm.setString(3, customerId);
//            ArrayList<CustomerDTO> details = searchCustomer(customerId);
//
//            boolean saveOrder = saveOrders(new OrderDTO(orderId,orderDate,customerId));
//            if (!saveOrder) {
//                connection.rollback();
//                connection.setAutoCommit(true);
//                System.out.println("Save order failed1");
//                return false;
//            }
//
////            stm = connection.prepareStatement("INSERT INTO OrderDetails (oid, itemCode, unitPrice, qty) VALUES (?,?,?,?)");
//
//            for (OrderDetailDTO detail : orderDetails) {
////                stm.setString(1, orderId);
////                stm.setString(2, detail.getItemCode());
////                stm.setBigDecimal(3, detail.getUnitPrice());
////                stm.setInt(4, detail.getQty());
//                boolean execute = saveOrderDetail(new OrderDetailDTO(orderId,detail.getItemCode(),detail.getQty(),detail.getUnitPrice()));
//
//                if (!execute) {
//                    connection.rollback();
//                    connection.setAutoCommit(true);
//                    System.out.println("Save order failed2");
//                    return false;
//                }
//
////                //Search & Update Item
//                ItemDTO item = findItem(detail.getItemCode());
//                item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());
//
////                PreparedStatement pstm = connection.prepareStatement("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?");
////                pstm.setString(1, item.getDescription());
////                pstm.setBigDecimal(2, item.getUnitPrice());
////                pstm.setInt(3, item.getQtyOnHand());
////                pstm.setString(4, item.getCode());
//                boolean execute2 = updateItem(item);
//
//                if (!execute2) {
//                    connection.rollback();
//                    connection.setAutoCommit(true);
//                    System.out.println("Save order failed3");
//                    return false;
//                }
//            }
//
//            connection.commit();
//            connection.setAutoCommit(true);
//            return true;
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public boolean saveOrderDetail(OrderDetailDTO orderDetailDTO) throws SQLException, ClassNotFoundException {
//        OrderDetail orderDetail = new OrderDetail(orderDetailDTO.getOrderId(),orderDetailDTO.getItemCode(),orderDetailDTO.getQty(),orderDetailDTO.getUnitPrice());
//        return orderDetailDAO.save(orderDetail);
//    }
//
//    @Override
//    public ArrayList<OrderDTO> search(String orderId) throws SQLException, ClassNotFoundException {
//        ArrayList<Order> orders = ordersDAO.search(orderId);
//        ArrayList<OrderDTO> orderDTOS = new ArrayList<>();
//        for (Order order : orders) {
//            OrderDTO orderDTO = new OrderDTO();
//            orderDTO.setOrderId((order.getOrderId()));
//            orderDTO.setOrderDate(order.getOrderDate());
//            orderDTO.setCustomerId(order.getCustomerId());
//            orderDTO.setCustomerName(order.getCustomerName());
//            orderDTO.setOrderTotal(order.getOrderTotal());
//            orderDTOS.add(orderDTO);
//        }
//        return orderDTOS;
//    }
//
//    public boolean saveOrders(OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
//        Order order = new Order(orderDTO.getOrderId(),orderDTO.getOrderDate(),orderDTO.getCustomerId());
//        return ordersDAO.save(order);
//    }
//
//    public ItemDTO findItem(String code) {
//        try {
////            Connection connection = DBConnection.getDbConnection().getConnection();
////            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Item WHERE code=?");
////            pstm.setString(1, code);
////            ResultSet rst = pstm.executeQuery();
//            ArrayList<ItemDTO> itemDTOS = searchItem(code);
////            rst.next();
//            for (ItemDTO itemDTO : itemDTOS) {
//                return new ItemDTO(code, itemDTO.getDescription(), itemDTO.getUnitPrice(), itemDTO.getQtyOnHand());
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Failed to find the Item " + code, e);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public ArrayList<CustomerDTO> getAllCustomerData() throws SQLException, ClassNotFoundException {
//        ArrayList<Customer> customers = customerDAO.getAllData();
//        ArrayList<CustomerDTO> customerDTOs = new ArrayList<>();
//        for (Customer customer : customers) {
//            CustomerDTO customerDTO = new CustomerDTO();
//            customerDTO.setId(customer.getId());
//            customerDTO.setName(customer.getName());
//            customerDTOs.add(customerDTO);
//        }
//        return customerDTOs;
//    }
//
//    @Override
//    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
//        Item item = new Item(dto.getCode(), dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand());
//        return itemDAO.update(item);
//    }
//
//}
