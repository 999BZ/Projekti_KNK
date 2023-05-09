package Controllers;

import Services.WindowSizeUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SideNavController  {

    @FXML
    private VBox navbar;
    private Stage stage;
    private Scene scene;
    private Parent root;


        @FXML
        private void goToHome() {
            // Load the Home page FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/Home.fxml"));
            try {
                Parent root = loader.load();
                Scene scene = new Scene(root, WindowSizeUtils.windowWidth, WindowSizeUtils.windowHeight);
                Stage stage = (Stage) navbar.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @FXML
        private void goToCourses() {
            // Load the Courses page FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/Courses.fxml"));
            try {
                Parent root = loader.load();
                Scene scene = new Scene(root,WindowSizeUtils.windowWidth, WindowSizeUtils.windowHeight);
                Stage stage = (Stage) navbar.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @FXML
        private void goToStudents() {
            // Load the Students page FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/Students.fxml"));
            try {
                Parent root = loader.load();
                Scene scene = new Scene(root,WindowSizeUtils.windowWidth, WindowSizeUtils.windowHeight);
                Stage stage = (Stage) navbar.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @FXML
        private void goToTeachers() {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/Teachers.fxml"));
            try {
                Parent root = loader.load();
                Scene scene = new Scene(root, WindowSizeUtils.windowWidth, WindowSizeUtils.windowHeight);
                Stage stage = (Stage) navbar.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    @FXML
    private void goToLogin() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log Out");
        alert.setContentText("Do you want to end your session and log out?");
        alert.getDialogPane().getStyleClass().add("confirmation-dialog");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/Login.fxml"));
            try {
                Parent root = loader.load();
                Scene scene = new Scene(root,WindowSizeUtils.windowWidth, WindowSizeUtils.windowHeight);
                Stage stage = (Stage) navbar.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // User chose cancel, do nothing
        }
    }



}
