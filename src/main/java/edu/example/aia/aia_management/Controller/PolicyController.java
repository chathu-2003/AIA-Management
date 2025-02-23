package edu.example.aia.aia_management.Controller;

import edu.example.aia.aia_management.Model.PolicyModel;
import edu.example.aia.aia_management.bo.BOFactory;
import edu.example.aia.aia_management.bo.custom.ClaimBO;
import edu.example.aia.aia_management.bo.custom.PolicyBo;
import edu.example.aia.aia_management.db.DBConection;
import edu.example.aia.aia_management.dto.PolicyDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class PolicyController {

    @FXML
    private TextField TxtSearchPolicyId;
    @FXML
    private Button BtnSearch;
    @FXML
    private Button BtnSupportTicket;
    @FXML
    private TableView<PolicyDto> managertable;
    @FXML
    private TableColumn<PolicyDto, Integer> mangeridcol;
    @FXML
    private TableColumn<PolicyDto, BigDecimal> preimumcol;
    @FXML
    private TableColumn<PolicyDto, Date> startcol;
    @FXML
    private TableColumn<PolicyDto, Date> enddatecol;
    @FXML
    private TableColumn<PolicyDto, Integer> undercol;
    @FXML
    private TableColumn<PolicyDto, Integer> policyidcol;
    @FXML
    private TableColumn<PolicyDto, String> policynumcol;
    @FXML
    private TableColumn<PolicyDto, String> coveragecol;
    @FXML
    private Button Btninsurance;
    @FXML
    private Button btnpayment;
    @FXML
    private TextField covaragetxt;
    @FXML
    private Button delect;
    @FXML
    private TextField enddatetxt;
    @FXML
    private Button logout;
    @FXML
    private TextField mtxt;
    @FXML
    private AnchorPane policy;
    @FXML
    private TextField policynumtxt;
    @FXML
    private TextField policytxt;
    @FXML
    private TextField premiumtxt;
    @FXML
    private Button save;
    @FXML
    private TextField startdatetxt;
    @FXML
    private TextField underwtxt;
    @FXML
    private Button update;

    private PolicyModel policyModel = new PolicyModel();

    public PolicyBo policyBo=(PolicyBo) BOFactory.getInstance().getBO(BOFactory.BOType.POLICY);

    @FXML
    public void loadTable() {
        try {
            ObservableList<PolicyDto> policyList = FXCollections.observableArrayList(policyModel.getAllPolicies());
            if (policyList.isEmpty()) {
                new Alert(Alert.AlertType.INFORMATION, "No data available.").show();
            }
            managertable.setItems(policyList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
        }
    }

    @FXML
    public void initialize() {
        // Set cell value factories for each column to map to PolicyDto fields
        policyidcol.setCellValueFactory(new PropertyValueFactory<>("policyId"));
        policynumcol.setCellValueFactory(new PropertyValueFactory<>("policyNumber"));
        startcol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        enddatecol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        preimumcol.setCellValueFactory(new PropertyValueFactory<>("premium"));
        coveragecol.setCellValueFactory(new PropertyValueFactory<>("coverage"));
        mangeridcol.setCellValueFactory(new PropertyValueFactory<>("managerId"));
        undercol.setCellValueFactory(new PropertyValueFactory<>("underWriterId"));

        // Load table data
        loadTable();

        getNextPolicyId();

        setupEnterKeyListeners();
    }

    @FXML
    void delectOnAction(ActionEvent event) {
        try {
            int policyId = Integer.parseInt(policytxt.getText());
            boolean isDeleted = policyModel.deletePolicy(policyId);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Policy Deleted Successfully!").show();
                loadTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Delete Policy!").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid Policy ID format.").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void logoutOnAction(ActionEvent event) throws IOException {
        policy.getChildren().clear();
        policy.getChildren().add(
                FXMLLoader.load(getClass().getResource("/view/Homepage.fxml"))
        );
    }

    @FXML
    void saveOnAction(ActionEvent event) {
        // Validate all fields
        if (isValidInput()) {
            String policyId = policytxt.getText();
            String policyNumber = policynumtxt.getText();
            String startDateStr = startdatetxt.getText();
            String endDateStr = enddatetxt.getText();

            Date startDate = Date.valueOf(startDateStr);
            Date endDate = Date.valueOf(endDateStr);

            BigDecimal premium;
            try {
                premium = new BigDecimal(premiumtxt.getText());
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Invalid premium amount.").show();
                return;
            }

            String coverage = covaragetxt.getText();
            int managerId;
            int underWriterId;

            try {
                managerId = Integer.parseInt(mtxt.getText());
                underWriterId = Integer.parseInt(underwtxt.getText());
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Manager ID and Underwriter ID must be numeric.").show();
                return;
            }

            try {
                PolicyDto policyDto = new PolicyDto(
                        Integer.parseInt(policyId),
                        policyNumber,
                        startDate,
                        endDate,
                        premium,
                        coverage,
                        managerId,
                        underWriterId
                );

                boolean isSaved = policyModel.savePolicy(policyDto);
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Policy Saved Successfully!").show();
                    loadTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Save Policy!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();

            }
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        try {
            if (isValidInput()) {
                int policyId = Integer.parseInt(policytxt.getText());
                String policyNumber = policynumtxt.getText();
                Date startDate = Date.valueOf(startdatetxt.getText());
                Date endDate = Date.valueOf(enddatetxt.getText());
                BigDecimal premium = new BigDecimal(premiumtxt.getText());
                String coverage = covaragetxt.getText();
                int managerId = Integer.parseInt(mtxt.getText());
                int underWriterId = Integer.parseInt(underwtxt.getText());

                PolicyDto policyDto = new PolicyDto(
                        policyId, policyNumber, startDate, endDate, premium, coverage, managerId, underWriterId
                );

                boolean isUpdated = policyModel.updatePolicy(policyDto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Policy Updated Successfully!").show();
                    loadTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Update Policy!").show();
                }
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid input format.").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();

        }
    }

    @FXML
    void BtninsuranceOnAction(ActionEvent event) throws IOException {
        policy.getChildren().clear();
        policy.getChildren().add(
                FXMLLoader.load(getClass().getResource("/View/insurancetype.fxml"))
        );
    }

    @FXML
    void btnpaymentOnAction(ActionEvent event) throws IOException {
        policy.getChildren().clear();
        policy.getChildren().add(
                FXMLLoader.load(getClass().getResource("/View/Payment.fxml"))
        );
    }

    @FXML
    void BtnSupportTicketOnAction(ActionEvent event)throws IOException {
        policy.getChildren().clear();
        policy.getChildren().add(
                FXMLLoader.load(getClass().getResource("/View/supportticket.fxml"))
        );
    }

    @FXML
    void Onmanagertable(MouseEvent event) {
        // Get the selected item from the TableView
        PolicyDto selectedPolicy = managertable.getSelectionModel().getSelectedItem();

        // If a row is selected, populate the text fields
        if (selectedPolicy != null) {
            policytxt.setText(String.valueOf(selectedPolicy.getPolicyId()));
            policynumtxt.setText(selectedPolicy.getPolicyNumber());
            startdatetxt.setText(selectedPolicy.getStartDate().toString());
            enddatetxt.setText(selectedPolicy.getEndDate().toString());
            premiumtxt.setText(selectedPolicy.getPremium().toString());
            covaragetxt.setText(selectedPolicy.getCoverage());
            mtxt.setText(String.valueOf(selectedPolicy.getManagerId()));
            underwtxt.setText(String.valueOf(selectedPolicy.getUnderWriterId()));
        }
    }

    private void getNextPolicyId() {
        try {
            int nextPolicyId = policyModel.getNextPolicyId(); // Corrected to use policyModel
            policytxt.setText(String.valueOf(nextPolicyId));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error while generating Policy ID: " + e.getMessage()).show();

        }
    }

    // Validation for the fields
    private boolean isValidInput() {
        if (policytxt.getText().isEmpty() || policynumtxt.getText().isEmpty() || startdatetxt.getText().isEmpty() ||
                enddatetxt.getText().isEmpty() || premiumtxt.getText().isEmpty() || covaragetxt.getText().isEmpty() ||
                mtxt.getText().isEmpty() || underwtxt.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "All fields must be filled out.").show();
            return false;
        }

        // Validate date format
        if (!startdatetxt.getText().matches("\\d{4}-\\d{2}-\\d{2}") || !enddatetxt.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
            new Alert(Alert.AlertType.ERROR, "Please enter dates in the format YYYY-MM-DD.").show();
            return false;
        }

        // Validate numeric fields
        try {
            new BigDecimal(premiumtxt.getText());
        } catch (NumberFormatException e) {
            premiumtxt.setStyle(premiumtxt.getStyle() + ";-fx-border-color: red;");
            return false;
        }

        try {
            Integer.parseInt(mtxt.getText());
            Integer.parseInt(underwtxt.getText());
        } catch (NumberFormatException e) {
            underwtxt.setStyle(underwtxt.getStyle() + ";-fx-border-color: red;");
            return false;
        }

        return true;
    }
    // Method to set up Enter key navigation between TextFields
    private void setupEnterKeyListeners() {
        policytxt.setOnKeyPressed(this::handleEnterKeyNavigation);
        policynumtxt.setOnKeyPressed(this::handleEnterKeyNavigation);
        startdatetxt.setOnKeyPressed(this::handleEnterKeyNavigation);
        enddatetxt.setOnKeyPressed(this::handleEnterKeyNavigation);
        premiumtxt.setOnKeyPressed(this::handleEnterKeyNavigation);
        covaragetxt.setOnKeyPressed(this::handleEnterKeyNavigation);
        mtxt.setOnKeyPressed(this::handleEnterKeyNavigation);
        underwtxt.setOnKeyPressed(this::handleEnterKeyNavigation);
    }

    // Handle Enter key navigation between fields
    private void handleEnterKeyNavigation(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (event.getSource() == policytxt) {
                policynumtxt.requestFocus();
            } else if (event.getSource() == policynumtxt) {
                startdatetxt.requestFocus();
            } else if (event.getSource() == startdatetxt) {
                enddatetxt.requestFocus();
            } else if (event.getSource() == enddatetxt) {
                premiumtxt.requestFocus();
            } else if (event.getSource() == premiumtxt) {
                covaragetxt.requestFocus();
            } else if (event.getSource() == covaragetxt) {
                mtxt.requestFocus();
            } else if (event.getSource() == mtxt) {
                underwtxt.requestFocus();
            } else if (event.getSource() == underwtxt) {
                save.fire(); // Trigger the save button's action when the last field is reached
            }
        }
    }
    @FXML
    void BtnGenarateOnAction(ActionEvent event) {
        Connection connection = null;
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/policyy.jrxml"));
            connection = DBConection.getInstance().getConnection();
            //<key/index,value> eka tamai string object widiyata danne
            Map<String,Object> parameters = new HashMap<>();
            parameters.put("today", LocalDate.now().toString());
            parameters.put("time", LocalTime.now().toString());
            //map ekka wada karana kota duplicate karanne naaa

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            JasperViewer.viewReport(jasperPrint); // Viewing the report
        } catch (JRException | SQLException e) {
            e.printStackTrace(); // Print the exception stack trace

        }
    }
    @FXML
    void BtnSearchOnAction(ActionEvent event) {
        // Clear any previous error styling
        TxtSearchPolicyId.setStyle(TxtSearchPolicyId.getStyle() + ";-fx-border-color: #7367F0;");

        // Validate if search input is empty
        String searchInput = TxtSearchPolicyId.getText();
        if (searchInput.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter a Policy ID to search!").show();
            TxtSearchPolicyId.setStyle(TxtSearchPolicyId.getStyle() + ";-fx-border-color: red;");
            return;
        }

        try {
            int policyId = Integer.parseInt(searchInput);
            PolicyDto foundPolicy = policyModel.searchPolicyById(policyId);

            if (foundPolicy != null) {
                // Populate the fields with policy details
                policytxt.setText(String.valueOf(foundPolicy.getPolicyId()));
                policynumtxt.setText(foundPolicy.getPolicyNumber());
                startdatetxt.setText(foundPolicy.getStartDate().toString());
                enddatetxt.setText(foundPolicy.getEndDate().toString());
                premiumtxt.setText(foundPolicy.getPremium().toString());
                covaragetxt.setText(foundPolicy.getCoverage());
                mtxt.setText(String.valueOf(foundPolicy.getManagerId()));
                underwtxt.setText(String.valueOf(foundPolicy.getUnderWriterId()));
            } else {
                new Alert(Alert.AlertType.ERROR, "Policy not found!").show();
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid Policy ID format. Please enter a numeric value.").show();
            TxtSearchPolicyId.setStyle(TxtSearchPolicyId.getStyle() + ";-fx-border-color: red;");
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
        }
    }
}




