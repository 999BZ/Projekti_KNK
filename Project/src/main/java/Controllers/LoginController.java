package Controllers;

import Models.User;
import Services.WindowSizeUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import Services.UserAuthService;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController  {
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField pwdPassword;
    boolean isLoginSuccessful = false;
    ActionEvent e;

    @FXML
    private void loginClick(ActionEvent e) throws IOException {
        String email = this.txtEmail.getText();
        String password = this.pwdPassword.getText();
        String position = "Admin";
        try{
            System.out.println("Log-in -> UserAuth");
            User user = UserAuthService.login(email,password);
            if(user == null){
                System.out.println("Username or password is incorrect!");
                return;
            }
            else {
                Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
                preferences.putInt("userId", user.getID());
                isLoginSuccessful = true;
                position = user.getPosition();
            }
            System.out.println("User is correct!");
        }catch (SQLException sqlException) {

        }
        if (isLoginSuccessful) {
            Parent home = FXMLLoader.load(getClass().getResource("/Main/Home.fxml"));

            Scene homePageScene = new Scene(home,WindowSizeUtils.windowWidth, WindowSizeUtils.windowHeight);

            Stage window = (Stage)  txtEmail.getScene().getWindow();

            window.setScene(homePageScene);
            window.show();
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
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
