module com.example.swimmingcompetition {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires java.sql;


    opens com.example.swimmingcompetition to javafx.fxml;
    exports com.example.swimmingcompetition;
}