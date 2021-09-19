package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.Customer;
import main.java.CustomerDbUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInSceneController implements Initializable {

    private CustomerDbUtil customerDbUtil;

    @FXML
    Button logInButton, registerButton;

    @FXML
    TextField emailTextField;

    @FXML
    PasswordField passwordField;

    @FXML
    Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerDbUtil = new CustomerDbUtil();
    }

    public void logIn(ActionEvent event) {

        // get customer
        Customer customer = customerDbUtil.logIn(emailTextField.getText(),
                passwordField.getText());

        // if customer exists, log them in
        if (customer!=null) {

            try {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/BankingScene.fxml"));
                Parent root = loader.load();

            BankingSceneController bankingSceneController=loader.getController();

            //pass customer to BankingSceneController
            bankingSceneController.setCustomer(customer);

            Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            errorLabel.setText("Invalid credentials!");
        }
    }

    public void switchToRegisterScene(ActionEvent event) {
        try {

            //Parent root =FXMLLoader.load(getClass().getResource("../resources/RegisterScene.fxml")); TODO: REMOVE

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/RegisterScene.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
