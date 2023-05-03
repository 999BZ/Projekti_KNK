package Controllers;

import Services.WindowSizeUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.prefs.Preferences;

public class CoursesController {
    private double previousWidth;
    private double previousHeight;

    @FXML
    private BorderPane rootPane;
    @FXML
    public void initialize() {
        // Load the previous window size from preferences
        WindowSizeUtils.loadWindowSize("loginWindow", rootPane);
    }
    @FXML
    public void handleWindowClose() {
        // Save the current window size to preferences when it is closed
        WindowSizeUtils.saveWindowSize("loginWindow", rootPane);
    }
}
