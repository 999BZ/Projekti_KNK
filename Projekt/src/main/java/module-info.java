module com.example.projekt {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projekt to javafx.fxml;
    exports com.example.projekt;
    exports Project to javafx.graphics;
    opens Project to javafx.fxml;
}