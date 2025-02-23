package edu.example.aia.aia_management.Controller;

//import edu.example.aia.aia_management.Model.PaymentDetailsModel;
import edu.example.aia.aia_management.bo.BOFactory;
import edu.example.aia.aia_management.bo.custom.PaymentDetailsBo;
import edu.example.aia.aia_management.bo.custom.impl.PaymentDetailsBOImpl;
import edu.example.aia.aia_management.dto.PaymentDetailsDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PaymentDetailsController {

    @FXML
    private ComboBox<String> BoxMonth;

    @FXML
    private Button BtnBack;

    @FXML
    private Button BtnDelect;

    @FXML
    private TableColumn<PaymentDetailsDto, Integer> ColPaymentId;

    @FXML
    private TableColumn<PaymentDetailsDto, Integer> ColPolicy;

    @FXML
    private TableColumn<PaymentDetailsDto, String> ColMonth;

    @FXML
    private TableColumn<PaymentDetailsDto, String> ColStatus;

    @FXML
    private TableView<PaymentDetailsDto> TablePayment;

    @FXML
    private TextField TxtPaymentId;

    @FXML
    private TextField TxtPolicy;

    @FXML
    private TextField TxtStatus;

    @FXML
    private AnchorPane pagePayment;
    PaymentDetailsBo paymentDetailsBo = (PaymentDetailsBo) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENTDETAIL);
    // Initialize the TableView columns and load data
    @FXML
    public void initialize() {
        ColPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        ColPolicy.setCellValueFactory(new PropertyValueFactory<>("policyId"));
        ColMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        ColStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        try {
            // Load all months into the ComboBox
            loadMonthsIntoComboBox();
            loadPaymentDetails();
        } catch (SQLException e) {
            showError("Failed to load payment details.");
            e.printStackTrace();
        }
    }

    // Method to load all available months into the ComboBox
    private void loadMonthsIntoComboBox() {
        BoxMonth.setItems(FXCollections.observableArrayList(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        ));
    }

    // Method to handle the filtering by month
    @FXML
    void BoxMonthOnAction(ActionEvent event) {
        String selectedMonth = BoxMonth.getValue();
        if (selectedMonth != null && !selectedMonth.isEmpty()) {
            List<PaymentDetailsDto> filteredDetails = paymentDetailsBo.getPaymentDetailsByMonth(selectedMonth);
            ObservableList<PaymentDetailsDto> observableList = FXCollections.observableArrayList(filteredDetails);
            TablePayment.setItems(observableList);
        }
    }

    // Method to load payment details into the TableView
    private void loadPaymentDetails() throws SQLException {
        List<PaymentDetailsDto> paymentDetails = paymentDetailsBo.getAllPaymentDetails();
        ObservableList<PaymentDetailsDto> observableList = FXCollections.observableArrayList(paymentDetails);
        TablePayment.setItems(observableList);
    }

    // Method to handle deletion of selected payment detail
    @FXML
    void BtnDeleteOnAction(ActionEvent event) {
        PaymentDetailsDto selectedDetail = TablePayment.getSelectionModel().getSelectedItem();
        if (selectedDetail != null) {
            try {
                boolean isDeleted = paymentDetailsBo.deletePaymentDetail(selectedDetail.getPaymentId());
                if (isDeleted) {
                    showInfo("Payment detail deleted successfully.");
                    loadPaymentDetails(); // Refresh the table
                } else {
                    showError("Failed to delete payment detail.");
                }
            } catch (SQLException e) {
                showError("An error occurred while deleting the payment detail.");
                e.printStackTrace();
            }
        } else {
            showError("Please select a payment detail to delete.");
        }
    }

    // Method to show error messages
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }

    // Method to show information messages
    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    // Method for handling the back button action
    @FXML
    void BtnBackOnAction(ActionEvent event)throws IOException {
        pagePayment.getChildren().clear();
        pagePayment.getChildren().add(FXMLLoader.load(getClass().getResource("/view/payment.fxml")));    }

    // Method to handle table row click
    @FXML
    void OnTablePayment(MouseEvent event) {
        PaymentDetailsDto selectedDetail = TablePayment.getSelectionModel().getSelectedItem();
        if (selectedDetail != null) {
            TxtPaymentId.setText(String.valueOf(selectedDetail.getPaymentId()));
            TxtPolicy.setText(String.valueOf(selectedDetail.getPolicyId()));
            TxtStatus.setText(selectedDetail.getStatus());
        }
    }
}
