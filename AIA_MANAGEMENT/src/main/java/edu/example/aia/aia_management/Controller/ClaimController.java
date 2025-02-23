package edu.example.aia.aia_management.Controller;

import edu.example.aia.aia_management.Model.ClaimModel;
import edu.example.aia.aia_management.dto.ClaimDto;
import edu.example.aia.aia_management.dto.PaymentDto;
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
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

public class ClaimController {

    @FXML
    private TextField TxtSearchClamis;
    @FXML
    private Button BtnSearch;
    @FXML
    private Button BtnBack;
    @FXML
    private Button BtnDelect;
    @FXML
    private Button BtnSave;
    @FXML
    private Button BtnUpdate;
    @FXML
    private TableColumn<ClaimDto, Integer> ColClaim;
    @FXML
    private TableColumn<ClaimDto, Integer> ColPolicy;
    @FXML
    private TableColumn<ClaimDto, BigDecimal> ColAmount;
    @FXML
    private TableColumn<ClaimDto, Date> ColDate;
    @FXML
    private AnchorPane PageClaim;
    @FXML
    private TableView<ClaimDto> TbleClaim;
    @FXML
    private TextField TxtClaim;
    @FXML
    private TextField TxtClaimAmount;
    @FXML
    private TextField TxtDate;
    @FXML
    private TextField TxtPolicy;

    // Initialize table columns and load data
    public void initialize() {
        ColClaim.setCellValueFactory(new PropertyValueFactory<>("claimId"));
        ColPolicy.setCellValueFactory(new PropertyValueFactory<>("policyId"));
        ColAmount.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        ColDate.setCellValueFactory(new PropertyValueFactory<>("claimDate"));

        loadTable();
        setNextClaimId();
        setupEnterKeyListeners();
    }

    // Load table with data from ClaimModel
    public void loadTable() {
        try {
            ObservableList<ClaimDto> claimList = FXCollections.observableArrayList(ClaimModel.getAllClaims());
            TbleClaim.setItems(claimList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
        }
    }

    // Save a new claim
    @FXML
    void BtnSaveOnAction(ActionEvent event) {
        if (validateFields()) {
            try {
                int claimId = Integer.parseInt(TxtClaim.getText());
                int policyId = Integer.parseInt(TxtPolicy.getText());
                BigDecimal claimAmount = new BigDecimal(TxtClaimAmount.getText());
                Date claimDate = Date.valueOf(TxtDate.getText());

                ClaimDto claim = new ClaimDto(claimId, policyId, claimAmount, claimDate);
                if (ClaimModel.saveClaim(claim)) {
                    loadTable(); // Refresh table after saving
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save the claim.").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
            }
        }
    }

    // Update an existing claim
    @FXML
    void BtnUpdateOnAction(ActionEvent event) {
        if (validateFields()) {
            try {
                int claimId = Integer.parseInt(TxtClaim.getText());
                int policyId = Integer.parseInt(TxtPolicy.getText());
                BigDecimal claimAmount = new BigDecimal(TxtClaimAmount.getText());
                Date claimDate = Date.valueOf(TxtDate.getText());

                ClaimDto claim = new ClaimDto(claimId, policyId, claimAmount, claimDate);
                if (ClaimModel.updateClaim(claim)) {
                    loadTable(); // Refresh table after updating
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update the claim.").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
            }
        }
    }

    // Delete a selected claim
    @FXML
    void BtnDelectOnAction(ActionEvent event) {
        ClaimDto selectedClaim = TbleClaim.getSelectionModel().getSelectedItem();
        if (selectedClaim != null) {
            try {
                if (ClaimModel.deleteClaim(selectedClaim.getClaimId())) {
                    loadTable(); // Refresh table after deletion
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the claim.").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
            }
        }
    }

    // Populate fields when a table row is clicked
    @FXML
    void OnClickTbleClaim(MouseEvent event) {
        ClaimDto selectedClaim = TbleClaim.getSelectionModel().getSelectedItem();
        if (selectedClaim != null) {
            TxtClaim.setText(String.valueOf(selectedClaim.getClaimId()));
            TxtPolicy.setText(String.valueOf(selectedClaim.getPolicyId()));
            TxtClaimAmount.setText(selectedClaim.getClaimAmount().toString());
            TxtDate.setText(selectedClaim.getClaimDate().toString());
        }
    }

    // Clear input fields
    private void clearFields() {
        TxtClaim.clear();
        TxtPolicy.clear();
        TxtClaimAmount.clear();
        TxtDate.clear();
    }

    @FXML
    void BtnBackOnAction(ActionEvent event) throws IOException {
        PageClaim.getChildren().clear();
        PageClaim.getChildren().add(
                FXMLLoader.load(getClass().getResource("/view/Homepage.fxml"))
        );
    }

    // Set the next claim ID
    private void setNextClaimId() {
        try {
            int nextClaimId = ClaimModel.getNextClaimId(); // Call the method in ClaimModel
            TxtClaim.setText(String.valueOf(nextClaimId));
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error generating Claim ID: " + e.getMessage()).show();
        }
    }

    // Validate input fields
    private boolean validateFields() {
        if (TxtClaim.getText().isEmpty() || TxtPolicy.getText().isEmpty() || TxtClaimAmount.getText().isEmpty() || TxtDate.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "All fields must be filled!").show();
            return false;
        }

        // Validate Claim ID (must be an integer)
        try {
            Integer.parseInt(TxtClaim.getText());
        } catch (NumberFormatException e) {
            TxtClaim.setStyle(TxtClaim.getStyle() + ";-fx-border-color: red;");
            return false;
        }

        // Validate Policy ID (must be an integer)
        try {
            Integer.parseInt(TxtPolicy.getText());
        } catch (NumberFormatException e) {
            TxtPolicy.setStyle(TxtPolicy.getStyle() + ";-fx-border-color: red;");
            return false;
        }

        // Validate Claim Amount (must be a positive number)
        try {
            BigDecimal claimAmount = new BigDecimal(TxtClaimAmount.getText());
            if (claimAmount.compareTo(BigDecimal.ZERO) <= 0) {
                new Alert(Alert.AlertType.ERROR, "Claim Amount must be greater than zero.").show();
                return false;
            }
        } catch (NumberFormatException e) {
            TxtClaimAmount.setStyle(TxtClaimAmount.getStyle() + ";-fx-border-color: red;");
            return false;
        }

        // Validate Date (must be in yyyy-MM-dd format)
        try {
            Date.valueOf(TxtDate.getText());
        } catch (IllegalArgumentException e) {
            TxtDate.setStyle(TxtDate.getStyle() + ";-fx-border-color: red;");
            return false;
        }
        return true;
    }

    private void setupEnterKeyListeners() {
        TxtClaim.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtPolicy.requestFocus(); // Move focus to the Policy field
            }
        });

        TxtPolicy.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtClaimAmount.requestFocus(); // Move focus to the Claim Amount field
            }
        });

        TxtClaimAmount.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtDate.requestFocus(); // Move focus to the Date field
            }
        });

        TxtDate.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                BtnSave.fire(); // Trigger the Save button's action
            }
        });
    }
    @FXML
    void BtnSearchOnAction(ActionEvent event) {
        String searchInput = TxtSearchClamis.getText();

        // Reset the border color for the search field
        TxtSearchClamis.setStyle(TxtSearchClamis.getStyle() + ";-fx-border-color: #7367F0;");

        if (searchInput.isEmpty()) {
            // Show a warning if the search field is empty
            new Alert(Alert.AlertType.WARNING, "Please enter a Claim ID to search!").show();
            TxtSearchClamis.setStyle(TxtSearchClamis.getStyle() + ";-fx-border-color: red;");
            return;
        }

        try {
            // Parse the entered Claim ID
            int claimId = Integer.parseInt(searchInput);

            // Use the ClaimModel to search for the claim by its ID
            ClaimDto claim = ClaimModel.searchClaim(claimId);

            if (claim != null) {
                // Populate the fields with the claim details
                TxtClaim.setText(String.valueOf(claim.getClaimId()));
                TxtPolicy.setText(String.valueOf(claim.getPolicyId()));
                TxtClaimAmount.setText(claim.getClaimAmount().toString());
                TxtDate.setText(claim.getClaimDate().toString());

                // Optionally, highlight the matching row in the TableView
                ObservableList<ClaimDto> claimList = TbleClaim.getItems();
                for (ClaimDto item : claimList) {
                    if (item.getClaimId() == claimId) {
                        TbleClaim.getSelectionModel().select(item);
                        TbleClaim.scrollTo(item);
                        break;
                    }
                }
            } else {
                // Show an error if no claim is found
                new Alert(Alert.AlertType.ERROR, "Claim not found!").show();
            }
        } catch (NumberFormatException e) {
            // Show an error if the input is not a valid integer
            new Alert(Alert.AlertType.ERROR, "Please enter a valid Claim ID.").show();
            TxtSearchClamis.setStyle(TxtSearchClamis.getStyle() + ";-fx-border-color: red;");
        } catch (SQLException e) {
            // Show an error if a database issue occurs
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
        }
    }
}
