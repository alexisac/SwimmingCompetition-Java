package com.example.swimmingcompetition;
import com.example.swimmingcompetition.service.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class InterfaceRegister {
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private PasswordField passwordFiledConfirm;
    @FXML
    private TextField textFieldUsername;
    ServiceAccount sa;
    ServiceParticipant sp;
    ServiceTask st;
    ServiceParticipantTask spt;


    /**
     * setter for services
     * @param sp - ServiceParticipant
     * @param st - ServiceTask
     * @param spt - ServiceParticipantTask
     * @param sa - ServiceAccount
     */
    public void setServAccount(ServiceAccount sa, ServiceParticipant sp, ServiceTask st, ServiceParticipantTask spt) {
        this.sa = sa;
        this.sp = sp;
        this.st = st;
        this.spt = spt;
    }


    /**
     * Sign up button
     */
    public void signUp(ActionEvent event) throws IOException{
        String username = textFieldUsername.getText();
        String password = passwordFieldPassword.getText();
        String confirmedPassword = passwordFiledConfirm.getText();
        textFieldUsername.clear();
        passwordFiledConfirm.clear();
        passwordFieldPassword.clear();
        if(password.equals(confirmedPassword)){
            if(username.length() != 0 && password.length() != 0){
                if(sa.ifExistAccount(username, password) == -1){
                    try{
                        sa.add(username, password);
                        Alert errorAlert =new Alert(Alert.AlertType.CONFIRMATION);
                        errorAlert.setHeaderText("YOUR ACCOUNT WAS ADDED");
                        errorAlert.setContentText("Now you can log in");
                        errorAlert.show();

                        back(event);
                    } catch (ServiceException er){
                        Alert errorAlert =new Alert(Alert.AlertType.ERROR);
                        errorAlert.setHeaderText("ERROR!");
                        errorAlert.setContentText(er.getMyMessage());
                        errorAlert.showAndWait();
                    }
                } else {
                    Alert errorAlert =new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("ERROR!");
                    errorAlert.setContentText("This username already exist!");
                    errorAlert.showAndWait();
                }
            } else if(username.length() == 0 && password.length() == 0){
                Alert errorAlert =new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("USERNAME AND PASSWORD INVALID!");
                errorAlert.setContentText("Username and password can't be empty!");
                errorAlert.showAndWait();
            } else if (username.length() == 0){
                Alert errorAlert =new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("USERNAME INVALID!");
                errorAlert.setContentText("Username can't be empty!");
                errorAlert.showAndWait();
            } else {
                Alert errorAlert =new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("PASSWORD INVALID!");
                errorAlert.setContentText("Password can't be empty!");
                errorAlert.showAndWait();
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("ERROR!");
            errorAlert.setContentText("The two passwords are not identical!");
            errorAlert.showAndWait();
        }
    }


    /**
     * Back button
     */
    public void back(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InterfaceLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 693, 543);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        InterfaceLogin intLog = fxmlLoader.getController();
        intLog.setService(sa, sp, st, spt);
        stage.setScene(scene);
        stage.show();
    }
}
