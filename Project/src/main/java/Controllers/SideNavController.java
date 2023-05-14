package Controllers;

import Services.GeneralUtil;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SideNavController   {

    @FXML
    private VBox navbar;
    private Stage stage;
    private Scene scene;
    private Parent root;
        @FXML
        private void goToHome() throws IOException {
            GeneralUtil.goToFXML("/Main/Home.fxml", (Stage) navbar.getScene().getWindow());
        }

        @FXML
        private void goToSubjects() throws IOException {
            GeneralUtil.goToFXML("/Main/Subjects.fxml", (Stage) navbar.getScene().getWindow());
        }

        @FXML
        private void goToStudents() throws IOException {
            GeneralUtil.goToFXML("/Main/Students.fxml", (Stage) navbar.getScene().getWindow());

        }

        @FXML
        private void goToTeachers() throws IOException {
            GeneralUtil.goToFXML("/Main/Teachers.fxml", (Stage) navbar.getScene().getWindow());
        }

         @FXML
         private void goToGrades() throws IOException {
            GeneralUtil.goToFXML("/Main/Grades.fxml", (Stage) navbar.getScene().getWindow());
         }



    @FXML
    private void handleLogOutButton() throws IOException {

        if (GeneralUtil.setDialog("Are you sure you want to log out?")) {
            GeneralUtil.goToFXML("/Main/Login.fxml", (Stage) navbar.getScene().getWindow());
        }
    }



}
