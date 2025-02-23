module edu.example.aia.aia_management {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires lombok;
    requires javafx.base;
    requires jdk.jdi;
    requires net.sf.jasperreports.core;
    requires java.mail;

    opens edu.example.aia.aia_management.Controller to javafx.fxml;
    opens edu.example.aia.aia_management.dto to javafx.base;
    opens edu.example.aia.aia_management.util to javafx.fxml;
    opens edu.example.aia.aia_management.Model to javafx.fxml;
    exports edu.example.aia.aia_management;

    opens edu.example.aia.aia_management;
}