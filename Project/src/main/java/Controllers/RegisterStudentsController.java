package Controllers;

import Services.GeneralUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import Models.User;
import Services.UserAuthService;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class RegisterStudentsController implements Initializable {
    @FXML
    private ImageView birthdayw;
    @FXML
    private ImageView firstnamew;
    @FXML
    private ImageView lastnamew;
    @FXML
    private ImageView phonew;
    @FXML
    private ImageView emailw;
    @FXML
    private ImageView gradeLvlw;
    @FXML
    private ImageView txtParalelw;
    @FXML
    private ImageView addressw;
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
    private DatePicker DateBirthdate;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtAddress;
    @FXML
    private Spinner<Integer> txtYear;
    @FXML
    private Spinner<Integer> txtParalel;

    public void initialize(URL url, ResourceBundle rb) {
        setWarnings(false);
        txtYear.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 12, 0));
        txtParalel.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 3, 0));
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
        if (GeneralUtil.setDialog("Add the Student to the System")) {
            registerStudent();
        }
    }




    @FXML
    private void registerStudent(){

        if (selectedFile != null) {
            imagePath = GeneralUtil.savePhoto(selectedFile);
        }

        String email = this.txtEmail.getText();
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if(email.matches(emailRegex)){
            if(!this.pwdPassword.getText().isEmpty() && !this.txtName.getText().isEmpty() && !this.txtSurname.getText().isEmpty() &&
                    !(this.DateBirthdate.getValue() == null) && !this.txtPhone.getText().isEmpty() &&
                    !this.txtAddress.getText().isEmpty() && this.txtYear.getValue() != 0 && this.txtParalel.getValue() != 0){
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
                    int paralel = this.txtParalel.getValue();
                    System.out.println("ALL GOOD");
                    try {
                        System.out.println("Register -> AuthService");
                        User user = UserAuthService.registerStudent(name, surname, birthdate, phone, address, year,paralel, email, password,"Student", imagePath);
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
        this.DateBirthdate.setValue(null);
        this.txtPhone.setText("");
        this.txtAddress.setText("");
        this.txtYear.getValueFactory().setValue(0);
        this.txtEmail.setText("");
        this.pwdPassword.setText("");
        this.profilePic.setImage(null);
        this.txtParalel.getValueFactory().setValue(0);
    }
public void checkWarnings(){
    if (this.txtName.getText().isEmpty()) {
        firstnamew.setVisible(true);
    }
    if (this.txtSurname.getText().isEmpty()) {
        lastnamew.setVisible(true);
    }
    if (this.DateBirthdate.getValue() == null) {
        birthdayw.setVisible(true);
    }
    if (this.txtPhone.getText().isEmpty()) {
        phonew.setVisible(true);
    }
    if (this.txtEmail.getText().isEmpty()) {
        addressw.setVisible(true);
    }
    if (this.txtYear.getValue() == 0) {
        gradeLvlw.setVisible(true);
    }
    if (this.txtParalel.getValue() == 0) {
        txtParalelw.setVisible(true);
    }
    if (this.pwdPassword.getText().isEmpty()) {
        passwordw.setVisible(true);
    }
}

    public void setWarnings( boolean set){
        birthdayw.setVisible(set);
        firstnamew.setVisible(set);
        lastnamew.setVisible(set);
        phonew.setVisible(set);
        emailw.setVisible(set);
        gradeLvlw.setVisible(set);
        addressw.setVisible(set);
        passwordw.setVisible(set);
        txtParalelw.setVisible(set);
        w.setVisible(set);
    }

}
