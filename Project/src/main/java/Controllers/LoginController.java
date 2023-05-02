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
                isLoginSuccessful = true;
            }
            System.out.println("User is correct!");
        }catch (SQLException sqlException) {

        }
        if (isLoginSuccessful) {
            // Create a new scene for the home page
            Parent homePageParent = FXMLLoader.load(getClass().getResource("/Main/Home.fxml"));
            Scene homePageScene = new Scene(homePageParent);

            // Get the stage information
            Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();

            // Set the new scene on the stage
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
