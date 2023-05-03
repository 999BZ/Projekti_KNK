package Controllers;

import Models.User;
import Services.UserAuthService;
import Services.WindowSizeUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class RegisterTeachersController implements Initializable {
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtSurname;
    @FXML
    private DatePicker DateBirthdate;
    @FXML
    private TextField txtPhone;
    @FXML
    private Spinner<Integer> txtYear;

    public void initialize(URL url, ResourceBundle rb) {
        txtYear.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 12, 0));
    }
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField pwdPassword;

    @FXML
    private void registerClick(ActionEvent e){
        String email = this.txtEmail.getText();
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if(email.matches(emailRegex)){
            if(!this.pwdPassword.getText().isEmpty() && !this.txtName.getText().isEmpty() && !this.txtSurname.getText().isEmpty() &&
                    !(this.DateBirthdate.getValue() == null) && !this.txtPhone.getText().isEmpty() &&
                    !this.txtAddress.getText().isEmpty() && this.txtYear.getValue() != 0){
                String password = this.pwdPassword.getText();
                if(password.length() > 7){
                    String name = this.txtName.getText();
                    String surname = this.txtSurname.getText();
                    LocalDate tempBirthdate = this.DateBirthdate.getValue();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String birthdate = tempBirthdate.format(formatter);
                    String phone = this.txtPhone.getText();
                    String address = this.txtAddress.getText();
                    int year = this.txtYear.getValue();
                    System.out.println("ALL GOOD");
                    try {
                        System.out.println("Register -> AuthService");
                        User user = UserAuthService.register(name, surname, birthdate, phone, address, year, email, password,"Teacher");
                        if (user != null){
                            System.out.println("User Registered");
                            this.txtName.setText("");
                            this.txtSurname.setText("");
                            this.DateBirthdate.setValue(null);
                            this.txtPhone.setText("");
                            this.txtAddress.setText("");
                            this.txtYear.getValueFactory().setValue(0);
                            this.txtEmail.setText("");
                            this.pwdPassword.setText("");
                        }
                    }catch (SQLException sqlException) {
                        System.out.println("Teacher couldn't register.");
                    }
                }else{
                    System.out.println("Password is too short!");
                }
            }else{
                System.out.println("Please fill all text fields");
            }
        }else{
            System.out.println("Please check your email and try again!");
        }


    }
    @FXML
    private void cancelClick(ActionEvent e){
        this.txtName.setText("");
        this.txtSurname.setText("");
        this.DateBirthdate.setValue(null);
        this.txtPhone.setText("");
        this.txtAddress.setText("");
        this.txtYear.getValueFactory().setValue(0);
        this.txtEmail.setText("");
        this.pwdPassword.setText("");
    }
    @FXML
    private BorderPane rootPane;
    @FXML
    public void initialize() {
        // Load the previous window size from preferences
        WindowSizeUtils.loadWindowSize("loginWindow", rootPane);
    }
    @FXML
    public void handleWindowClose() {
        // Save the current window size to preferences when it is closed
        WindowSizeUtils.saveWindowSize("loginWindow", rootPane);
    }
}