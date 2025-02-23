package edu.example.aia.aia_management.Controller;

import edu.example.aia.aia_management.dto.UserPolicyDetailsDto;
import edu.example.aia.aia_management.Model.UserPolicyDetailsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.List;

public class UserPolicyDetailsController {

    @FXML
    private Button BtnBack;

    @FXML
    private Button BtnDelect;

    @FXML
    private Button BtnSave;

    @FXML
    private Button BtnSearch;

    @FXML
    private Button BtnUpdate;

    @FXML
    private TableColumn<UserPolicyDetailsDto, Integer> ColPolicyId;

    @FXML
    private TableColumn<UserPolicyDetailsDto, Integer> ColUserId;

    @FXML
    private TableColumn<UserPolicyDetailsDto, Integer> ColUserPolicyDetail;

    @FXML
    private TableView<UserPolicyDetailsDto> TableUserpolicyDetails;

    @FXML
    private TextField TxtPolicy;

    @FXML
    private TextField TxtSearchClamis;

    @FXML
    private TextField TxtUser;

    @FXML
    private TextField TxtUserpolicyDetails;

    @FXML
    private AnchorPane pageuserpolicy;

    private ObservableList<UserPolicyDetailsDto> dataList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadUserPolicyDetails();
    }

    // Save Button Action
    @FXML
    void BtnSaveOnAction(ActionEvent event) {
        try {
            int userId = Integer.parseInt(TxtUser.getText());
            int policyId = Integer.parseInt(TxtPolicy.getText());

            UserPolicyDetailsDto userPolicyDetailsDto = new UserPolicyDetailsDto(0, userId, policyId);

            boolean isSaved = UserPolicyDetailsModel.saveUserPolicyDetails(userPolicyDetailsDto);
            if (isSaved) {
                loadUserPolicyDetails();
                showAlert("Success", "User Policy Details Saved");
            } else {
                showAlert("Error", "Failed to Save User Policy Details");
            }
        } catch (SQLException e) {
            showAlert("Error", "Error while saving");
            e.printStackTrace();
        }
    }

    // Update Button Action
    @FXML
    void BtnUpdateOnAction(ActionEvent event) {
        try {
            UserPolicyDetailsDto selectedItem = TableUserpolicyDetails.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                selectedItem.setUserId(Integer.parseInt(TxtUser.getText()));
                selectedItem.setPolicyId(Integer.parseInt(TxtPolicy.getText()));

                boolean isUpdated = UserPolicyDetailsModel.updateUserPolicyDetails(selectedItem);
                if (isUpdated) {
                    loadUserPolicyDetails();
                    showAlert("Success", "User Policy Details Updated");
                } else {
                    showAlert("Error", "Failed to Update User Policy Details");
                }
            } else {
                showAlert("Error", "No item selected");
            }
        } catch (SQLException e) {
            showAlert("Error", "Error while updating");
            e.printStackTrace();
        }
    }

    // Delete Button Action
    @FXML
    void BtnDelectOnAction(ActionEvent event) {
        try {
            UserPolicyDetailsDto selectedItem = TableUserpolicyDetails.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                boolean isDeleted = UserPolicyDetailsModel.deleteUserPolicyDetails(selectedItem.getUserPolicyDetailsId());
                if (isDeleted) {
                    dataList.remove(selectedItem);
                    showAlert("Success", "User Policy Details Deleted");
                } else {
                    showAlert("Error", "Failed to Delete User Policy Details");
                }
            } else {
                showAlert("Error", "No item selected");
            }
        } catch (SQLException e) {
            showAlert("Error", "Error while deleting");
            e.printStackTrace();
        }
    }

    // Search Button Action
    @FXML
    void BtnSearchOnAction(ActionEvent event) {
        try {
            int userId = Integer.parseInt(TxtSearchClamis.getText());
            List<UserPolicyDetailsDto> searchResults = UserPolicyDetailsModel.searchUserPolicyDetails(userId);
            dataList.setAll(searchResults);
        } catch (SQLException | NumberFormatException e) {
            showAlert("Error", "Error during search");
            e.printStackTrace();
        }
    }

    // Load All User Policy Details into TableView
    private void loadUserPolicyDetails() {
        try {
            List<UserPolicyDetailsDto> userPolicyDetailsList = UserPolicyDetailsModel.getAllUserPolicyDetails();
            dataList.setAll(userPolicyDetailsList);

            TableUserpolicyDetails.setItems(dataList);

            // Bind table columns to data fields
            ColUserId.setCellValueFactory(cellData -> cellData.getValue().userIdProperty().asObject());
            ColPolicyId.setCellValueFactory(cellData -> cellData.getValue().policyIdProperty().asObject());
            ColUserPolicyDetail.setCellValueFactory(cellData -> cellData.getValue().userPolicyDetailsIdProperty().asObject());
        } catch (SQLException e) {
            showAlert("Error", "Error while loading data");
            e.printStackTrace();
        }
    }

    // Show Alert Utility
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Table Row Click Action (for updating)
    @FXML
    void OnClickTableUserpolicyDetails(MouseEvent event) {
        UserPolicyDetailsDto selectedItem = TableUserpolicyDetails.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            TxtUser.setText(String.valueOf(selectedItem.getUserId()));
            TxtPolicy.setText(String.valueOf(selectedItem.getPolicyId()));
            TxtUserpolicyDetails.setText(String.valueOf(selectedItem.getUserPolicyDetailsId()));
        }
    }
    @FXML
    void BtnBackOnAction(ActionEvent event) {
        // Action for BtnBack
    }

}
