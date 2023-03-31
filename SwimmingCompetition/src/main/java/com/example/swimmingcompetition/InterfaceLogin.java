package com.example.swimmingcompetition;
import com.example.swimmingcompetition.service.ServiceAccount;
import com.example.swimmingcompetition.service.ServiceParticipant;
import com.example.swimmingcompetition.service.ServiceParticipantTask;
import com.example.swimmingcompetition.service.ServiceTask;
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

public class InterfaceLogin {
    @FXML
    private TextField textFieldUsername;
    @FXML
    private PasswordField passwordFieldPassword;
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
    public void setService(ServiceAccount sa, ServiceParticipant sp, ServiceTask st, ServiceParticipantTask spt) {
        this.sa = sa;
        this.sp = sp;
        this.st = st;
        this.spt = spt;
    }


    /**
     * Login button
     */
    public void logIn(ActionEvent event) throws IOException{
        String username = textFieldUsername.getText();
        String password = passwordFieldPassword.getText();
        textFieldUsername.clear();
        passwordFieldPassword.clear();
        if(username.length() != 0 && password.length() != 0){
            int idAccount = sa.ifExistAccount(username, password);
            if(idAccount != -1){
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InterfaceAdmin.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 878, 561);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    InterfaceAdmin intAdmin = fxmlLoader.getController();
                    intAdmin.setServ(sp, st, spt, sa);
                    intAdmin.initialization();
                    stage.setScene(scene);
                    stage.show();
            } else {
                Alert errorAlert =new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("ERROR!");
                errorAlert.setContentText("This account doesn't exist!");
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
    }

    /**
     * Sign up button
     */
    public void signUp(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InterfaceRegister.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 693, 543);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        InterfaceRegister intReg = fxmlLoader.getController();
        intReg.setServAccount(sa, sp, st, spt);
        stage.setScene(scene);
        stage.show();
    }

}
