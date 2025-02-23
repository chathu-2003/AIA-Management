package edu.example.aia.aia_management.bo.custom.impl;

import edu.example.aia.aia_management.bo.custom.NotificationBo;
import edu.example.aia.aia_management.dao.DAOFactory;
import edu.example.aia.aia_management.dao.custom.NotificationDao;
import edu.example.aia.aia_management.dto.ClaimDto;
import edu.example.aia.aia_management.dto.NotificationDto;
import edu.example.aia.aia_management.entity.Claim;
import edu.example.aia.aia_management.entity.Notification;
import edu.example.aia.aia_management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationBoImpl implements NotificationBo {

    NotificationDao notificationDao=(NotificationDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.NOTIFICATION);

    @Override
    public  boolean saveNotification(NotificationDto notificationn) throws SQLException, ClassNotFoundException {
//        String sql = "INSERT INTO Notification (NotificationId, UserId, Message, Date) VALUES (?, ?, ?, ?)";
//        return CrudUtil.execute(sql, notificationDto.getNotificationId(), notificationDto.getUserId(), notificationDto.getMessage(), notificationDto.getDate());
        Notification notification=new Notification(notificationn.getNotificationId(),notificationn.getUserId(),notificationn.getMessage(),notificationn.getDate());
        return notificationDao.save(notification);
    }
@Override
    public  boolean updateNotification(NotificationDto notification) throws SQLException, ClassNotFoundException {
//        String sql = "UPDATE Notification SET UserId = ?, Message = ?, Date = ? WHERE NotificationId = ?";
//        return CrudUtil.execute(sql, notificationDto.getUserId(), notificationDto.getMessage(), notificationDto.getDate(), notificationDto.getNotificationId());
    Notification notifications=new Notification(notification.getNotificationId(),notification.getUserId(),notification.getMessage(),notification.getDate());
    return notificationDao.update(notifications);
    }
@Override
    public  boolean deleteNotification(int notificationId) throws SQLException, ClassNotFoundException {
//        String sql = "DELETE FROM Notification WHERE NotificationId = ?";
//        return CrudUtil.execute(sql, notificationId);
   notificationDao.delete(notificationId);
   return true;
    }
@Override
    public  ArrayList<NotificationDto> getAllNotifications() throws SQLException, ClassNotFoundException {
//        String sql = "SELECT * FROM Notification";
//        ResultSet resultSet = CrudUtil.execute(sql);
//        List<NotificationDto> notifications = new ArrayList<>();
//
//        while (resultSet.next()) {
//            int notificationId = resultSet.getInt("NotificationId");
//            int userId = resultSet.getInt("UserId");
//            String message = resultSet.getString("Message");
//            LocalDate date = resultSet.getDate("Date").toLocalDate();
//
//            notifications.add(new NotificationDto(notificationId, userId, message, date));
//        }
//
//        return notifications;
        ArrayList<Notification> notificationArrayList = notificationDao.getAll();
        ArrayList<NotificationDto> notificationDtos = new ArrayList<>();

        for (Notification notification : notificationArrayList) {
            notificationDtos.add(new NotificationDto(notification.getNotificationId(), notification.getUserId(), notification.getMessage(), notification.getDate()));

        }
        return notificationDtos;
    }
    public  NotificationDto searchNotificationById(int notificationId) throws SQLException {
        String sql = "SELECT * FROM notification WHERE notificationId = ?";
        ResultSet rs = CrudUtil.execute(sql, notificationId);

        if (rs.next()) {
            return new NotificationDto(
                    rs.getInt("notificationId"),
                    rs.getInt("userId"),
                    rs.getString("message"),
                    rs.getDate("date").toLocalDate()
            );
        }
        return null; // No notification found
    }

    // Method to search notifications by Message
    public  ArrayList<NotificationDto> searchNotificationsByMessage(String message) throws SQLException, ClassNotFoundException {
//        String sql = "SELECT * FROM notification WHERE message LIKE ?";
//        ResultSet rs = CrudUtil.execute(sql, "%" + message + "%");
//        List<NotificationDto> notifications = new ArrayList<>();
//
//        while (rs.next()) {
//            notifications.add(new NotificationDto(
//                    rs.getInt("notificationId"),
//                    rs.getInt("userId"),
//                    rs.getString("message"),
//                    rs.getDate("date").toLocalDate()
//            ));
//        }
//        return notifications;
   ArrayList<Notification> notifications=notificationDao.search(message);
   ArrayList<NotificationDto> notificationDtos=new ArrayList<>();
   for (Notification notification:notifications){

           NotificationDto notificationDto=new NotificationDto();
       notificationDto.setNotificationId(notification.getNotificationId());
       notificationDto.setUserId(notification.getUserId());
       notificationDto.setMessage(notification.getMessage());
       notificationDto.setDate(notification.getDate());

   }
   return notificationDtos;
    }
}
