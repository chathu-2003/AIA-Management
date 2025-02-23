package edu.example.aia.aia_management.Controller;

import edu.example.aia.aia_management.Model.PaymentModel;
import edu.example.aia.aia_management.Model.PolicyModel;
import edu.example.aia.aia_management.dto.PaymentDto;
import edu.example.aia.aia_management.dto.PolicyDto;
import javafx.collections.FXCollections;
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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class PaymentController {

    @FXML
    private Button BtnSearch;
    @FXML
    private TextField TxtSearch;
    @FXML
    private ComboBox<Integer> BoxPolicy;  // ComboBox for Policy
    @FXML
    private TableColumn<PaymentDto, Integer> ColAmount;
    @FXML
    private TableColumn<PaymentDto, Integer> ColPaymentId;
    @FXML
    private TableColumn<PaymentDto, String> ColPaymentMethod;
    @FXML
    private TableColumn<PaymentDto, Integer> ColPolicyId;
    @FXML
    private TableView<PaymentDto> TablePayment;
    @FXML
    private TextField TxtAmount;
    @FXML
    private Pane PagePayment;
    @FXML
    private TextField TxtMetthod;
    @FXML
    private TextField TxtPaymentId;

    private final PaymentModel paymentModel = new PaymentModel();
    private final PolicyModel policyModel = new PolicyModel();

    @FXML
    public void initialize() {
        // Set up the table columns
        ColPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        ColPolicyId.setCellValueFactory(new PropertyValueFactory<>("policyId"));
        ColAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        ColPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));

        // Load the payments and populate the ComboBox
        loadPayments();
        loadPolicies();  // Load policies into the ComboBox
        getNextPaymentId();
        setupEnterKeyListeners();
    }

    private void loadPayments() {
        try {
            ObservableList<PaymentDto> payments = FXCollections.observableArrayList(paymentModel.getAllPayments());
            TablePayment.setItems(payments);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load payments").show();
        }
    }

    private void loadPolicies() {
        try {
            // Fetch all policies from the PolicyModel
            List<PolicyDto> policies = policyModel.getAllPolicies();

            // Extract policy IDs from the PolicyDto list and add to ComboBox
            ObservableList<Integer> policyIds = FXCollections.observableArrayList();
            for (PolicyDto policy : policies) {
                policyIds.add(policy.getPolicyId());
            }

            // Set the items of the ComboBox with the policy IDs
            BoxPolicy.setItems(policyIds);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load policies").show();
        }
    }

    @FXML
    void BtnSaveOnAction(ActionEvent event) {
        try {
            int paymentId = Integer.parseInt(TxtPaymentId.getText());
            int policyId = BoxPolicy.getValue();
            BigDecimal amount = new BigDecimal(TxtAmount.getText());
            String paymentMethod = TxtMetthod.getText();

            PaymentDto payment = new PaymentDto(paymentId, policyId, amount, paymentMethod);
            if (paymentModel.savePayment(payment)) {
                new Alert(Alert.AlertType.INFORMATION, "Payment saved successfully").show();
                loadPayments();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save payment").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while saving payment").show();
        }
    }

    @FXML
    void BtnUpdateOnAction(ActionEvent event) {
        try {
            int paymentId = Integer.parseInt(TxtPaymentId.getText());
            int policyId = BoxPolicy.getValue();
            BigDecimal amount = new BigDecimal(TxtAmount.getText());
            String paymentMethod = TxtMetthod.getText();

            PaymentDto payment = new PaymentDto(paymentId, policyId, amount, paymentMethod);
            if (paymentModel.updatePayment(payment)) {
                new Alert(Alert.AlertType.INFORMATION, "Payment updated successfully").show();
                loadPayments();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update payment").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while updating payment").show();
        }
    }

    @FXML
    void BtnDelectOnAction(ActionEvent event) {
        try {
            int paymentId = Integer.parseInt(TxtPaymentId.getText());

            if (paymentModel.deletePayment(paymentId)) {
                new Alert(Alert.AlertType.INFORMATION, "Payment deleted successfully").show();
                loadPayments();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete payment").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while deleting payment").show();
        }
    }

    @FXML
    void BtnBackOnAction(ActionEvent event)throws IOException {
        PagePayment.getChildren().clear();
        PagePayment.getChildren().add(
                FXMLLoader.load(getClass().getResource("/view/Homepage.fxml"))
        );
    }

    @FXML
    void OnTablePayment(MouseEvent event) {
        // Get the selected item from the TableView
        PaymentDto selectedPayment = TablePayment.getSelectionModel().getSelectedItem();

        // If a row is selected, populate the text fields
        if (selectedPayment != null) {
            TxtPaymentId.setText(String.valueOf(selectedPayment.getPaymentId()));
            BoxPolicy.setValue(selectedPayment.getPolicyId());
            TxtAmount.setText(selectedPayment.getAmount().toString());
            TxtMetthod.setText(selectedPayment.getPaymentMethod());
        }
    }

    private void getNextPaymentId() {
        try {
            String nextPaymentId = paymentModel.getNextPaymentId(); // Call the new method in PaymentModel
            TxtPaymentId.setText(nextPaymentId);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while generating Payment ID: " + e.getMessage()).show();
        }
    }

    private void setupEnterKeyListeners() {
        // When Enter is pressed in Payment ID field, focus moves to Policy ComboBox
        TxtPaymentId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                BoxPolicy.requestFocus(); // Move focus to the Policy ComboBox
            }
        });

        // When Enter is pressed in Policy ComboBox, focus moves to Amount field
        BoxPolicy.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtAmount.requestFocus(); // Move focus to the Amount field
            }
        });

        // When Enter is pressed in Amount field, focus moves to Payment Method field
        TxtAmount.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtMetthod.requestFocus(); // Move focus to the Payment Method field
            }
        });

        // When Enter is pressed in Payment Method field, trigger Save button action
    }

    @FXML
    void BtnSearchOnAction(ActionEvent event) {
        String searchInput = TxtSearch.getText();

        // Reset the border color for the search field
        TxtSearch.setStyle(TxtSearch.getStyle() + ";-fx-border-color: #7367F0;");

        if (searchInput.isEmpty()) {
            // Show a warning if the search field is empty
            new Alert(Alert.AlertType.WARNING, "Please enter a Payment ID to search!").show();
            TxtSearch.setStyle(TxtSearch.getStyle() + ";-fx-border-color: red;");
            return;
        }

        try {
            // Parse the entered Payment ID
            int paymentId = Integer.parseInt(searchInput);

            // Use the paymentModel to search for the payment by its ID
            PaymentDto payment = paymentModel.getPaymentById(paymentId);

            if (payment != null) {
                // Populate the fields with the payment details
                TxtPaymentId.setText(String.valueOf(payment.getPaymentId()));
                BoxPolicy.setValue(payment.getPolicyId());
                TxtAmount.setText(payment.getAmount().toString());
                TxtMetthod.setText(payment.getPaymentMethod());

                // Optionally, highlight the matching row in the TableView
                ObservableList<PaymentDto> paymentList = TablePayment.getItems();
                for (PaymentDto item : paymentList) {
                    if (item.getPaymentId() == paymentId) {
                        TablePayment.getSelectionModel().select(item);
                        TablePayment.scrollTo(item);
                        break;
                    }
                }
            } else {
                // Show an error if no payment is found
                new Alert(Alert.AlertType.ERROR, "Payment not found!").show();
            }
        } catch (NumberFormatException e) {
            // Show an error if the input is not a valid integer
            new Alert(Alert.AlertType.ERROR, "Please enter a valid Payment ID.").show();
            TxtSearch.setStyle(TxtSearch.getStyle() + ";-fx-border-color: red;");
        } catch (SQLException e) {
            // Show an error if a database issue occurs
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
        }
    }
    @FXML
    private void BoxPolicyOnAction(ActionEvent event) {
        // Handle ComboBox action here
        Integer selectedPolicyId = BoxPolicy.getValue();
        System.out.println("Selected Policy ID: " + selectedPolicyId);
    }

}
