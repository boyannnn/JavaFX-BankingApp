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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.java.Customer;
import main.java.CustomerDbUtil;
import main.java.Helper;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TransferSceneController implements Initializable {

    @FXML
    private Button transactionButton, idButton;

    @FXML
    Label transactionValueLabel, receiverIdLabel;

    @FXML
    Label errorLabel;

    private CustomerDbUtil customerDbUtil;

    private Customer customer;

    private ImageView upGreenArrow, downGreenArrow, upGreyArrow, downGreyArrow;


    // true -> keyboard input goes to Transaction value
    // false -> keyboard input goes to Receiver ID
    private boolean labelUsed;


    @Override
    public void initialize(URL url, ResourceBundle resources) {

        labelUsed = true;
        upGreenArrow = new ImageView(new Image(getClass().getResourceAsStream("/images/upGreenArrow.png")));
        downGreenArrow = new ImageView(new Image(getClass().getResourceAsStream("/images/downGreenArrow.png")));
        upGreyArrow = new ImageView(new Image(getClass().getResourceAsStream("/images/upGreyArrow.png")));
        downGreyArrow = new ImageView(new Image(getClass().getResourceAsStream("/images/downGreyArrow.png")));

        customerDbUtil= new CustomerDbUtil();

        transactionButton.setGraphic(upGreenArrow);
        idButton.setGraphic(downGreyArrow);

    }

    public CustomerDbUtil getCustomerDbUtil() {
        return customerDbUtil;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getTransactionValueLabel(){
        return this.transactionValueLabel.getText();
    }
    public int getReceiverIdLabel(){
        return Integer.parseInt(this.receiverIdLabel.getText());
    }
    public void setTransactionValueLabel(String number){ //TODO: PROBLEM
        this.transactionValueLabel.setText(number);
    }

    public boolean getLabelUsed() {
        return labelUsed;
    }

    public void setLabelUsed(boolean labelUsed) {
        this.labelUsed = labelUsed;
    }

    public void setReceiverIdLabel(String receiverIdLabel) {
        this.receiverIdLabel.setText(receiverIdLabel);
    }

    public void setErrorLabel(String text){
        this.errorLabel.setText(text);
    }

    public void processNumbers(ActionEvent event) {

        // get Button pressed
        String buttonDigit = ((Button) event.getSource()).getText();

        if (getLabelUsed()){

            //  get withdrawal label without '£' symbol in int
            String currentWithdrawalAmount = Helper.toCleanNumber(getTransactionValueLabel().substring(1));

            if (Integer.parseInt(currentWithdrawalAmount) == 0) {
                setTransactionValueLabel("£" + buttonDigit + ".00"); //TODO: PROBLEM
            } else {
                String newWithdrawalAmount = currentWithdrawalAmount + buttonDigit;

                if (Integer.parseInt(newWithdrawalAmount) < 2500) {
                    setTransactionValueLabel("£" + newWithdrawalAmount + ".00");

                } else {
                    setErrorLabel("YOU CAN ONLY TRANSFER UP TO £2500.00");
                    setTransactionValueLabel("£2500.00");
                }
            }
        } else {
            String currentIdNumber = String.valueOf(getReceiverIdLabel());
            if ((currentIdNumber + buttonDigit).length() > 3) {
                setErrorLabel("ID CANNOT BE HIGHER THAN 999!");
                setReceiverIdLabel("999");
            } else if (Integer.parseInt(currentIdNumber)==0) {
                setReceiverIdLabel(buttonDigit);
            } else {
                setReceiverIdLabel(currentIdNumber+buttonDigit);
            }
        }
    }

    public void back(ActionEvent event){
        switchToBankingScene(event);
    }

    public void reset() {

        if (getLabelUsed()) {
            setTransactionValueLabel("£0.00");
        } else {
            setReceiverIdLabel("0");
        }
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void transfer(ActionEvent event) {
        float currentTransferAmount = Float.parseFloat(getTransactionValueLabel().substring(1));
        float customerFundsAfterWithdrawal = getCustomer().getBalance() - currentTransferAmount;

        if (!(getReceiverIdLabel() == getCustomer().getId())) {

            if (getCustomerDbUtil().customerExists(getReceiverIdLabel())) {

                if (currentTransferAmount <= getCustomer().getBalance()) {

                    // set Sender Customer balance inside the app
                    getCustomer().setBalance(customerFundsAfterWithdrawal);

                    // set Sender Customer balance inside Database
                    getCustomerDbUtil().updateBalance(customerFundsAfterWithdrawal, getCustomer().getId());

                    // set Receiver Customer balance inside Database
                    getCustomerDbUtil().receiveTransfer(currentTransferAmount, getReceiverIdLabel());

                    // switch scene
                    switchToBankingScene(event);
                } else {
                    setErrorLabel("NOT ENOUGH FUNDS!");
                }
            } else {
                setErrorLabel("CUSTOMER WITH THIS ID DOES NOT EXIST!");
            }
        } else {
            setErrorLabel("YOU CANNOT SEND MONEY TO YOURSELF! CHANGE ID!");
        }
    }

    public void switchToTransactionLabel(){
        if (getLabelUsed()){
            setLabelUsed(false);
            transactionButton.setGraphic(upGreyArrow);
            idButton.setGraphic(downGreenArrow);
        }
    }

    public void switchToReceiverIdLabel(){
        if (!getLabelUsed()){
            setLabelUsed(true);
            transactionButton.setGraphic(upGreenArrow);
            idButton.setGraphic(downGreyArrow);
        }
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
