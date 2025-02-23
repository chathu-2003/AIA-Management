package edu.example.aia.aia_management.entity;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Notification {

    private IntegerProperty notificationId;
    private IntegerProperty userId;
    private StringProperty message;
    private ObjectProperty<LocalDate> date;

    public Notification(int notificationId, int userId, String message, LocalDate date) {
        this.notificationId = new SimpleIntegerProperty(notificationId);
        this.userId = new SimpleIntegerProperty(userId);
        this.message = new SimpleStringProperty(message);
        this.date = new SimpleObjectProperty<>(date);
    }

    // Getters and Setters for the properties
    public int getNotificationId() {
        return notificationId.get();
    }

    public void setNotificationId(int notificationId) {
        this.notificationId.set(notificationId);
    }

    public IntegerProperty notificationIdProperty() {
        return notificationId;
    }

    public int getUserId() {
        return userId.get();
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public String getMessage() {
        return message.get();
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    public StringProperty messageProperty() {
        return message;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    @Override
    public String toString() {
        return "NotificationDto{" +
                "notificationId=" + notificationId.get() +
                ", userId=" + userId.get() +
                ", message='" + message.get() + '\'' +
                ", date=" + date.get() +
                '}';
    }
}
