package Controllers;

import Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import Services.UserAuthService;
import javafx.stage.Stage;
import java.util.prefs.Preferences;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField pwdPassword;
    boolean isLoginSuccessful = false;
    @FXML
    private void loginClick(ActionEvent e) throws IOException {
        String email = this.txtEmail.getText();
        String password = this.pwdPassword.getText();
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
                preferences.put("name", user.getEmail());
                isLoginSuccessful = true;
            }
            System.out.println("User is correct!");
        }catch (SQLException sqlException) {

        }
        if (isLoginSuccessful) {
            Parent homePageParent = FXMLLoader.load(getClass().getResource("/Main/Home.fxml"));
            Scene homePageScene = new Scene(homePageParent);

            Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();

            window.setScene(homePageScene);
            window.show();
        }
    }
    @FXML
    private void cancelClick(ActionEvent e){
        this.txtEmail.setText("");
        this.pwdPassword.setText("");
    }
}
