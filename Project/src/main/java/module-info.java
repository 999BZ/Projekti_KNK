module com.example.test {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.test to javafx.fxml;
    opens Projekt to javafx.fxml;
    exports Projekt to javafx.graphics;
    exports com.example.test;
}