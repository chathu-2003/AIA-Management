package edu.example.aia.aia_management.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HomeController {

    @FXML
    private Button BtnCommision;

    @FXML
    private Button BtnClaim;

    @FXML
    private Button FeedBack;

    @FXML
    private Button BtnCustomer;

    @FXML
    private AnchorPane homepage;

    @FXML
    private Button logout;

    @FXML
    private Button manager;

    @FXML
    private Button policy;

    @FXML
    void logoutOnAction(ActionEvent event)throws IOException {
        homepage.getChildren().clear();
        homepage.getChildren().add(
                FXMLLoader.load(getClass().getResource("/view/loginpage.fxml"))
        );
    }

    @FXML
    void navigatetomanager(ActionEvent event)throws IOException {
        homepage.getChildren().clear();
        homepage.getChildren().add(
                FXMLLoader.load(getClass().getResource("/view/manager.fxml"))
        );
    }

    @FXML
    void navigatetopolicy(ActionEvent event)throws IOException {
        homepage.getChildren().clear();
        homepage.getChildren().add(
                FXMLLoader.load(getClass().getResource("/view/policy.fxml"))
        );
    }
    @FXML
    void navigatetoFeedBack(ActionEvent event)throws IOException {
        homepage.getChildren().clear();
        homepage.getChildren().add(
                FXMLLoader.load(getClass().getResource("/view/feedback.fxml"))
        );
    }
    @FXML
    void navigatetoBtnCustomer(ActionEvent event)throws IOException {
        homepage.getChildren().clear();
        homepage.getChildren().add(
                FXMLLoader.load(getClass().getResource("/view/customer.fxml"))
        );
    }
    @FXML
    void navigatetoBtnClaim(ActionEvent event)throws IOException {
        homepage.getChildren().clear();
        homepage.getChildren().add(
                FXMLLoader.load(getClass().getResource("/view/claim.fxml"))
        );
    }
    @FXML
    void navigatetoBtnCommision(ActionEvent event)throws IOException {
        homepage.getChildren().clear();
        homepage.getChildren().add(
                FXMLLoader.load(getClass().getResource("/view/commission.fxml"))
        );
    }

    @FXML
    void navigatetoBtnUserpolicy(ActionEvent event)throws IOException {
        homepage.getChildren().clear();
        homepage.getChildren().add(
                FXMLLoader.load(getClass().getResource("/view/userpolicydetails.fxml"))
        );
    }
}
