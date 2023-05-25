package Controllers;

import Models.User;
import Repository.UserRepository;
import Services.LanguageUtil;
import Services.WindowSizeUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import Services.UserAuthService;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController implements Initializable{
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField pwdPassword;
    @FXML
    private Label warning;
    @FXML
    private Label email;
    @FXML
    private Label password;
    @FXML
    private Label slogin;
    @FXML
    private Label copyright;
    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;


    @FXML
    private ChoiceBox<String> languagesBox;
    boolean isLoginSuccessful = false;
    private ResourceBundle bundle;

    @FXML
    private void loginClick(ActionEvent e) throws IOException, SQLException {
        String email = this.txtEmail.getText();
        String password = this.pwdPassword.getText();
        String position = null;
        try{
            System.out.println("Log-in -> UserAuth");
            User user = UserAuthService.login(email,password);
            if(user == null){
                System.out.println("Username or password is incorrect!");
                this.warning.setVisible(true);
                return;
            }
            else {
                Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
                preferences.putInt("userId", user.getID());
                preferences.put("position", user.getPosition());
//                preferences.put("imageUrl", user.getProfileImg());
                isLoginSuccessful = true;
                position = user.getPosition();
            }
            System.out.println("User is correct!");
        }catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }catch (Exception t){
            System.out.println(t.getMessage());
        }
        if (isLoginSuccessful) {
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
            Stage stage = (Stage) pwdPassword.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

    }
    @FXML
    private void cancelClick(ActionEvent e){
        this.txtEmail.setText("");
        this.pwdPassword.setText("");
    }
    @FXML
    private void handleEnterKeyPressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    loginClick(new ActionEvent());
                } catch (IOException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.warning.setVisible(false);
        languagesBox.setValue(LanguageUtil.getLanguage());
        this.setLanguage(null);
        languagesBox.getItems().addAll("English", "Albanian");
        languagesBox.setOnAction(this::setLanguage);
    }

    public void setLanguage(ActionEvent event) {
        if (languagesBox.getValue() == "Albanian") {
            System.out.println(LanguageUtil.getLanguage());
            try {
                Locale locale = new Locale("sq");
                bundle = ResourceBundle.getBundle("Bilinguality.NameTags_sq", locale);
                updateLabels();
            } catch (Exception e) {
                System.out.println(e);
            }
            LanguageUtil.setLanguage("Albanian");
        } else {
            System.out.println(LanguageUtil.getLanguage());
            try {
                Locale locale = new Locale("en");
                bundle = ResourceBundle.getBundle("Bilinguality.NameTags_en", locale);
                updateLabels();
            } catch (Exception e) {
                System.out.println(e);
            }
            LanguageUtil.setLanguage("English");
        }
    }

    public void updateLabels(){
        slogin.setText(bundle.getString("slogin"));
        email.setText(bundle.getString("email") + ":");
        password.setText(bundle.getString("password") + ":");
        loginButton.setText(bundle.getString("login"));
        cancelButton.setText(bundle.getString("cancel"));
        copyright.setText("Copyright ©. " + (bundle.getString("copyright")));
        warning.setText(bundle.getString("checkEP"));
    }
}
