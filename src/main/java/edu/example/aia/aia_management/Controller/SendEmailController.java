package edu.example.aia.aia_management.Controller;

//import edu.example.aia.aia_management.edu.example.aia.aia_management.Model.CustomerModel;
import edu.example.aia.aia_management.Model.CustomerModel;
import edu.example.aia.aia_management.dto.CustomerDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmailController {

    @FXML
    private Button search;

    @FXML
    private Button send;

    @FXML
    private TextField txtCustomerID;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSubject;

    @FXML
    private TextField txtmail;

    @FXML
    void searchOnAction(ActionEvent event) {
        String customerId = txtCustomerID.getText();

        // Check if the Customer ID field is empty
        if (customerId.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter a Customer ID").show();
            return;
        }

        try {
            // Search for the customer using CustomerModel
            CustomerDto customerdto = CustomerModel.searchCustomerById(Integer.parseInt(customerId));

            if (customerdto != null) {
                // Populate the fields with customer details
                txtName.setText(customerdto.getCustomerName());
                txtmail.setText(customerdto.getCustomerEmail());
            } else {
                // Show information alert if the customer is not found
                new Alert(Alert.AlertType.INFORMATION, "No customer found with the given ID").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load customer details").show();
        }
    }

    @FXML
    void sendOnAction(ActionEvent event) {
        final String FROM = "Suranjik1980@gmail.com";

        String subject = txtSubject.getText();
        String body = txtDescription.getText();

        if (subject.isEmpty() || body.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter a subject and body").show();
            return;
        }
        System.out.println(FROM+""+ txtmail.getText()+""+ subject+""+ body);
        sendEmailWithGmail(FROM, txtmail.getText(), subject, body);
    }

    private void sendEmailWithGmail(String from, String to, String subject, String body) {
        String PASSWORD = "uxuu nsth ubgs wgjs";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, PASSWORD);
            }
        });


        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);

            new Alert(Alert.AlertType.INFORMATION, "Email sent successfully").show();
        } catch (MessagingException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to send email").show();
        }
    }

    public void setCustomerEmail(String email) {
    }
}
