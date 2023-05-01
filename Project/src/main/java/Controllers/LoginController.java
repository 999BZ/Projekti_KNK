package Controllers;

import Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import Services.UserAuthService;

import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField pwdPassword;

    @FXML
    private void loginClick(ActionEvent e){
        String email = this.txtEmail.getText();
        String password = this.pwdPassword.getText();
        try{
            User user = UserAuthService.login(email,password);
            if(user == null){
                System.out.println("Username or password is incorrect!");
                return;
            }
            System.out.println("User is correct!");
        }catch (SQLException sqlException) {

        }
    }
    @FXML
    private void cancelClick(ActionEvent e){
        this.txtEmail.setText("");
        this.pwdPassword.setText("");
    }
}
