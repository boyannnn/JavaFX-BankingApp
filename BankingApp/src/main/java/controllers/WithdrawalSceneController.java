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
import javafx.stage.Stage;
import main.java.Customer;
import main.java.CustomerDbUtil;
import main.java.Helper;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WithdrawalSceneController implements Initializable {

    private Customer customer;
    private CustomerDbUtil customerDbUtil;

    @FXML
    Label withdrawalValueLabel,errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         customerDbUtil = new CustomerDbUtil();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    public String getWithdrawalValueLabel(){
        return this.withdrawalValueLabel.getText(); //something like £2500.00
    }

    public void setWithdrawalValueLabel(String number){ //TODO: PROBLEM
        this.withdrawalValueLabel.setText(number);
    }

    public void setErrorLabel(String text) {
        this.errorLabel.setText(text);
    }

    public CustomerDbUtil getCustomerDbUtil() {
        return customerDbUtil;
    }

    public void processNumbers(ActionEvent event) {

        // get Button pressed
        String buttonDigit = ((Button) event.getSource()).getText();

        //  get withdrawal label without '£' symbol in int
        String currentWithdrawalAmount = Helper.toCleanNumber(getWithdrawalValueLabel().substring(1));

        if (Integer.parseInt(currentWithdrawalAmount) == 0) {
            setWithdrawalValueLabel("£" + buttonDigit + ".00"); //TODO: PROBLEM
        } else {
            String newWithdrawalAmount = currentWithdrawalAmount + buttonDigit;

            if (Integer.parseInt(newWithdrawalAmount) < 2500) {
                setWithdrawalValueLabel("£" + newWithdrawalAmount + ".00");

            } else {
                setErrorLabel("YOU CAN ONLY WITHDRAW UP TO £2500.00");
                setWithdrawalValueLabel("£2500.00");
            }
        }
    }

    public void withdraw(ActionEvent event){

        float currentWithdrawalAmount = Float.parseFloat(getWithdrawalValueLabel().substring(1));
        float customerFundsAfterWithdrawal = getCustomer().getBalance()-currentWithdrawalAmount;


        if (currentWithdrawalAmount <= getCustomer().getBalance()){

            // set Customer balance inside the app
            getCustomer().setBalance(customerFundsAfterWithdrawal);

            // set Customer balance inside Database
            getCustomerDbUtil().updateBalance(customerFundsAfterWithdrawal,getCustomer().getId());

            // switch scene
            switchToBankingScene(event);
        } else {
            setErrorLabel("NOT ENOUGH FUNDS");
        }
    }
    public void back(ActionEvent event){
        switchToBankingScene(event);
    }
    public void reset(){
        setWithdrawalValueLabel("£0.00");
    }

    public void switchToBankingScene(ActionEvent event){
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/BankingScene.fxml"));
            Parent root = loader.load();

            BankingSceneController bankingSceneController=loader.getController();

            bankingSceneController.setCustomer(getCustomer());

            Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
