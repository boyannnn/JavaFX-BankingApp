package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.java.Customer;
import main.java.Helper;

import java.io.IOException;

public class BankingSceneController {

    @FXML
    private Label nameLabel,balanceLabel,idLabel;

    private Customer customer;


    // TODO: find a way to remove boiler code when switching scenes
    public void deposit(ActionEvent event){
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/DepositScene.fxml"));
            Parent root = loader.load();

            // send customer info to next scene
            DepositSceneController depositSceneController=loader.getController();
            depositSceneController.setCustomer(getCustomer());

            Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void withdrawal(ActionEvent event){
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/WithdrawalScene.fxml"));
            Parent root = loader.load();

            // send customer info to next scene
            WithdrawalSceneController withdrawalSceneController=loader.getController();
            withdrawalSceneController.setCustomer(getCustomer());

            Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNameLabel(String text) {
        this.nameLabel.setText(text);
    }

    public void setBalanceLabel(String text) {
        this.balanceLabel.setText(text);
    }

    public void setIdLabel(String text) {
        this.idLabel.setText(text);
    }

    public void transfer(ActionEvent event){
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/TransferScene.fxml"));
            Parent root = loader.load();

            // send customer info to next scene
            TransferSceneController transferSceneController=loader.getController();
            transferSceneController.setCustomer(getCustomer());

            Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
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

    public void setCustomer(Customer customer){
        this.customer = customer;
        setNameLabel("Name: "+getCustomer().getFirstName()+" "+getCustomer().getLastName());
        setBalanceLabel("Balance: "+ Helper.formatCurrency(getCustomer().getBalance()));
        setIdLabel("Identification number: "+ getCustomer().getId());
    }

    public Customer getCustomer(){
        return this.customer;
    }


}
