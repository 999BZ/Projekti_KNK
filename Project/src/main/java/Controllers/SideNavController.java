package Controllers;

import Services.GeneralUtil;
import Services.WindowSizeUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import java.util.prefs.Preferences;

public class SideNavController  implements Initializable{

    @FXML
    private VBox navbar;
    @FXML
    private Button gradesButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button studentsButton;

    @FXML
    private Button subjectsButton;

    @FXML
    private Button teachersButton;

        private String position;
        @FXML
        private void goToHome() throws IOException {
            GeneralUtil.goToFXML("/Main/Home.fxml", (Stage) navbar.getScene().getWindow());
        }
        @FXML
        private void goToHelp() throws IOException {
            GeneralUtil.goToFXML("/Main/Help.fxml", (Stage) navbar.getScene().getWindow());
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //get user position from preferences
        Preferences prefs = Preferences.userNodeForPackage(LoginController.class);
        this.position = prefs.get("position", null);
        if(this.position.equals("Student")){
            studentsButton.setVisible(false);
            teachersButton.setVisible(false);
            subjectsButton.setVisible(false);
            gradesButton.setVisible(false);
        }
        else if(this.position.equals("Teacher")){
            studentsButton.setVisible(false);
            teachersButton.setVisible(false);
            subjectsButton.setVisible(false);
        }
        else if(this.position.equals("Admin")){
            studentsButton.setVisible(true);
            teachersButton.setVisible(true);
            subjectsButton.setVisible(true);
            gradesButton.setVisible(true);
        }
        else{
            System.out.println("Error! Position not found!");
            System.out.println("Position: " + this.position);
        }
    }
}
