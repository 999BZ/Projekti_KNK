module com.example.test {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.prefs;
    opens Models;
    opens com.example.test to javafx.fxml;
    opens Main to javafx.fxml;
    exports Main to javafx.graphics;

    exports Controllers to javafx.graphics;
    opens Controllers to javafx.fxml;
}