package edu.example.aia.aia_management;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent load = FXMLLoader.load(getClass().getResource("/view/loginpage.fxml"));


        stage.setTitle("AiA Management System");
//        stage.getIcons().add()

        Scene scene = new Scene(load);
        stage.setScene(scene);
        stage.show();

    }
}
