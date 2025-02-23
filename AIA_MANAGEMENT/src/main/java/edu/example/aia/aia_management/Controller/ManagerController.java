package edu.example.aia.aia_management.Controller;

import edu.example.aia.aia_management.Model.ManagerModel;
import edu.example.aia.aia_management.dto.InsurancetypeDto;
import edu.example.aia.aia_management.dto.ManagerDto;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ManagerController {


    @FXML
    private TextField TxtSearch;

    @FXML
    private Button BtnSearch;

    @FXML
    private Button BtnDelete;

    @FXML
    private Button BtnLogout;

    @FXML
    private Button BtnSave;

    @FXML
    private Button BtnUpdate;

    @FXML
    private TableColumn<ManagerDto, String> ColEmail;

    @FXML
    private TableColumn<ManagerDto, Integer> ColManager;

    @FXML
    private TableColumn<ManagerDto, String> ColName;

    @FXML
    private TableColumn<ManagerDto, String> ColPhone;

    @FXML
    private Pane Managerpage;

    @FXML
    private TableView<ManagerDto> TableManager;

    @FXML
    private TextField TxtEmail;

    @FXML
    private TextField TxtManager;

    @FXML
    private TextField TxtName;

    @FXML
    private TextField TxtPhone;

    private ManagerModel managerModel = new ManagerModel();

    @FXML
    public void initialize() {
        // Set up columns with ManagerDto fields
        ColManager.setCellValueFactory(new PropertyValueFactory<>("managerId"));
        ColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ColEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        ColPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        // Load initial data into TableView
        loadManagers();
        getNextManagerId();
        setupEnterKeyListeners();
    }

    // Load managers into TableView
    private void loadManagers() {
        try {
            List<ManagerDto> managers = managerModel.loadManagers();
            TableManager.getItems().setAll(managers);
        } catch (SQLException e) {
            showAlert("Error", "Failed to load data: " + e.getMessage());
        }
    }

    @FXML
    void BtnSaveOnAction(ActionEvent event) {
        try {
            int managerId = Integer.parseInt(TxtManager.getText()); // Get ManagerId from input
            ManagerDto manager = new ManagerDto(
                    managerId,
                    TxtName.getText(),
                    TxtEmail.getText(),
                    TxtPhone.getText()
            );
            if (managerModel.saveManager(manager)) {
                showAlert("Success", "Manager saved successfully.");
                loadManagers();
            } else {
                showAlert("Error", "Failed to save manager.");
            }
        } catch (SQLException e) {
            showAlert("Error", "Database error: " + e.getMessage());
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid Manager ID format.");
        }
    }


    @FXML
    void BtnUpdateOnAction(ActionEvent event) {
        try {
            int managerId = Integer.parseInt(TxtManager.getText());
            ManagerDto manager = new ManagerDto(
                    managerId,
                    TxtName.getText(),
                    TxtEmail.getText(),
                    TxtPhone.getText()
            );

            if (managerModel.updateManager(manager)) {
                showAlert("Success", "Manager updated successfully.");
                loadManagers();
            } else {
                showAlert("Error", "Failed to update manager.");
            }
        } catch (SQLException e) {
            showAlert("Error", "Database error: " + e.getMessage());
        }
    }

    @FXML
    void BtnDeleteOnAction(ActionEvent event) {
        try {
            int managerId = Integer.parseInt(TxtManager.getText());

            if (managerModel.deleteManager(managerId)) {
                showAlert("Success", "Manager deleted successfully.");
                loadManagers();
            } else {
                showAlert("Error", "Failed to delete manager.");
            }
        } catch (SQLException e) {
            showAlert("Error", "Database error: " + e.getMessage());
        }
    }

    @FXML
    void BtnLogoutOnAction(ActionEvent event) throws IOException {
        Managerpage.getChildren().clear();
        Managerpage.getChildren().add(
                FXMLLoader.load(getClass().getResource("/view/Homepage.fxml"))
        );
        showAlert("Logout", "Logged out successfully!");
        // Implement logout logic if required
    }

    // Utility method for showing alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void OnClickTable(MouseEvent event) {
        ManagerDto selectedManager = TableManager.getSelectionModel().getSelectedItem();
        if (selectedManager != null) {
            // Populate text fields with selected row data
            TxtManager.setText(String.valueOf(selectedManager.getManagerId()));
            TxtName.setText(selectedManager.getName());
            TxtEmail.setText(selectedManager.getEmail());
            TxtPhone.setText(selectedManager.getPhone());
        }
    }

    private void getNextManagerId() {
        try {
            String nextManagerId = managerModel.getNextManagerId(); // Call the new method in ManagerModel
            TxtManager.setText(nextManagerId);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while generating Manager ID: " + e.getMessage()).show();
        }
    }

    private void setupEnterKeyListeners() {
        TxtManager.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtName.requestFocus();
            }
        });

        TxtName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtEmail.requestFocus();
            }
        });

        TxtEmail.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtPhone.requestFocus();
            }
        });

        TxtPhone.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                BtnSave.fire(); // Trigger the save button action
            }
        });
    }
    @FXML
    void BtnSearchOnAction(ActionEvent event) {
        String searchInput = TxtSearch.getText(); // Get input from search field

        // Reset the border color for the search field
        TxtSearch.setStyle("-fx-border-color: #7367F0;");

        if (searchInput.isEmpty()) {
            // Show a warning if the search field is empty
            new Alert(Alert.AlertType.WARNING, "Please enter a Manager ID to search!").show();
            TxtSearch.setStyle("-fx-border-color: red;");
            return;
        }

        try {
            // Parse the entered Manager ID
            int managerId = Integer.parseInt(searchInput);

            // Use the managerModel to search for the Manager
            ManagerDto manager = managerModel.searchManagerById(managerId);

            if (manager != null) {
                // Populate the fields with the manager details
                TxtManager.setText(String.valueOf(manager.getManagerId()));
                TxtName.setText(manager.getName());
                TxtEmail.setText(manager.getEmail());
                TxtPhone.setText(manager.getPhone());

                // Highlight the matching manager in the TableView
                ObservableList<ManagerDto> managerList = TableManager.getItems();
                for (ManagerDto item : managerList) {
                    if (item.getManagerId() == managerId) {
                        TableManager.getSelectionModel().select(item);
                        TableManager.scrollTo(item);
                        break;
                    }
                }
            } else {
                // Show an error if no manager is found
                new Alert(Alert.AlertType.ERROR, "Manager not found!").show();
            }
        } catch (NumberFormatException e) {
            // Show an error if the input is not a valid integer
            new Alert(Alert.AlertType.ERROR, "Please enter a valid Manager ID.").show();
            TxtSearch.setStyle("-fx-border-color: red;");
        } catch (SQLException e) {
            // Show an error if a database issue occurs
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
        }
    }
}


