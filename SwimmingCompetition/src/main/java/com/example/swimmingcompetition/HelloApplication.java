package com.example.swimmingcompetition;
import com.example.swimmingcompetition.domain.*;
import com.example.swimmingcompetition.repository.RepoBDAccount;
import com.example.swimmingcompetition.repository.RepoBDParticipant;
import com.example.swimmingcompetition.repository.RepoBDParticipantTask;
import com.example.swimmingcompetition.repository.RepoBDTask;
import com.example.swimmingcompetition.service.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Properties props=new Properties();
        try {
            props.load(new FileReader("db.config"));
        } catch (IOException e) {
            System.out.println("Can't find bd.config " + e);
        }

        RepoBDAccount ra = new RepoBDAccount(props);
        RepoBDParticipant rp = new RepoBDParticipant(props);
        RepoBDTask rt = new RepoBDTask(props);
        RepoBDParticipantTask rpt = new RepoBDParticipantTask(props);
        Validate v = new Validate();

        ServiceAccount sa = new ServiceAccount(ra, v);
        ServiceParticipant sp = new ServiceParticipant(rp, v);
        ServiceTask st = new ServiceTask(rt);
        ServiceParticipantTask spt = new ServiceParticipantTask(rpt);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("InterfaceLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 693, 543);
        InterfaceLogin login = fxmlLoader.getController();
        login.setService(sa, sp, st, spt);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}