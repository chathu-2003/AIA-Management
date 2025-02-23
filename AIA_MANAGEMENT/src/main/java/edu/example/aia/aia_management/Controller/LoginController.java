package edu.example.aia.aia_management.Controller;

import edu.example.aia.aia_management.Model.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private Button loginbutton;

    @FXML
    private AnchorPane loginpage;

    @FXML
    private PasswordField passwordtext;

    @FXML
    private Button signup;

    @FXML
    private TextField usernametext;

    private UserModel userModel = new UserModel();

    @FXML
    public void initialize() {
        setupEnterKeyListeners();
    }

    @FXML
    void loginbuttonOnAction(ActionEvent event) {
        String username = usernametext.getText().trim();
        String password = passwordtext.getText().trim();

        // Validate input fields
        if (username.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Username cannot be empty!").show();
            usernametext.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Password cannot be empty!").show();
            passwordtext.requestFocus();
            return;
        }

        try {
            // Fetch user details from the model
            String isUserName = userModel.getUserName(username);
            if (isUserName == null) {
                new Alert(Alert.AlertType.ERROR, "Username not found!").show();
                return;
            }

            String userId = userModel.getUserId(isUserName);
            if (userId == null) {
                new Alert(Alert.AlertType.ERROR, "Error retrieving user ID!").show();
                return;
            }

            String storedPassword = userModel.getPassword(userId);
            if (storedPassword == null) {
                new Alert(Alert.AlertType.ERROR, "Error retrieving password!").show();
                return;
            }

            // Validate username and password
            if (isUserName.equals(username) && storedPassword.equals(password)) {
                // Successful login
                loginpage.getChildren().clear();
                loginpage.getChildren().add(FXMLLoader.load(getClass().getResource("/view/HomePage.fxml")));
            } else {
                // Invalid credentials
                new Alert(Alert.AlertType.ERROR, "Incorrect username or password!").show();
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while processing login!").show();
        }
    }

    @FXML
    void signupOnAction(ActionEvent event) {
        try {
            // Navigate to the signup page
            loginpage.getChildren().clear();
            loginpage.getChildren().add(FXMLLoader.load(getClass().getResource("/view/signuppage.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while loading the signup page!").show();
        }
    }

    // Set up Enter key navigation for login form fields
    private void setupEnterKeyListeners() {
        usernametext.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordtext.requestFocus(); // Move focus to the password field
            }
        });

        passwordtext.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginbutton.fire(); // Trigger the login button's action
            }
        });

        // Optional: Enable Enter key for the signup button
        signup.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                signup.fire(); // Trigger the signup button's action
            }
        });
    }
}
