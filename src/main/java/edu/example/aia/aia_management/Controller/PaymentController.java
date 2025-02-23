package edu.example.aia.aia_management.Controller;

//import edu.example.aia.aia_management.edu.example.aia.aia_management.Model.PaymentModel;
//import edu.example.aia.aia_management.edu.example.aia.aia_management.Model.PolicyModel;
import edu.example.aia.aia_management.bo.BOFactory;
import edu.example.aia.aia_management.bo.custom.ClaimBO;
import edu.example.aia.aia_management.bo.custom.PaymentBo;
import edu.example.aia.aia_management.bo.custom.impl.PaymentBOImpl;
import edu.example.aia.aia_management.dto.PaymentDetailsDto;
import edu.example.aia.aia_management.dto.PaymentDto;
import edu.example.aia.aia_management.dto.PolicyDto;
import edu.example.aia.aia_management.dto.SavePaymentDto;
import edu.example.aia.aia_management.tm.PaymentTm;
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
import java.util.ArrayList;
import java.util.List;

public class PaymentController {

    @FXML
    private Button BtnDelect;
    @FXML
    private ComboBox<String> BoxMonth; // ComboBox for selecting month
    @FXML
    private Button BtnSearch;
    @FXML
    private TextField TxtSearch;
    @FXML
    private ComboBox<Integer> BoxPolicy;  // ComboBox for Policy
    @FXML
    private TableColumn<PaymentTm, Integer> ColAmount;
    @FXML
    private TableColumn<PaymentTm, Integer> ColPaymentId;
    @FXML
    private TableColumn<PaymentTm, String> ColPaymentMethod;
    @FXML
    private TableView<PaymentTm> TablePayment;
    @FXML
    private TextField TxtAmount;
    @FXML
    private Pane PagePayment;
    @FXML
    private TextField TxtMethod; // Corrected typo
    @FXML
    private TextField TxtPaymentId;

//    private final PaymentModel paymentModel = new PaymentModel();
//    private final PolicyModel policyModel = new PolicyModel();
    PaymentBo paymentBo = (PaymentBo) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);


    @FXML
    public void initialize() throws SQLException {
        // Set up the table columns
        ColPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        //    ColPolicyId.setCellValueFactory(new PropertyValueFactory<>("policyId"));
        ColAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        ColPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));

        // Load the payments, policies, and months
        loadPayments();
        loadPolicies();
        loadMonths(); // Ensure months are loaded into ComboBox
        getNextPaymentId();
        setupEnterKeyListeners();
    }

    private void loadPayments() {
        try {

            ArrayList<PaymentDto> paymentDtos = paymentBo.getAllPayments();
            ObservableList<PaymentTm> payments = FXCollections.observableArrayList();

            for (PaymentDto paymentDto : paymentDtos) {
                PaymentTm paymentTm = new PaymentTm(
                        paymentDto.getPaymentId(),
                   //     selectedItem,  // Use the selected policy ID here
                        paymentDto.getAmount(),
                        paymentDto.getPaymentMethod()
                );
                payments.add(paymentTm);
            }

            TablePayment.setItems(payments);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load payments").show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An unexpected error occurred").show();
        }
    }


    private void loadPolicies() throws SQLException {
        List<PolicyDto> policies = paymentBo.getAllPolicies();
        ObservableList<Integer> policyIds = FXCollections.observableArrayList();
        for (PolicyDto policy : policies) {
            policyIds.add(policy.getPolicyId());
        }
        BoxPolicy.setItems(policyIds);
    }

    private void loadMonths() {
        ObservableList<String> months = FXCollections.observableArrayList(
                "January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December"
        );
        BoxMonth.setItems(months);
    }
//PaymentModel paymentModel=new PaymentModel();
    @FXML
    void BtnSaveOnAction(ActionEvent event) {

        ArrayList<PaymentDto> paymentDtos = new ArrayList<>();
        ArrayList<PaymentDetailsDto> paymentDetailsDtos = new ArrayList<>();

        try {
            int paymentId = Integer.parseInt(TxtPaymentId.getText().trim());
            String paymentMethod = TxtMethod.getText().trim();
            BigDecimal amount = new BigDecimal(TxtAmount.getText().trim());
            Integer policyId = BoxPolicy.getSelectionModel().getSelectedItem();
            String month = BoxMonth.getSelectionModel().getSelectedItem();
            String status = amount.compareTo(BigDecimal.ZERO) > 0 ? "Payed" : "Non - Payed"; // used ternary oparetor

            if (policyId == null || month == null) {
                new Alert(Alert.AlertType.ERROR, "Please select a policy and month.").show();
                return;
            }

        //    System.out.println(paymentId + " | " + paymentMethod + " | " + amount + " | " + policyId + " | " + month + " | " + status);

            PaymentDto paymentDto = new PaymentDto(
                    paymentId,
                    amount,
                    paymentMethod
            );
            paymentDtos.add(paymentDto);

            PaymentDetailsDto paymentDetailsDto = new PaymentDetailsDto(
                    policyId,
                    paymentId,
                    month,
                    status
            );
            paymentDetailsDtos.add(paymentDetailsDto);

            SavePaymentDto savePaymentDto = new SavePaymentDto(
                    paymentDtos,
                    paymentDetailsDtos
            );

            boolean isSaved = paymentBo.savePayment(savePaymentDto);

            if(isSaved) {
                System.out.println("Wade hari");
                new Alert(Alert.AlertType.INFORMATION, "Added succuessfully").show();
             //   initialize();
            }else{
                new Alert(Alert.AlertType.WARNING, "Failed to save payment").show();
            }


        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
            new Alert(Alert.AlertType.ERROR, "Please enter valid numbers for Payment ID and Amount.").show();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, "A Sql error occurred").show();
        }
    }

    @FXML
    void BtnUpdateOnAction(ActionEvent event) {
        try {
            int paymentId = Integer.parseInt(TxtPaymentId.getText());
            int policyId = BoxPolicy.getValue();
            BigDecimal amount = new BigDecimal(TxtAmount.getText());
            String paymentMethod = TxtMethod.getText();

            PaymentDto payment = new PaymentDto(paymentId, amount, paymentMethod);
            if (paymentBo.updatePayment(payment)) {
                new Alert(Alert.AlertType.INFORMATION, "Payment updated successfully").show();
                loadPayments();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update payment").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while updating payment").show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void BtnDeleteOnAction(ActionEvent event) {
        try {
            int paymentId = Integer.parseInt(TxtPaymentId.getText());
            if (paymentBo.deletePayment(paymentId)) {
                new Alert(Alert.AlertType.INFORMATION, "Payment deleted successfully").show();
                loadPayments();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete payment").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while deleting payment").show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void BtnBackOnAction(ActionEvent event) throws IOException {
        PagePayment.getChildren().clear();
        PagePayment.getChildren().add(FXMLLoader.load(getClass().getResource("/view/Homepage.fxml")));
    }

    @FXML
    void OnTablePayment(MouseEvent event) {
        PaymentTm selectedPayment = TablePayment.getSelectionModel().getSelectedItem();
        if (selectedPayment != null) {
            TxtPaymentId.setText(String.valueOf(selectedPayment.getPaymentId()));
        //    BoxPolicy.setValue(selectedPayment.getPolicyId());
            TxtAmount.setText(selectedPayment.getAmount().toString());
            TxtMethod.setText(selectedPayment.getPaymentMethod());
        }
    }

    private void getNextPaymentId() {
        try {
            String nextPaymentId = String.valueOf(paymentBo.getNextPaymentId());
            TxtPaymentId.setText(nextPaymentId);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while generating Payment ID: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupEnterKeyListeners() {
        TxtPaymentId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) BoxPolicy.requestFocus();
        });

        BoxPolicy.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) TxtAmount.requestFocus();
        });

        TxtAmount.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) TxtMethod.requestFocus();
        });
    }

    @FXML
    void BtnSearchOnAction(ActionEvent event)throws IOException {
        PagePayment.getChildren().clear();
        PagePayment.getChildren().add(FXMLLoader.load(getClass().getResource("/view/paymentdetails.fxml")));

        // Uncomment and complete the search logic if needed
//        String searchInput = TxtSearch.getText();
//        TxtSearch.setStyle(TxtSearch.getStyle() + ";-fx-border-color: #7367F0;");
//
//        if (searchInput.isEmpty()) {
//            new Alert(Alert.AlertType.WARNING, "Please enter a Payment ID to search!").show();
//            TxtSearch.setStyle(TxtSearch.getStyle() + ";-fx-border-color: red;");
//            return;
//        }
//
//        try {
//            int paymentId = Integer.parseInt(searchInput);
//            PaymentDto payment = paymentModel.getPaymentById(paymentId);
//            if (payment != null) {
//                TxtPaymentId.setText(String.valueOf(payment.getPaymentId()));
//                TxtAmount.setText(payment.getAmount().toString());
//                TxtMethod.setText(payment.getPaymentMethod());
//
//                ObservableList<PaymentTm> paymentList = TablePayment.getItems();
//                for (PaymentDto item : paymentList) {
//                    if (item.getPaymentId() == paymentId) {
//                        TablePayment.getSelectionModel().select(item);
//                        TablePayment.scrollTo(item);
//                        break;
//                    }
//                }
//            } else {
//                new Alert(Alert.AlertType.ERROR, "Payment not found!").show();
//            }
//        } catch (NumberFormatException e) {
//            new Alert(Alert.AlertType.ERROR, "Please enter a valid Payment ID.").show();
//            TxtSearch.setStyle(TxtSearch.getStyle() + ";-fx-border-color: red;");
//        } catch (SQLException e) {
//            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
//        }
    }

    @FXML
    private void BoxPolicyOnAction(ActionEvent event) {
        Integer selectedPolicyId = BoxPolicy.getValue();
        System.out.println("Selected Policy ID: " + selectedPolicyId);
    }

    @FXML
    void BoxMonthOnAction(ActionEvent event) {
        String selectedMonth = BoxMonth.getValue();
        System.out.println("Selected Month: " + selectedMonth);
    }
}
