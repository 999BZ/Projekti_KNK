package Controllers;

import Models.User;
import Services.GeneralUtil;
import Services.UserAuthService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class RegisterAdminController implements Initializable {

    @FXML
    private ImageView firstnamew;
    @FXML
    private ImageView lastnamew;
    @FXML
    private ImageView phonew;
    @FXML
    private ImageView emailw;

    @FXML
    private ImageView passwordw;
    @FXML
    private Label w;

    private Stage primaryStage;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtSurname;

    @FXML
    private TextField txtPhone;

    public void initialize(URL url, ResourceBundle rb) {
        setWarnings(false);
    }

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField pwdPassword;
    @FXML
    private ImageView profilePic;
    private String imagePath ;

    private File selectedFile;
    @FXML
    private void handleImageUploadButton(ActionEvent event) throws IOException {
        selectedFile = GeneralUtil.handleImageUpdate(profilePic);
    }
    @FXML
    private void registerClick(ActionEvent e) throws IOException {
        if (GeneralUtil.setDialog("Add the Admin to the System")) {
            registerAdmin();
        }
    }




    @FXML
    private void registerAdmin(){

        if (selectedFile != null) {
            imagePath = GeneralUtil.savePhoto(selectedFile);
        }

        String email = this.txtEmail.getText();
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if(email.matches(emailRegex)){
            if(!this.pwdPassword.getText().isEmpty() && !this.txtName.getText().isEmpty() && !this.txtSurname.getText().isEmpty()
                    && !this.txtPhone.getText().isEmpty()){
                String password = this.pwdPassword.getText();
                if(password.length() > 7){
                    String name = this.txtName.getText();
                    String surname = this.txtSurname.getText();
                    String phone = this.txtPhone.getText();
                    System.out.println("ALL GOOD");
                    try {
                        System.out.println("Register -> AuthService");
                        User user = UserAuthService.registerAdmin(name, surname, phone, email, password,imagePath);
                        if (user != null){
                            System.out.println("User Registered");
                            cancelClick(new ActionEvent());
                            setWarnings(false);
                        }
                    }catch (SQLException sqlException) {
                        System.out.println("Student couldn't register.");
                    }
                }else{
                    System.out.println("Password is too short!");
                    w.setText("Password is too short!");
                    checkWarnings();
                }
            }else{
                System.out.println("Please fill all text fields");
                w.setText("Please fill out all required fields");
                checkWarnings();

            }
        }else{
            System.out.println("Please check your email and try again!");
            w.setText("Please fill out all required fields");
            emailw.setVisible(true);
            checkWarnings();
        }
    this.profilePic.setImage(null);

    }
    @FXML
    private void cancelClick(ActionEvent e){
        this.txtName.setText("");
        this.txtSurname.setText("");
        this.txtPhone.setText("");
        this.txtEmail.setText("");
        this.pwdPassword.setText("");
        this.profilePic.setImage(null);
    }
public void checkWarnings(){
    if (this.txtName.getText().isEmpty()) {
        firstnamew.setVisible(true);
    }
    if (this.txtSurname.getText().isEmpty()) {
        lastnamew.setVisible(true);
    }
    if (this.txtPhone.getText().isEmpty()) {
        phonew.setVisible(true);
    }
    if (this.pwdPassword.getText().isEmpty()) {
        passwordw.setVisible(true);
    }
}

    public void setWarnings( boolean set){
        firstnamew.setVisible(set);
        lastnamew.setVisible(set);
        phonew.setVisible(set);
        emailw.setVisible(set);
        passwordw.setVisible(set);
        w.setVisible(set);
    }

}
