package edu.example.aia.aia_management.Controller;

import edu.example.aia.aia_management.Model.ClaimModel;
import edu.example.aia.aia_management.Model.FeedBackModel;
import edu.example.aia.aia_management.dto.ClaimDto;
import edu.example.aia.aia_management.dto.FeedBackDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class FeedBackController {
    @FXML
    private TextField TxtSearchfeedBack;
    @FXML
    private Button BtnSearch;
    @FXML
    private Button BtnBack, BtnDelete, BtnSave, BtnUpdate;
    @FXML
    private TableColumn<FeedBackDto, Integer> ColFeed, ColManager;
    @FXML
    private TableColumn<FeedBackDto, Date> ColDate;
    @FXML
    private TableColumn<FeedBackDto, String> ColComment;
    @FXML
    private AnchorPane PageFeedBack;
    @FXML
    private TableView<FeedBackDto> TableFeedBack;
    @FXML
    private TextField TxtComment, TxtDate, TxtFeed, TxtManager;

    private final FeedBackModel feedbackModel = new FeedBackModel();

    @FXML
    public void initialize() {
        // Bind table columns to FeedBackDto properties
        ColFeed.setCellValueFactory(new PropertyValueFactory<>("feedbackId"));
        ColManager.setCellValueFactory(new PropertyValueFactory<>("managerId"));
        ColDate.setCellValueFactory(new PropertyValueFactory<>("feedbackDate"));
        ColComment.setCellValueFactory(new PropertyValueFactory<>("comment"));

        // Load data into the TableView
        loadFeedbackData();
        getNextFeedbackId();
        setupEnterKeyListeners();
    }

    private void loadFeedbackData() {
        try {
            List<FeedBackDto> feedbackList = feedbackModel.getAllFeedback();
            ObservableList<FeedBackDto> feedbackObservableList = FXCollections.observableArrayList(feedbackList);
            TableFeedBack.setItems(feedbackObservableList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load feedback data: " + e.getMessage()).show();
        }
    }

    @FXML
    void BtnSaveOnAction(ActionEvent event) {
        if (validateFields()) {
            try {
                int feedbackId = Integer.parseInt(TxtFeed.getText());
                int managerId = Integer.parseInt(TxtManager.getText());
                Date feedbackDate = Date.valueOf(TxtDate.getText());
                String comment = TxtComment.getText();

                FeedBackDto feedback = new FeedBackDto(feedbackId, managerId, feedbackDate, comment);
                if (feedbackModel.saveFeedback(feedback)) {
                    new Alert(Alert.AlertType.INFORMATION, "Feedback saved successfully").show();
                    loadFeedbackData();
                    clearForm();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save feedback").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Error while saving feedback: " + e.getMessage()).show();
            }
        }
    }

    @FXML
    void BtnDeleteOnAction(ActionEvent event) {
        if (validateFields()) {
            try {
                int feedbackId = Integer.parseInt(TxtFeed.getText());

                if (feedbackModel.deleteFeedback(feedbackId)) {
                    new Alert(Alert.AlertType.INFORMATION, "Feedback deleted successfully").show();
                    loadFeedbackData();
                    clearForm();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete feedback").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Error while deleting feedback: " + e.getMessage()).show();
            }
        }
    }

    @FXML
    void BtnUpdateOnAction(ActionEvent event) {
        if (validateFields()) {
            try {
                int feedbackId = Integer.parseInt(TxtFeed.getText());
                int managerId = Integer.parseInt(TxtManager.getText());
                Date feedbackDate = Date.valueOf(TxtDate.getText());
                String comment = TxtComment.getText();

                FeedBackDto feedback = new FeedBackDto(feedbackId, managerId, feedbackDate, comment);
                if (feedbackModel.updateFeedback(feedback)) {
                    new Alert(Alert.AlertType.INFORMATION, "Feedback updated successfully").show();
                    loadFeedbackData();
                    clearForm();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update feedback").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Error while updating feedback: " + e.getMessage()).show();
            }
        }
    }

    @FXML
    void OnClickTable(MouseEvent event) {
        FeedBackDto selectedFeedback = TableFeedBack.getSelectionModel().getSelectedItem();
        if (selectedFeedback != null) {
            TxtFeed.setText(String.valueOf(selectedFeedback.getFeedbackId()));
            TxtManager.setText(String.valueOf(selectedFeedback.getManagerId()));
            TxtDate.setText(selectedFeedback.getFeedbackDate().toString());
            TxtComment.setText(selectedFeedback.getComment());
        }
    }

    private void clearForm() {
        TxtFeed.clear();
        TxtManager.clear();
        TxtDate.clear();
        TxtComment.clear();
    }

    @FXML
    void BtnBackOnAction(ActionEvent event) throws IOException {
        PageFeedBack.getChildren().clear();
        PageFeedBack.getChildren().add(
                FXMLLoader.load(getClass().getResource("/view/Homepage.fxml"))
        );
    }

    private void getNextFeedbackId() {
        try {
            String nextFeedbackId = feedbackModel.getNextFeedbackId(); // Call the new method in FeedBackModel
            TxtFeed.setText(nextFeedbackId);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while generating Feedback ID: " + e.getMessage()).show();
        }
    }

    // Validate input fields
    private boolean validateFields() {
        if (TxtFeed.getText().isEmpty() || TxtManager.getText().isEmpty() || TxtDate.getText().isEmpty() || TxtComment.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "All fields must be filled!").show();
            return false;
        }

        // Validate Feedback ID (must be an integer)
        try {
            Integer.parseInt(TxtFeed.getText());
        } catch (NumberFormatException e) {
            TxtFeed.setStyle(TxtFeed.getStyle() + ";-fx-border-color: red;");
            return false;
        }

        // Validate Manager ID (must be an integer)
        try {
            Integer.parseInt(TxtManager.getText());
        } catch (NumberFormatException e) {
            TxtManager.setStyle(TxtManager.getStyle() + ";-fx-border-color: red;");
            return false;
        }

        // Validate Date (must be in yyyy-MM-dd format)
        try {
            Date.valueOf(TxtDate.getText());
        } catch (IllegalArgumentException e) {
            TxtDate.setStyle(TxtDate.getStyle() + ";-fx-border-color: red;");
            return false;
        }

        // Validate Comment (cannot be empty)
        if (TxtComment.getText().trim().isEmpty()) {
            TxtComment.setStyle(TxtComment.getStyle() + ";-fx-border-color: red;");
            return false;
        }

        return true;
    }
    private void setupEnterKeyListeners() {
        TxtFeed.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtManager.requestFocus(); // Move focus to the Manager ID field
            }
        });

        TxtManager.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtDate.requestFocus(); // Move focus to the Date field
            }
        });

        TxtDate.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtComment.requestFocus(); // Move focus to the Comment field
            }
        });

        TxtComment.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                BtnSave.fire(); // Trigger the Save button's action
            }
        });
    }
    @FXML
    void BtnSearchOnAction(ActionEvent event) {
        String feedbackIdText = TxtSearchfeedBack.getText();

        // Reset the border color for the search field
        TxtSearchfeedBack.setStyle(TxtSearchfeedBack.getStyle() + ";-fx-border-color: #7367F0;");

        if (feedbackIdText.isEmpty()) {
            // Show a warning if the search field is empty
            new Alert(Alert.AlertType.WARNING, "Please enter a Feedback ID to search!").show();
            TxtSearchfeedBack.setStyle(TxtSearchfeedBack.getStyle() + ";-fx-border-color: red;");
            return;
        }

        try {
            // Parse the entered Feedback ID
            int feedbackId = Integer.parseInt(feedbackIdText);

            // Use the searchFeedbackById method to search for the feedback
            FeedBackDto feedback = feedbackModel.searchFeedbackById(feedbackId);

            if (feedback != null) {
                // Populate the fields with the feedback details
                TxtFeed.setText(String.valueOf(feedback.getFeedbackId()));
                TxtManager.setText(String.valueOf(feedback.getManagerId()));
                TxtDate.setText(feedback.getFeedbackDate().toString());
                TxtComment.setText(feedback.getComment());

                // Highlight the matching feedback in the TableView
                ObservableList<FeedBackDto> feedbackList = TableFeedBack.getItems();
                for (FeedBackDto item : feedbackList) {
                    if (item.getFeedbackId() == feedbackId) {
                        TableFeedBack.getSelectionModel().select(item);
                        TableFeedBack.scrollTo(item);
                        break;
                    }
                }
            } else {
                // Show an error if no feedback is found
                new Alert(Alert.AlertType.ERROR, "Feedback not found!").show();
            }
        } catch (NumberFormatException e) {
            // Show an error if the input is not a valid integer
            new Alert(Alert.AlertType.ERROR, "Please enter a valid Feedback ID.").show();
            TxtSearchfeedBack.setStyle(TxtSearchfeedBack.getStyle() + ";-fx-border-color: red;");
        } catch (SQLException e) {
            // Show an error if a database issue occurs
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
        }
    }
}

