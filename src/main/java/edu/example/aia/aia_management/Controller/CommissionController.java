package edu.example.aia.aia_management.Controller;

//import edu.example.aia.aia_management.edu.example.aia.aia_management.Model.CommissionModel;
import edu.example.aia.aia_management.bo.BOFactory;
import edu.example.aia.aia_management.bo.custom.CommissionBO;
import edu.example.aia.aia_management.dto.CommissionDTO;
import edu.example.aia.aia_management.entity.Commission;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommissionController {
    @FXML
    private Button BtnSearch;

    @FXML
    private TextField TxtSearchCommision;

    @FXML
    private Button BtnBack;

    @FXML
    private Button BtnDelete;

    @FXML
    private Button BtnSave;

    @FXML
    private Button BtnUpdate;

    @FXML
    private TableColumn<CommissionDTO, Integer> ColAgentId;

    @FXML
    private TableColumn<CommissionDTO, BigDecimal> ColAmount;

    @FXML
    private TableColumn<CommissionDTO, Integer> ColCommissionId;

    @FXML
    private TableColumn<CommissionDTO, LocalDate> ColDate;

    @FXML
    private TableView<CommissionDTO> TbleCommission;

    @FXML
    private TextField TxtAgent;

    @FXML
    private TextField TxtAmount;

    @FXML
    private AnchorPane commisionpage;

    @FXML
    private TextField TxtCommision;

    @FXML
    private TextField TxtDate;

    public CommissionBO commissionBO=(CommissionBO) BOFactory.getInstance().getBO(BOFactory.BOType.COMMISSION);


    @FXML

    public void initialize() {
        ColCommissionId.setCellValueFactory(new PropertyValueFactory<>("commissionId"));
        ColAgentId.setCellValueFactory(new PropertyValueFactory<>("agentId"));
        ColAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        ColDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        loadTableData();

        // Add listener for table row click
        TbleCommission.setOnMouseClicked(this::OnClickTbleCommission);

        setupEnterKeyListeners();
    }

    @FXML
    void BtnSaveOnAction(ActionEvent event) {
        if (validateFields()) {
            try {
                CommissionDTO commission = new CommissionDTO(
                        Integer.parseInt(TxtCommision.getText()),
                        Integer.parseInt(TxtAgent.getText()),
                        new BigDecimal(TxtAmount.getText()),
                        LocalDate.parse(TxtDate.getText())
                );

                boolean isSaved = commissionBO.saveCommission(commission);
                if (isSaved) {
                    showAlert("Success", "Commission saved successfully.");
                    loadTableData();
                    clearFields();
                } else {
                    showAlert("Error", "Failed to save the Commission.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Database Error: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void BtnUpdateOnAction(ActionEvent event) {
        if (validateFields()) {
            try {
                CommissionDTO commission = new CommissionDTO(
                        Integer.parseInt(TxtCommision.getText()),
                        Integer.parseInt(TxtAgent.getText()),
                        new BigDecimal(TxtAmount.getText()),
                        LocalDate.parse(TxtDate.getText())
                );

                boolean isUpdated = commissionBO.updateCommission(commission);
                if (isUpdated) {
                    showAlert("Success", "Commission updated successfully.");
                    loadTableData();
                    clearFields();
                } else {
                    showAlert("Error", "Failed to update the Commission.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Database Error: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void BtnDeleteOnAction(ActionEvent event) {
        if (validateFields()) {
            try {
                int commissionId = Integer.parseInt(TxtCommision.getText());

                boolean isDeleted = commissionBO.deleteCommission(commissionId);
                if (isDeleted) {
                    showAlert("Success", "Commission deleted successfully.");
                    loadTableData();
                    clearFields();
                } else {
                    showAlert("Error", "Failed to delete the Commission.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Database Error: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Load data into the TableView
    private void loadTableData() {
        try {
            List<CommissionDTO> commissionList = commissionBO.getAllCommissions();
            ObservableList<CommissionDTO> observableList = FXCollections.observableArrayList(commissionList);
            TbleCommission.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load data: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Clear input fields
    private void clearFields() {
        TxtCommision.clear();
        TxtAgent.clear();
        TxtAmount.clear();
        TxtDate.clear();
    }

    // Display alert message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void BtnBackOnAction(ActionEvent event)throws IOException {
        commisionpage.getChildren().clear();
        commisionpage.getChildren().add(
                FXMLLoader.load(getClass().getResource("/View/Homepage.fxml"))
        );
    }

    @FXML
    void OnClickTbleCommission(MouseEvent event) {
        // Get the selected item
        CommissionDTO selectedCommission = TbleCommission.getSelectionModel().getSelectedItem();

        if (selectedCommission != null) {
            // Populate the text fields with the selected CommissionDTO details
            TxtCommision.setText(String.valueOf(selectedCommission.getCommissionId()));
            TxtAgent.setText(String.valueOf(selectedCommission.getAgentId()));
            TxtAmount.setText(selectedCommission.getAmount().toString());
            TxtDate.setText(selectedCommission.getDate().toString());
        }
    }

    // Validate input fields
    private boolean validateFields() {
        // Validate Commission ID (must be an integer)
        if (TxtCommision.getText().isEmpty()) {
            showAlert("Error", "Commission ID cannot be empty.");
            return false;
        }
        try {
            Integer.parseInt(TxtCommision.getText());
        } catch (NumberFormatException e) {
            TxtCommision.setStyle(TxtCommision.getStyle() + ";-fx-border-color: red;");
            return false;
        }

        // Validate Agent ID (must be an integer)
        if (TxtAgent.getText().isEmpty()) {
            showAlert("Error", "Agent ID cannot be empty.");
            return false;
        }
        try {
            Integer.parseInt(TxtAgent.getText());
        } catch (NumberFormatException e) {
            TxtAgent.setStyle(TxtAgent.getStyle() + ";-fx-border-color: red;");
            return false;
        }

        // Validate Amount (must be a valid decimal number)
        if (TxtAmount.getText().isEmpty()) {
            showAlert("Error", "Amount cannot be empty.");
            return false;
        }
        try {
            new BigDecimal(TxtAmount.getText());
        } catch (NumberFormatException e) {
            TxtAmount.setStyle(TxtAmount.getStyle() + ";-fx-border-color: red;");
            return false;
        }

        // Validate Date (must be in the format yyyy-MM-dd)
        if (TxtDate.getText().isEmpty()) {
            showAlert("Error", "Date cannot be empty.");
            return false;
        }
        try {
            LocalDate.parse(TxtDate.getText());
        } catch (Exception e) {
            TxtDate.setStyle(TxtDate.getStyle() + ";-fx-border-color: red;");
            return false;
        }

        return true;
    }
    private void setupEnterKeyListeners() {
        TxtCommision.setOnKeyPressed(this::handleEnterKey);
        TxtAgent.setOnKeyPressed(this::handleEnterKey);
        TxtAmount.setOnKeyPressed(this::handleEnterKey);
        TxtDate.setOnKeyPressed(this::handleEnterKey);
    }

    // Common handler for Enter key actions
    private void handleEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            Object source = event.getSource();

            if (source.equals(TxtCommision)) {
                TxtAgent.requestFocus();
            } else if (source.equals(TxtAgent)) {
                TxtAmount.requestFocus();
            } else if (source.equals(TxtAmount)) {
                TxtDate.requestFocus();
            } else if (source.equals(TxtDate)) {
                BtnSave.fire(); // Trigger the Save button's action
            }
        }
    }

    @FXML
    void BtnSearchOnAction(ActionEvent event) {
        // Get the Commission ID from the search text field
        String commissionIdText = TxtSearchCommision.getText();

        // Validate that the input is not empty and is an integer
        if (commissionIdText.isEmpty()) {
            showAlert("Error", "Please enter a Commission ID to search.");
            TxtSearchCommision.setStyle(TxtSearchCommision.getStyle() + ";-fx-border-color: red;");
            return;
        }

        try {
            int commissionId = Integer.parseInt(commissionIdText);
            TxtSearchCommision.setStyle(TxtSearchCommision.getStyle() + ";-fx-border-color: none;");

            // Call the model to search for the commission
            ArrayList<CommissionDTO> commission = commissionBO.searchCommission(String.valueOf(commissionId));

            CommissionDTO commissionDTO = new CommissionDTO();
            if (commission != null) {
                // Populate the fields with the retrieved commission details
                TxtCommision.setText(String.valueOf(commissionDTO.getCommissionId()));
                TxtAgent.setText(String.valueOf(commissionDTO.getAgentId()));
                TxtAmount.setText(commissionDTO.getAmount().toString());
                TxtDate.setText(commissionDTO.getDate().toString());


            } else {
                showAlert("Error", "No commission found with the given ID.");
                clearFields();
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Commission ID must be a valid number.");
            TxtSearchCommision.setStyle(TxtSearchCommision.getStyle() + ";-fx-border-color: red;");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

