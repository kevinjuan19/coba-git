module com.perbaikan_quiz {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens Model to javafx.fxml;
    exports Model;
}