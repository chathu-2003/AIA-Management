package edu.example.aia.aia_management.Controller;

import edu.example.aia.aia_management.Model.InsuranceTypeModel;
import edu.example.aia.aia_management.bo.BOFactory;
import edu.example.aia.aia_management.bo.custom.InsuranceTypeBo;
import edu.example.aia.aia_management.dto.FeedBackDto;
import edu.example.aia.aia_management.dto.InsurancetypeDto;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class InsurancetypeController {

    @FXML
    private TextField TxtSearchInsurance;

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
    private TableColumn<InsurancetypeDto, String> ColDescription;

    @FXML
    private TableColumn<InsurancetypeDto, Integer> ColPolicyId;

    @FXML
    private TableColumn<InsurancetypeDto, Integer> ColTypeId;

    @FXML
    private TableColumn<InsurancetypeDto, String> ColTypeName;

    @FXML
    private Pane InsurancePane;

    @FXML
    private AnchorPane Insurancepage;

    @FXML
    private TableView<InsurancetypeDto> TbleInsurance;

    @FXML
    private TextField TxtDescription;

    @FXML
    private TextField TxtName;

    @FXML
    private TextField TxtPolicy;

    @FXML
    private TextField TxtType;

    private InsuranceTypeModel model;

    public InsuranceTypeBo insuranceTypeBo=(InsuranceTypeBo) BOFactory.getInstance().getBO(BOFactory.BOType.INSURANCETYPE);

    @FXML
    private void initialize() {
        model = new InsuranceTypeModel();

        // Set up the table columns with their corresponding property values
        ColTypeId.setCellValueFactory(new PropertyValueFactory<>("typeId"));
        ColPolicyId.setCellValueFactory(new PropertyValueFactory<>("policyid"));
        ColTypeName.setCellValueFactory(new PropertyValueFactory<>("typename"));
        ColDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        loadInsuranceTypes();// Load data into the table when the controller is initialized
        getNextTypeId();
        setupEnterKeyListeners();
    }

    private void loadInsuranceTypes() {
        TbleInsurance.getItems().clear();
        try {
            List<InsurancetypeDto> insuranceTypes = model.getAllInsuranceTypes();
            TbleInsurance.getItems().addAll(insuranceTypes);
        } catch (SQLException e) {
            showAlert("Error", "Failed to load data: " + e.getMessage(), AlertType.ERROR);
        }
    }

    private void clearFields() {
        TxtType.clear();
        TxtPolicy.clear();
        TxtName.clear();
        TxtDescription.clear();
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void BtnDeleteOnAction(ActionEvent event) {
        InsurancetypeDto selectedInsuranceType = TbleInsurance.getSelectionModel().getSelectedItem();
        if (selectedInsuranceType != null) {
            try {
                boolean isDeleted = insuranceTypeBo.deleteInsuranceType(selectedInsuranceType.getTypeId());
                if (isDeleted) {
                    showAlert("Success", "Insurance type deleted successfully.", AlertType.INFORMATION);
                    loadInsuranceTypes(); // Refresh the table view
                    // clearFields(); // Clear input fields
                } else {
                    showAlert("Error", "Failed to delete insurance type.", AlertType.ERROR);
                }
            } catch (SQLException e) {
                showAlert("Error", "Database error: " + e.getMessage(), AlertType.ERROR);
            }
        } else {
            showAlert("Warning", "Please select an insurance type to delete.", AlertType.WARNING);
        }
    }

    @FXML
    void BtnSaveOnAction(ActionEvent event) {
        try {
            InsurancetypeDto insuranceType = new InsurancetypeDto();
            insuranceType.setTypeId(Integer.parseInt(TxtType.getText())); // Get Type ID from input
            insuranceType.setPolicyid(Integer.parseInt(TxtPolicy.getText()));
            insuranceType.setTypename(TxtName.getText());
            insuranceType.setDescription(TxtDescription.getText());

            boolean isAdded = insuranceTypeBo.addInsuranceType(insuranceType);
            if (isAdded) {
                showAlert("Success", "Insurance type saved successfully.", AlertType.INFORMATION);
                loadInsuranceTypes(); // Refresh the table view
                clearFields(); // Clear input fields
            } else {
                showAlert("Error", "Failed to save insurance type.", AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            showAlert("Warning", "Please enter valid numeric values for Type ID and Policy ID.", AlertType.WARNING);
        } catch (SQLException e) {
            showAlert("Error", "Database error: " + e.getMessage(), AlertType.ERROR);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void BtnUpdateOnAction(ActionEvent event) {
        InsurancetypeDto selectedInsuranceType = TbleInsurance.getSelectionModel().getSelectedItem();
        if (selectedInsuranceType != null) {
            try {
                selectedInsuranceType.setPolicyid(Integer.parseInt(TxtPolicy.getText()));
                selectedInsuranceType.setTypename(TxtName.getText());
                selectedInsuranceType.setDescription(TxtDescription.getText());

                boolean isUpdated = model.updateInsuranceType(selectedInsuranceType);
                if (isUpdated) {
                    showAlert("Success", "Insurance type updated successfully.", AlertType.INFORMATION);
                    loadInsuranceTypes(); // Refresh the table view
                    //  clearFields(); // Clear input fields
                } else {
                    showAlert("Error", "Failed to update insurance type.", AlertType.ERROR);
                }
            } catch (NumberFormatException e) {
                showAlert("Warning", "Please enter valid numeric values for Policy ID.", AlertType.WARNING);
            } catch (SQLException e) {
                showAlert("Error", "Database error: " + e.getMessage(), AlertType.ERROR);
            }
        } else {
            showAlert("Warning", "Please select an insurance type to update.", AlertType.WARNING);
        }
    }

    @FXML
    void BtnLogoutOnAction(ActionEvent event) throws IOException {
        Insurancepage.getChildren().clear();
        Insurancepage.getChildren().add(
                FXMLLoader.load(getClass().getResource("/View/policy.fxml"))
        );
    }
    @FXML
    void OnTbleInsurance(MouseEvent event) {
        // Get the selected item from the TableView
        InsurancetypeDto selectedInsuranceType = TbleInsurance.getSelectionModel().getSelectedItem();

        // If a row is selected, populate the text fields
        if (selectedInsuranceType != null) {
            TxtType.setText(String.valueOf(selectedInsuranceType.getTypeId()));
            TxtPolicy.setText(String.valueOf(selectedInsuranceType.getPolicyid()));
            TxtName.setText(selectedInsuranceType.getTypename());
            TxtDescription.setText(selectedInsuranceType.getDescription());
        }
    }
    private void getNextTypeId() {
        try {
            String nextTypeId = model.getNextTypeId(); // Call the new method in InsuranceTypeModel
            TxtType.setText(nextTypeId);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while generating Type ID: " + e.getMessage()).show();
        }
    }
    private void setupEnterKeyListeners() {
        // Focus moves from Type ID to Policy ID when Enter is pressed
        TxtType.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtPolicy.requestFocus();
            }
        });

        // Focus moves from Policy ID to Type Name when Enter is pressed
        TxtPolicy.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtName.requestFocus();
            }
        });

        // Focus moves from Type Name to Description when Enter is pressed
        TxtName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtDescription.requestFocus();
            }
        });

        // When Enter is pressed in the Description field, trigger the Save button's action
        TxtDescription.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                BtnSave.fire(); // Trigger Save button's action
            }
        });
    }
    @FXML
    void BtnSearchOnAction(ActionEvent event) {
        String searchInput = TxtSearchInsurance.getText();

        // Reset the border color for the search field
        TxtSearchInsurance.setStyle(TxtSearchInsurance.getStyle() + ";-fx-border-color: #7367F0;");

        if (searchInput.isEmpty()) {
            // Show a warning if the search field is empty
            new Alert(Alert.AlertType.WARNING, "Please enter an Insurance Type ID to search!").show();
            TxtSearchInsurance.setStyle(TxtSearchInsurance.getStyle() + ";-fx-border-color: red;");
            return;
        }

        try {
            // Parse the entered Insurance Type ID
            int typeId = Integer.parseInt(searchInput);

            // Use the searchInsuranceTypeById method to search for the insurance type
            InsurancetypeDto insuranceType = model.searchInsuranceTypeById(typeId);

            if (insuranceType != null) {
                // Populate the fields with the insurance type details
                TxtType.setText(String.valueOf(insuranceType.getTypeId()));
                TxtPolicy.setText(String.valueOf(insuranceType.getPolicyid()));
                TxtName.setText(insuranceType.getTypename());
                TxtDescription.setText(insuranceType.getDescription());

                // Highlight the matching insurance type in the TableView
                ObservableList<InsurancetypeDto> insuranceList = TbleInsurance.getItems();
                for (InsurancetypeDto item : insuranceList) {
                    if (item.getTypeId() == typeId) {
                        TbleInsurance.getSelectionModel().select(item);
                        TbleInsurance.scrollTo(item);
                        break;
                    }
                }
            } else {
                // Show an error if no insurance type is found
                new Alert(Alert.AlertType.ERROR, "Insurance Type not found!").show();
            }
        } catch (NumberFormatException e) {
            // Show an error if the input is not a valid integer
            new Alert(Alert.AlertType.ERROR, "Please enter a valid Insurance Type ID.").show();
            TxtSearchInsurance.setStyle(TxtSearchInsurance.getStyle() + ";-fx-border-color: red;");
        } catch (SQLException e) {
            // Show an error if a database issue occurs
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
        }
    }
}
