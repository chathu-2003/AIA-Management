package edu.example.aia.aia_management.bo.custom;

import edu.example.aia.aia_management.bo.SuperBo;
import edu.example.aia.aia_management.dto.NotificationDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface NotificationBo extends SuperBo {
    boolean saveNotification(NotificationDto notificationn) throws SQLException, ClassNotFoundException;

    boolean updateNotification(NotificationDto notification) throws SQLException, ClassNotFoundException;

    boolean deleteNotification(int notificationId) throws SQLException, ClassNotFoundException;

    ArrayList<NotificationDto> getAllNotifications() throws SQLException, ClassNotFoundException;
}
