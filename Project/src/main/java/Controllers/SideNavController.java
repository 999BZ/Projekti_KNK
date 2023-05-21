package Controllers;

import Repository.UserRepository;
import Services.GeneralUtil;
import Services.LanguageUtil;
import Services.WindowSizeUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import java.awt.Desktop;

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
    @FXML
    private GridPane buttonsGrid;
    @FXML
    private Button profileButton;
    @FXML
    private StackPane gradesPane;
    @FXML
    private ImageView profilePic;

    @FXML
    private ChoiceBox<String> languagesBox;

    private String position;
    private ResourceBundle bundle;
    @FXML
    private void goToHome() throws IOException {
        GeneralUtil.goToFXML("/Main/Home.fxml", (Stage) navbar.getScene().getWindow());
    }
    @FXML
    private void goToHelp(ActionEvent event) throws IOException {
//        GeneralUtil.goToFXML("/Main/Help_en.html", (Stage) navbar.getScene().getWindow());
//        File htmlFile = new File("/Main/Help_en.html");
        // file:///C:/Users/eljon/OneDrive/Documents/GitHub/Projekti_KNK/Project/src/main/resources/Main/Help_en.html
        try {
            if (LanguageUtil.getLanguage().equals("Albanian")){
                Desktop.getDesktop().browse(new URI("file:///C:/Users/eljon/OneDrive/Documents/GitHub/Projekti_KNK/Project/src/main/resources/Main/Help_sq.html"));
            } else {
                Desktop.getDesktop().browse(new URI("file:///C:/Users/eljon/OneDrive/Documents/GitHub/Projekti_KNK/Project/src/main/resources/Main/Help_en.html"));
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
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
    private void goToProfile() throws IOException, SQLException {
        Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
        Parent root = null;
        if(position.equals("Teacher")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/TeacherInfo.fxml"));
            root = loader.load();
            TeacherInfoController controller = loader.getController();
            controller.setTeacher(UserRepository.getTeacherByUserID(preferences.getInt("userId", 0)));
        } else if (position.equals("Student")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/StudentInfo.fxml"));
            root = loader.load();
            StudentInfoController controller = loader.getController();
            controller.setStudent(UserRepository.getStudentByUserID(preferences.getInt("userId", 0)));
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/Home.fxml"));
            root = loader.load();
        }
        Scene scene = new Scene(root, WindowSizeUtils.windowWidth, WindowSizeUtils.windowHeight);
        Stage stage = (Stage) gradesButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    private void handleLogOutButton() throws IOException {
        if (LanguageUtil.getLanguage().equals("Albanian")){
            if (GeneralUtil.setDialog("Dëshironi që të shkyçeni?")) {
                GeneralUtil.goToFXML("/Main/Login.fxml", (Stage) navbar.getScene().getWindow());
                //empty the preferences object
                // Obtain the preferences object
                Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
                // Clear the preferences
                try {
                    preferences.clear();
                    System.out.println("Preferences cleared successfully.");
                } catch (Exception e) {
                    System.out.println("Failed to clear preferences: " + e.getMessage());
                }
            }
        } else {
            if (GeneralUtil.setDialog("Are you sure you want to log out?")) {
                GeneralUtil.goToFXML("/Main/Login.fxml", (Stage) navbar.getScene().getWindow());
                //empty the preferences object
                // Obtain the preferences object
                Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
                // Clear the preferences
                try {
                    preferences.clear();
                    System.out.println("Preferences cleared successfully.");
                } catch (Exception e) {
                    System.out.println("Failed to clear preferences: " + e.getMessage());
                }
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //get user position from preferences
        Preferences prefs = Preferences.userNodeForPackage(LoginController.class);
        this.position = prefs.get("position",null);
//        String profileUrl = prefs.get("imageUrl", null);
//        if(profileUrl!=null) {
//            String relativePath = profileUrl.replace("src/main/resources", "");
//            InputStream inputStream = getClass().getResourceAsStream(relativePath);
//            if (inputStream != null) {
//                Image image = new Image(inputStream);
//                profilePic.setImage(image);
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            } else {
//                System.out.println("Error loading image: " + profileUrl);
//            }
//
//        }
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
            buttonsGrid.getChildren().remove(gradesPane);
            buttonsGrid.add(gradesPane, 0, 0);
        }
        else if(this.position.equals("Admin")){
            studentsButton.setVisible(true);
            teachersButton.setVisible(true);
            subjectsButton.setVisible(true);
            gradesButton.setVisible(false);
        }
        else{
            System.out.println("Error! Position not found!");
            System.out.println("Position: " + this.position);
        }

        languagesBox.setValue(LanguageUtil.getLanguage());
        this.setLanguage(null);
        languagesBox.getItems().addAll("En", "Sq");
        languagesBox.setOnAction(this::setLanguage);
    }

    public void setLanguage(ActionEvent event) {
        if (languagesBox.getValue() == "Sq") {
            System.out.println(LanguageUtil.getLanguage());
            try {
                Locale locale = new Locale("sq");
                bundle = ResourceBundle.getBundle("Bilinguality.NameTags_sq", locale);
                updateLabels();
            } catch (Exception e) {
                System.out.println(e);
            }
            LanguageUtil.setLanguage("Sq");
        } else {
            System.out.println(LanguageUtil.getLanguage());
            try {
                Locale locale = new Locale("en");
                bundle = ResourceBundle.getBundle("Bilinguality.NameTags_en", locale);
                updateLabels();
            } catch (Exception e) {
                System.out.println(e);
            }
            LanguageUtil.setLanguage("En");
        }
    }

    public void updateLabels(){
        studentsButton.setText(bundle.getString("students"));
        teachersButton.setText(bundle.getString("teachers"));
        subjectsButton.setText(bundle.getString("subjects"));
        gradesButton.setText(bundle.getString("grades"));
        logoutButton.setText(bundle.getString("logout"));
    }
}
