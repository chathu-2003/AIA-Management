package edu.example.aia.aia_management.Controller;

import edu.example.aia.aia_management.Model.NotificationModel;
import edu.example.aia.aia_management.dto.InsurancetypeDto;
import edu.example.aia.aia_management.dto.NotificationDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class NotificationController {
    @FXML
    private Button BtnSearch;

    @FXML
    private TextField TxtSearch;

    @FXML
    private Button BtnBack;

    @FXML
    private Button BtnDelete;

    @FXML
    private Button BtnSave;

    @FXML
    private Button BtnUpdate;

    @FXML
    private TableColumn<NotificationDto, Integer> ColNotificationId;

    @FXML
    private TableColumn<NotificationDto, Integer> ColUserId;

    @FXML
    private TableColumn<NotificationDto, String> ColMessage;

    @FXML
    private TableColumn<NotificationDto, LocalDate> ColDate;

    @FXML
    private Pane Notificationpage;

    @FXML
    private TableView<NotificationDto> TableNotification;

    @FXML
    private TextField TxtDate;

    @FXML
    private TextField TxtMessage;

    @FXML
    private TextField TxtNotificationId;

    @FXML
    private TextField TxtUserId;

    private ObservableList<NotificationDto> notificationList = FXCollections.observableArrayList();

    // Load data into the TableView
    private void loadTableData() {
        try {
            notificationList.clear();
            notificationList.addAll(NotificationModel.getAllNotifications());
            TableNotification.setItems(notificationList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Save a new notification
    @FXML
    void BtnSaveOnAction(ActionEvent event) {
        try {
            int notificationId = Integer.parseInt(TxtNotificationId.getText());
            int userId = Integer.parseInt(TxtUserId.getText());
            String message = TxtMessage.getText();
            LocalDate date = LocalDate.parse(TxtDate.getText());

            NotificationDto notificationDto = new NotificationDto(notificationId, userId, message, date);
            boolean isSaved = NotificationModel.saveNotification(notificationDto);

            if (isSaved) {
                loadTableData(); // Refresh the TableView
                clearFields(); // Clear the input fields
            }

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // Update an existing notification
    @FXML
    void BtnUpdateOnAction(ActionEvent event) {
        try {
            int notificationId = Integer.parseInt(TxtNotificationId.getText());
            int userId = Integer.parseInt(TxtUserId.getText());
            String message = TxtMessage.getText();
            LocalDate date = LocalDate.parse(TxtDate.getText());

            NotificationDto notificationDto = new NotificationDto(notificationId, userId, message, date);
            boolean isUpdated = NotificationModel.updateNotification(notificationDto);

            if (isUpdated) {
                loadTableData(); // Refresh the TableView
                clearFields(); // Clear the input fields
            }

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // Delete a notification
    @FXML
    void BtnDeleteOnAction(ActionEvent event) {
        try {
            int notificationId = Integer.parseInt(TxtNotificationId.getText());
            boolean isDeleted = NotificationModel.deleteNotification(notificationId);

            if (isDeleted) {
                loadTableData(); // Refresh the TableView
                clearFields(); // Clear the input fields
            }

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // Handle row click in TableView to populate fields
    @FXML
    void OnClickTableNotification(MouseEvent event) {
        NotificationDto selectedNotification = TableNotification.getSelectionModel().getSelectedItem();
        if (selectedNotification != null) {
            TxtNotificationId.setText(String.valueOf(selectedNotification.getNotificationId()));
            TxtUserId.setText(String.valueOf(selectedNotification.getUserId()));
            TxtMessage.setText(selectedNotification.getMessage());
            TxtDate.setText(String.valueOf(selectedNotification.getDate()));
        }
    }

    // Clear input fields
    private void clearFields() {
        TxtNotificationId.clear();
        TxtUserId.clear();
        TxtMessage.clear();
        TxtDate.clear();
    }

    // Initialization method to load table data when the page is loaded
    @FXML
    public void initialize() {
        // Bind columns to NotificationDto properties
        ColNotificationId.setCellValueFactory(cellData -> cellData.getValue().notificationIdProperty().asObject());
        ColUserId.setCellValueFactory(cellData -> cellData.getValue().userIdProperty().asObject());
        ColMessage.setCellValueFactory(cellData -> cellData.getValue().messageProperty());
        ColDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        loadTableData(); // Initial table data load
        setupEnterKeyListeners();
    }
    @FXML
    void BtnBackOnAction(ActionEvent event)throws IOException {
        Notificationpage.getChildren().clear();
        Notificationpage.getChildren().add(
                FXMLLoader.load(getClass().getResource("/view/Homepage.fxml"))
        );
    }
    private void setupEnterKeyListeners() {
        // When Enter is pressed in Notification ID field, focus moves to User ID field
        TxtNotificationId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtUserId.requestFocus();
            }
        });

        // When Enter is pressed in User ID field, focus moves to Message field
        TxtUserId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtMessage.requestFocus();
            }
        });

        // When Enter is pressed in Message field, focus moves to Date field
        TxtMessage.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtDate.requestFocus();
            }
        });

        // When Enter is pressed in Date field, trigger Save button action
        TxtDate.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                BtnSave.fire(); // Trigger Save button's action
            }
        });
    }
    @FXML
    void BtnSearchOnAction(ActionEvent event) {
        String searchInput = TxtSearch.getText();

        // Reset the border color for the search field
        TxtSearch.setStyle(TxtSearch.getStyle() + ";-fx-border-color: #7367F0;");

        if (searchInput.isEmpty()) {
            // Show a warning if the search field is empty
            new Alert(Alert.AlertType.WARNING, "Please enter a Notification ID or Message to search!").show();
            TxtSearch.setStyle(TxtSearch.getStyle() + ";-fx-border-color: red;");
            return;
        }

        try {
            // Clear the current table data
            notificationList.clear();

            // Search by NotificationId or Message based on the user input
            if (searchInput.matches("\\d+")) {
                // Search by NotificationId (if input is a number)
                int notificationId = Integer.parseInt(searchInput);
                NotificationDto notification = NotificationModel.searchNotificationById(notificationId);

                if (notification != null) {
                    notificationList.add(notification); // Add found notification to list
                } else {
                    new Alert(Alert.AlertType.ERROR, "Notification not found!").show();
                }
            } else {
                // Search by Message (if input is not a number)
                notificationList.addAll(NotificationModel.searchNotificationsByMessage(searchInput));
            }

            // Update the TableView with the search results
            TableNotification.setItems(notificationList);

        } catch (SQLException e) {
            // Show an error if a database issue occurs
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
        } catch (NumberFormatException e) {
            // Show an error if the input is not valid
            new Alert(Alert.AlertType.ERROR, "Invalid search input! Please enter a valid Notification ID or Message.").show();
        }
    }
}

