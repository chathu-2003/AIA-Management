package edu.example.aia.aia_management.Controller;

//import edu.example.aia.aia_management.edu.example.aia.aia_management.Model.CustomerModel;
import edu.example.aia.aia_management.bo.BOFactory;
import edu.example.aia.aia_management.bo.custom.CustomerBo;
import edu.example.aia.aia_management.db.DBConection;
import edu.example.aia.aia_management.dto.CustomerDto;
import edu.example.aia.aia_management.dto.InsurancetypeDto;
import edu.example.aia.aia_management.tm.CustomerTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerDetailsController {

    @FXML
    private Button BtnSendMail;
    @FXML
    private Button BtnSearch;
    @FXML
    private TextField TxtSearch;;
    @FXML
    private Button BtnBack, BtnDelect, BtnSave, BtnUpdate;
    @FXML
    private Button Notification;
    @FXML
    private AnchorPane PageCustomer;
    @FXML
    private TableColumn<CustomerDto, Integer> ColCustomer, ColManager;
    @FXML
    private TableColumn<CustomerDto, String> ColName, ColAddress, ColPhone, ColEmail;
    @FXML
    private TableView<CustomerDto> TableCustomer;
    @FXML
    private TextField TxtAddress, TxtCustomer, TxtEmail, TxtManger, TxtName, TxtPhone;
//    private final CustomerModel customerModel = new CustomerModel();

    public CustomerBo customerBo=(CustomerBo) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);

    @FXML
    public void initialize() {
        ColCustomer.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        ColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ColAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        ColPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        ColEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        ColManager.setCellValueFactory(new PropertyValueFactory<>("managerId"));

        loadCustomerData();
        getNextCustomerID();
        setupEnterKeyListeners();
    }

    private void loadCustomerData() {
        try {
            List<CustomerDto> customerList = customerBo.getAllCustomers();
            ObservableList<CustomerDto> customerObservableList = FXCollections.observableArrayList(customerList);
            TableCustomer.setItems(customerObservableList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customer data: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void BtnSaveOnAction(ActionEvent event) {
        if (validateFields()) {
            try {
                CustomerDto customer = new CustomerDto(
                        Integer.parseInt(TxtCustomer.getText()),
                        TxtName.getText(),
                        TxtAddress.getText(),
                        TxtPhone.getText(),
                        TxtEmail.getText(),
                        Integer.parseInt(TxtManger.getText())
                );

                if (customerBo.saveCustomer(customer)) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer saved successfully").show();
                    loadCustomerData();
                    clearForm();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save customer").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Error while saving customer: " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void BtnDelectOnAction(ActionEvent event) {
        if (validateFields()) {
            try {
                int customerId = Integer.parseInt(TxtCustomer.getText());

                if (customerBo.deleteCustomer(customerId)) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer deleted successfully").show();
                    loadCustomerData();
                    clearForm();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete customer").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Error while deleting customer: " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void BtnUpdateOnAction(ActionEvent event) {
        if (validateFields()) {
            try {
                CustomerDto customer = new CustomerDto(
                        Integer.parseInt(TxtCustomer.getText()),
                        TxtName.getText(),
                        TxtAddress.getText(),
                        TxtPhone.getText(),
                        TxtEmail.getText(),
                        Integer.parseInt(TxtManger.getText())
                );

                if (customerBo.updateCustomer(customer)) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer updated successfully").show();
                    loadCustomerData();
                    clearForm();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update customer").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Error while updating customer: " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    void OnClickTable(MouseEvent event) {
        CustomerDto selectedCustomer = TableCustomer.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            TxtCustomer.setText(String.valueOf(selectedCustomer.getCustomerId()));
            TxtName.setText(selectedCustomer.getName());
            TxtAddress.setText(selectedCustomer.getAddress());
            TxtPhone.setText(selectedCustomer.getPhone());
            TxtEmail.setText(selectedCustomer.getEmail());
            TxtManger.setText(String.valueOf(selectedCustomer.getManagerId()));
        }
    }

    private void clearForm() {
        TxtCustomer.clear();
        TxtName.clear();
        TxtAddress.clear();
        TxtPhone.clear();
        TxtEmail.clear();
        TxtManger.clear();
    }

    @FXML
    void BtnBackOnAction(ActionEvent event) throws IOException {
        PageCustomer.getChildren().clear();
        PageCustomer.getChildren().add(
                FXMLLoader.load(getClass().getResource("/view/Homepage.fxml"))
        );
    }

    private void getNextCustomerID() {
        try {
            String nextCustomerId = String.valueOf(customerBo.getNextCustomer()); // Call the new method in CustomerModel
            TxtCustomer.setText(nextCustomerId);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while generating Customer ID: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void navigatetoNotification(ActionEvent event) throws IOException {
        PageCustomer.getChildren().clear();
        PageCustomer.getChildren().add(
                FXMLLoader.load(getClass().getResource("/view/notification.fxml"))
        );
    }

    // Validation method for all fields
    private boolean validateFields() {
        // Validate Customer ID (must be an integer)
        if (TxtCustomer.getText().isEmpty()) {
            showAlert("Error", "Customer ID cannot be empty.");
            return false;
        }
        try {
            Integer.parseInt(TxtCustomer.getText());
        } catch (NumberFormatException e) {
            TxtCustomer.setStyle(TxtCustomer.getStyle() + ";-fx-border-color: red;");
            return false;
        }

        // Validate Name (cannot be empty)
        if (TxtName.getText().isEmpty()) {
            showAlert("Error", "Name cannot be empty.");
            return false;
        }

        // Validate Address (cannot be empty)
        if (TxtAddress.getText().isEmpty()) {
            showAlert("Error", "Address cannot be empty.");
            return false;
        }

        // Validate Phone (must be a valid phone number)
        if (TxtPhone.getText().isEmpty()) {
            showAlert("Error", "Phone number cannot be empty.");
            return false;
        }
        if (!isValidPhoneNumber(TxtPhone.getText())) {
            showAlert("Error", "Phone number must be in a valid format.");
            return false;
        }

        // Validate Email (must be in valid email format)
        if (TxtEmail.getText().isEmpty()) {
            showAlert("Error", "Email cannot be empty.");
            return false;
        }
        if (!isValidEmail(TxtEmail.getText())) {
            showAlert("Error", "Email must be in a valid format.");
            return false;
        }

        // Validate Manager ID (must be an integer)
        if (TxtManger.getText().isEmpty()) {
            showAlert("Error", "Manager ID cannot be empty.");
            return false;
        }
        try {
            Integer.parseInt(TxtManger.getText());
        } catch (NumberFormatException e) {
            TxtManger.setStyle(TxtManger.getStyle() + ";-fx-border-color: red;");
            return false;
        }

        return true;
    }

    // Show alert with custom message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Validate phone number (simple regex for phone format like (xxx) xxx-xxxx or xxxxxxxxxx)
    private boolean isValidPhoneNumber(String phone) {
        String regex = "^\\(?(\\d{3})\\)?[- ]?\\d{3}[- ]?\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    // Validate email address using regex
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private void setupEnterKeyListeners() {
        TxtCustomer.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtName.requestFocus(); // Move focus to the Name field
            }
        });

        TxtName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtAddress.requestFocus(); // Move focus to the Address field
            }
        });

        TxtAddress.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtPhone.requestFocus(); // Move focus to the Phone field
            }
        });

        TxtPhone.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtEmail.requestFocus(); // Move focus to the Email field
            }
        });

        TxtEmail.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtManger.requestFocus(); // Move focus to the Manager ID field
            }
        });

        TxtManger.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                BtnSave.fire(); // Trigger Save button's action
            }
        });
    }
    @FXML
    void BtnGenarateOnAction(ActionEvent event) {
        Connection connection = null;
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/customer.jrxml"));
            connection = DBConection.getInstance().getConnection();
            //<key/index,value> eka tamai string object widiyata danne
            Map<String,Object> parameters = new HashMap<>();
            parameters.put("today", LocalDate.now().toString());
            //map ekka wada karana kota duplicate karanne naaa

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            JasperViewer.viewReport(jasperPrint); // Viewing the report
        } catch (JRException | SQLException e) {
            e.printStackTrace(); // Print the exception stack trace
        }
    }
    @FXML
    void BtnSearchOnAction(ActionEvent event) {
        String searchInput = TxtSearch.getText().trim();

        // Reset the border color for the search field
        TxtSearch.setStyle(TxtSearch.getStyle() + ";-fx-border-color: #7367F0;");

        if (searchInput.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter a Customer ID to search!").show();
            TxtSearch.setStyle(TxtSearch.getStyle() + ";-fx-border-color: red;");
            return;
        }

        try {
            // Parse the entered Customer ID
            int customerId = Integer.parseInt(searchInput);

            // Search for the customer by ID
            CustomerDto customer = customerBo.searchCustomerById(customerId);

            if (customer != null) {
                // Populate the fields with the customer details
                TxtCustomer.setText(String.valueOf(customer.getCustomerId()));
                TxtName.setText(customer.getName());
                TxtAddress.setText(customer.getAddress());
                TxtPhone.setText(customer.getPhone());
                TxtEmail.setText(customer.getEmail());
                TxtManger.setText(String.valueOf(customer.getManagerId()));

                // Highlight the matching customer in the TableView
                ObservableList<CustomerDto> customerList = TableCustomer.getItems();
                for (CustomerDto item : customerList) {
                    if (item.getCustomerId() == customerId) {
                        TableCustomer.getSelectionModel().select(item);
                        TableCustomer.scrollTo(item);
                        break;
                    }
                }
            } else {
                // Show an error if no customer is found
                new Alert(Alert.AlertType.ERROR, "Customer not found!").show();
            }
        } catch (NumberFormatException e) {
            // Show an error if the input is not a valid integer
            new Alert(Alert.AlertType.ERROR, "Please enter a valid Customer ID.").show();
            TxtSearch.setStyle(TxtSearch.getStyle() + ";-fx-border-color: red;");
        } catch (SQLException e) {
            // Show an error if a database issue occurs
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
        }
    }
    @FXML
    void BtnSendMailOnAction(ActionEvent event) {
        CustomerDto selectedItem = TableCustomer.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a customer!").show();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Email.fxml"));
            Parent load = loader.load();

            SendEmailController sendMailController = loader.getController();

            // Pass the selected customer's email
            String email = ((CustomerDto) selectedItem).getCustomerEmail();
            sendMailController.setCustomerEmail(email);

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Send email");

            // Set window as modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // Get the window of the current scene
            Window underWindow = ((Node) event.getSource()).getScene().getWindow();
            stage.initOwner(underWindow);

            stage.showAndWait();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load email UI!").show();
            e.printStackTrace();
        }
    }
}

