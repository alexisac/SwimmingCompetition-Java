package com.example.swimmingcompetition;
import com.example.swimmingcompetition.domain.Participant;
import com.example.swimmingcompetition.domain.Task;
import com.example.swimmingcompetition.service.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class InterfaceAdmin {

    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldAge;
    @FXML
    private TableColumn<Participant, String> columnName;
    @FXML
    private TableColumn<Participant, String> columnAge;
    @FXML
    private TableView<Participant> tableParticipants;
    @FXML
    private TableView<Task> tableTasks;
    @FXML
    private TableColumn<Task, String> columnDistance;
    @FXML
    private TableColumn<Task, String> columnStyle;
    @FXML
    private TableColumn<Task, String> columnNrOfParticipants;

    ServiceParticipant sp;
    ServiceTask st;
    ServiceParticipantTask spt;
    ServiceAccount sa;


    /**
     * setter for services
     * @param sp - ServiceParticipant
     * @param st - ServiceTask
     * @param spt - ServiceParticipantTask
     * @param sa - ServiceAccount
     */
    public void setServ(ServiceParticipant sp, ServiceTask st, ServiceParticipantTask spt, ServiceAccount sa) {
        this.sp = sp;
        this.st = st;
        this.spt = spt;
        this.sa = sa;
    }


    /**
     * tables initialization
     */
    public void initialization(){
        initializeTabelParticipants();
        initializeTabelTasks();
    }


    /**
     * initialize the participants table
     */
    private void initializeTabelParticipants(){
        columnName.setCellValueFactory(Participant -> {
            Participant p = Participant.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(p.getName()));
        });

        columnAge.setCellValueFactory(Participant -> {
            Participant p = Participant.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(p.getAge()));
        });

        Platform.runLater(() -> {
            List<Participant> participantsList = sp.findAll();
            tableParticipants.setItems(FXCollections.observableArrayList(participantsList));
        });
    }


    /**
     * initialize the tasks table
     */
    private void initializeTabelTasks(){
        columnDistance.setCellValueFactory(Task -> {
            Task t = Task.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(t.getDistance().getDist()));
        });
        columnStyle.setCellValueFactory(Task -> {
            Task t = Task.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(t.getStyle()));
        });
        columnNrOfParticipants.setCellValueFactory(Task -> {
            Task t = Task.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(nrOfPArticipants(t.getId())));
        });

        Platform.runLater(() -> {
            List<Task> tasksList = st.findAll();
            tableTasks.setItems(FXCollections.observableArrayList(tasksList));
        });
    }


    /**
     * number of participants for one task
     * @param idTask - task's id
     * @return - number of participants for one task
     */
    private int nrOfPArticipants(int idTask){
        return spt.findAllParticipants(idTask).size();
    }


    /**
     * reload the information in the participant's table
     */
    private void reloadTabelParticipants(){
        List<Participant> participantsList = sp.findAll();
        tableParticipants.setItems(FXCollections.observableArrayList(participantsList));
    }


    /**
     * reload the information in the task's table
     */
    private void reloadTabelTasks(){
        List<Task> tasksList = st.findAll();
        tableTasks.setItems(FXCollections.observableArrayList(tasksList));
    }


    /**
     * Sign Up button
     */
    public void signUpTaskButton(){
        Task t = tableTasks.getSelectionModel().getSelectedItem();
        Participant p = tableParticipants.getSelectionModel().getSelectedItem();
        tableTasks.getSelectionModel().clearSelection();
        tableParticipants.getSelectionModel().clearSelection();
        if(t != null && p != null){
            try{
                spt.add(p.getId(), t.getId());
                reloadTabelTasks();
            } catch (ServiceException er){
                Alert errorAlert =new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("ERROR!");
                errorAlert.setContentText(er.getMyMessage());
                errorAlert.showAndWait();
            }
        } else if (t == null && p == null){
            Alert errorAlert =new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("ERROR!");
            errorAlert.setContentText("You didn't select one participant and one task");
            errorAlert.showAndWait();
        } else if (t == null){
            Alert errorAlert =new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("ERROR!");
            errorAlert.setContentText("You didn't select one task");
            errorAlert.showAndWait();
        } else {
            Alert errorAlert =new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("ERROR!");
            errorAlert.setContentText("You didn't select one participant");
            errorAlert.showAndWait();
        }
    }


    /**
     * Search button
     */
    public void mySearchButton(ActionEvent event) throws IOException{
        Task t = tableTasks.getSelectionModel().getSelectedItem();
        tableTasks.getSelectionModel().clearSelection();
        if(t != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InterfaceSearchByTask.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 693, 543);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            InterfaceSearchByTask intS = fxmlLoader.getController();
            intS.setServSearch(sp, st, spt, sa, t.getId());
            intS.initialization();
            stage.setScene(scene);
            stage.show();
        } else {
            Alert errorAlert =new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("ERROR!");
            errorAlert.setContentText("You didn't select one task");
            errorAlert.showAndWait();
        }
    }


    /**
     * Logout button
     */
    public void logoutButton(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InterfaceLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 693, 543);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        InterfaceLogin intLogin = fxmlLoader.getController();
        intLogin.setService(sa, sp, st, spt);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Add Participant button
     */
    public void addParticipantButton(){
        String name = textFieldName.getText();
        String age = textFieldAge.getText();
        textFieldName.clear();
        textFieldAge.clear();
        if(name.length() != 0 && age.length() != 0) {
            try {
                sp.add(name, Integer.parseInt(age));
                reloadTabelParticipants();
            } catch (ServiceException ex) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("ERROR!");
                errorAlert.setContentText(ex.getMyMessage());
                errorAlert.showAndWait();
            }
        } else if(name.length() == 0 && age.length() == 0){
            Alert errorAlert =new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("NAME AND AGE INVALID!");
            errorAlert.setContentText("Name and age can't be empty!");
            errorAlert.showAndWait();
        } else if (name.length() == 0){
            Alert errorAlert =new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("NAME INVALID!");
            errorAlert.setContentText("Name can't be empty!");
            errorAlert.showAndWait();
        } else {
            Alert errorAlert =new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("AGE INVALID!");
            errorAlert.setContentText("Age can't be empty!");
            errorAlert.showAndWait();
        }
    }


    /**
     * Delete PArticipant button
     */
    public void deleteParticipantButton(){
        Participant p = tableParticipants.getSelectionModel().getSelectedItem();
        tableParticipants.getSelectionModel().clearSelection();

        if(p != null){
            sp.delete(p.getId());
            reloadTabelParticipants();
            reloadTabelTasks();
        }  else {
            Alert errorAlert =new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("ERROR!");
            errorAlert.setContentText("You didn't select one participant");
            errorAlert.showAndWait();
        }
    }
}
