package com.example.swimmingcompetition;
import com.example.swimmingcompetition.domain.Participant;
import com.example.swimmingcompetition.domain.ParticipantTask;
import com.example.swimmingcompetition.domain.Task;
import com.example.swimmingcompetition.service.ServiceAccount;
import com.example.swimmingcompetition.service.ServiceParticipant;
import com.example.swimmingcompetition.service.ServiceParticipantTask;
import com.example.swimmingcompetition.service.ServiceTask;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class InterfaceSearchByTask {
    @FXML
    private TableView<Pair<Participant, Task>> tableParticipantTask;
    @FXML
    private TableColumn<Pair<Participant, Task>, String> columnName;
    @FXML
    private TableColumn<Pair<Participant, Task>, String> columnAge;
    @FXML
    private TableColumn<Pair<Participant, Task>, String> columnDistance;
    @FXML
    private TableColumn<Pair<Participant, Task>, String> columnStyle;
    ServiceParticipant sp;
    ServiceTask st;
    ServiceParticipantTask spt;
    ServiceAccount sa;
    int idTask;


    /**
     * setter for services
     * @param sp - ServiceParticipant
     * @param st - ServiceTask
     * @param spt - ServiceParticipantTask
     * @param sa - ServiceAccount
     * @param idTask - Task's id
     */
    public void setServSearch(ServiceParticipant sp, ServiceTask st, ServiceParticipantTask spt, ServiceAccount sa, int idTask) {
        this.sp = sp;
        this.st = st;
        this.idTask = idTask;
        this.sa = sa;
        this.spt = spt;
    }


    /**
     * table initialization
     */
    public void initialization(){

        columnDistance.setCellValueFactory(Task -> {
            Pair<Participant, Task> t = Task.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(t.getValue().getDistance().getDist()));
        });
        columnStyle.setCellValueFactory(Task -> {
            Pair<Participant, Task> t = Task.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(t.getValue().getStyle()));
        });
        columnName.setCellValueFactory(Participant -> {
            Pair<Participant, Task> t = Participant.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(t.getKey().getName()));
        });
        columnAge.setCellValueFactory(Participant -> {
            Pair<Participant, Task> t = Participant.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(t.getKey().getAge()));
        });


        Platform.runLater(() -> {
            List<Pair<Participant, Task>> participantsTasksList = listForInitialize(idTask);
            tableParticipantTask.setItems(FXCollections.observableArrayList(participantsTasksList));
        });

    }


    /**
     * It receives the participant's id, and it returns all tasks that the participant signed up for
     * @param idTask - task's id
     * @return - an array with all participants who participate at that task
     */
    private Vector<Participant> listParticipans(int idTask){
        Vector<ParticipantTask> vectParticipantTask = spt.findAllParticipants(idTask);

        Vector<Participant> vectParticipant = new Vector<Participant>();
        for (ParticipantTask pt: vectParticipantTask) {
            vectParticipant.add(sp.findOne(pt.getIdParticipant()));
        }
        return vectParticipant;
    }


    /**
     * It receives the selected task id and it returns all participants for this task
     * @param idParticipant - participant's id
     * @return - an array with all the tasks to which that participant signed up
     */
    private Vector<Task> listTasks(int idParticipant){
        Vector<ParticipantTask> vectParticipantTask = spt.findAllTasks(idParticipant);

        Vector<Task> vectTasks = new Vector<Task>();
        for (ParticipantTask pt: vectParticipantTask) {
            vectTasks.add(st.findOne(pt.getIdTask()));
        }
        return vectTasks;
    }


    /**
     * For every participant it takes all tasks, and then it creates <Participant, Task> pairs
     * @param idTask = task's id
     * @return - an array with <Participant, Task> pairs
     */
    private Vector<Pair<Participant, Task>> listForInitialize(int idTask){
        Vector<Pair<Participant, Task>> vect = new Vector<>();
        for (Participant p:listParticipans(idTask)) {
            for (Task t:listTasks(p.getId())) {
                vect.add(new Pair<>(p, t));
            }
        }
        return vect;
    }


    /**
     * Back button
     */
    public void backButton(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InterfaceAdmin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 878, 561);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        InterfaceAdmin intAdmin = fxmlLoader.getController();
        intAdmin.setServ(sp, st, spt, sa);
        intAdmin.initialization();
        stage.setScene(scene);
        stage.show();
    }
}
