package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import Models.User;
import Services.UserAuthService;

import java.sql.SQLException;

public class RegisterController {
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtSurname;
    @FXML
    private TextField txtBirthdate;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtYear;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField pwdPassword;

    @FXML
    private void registerClick(ActionEvent e){
        String email = this.txtEmail.getText();
        String password = this.pwdPassword.getText();
        String name = this.txtName.getText();
        String surname = this.txtSurname.getText();
        String birthdate = this.txtBirthdate.getText();
        String phone = this.txtPhone.getText();
        String address = this.txtAddress.getText();
        int year = Integer.parseInt(this.txtYear.getText());
        try{
            System.out.println("Register -> AuthService");
            User user = UserAuthService.register(name,surname,birthdate,phone,address,year,email,password);
            if (user != null){
                System.out.println("User Registered");
            }
        }catch (SQLException sqlException) {

        }

    }
    @FXML
    private void cancelClick(ActionEvent e){
        this.txtName.setText("");
        this.txtSurname.setText("");
        this.txtBirthdate.setText("");
        this.txtPhone.setText("");
        this.txtAddress.setText("");
        this.txtYear.setText("");
        this.txtEmail.setText("");
        this.pwdPassword.setText("");
    }
}
