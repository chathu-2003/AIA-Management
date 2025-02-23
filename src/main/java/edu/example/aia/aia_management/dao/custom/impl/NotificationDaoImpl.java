package edu.example.aia.aia_management.dao.custom.impl;

import edu.example.aia.aia_management.dao.SQlUtil;
import edu.example.aia.aia_management.dao.custom.NotificationDao;
import edu.example.aia.aia_management.entity.Notification;
import edu.example.aia.aia_management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDaoImpl implements NotificationDao {

    @Override
    public boolean save(Notification notification) throws SQLException, ClassNotFoundException {
        return SQlUtil.execute("INSERT INTO Notification (NotificationId, UserId, Message, Date) VALUES (?, ?, ?, ?)",
                notification.getNotificationId(),
                notification.getUserId(),
                notification.getMessage(),
                notification.getDate());
    }

    @Override
    public boolean update(Notification notification) throws SQLException, ClassNotFoundException {
        return SQlUtil.execute("UPDATE Notification SET UserId = ?, Message = ?, Date = ? WHERE NotificationId = ?",
                notification.getUserId(),
                notification.getMessage(),
                notification.getDate(),
                notification.getNotificationId());
    }

    @Override
    public boolean delete(int notificationId) throws SQLException, ClassNotFoundException {
        SQlUtil.execute("DELETE FROM Notification WHERE NotificationId = ?", notificationId);
        return true;
    }

    @Override
    public int getNextId() throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public ArrayList<Notification> search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Notification> getAll() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Notification");
        ArrayList<Notification> notifications = new ArrayList<>();

        while (resultSet.next()) {
            Notification notification=new Notification(
                    resultSet.getInt("NotificationId"),
                    resultSet.getInt("UserId"),
                    resultSet.getString("Message"),
                    resultSet.getDate("Date").toLocalDate()
            );
            notifications.add(notification);
        }
        return notifications;
    }

    @Override
    public Notification searchById(int notificationId) throws SQLException, ClassNotFoundException {
        ResultSet rs = SQlUtil.execute("SELECT * FROM Notification WHERE NotificationId = ?", notificationId);
        if (rs.next()) {
            return new Notification(
                    rs.getInt("NotificationId"),
                    rs.getInt("UserId"),
                    rs.getString("Message"),
                    rs.getDate("Date").toLocalDate()
            );
        }
        return null;
    }

    @Override
    public List<Notification> searchByMessage(String message) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Notification WHERE Message LIKE ?", "%" + message + "%");
        List<Notification> notifications = new ArrayList<>();

        while (rs.next()) {
            notifications.add(new Notification(
                    rs.getInt("NotificationId"),
                    rs.getInt("UserId"),
                    rs.getString("Message"),
                    rs.getDate("Date").toLocalDate()
            ));
        }
        return notifications;
    }
}
