package edu.example.aia.aia_management.Model;

import edu.example.aia.aia_management.dto.NotificationDto;
import edu.example.aia.aia_management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NotificationModel {

    public static boolean saveNotification(NotificationDto notificationDto) throws SQLException {
        String sql = "INSERT INTO Notification (NotificationId, UserId, Message, Date) VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql, notificationDto.getNotificationId(), notificationDto.getUserId(), notificationDto.getMessage(), notificationDto.getDate());
    }

    public static boolean updateNotification(NotificationDto notificationDto) throws SQLException {
        String sql = "UPDATE Notification SET UserId = ?, Message = ?, Date = ? WHERE NotificationId = ?";
        return CrudUtil.execute(sql, notificationDto.getUserId(), notificationDto.getMessage(), notificationDto.getDate(), notificationDto.getNotificationId());
    }

    public static boolean deleteNotification(int notificationId) throws SQLException {
        String sql = "DELETE FROM Notification WHERE NotificationId = ?";
        return CrudUtil.execute(sql, notificationId);
    }

    public static List<NotificationDto> getAllNotifications() throws SQLException {
        String sql = "SELECT * FROM Notification";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<NotificationDto> notifications = new ArrayList<>();

        while (resultSet.next()) {
            int notificationId = resultSet.getInt("NotificationId");
            int userId = resultSet.getInt("UserId");
            String message = resultSet.getString("Message");
            LocalDate date = resultSet.getDate("Date").toLocalDate();

            notifications.add(new NotificationDto(notificationId, userId, message, date));
        }

        return notifications;
    }
    public static NotificationDto searchNotificationById(int notificationId) throws SQLException {
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
    public static List<NotificationDto> searchNotificationsByMessage(String message) throws SQLException {
        String sql = "SELECT * FROM notification WHERE message LIKE ?";
        ResultSet rs = CrudUtil.execute(sql, "%" + message + "%");
        List<NotificationDto> notifications = new ArrayList<>();

        while (rs.next()) {
            notifications.add(new NotificationDto(
                    rs.getInt("notificationId"),
                    rs.getInt("userId"),
                    rs.getString("message"),
                    rs.getDate("date").toLocalDate()
            ));
        }
        return notifications;
    }
}
