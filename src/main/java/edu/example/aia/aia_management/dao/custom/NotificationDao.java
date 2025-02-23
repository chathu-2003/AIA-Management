package edu.example.aia.aia_management.dao.custom;

import edu.example.aia.aia_management.dao.CrudDAO;
import edu.example.aia.aia_management.entity.Notification;

import java.sql.SQLException;
import java.util.List;

public interface NotificationDao  extends CrudDAO<Notification> {
    Notification searchById(int notificationId) throws SQLException, ClassNotFoundException;

    List<Notification> searchByMessage(String message) throws SQLException;
}
