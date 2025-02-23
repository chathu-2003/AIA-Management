package edu.example.aia.aia_management.Controller;

import edu.example.aia.aia_management.Model.SupportTicketModel;
import edu.example.aia.aia_management.bo.BOFactory;
import edu.example.aia.aia_management.bo.custom.ClaimBO;
import edu.example.aia.aia_management.bo.custom.SupportTicketBo;
import edu.example.aia.aia_management.dto.InsurancetypeDto;
import edu.example.aia.aia_management.dto.SupportTicketDto;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SupportTicketController {

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
    private TableColumn<SupportTicketDto, Integer> ColCustomerId;

    @FXML
    private TableColumn<SupportTicketDto, String> ColIssueDescription;

    @FXML
    private TableColumn<SupportTicketDto, String> ColStatus;

    @FXML
    private TableColumn<SupportTicketDto, Integer> ColSupportTicketId;

    @FXML
    private TableView<SupportTicketDto> TableSupportTicket;

    @FXML
    private TextField TxtCustomerId;

    @FXML
    private AnchorPane Supportticketpage;

    @FXML
    private TextField TxtIssueDescription;

    @FXML
    private TextField TxtStatus;

    @FXML
    private TextField TxtSupportTicketId;

    public SupportTicketBo supportTicketBo=(SupportTicketBo) BOFactory.getInstance().getBO(BOFactory.BOType.SUPPORTTICKET);

    // Initialize the TableView
    @FXML
    public void initialize() {
        ColSupportTicketId.setCellValueFactory(new PropertyValueFactory<>("supportTicketId"));
        ColCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        ColIssueDescription.setCellValueFactory(new PropertyValueFactory<>("issueDescription"));
        ColStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadTableData();
        getNextSupportTicketId();
        setupEnterKeyListeners();
    }
    private void getNextSupportTicketId() {
        try {
            int nextId = SupportTicketModel.getNextSupportTicketId();
            TxtSupportTicketId.setText(String.valueOf(nextId));
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to generate the next Support Ticket ID: " + e.getMessage());
        }
    }
    @FXML
    void BtnSaveOnAction(ActionEvent event) {
        try {
            SupportTicketDto ticket = new SupportTicketDto(
                    Integer.parseInt(TxtSupportTicketId.getText()),
                    Integer.parseInt(TxtCustomerId.getText()),
                    TxtIssueDescription.getText(),
                    TxtStatus.getText()
            );

            boolean isSaved = SupportTicketModel.saveSupportTicket(ticket);
            if (isSaved) {
                showAlert("Success", "Support Ticket saved successfully.");
                loadTableData();
                clearFields();
            } else {
                showAlert("Error", "Failed to save the Support Ticket.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database Error: " + e.getMessage());
        }
    }

    @FXML
    void BtnUpdateOnAction(ActionEvent event) {
        try {
            SupportTicketDto ticket = new SupportTicketDto(
                    Integer.parseInt(TxtSupportTicketId.getText()),
                    Integer.parseInt(TxtCustomerId.getText()),
                    TxtIssueDescription.getText(),
                    TxtStatus.getText()
            );

            boolean isUpdated = supportTicketBo.updateSupportTicket(ticket);
            if (isUpdated) {
                showAlert("Success", "Support Ticket updated successfully.");
                loadTableData();
                clearFields();
            } else {
                showAlert("Error", "Failed to update the Support Ticket.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void BtnDeleteOnAction(ActionEvent event) {
        try {
            int supportTicketId = Integer.parseInt(TxtSupportTicketId.getText());

            boolean isDeleted = supportTicketBo.deleteSupportTicket(supportTicketId);
            if (isDeleted) {
                showAlert("Success", "Support Ticket deleted successfully.");
                loadTableData();
                clearFields();
            } else {
                showAlert("Error", "Failed to delete the Support Ticket.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database Error: " + e.getMessage());
        }
    }

    @FXML
    void OnTableSupportTicket(MouseEvent event) {
        SupportTicketDto selectedTicket = TableSupportTicket.getSelectionModel().getSelectedItem();
        if (selectedTicket != null) {
            TxtSupportTicketId.setText(String.valueOf(selectedTicket.getSupportTicketId()));
            TxtCustomerId.setText(String.valueOf(selectedTicket.getCustomerId()));
            TxtIssueDescription.setText(selectedTicket.getIssueDescription());
            TxtStatus.setText(selectedTicket.getStatus());
        }
    }

    // Load data into the TableView
    private void loadTableData() {
        try {
            List<SupportTicketDto> ticketList = supportTicketBo.getAllSupportTickets();
            ObservableList<SupportTicketDto> observableList = FXCollections.observableArrayList(ticketList);
            TableSupportTicket.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load data: " + e.getMessage());
        }
    }

    // Clear input fields
    private void clearFields() {
        TxtSupportTicketId.clear();
        TxtCustomerId.clear();
        TxtIssueDescription.clear();
        TxtStatus.clear();
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
        Supportticketpage.getChildren().clear();
        Supportticketpage.getChildren().add(
                FXMLLoader.load(getClass().getResource("/View/Homepage.fxml"))
        );
    }
    private void setupEnterKeyListeners() {
        // When Enter is pressed in Support Ticket ID field, focus moves to Customer ID field
        TxtSupportTicketId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtCustomerId.requestFocus(); // Move focus to Customer ID field
            }
        });

        // When Enter is pressed in Customer ID field, focus moves to Issue Description field
        TxtCustomerId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtIssueDescription.requestFocus(); // Move focus to Issue Description field
            }
        });

        // When Enter is pressed in Issue Description field, focus moves to Status field
        TxtIssueDescription.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtStatus.requestFocus(); // Move focus to Status field
            }
        });

        // When Enter is pressed in Status field, trigger Save button action
        TxtStatus.setOnKeyPressed(event -> {
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
            new Alert(Alert.AlertType.WARNING, "Please enter a Support Ticket ID to search!").show();
            TxtSearch.setStyle(TxtSearch.getStyle() + ";-fx-border-color: red;");
            return;
        }

        try {
            // Parse the entered Support Ticket ID
            int supportTicketId = Integer.parseInt(searchInput);

            // Use the searchSupportTicketById method to search for the support ticket
            SupportTicketDto supportTicket = SupportTicketModel.searchSupportTicketById(supportTicketId);

            if (supportTicket != null) {
                // Populate the fields with the support ticket details
                TxtSupportTicketId.setText(String.valueOf(supportTicket.getSupportTicketId()));
                TxtCustomerId.setText(String.valueOf(supportTicket.getCustomerId()));
                TxtIssueDescription.setText(supportTicket.getIssueDescription());
                TxtStatus.setText(supportTicket.getStatus());

                // Highlight the matching support ticket in the TableView
                ObservableList<SupportTicketDto> ticketList = TableSupportTicket.getItems();
                for (SupportTicketDto ticket : ticketList) {
                    if (ticket.getSupportTicketId() == supportTicketId) {
                        TableSupportTicket.getSelectionModel().select(ticket);
                        TableSupportTicket.scrollTo(ticket);
                        break;
                    }
                }
            } else {
                // Show an error if no support ticket is found
                new Alert(Alert.AlertType.ERROR, "Support Ticket not found!").show();
            }
        } catch (NumberFormatException e) {
            // Show an error if the input is not a valid integer
            new Alert(Alert.AlertType.ERROR, "Please enter a valid Support Ticket ID.").show();
            TxtSearch.setStyle(TxtSearch.getStyle() + ";-fx-border-color: red;");
        } catch (SQLException e) {
            // Show an error if a database issue occurs
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
        }
    }
}

   