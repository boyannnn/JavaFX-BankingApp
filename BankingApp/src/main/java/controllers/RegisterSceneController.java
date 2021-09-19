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
import main.java.CustomerDbUtil;
import main.java.Helper;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterSceneController implements Initializable {

    private CustomerDbUtil customerDbUtil;

    @FXML
    Button registerButton,backButton;

    @FXML
    TextField firstNameTextField, lastNameTextField, emailTextField;

    @FXML
    PasswordField passwordField;

    @FXML
    Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerDbUtil=new CustomerDbUtil();
    }

    public void setErrorLabel(String text) {
        this.errorLabel.setText(text);
    }

    public void register(ActionEvent event){
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String password = passwordField.getText();

        if (Helper.isValidEmail(email)) {
            if (Helper.isValidPassword(password)) {
                customerDbUtil.register(firstName, lastName, email, password);
                switchToLogInScene(event);
            } else {
                setErrorLabel("Password must have 8 DIGITS AND 1 LETTER, SPECIAL CHAR and NUMBER!");
            }
        } else {
            setErrorLabel("Enter a valid Email!");
        }
    }

    public void switchToLogInScene(ActionEvent event){
            try {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/LogInScene.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }