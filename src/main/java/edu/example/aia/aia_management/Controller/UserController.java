package edu.example.aia.aia_management.Controller;

//import edu.example.aia.aia_management.edu.example.aia.aia_management.Model.UserModel;
import edu.example.aia.aia_management.bo.BOFactory;
import edu.example.aia.aia_management.bo.custom.ClaimBO;
import edu.example.aia.aia_management.bo.custom.UserBo;
import edu.example.aia.aia_management.dto.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLOutput;

public class UserController {

    @FXML
    private Button BtnLogout;

    @FXML
    private Button Btnsignup;

    @FXML
    private TextField TxtAddress;

    @FXML
    private TextField TxtEmail;

    @FXML
    private TextField TxtName;

    @FXML
    private TextField TxtPassword;

    @FXML
    private TextField TxtUser;

    @FXML
    private TextField Txtnumber;

    @FXML
    private RadioButton agreeradio;

    @FXML
    private AnchorPane signuppage;

//    private final UserModel userModel = new UserModel();

    public UserBo userBo=(UserBo) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @FXML
    public void initialize() {
        getNextUserId();
        setupEnterKeyListeners();// Initialize with the next User ID
    }

    @FXML
    void BtnLogoutOnAction(ActionEvent event) throws IOException {
        signuppage.getChildren().clear();
        signuppage.getChildren().add(
                FXMLLoader.load(getClass().getResource("/view/loginpage.fxml"))
        );
    }

    @FXML
    void BtnsignupOnAction(ActionEvent event) {
        // Perform validation checks
        if (!isValidUserId(TxtUser.getText())) {
            showAlert("Invalid User ID", "User ID must be a number.");
            return;
        }

        if (!isValidPassword(TxtPassword.getText())) {
            showAlert("Invalid Password", "Password must be at least 6 characters long.");
            return;
        }

        if (!isValidName(TxtName.getText())) {
            showAlert("Invalid Name", "Name cannot be empty and should only contain letters.");
            return;
        }

        if (!isValidEmail(TxtEmail.getText())) {
            showAlert("Invalid Email", "Please enter a valid email address.");
            return;
        }

        if (!isValidPhoneNumber(Txtnumber.getText())) {
            showAlert("Invalid Phone Number", "Phone number should be 10 digits and contain only numbers.");
            return;
        }

        if (!isValidAddress(TxtAddress.getText())) {
            showAlert("Invalid Address", "Address cannot be empty.");
            return;
        }

        if (!agreeradio.isSelected()) {
            showAlert("Agreement Required", "You must agree to the terms to sign up.");
            return;
        }

        // If all validations pass, create a UserDto object and save it
        UserDto user = new UserDto(
                Integer.parseInt(TxtUser.getText()),
                TxtPassword.getText(),
                TxtName.getText(),
                TxtEmail.getText(),
                Txtnumber.getText(),
                TxtAddress.getText()
        );

        try {
            boolean isSaved = userBo.saveUser(user);
            if (isSaved) {
                showAlert("Success", "User signed up successfully!");
            } else {
                showAlert("Error", "Failed to sign up the user.");
            }
        } catch (SQLException e) {
            showAlert("Database Error", "An error occurred while saving the user.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void agreeradioOnAction(ActionEvent event) {
        showAlert("Logout", "Thank you agreee successfully! 😊");
    }

    // Validation methods
    private boolean isValidUserId(String userId) {
        return userId.matches("\\d+");
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6; // Adjust complexity as needed
    }

    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z ]+") && !name.trim().isEmpty();
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}");
    }

    private boolean isValidAddress(String address) {
        return !address.trim().isEmpty();
    }

    // Utility method to show an alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void getNextUserId() {
        try {
            String nextUserId = String.valueOf(userBo.getNextUserId());
            TxtUser.setText(nextUserId);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while generating User ID: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupEnterKeyListeners() {
        TxtUser.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtPassword.requestFocus();
            }
        });

        TxtPassword.setOnKeyPressed(event -> {
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
                Txtnumber.requestFocus();
            }
        });

        Txtnumber.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                TxtAddress.requestFocus();
            }
        });

        TxtAddress.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                Btnsignup.fire(); // Trigger the sign-up button action
            }
        });
    }
}

