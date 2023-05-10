package Controllers;

import Models.User;
import Services.UserAuthService;
import Services.WindowSizeUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.UUID;

public class RegisterTeachersController implements Initializable  {
    private Stage primaryStage;

    @FXML
    private ImageView birthdayw;
    @FXML
    private ImageView firstnamew;
    @FXML
    private ImageView lastnamew;
    @FXML
    private ImageView phonew;
    @FXML
    private ImageView emailw;;
    @FXML
    private ImageView addressw;
    @FXML
    private ImageView passwordw;
    @FXML
    private Label w;
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
    private TextField txtEmail;

    @FXML
    private PasswordField pwdPassword;
    @FXML
    private ImageView profilePic;
    private String imagePath;

    File selectedFile;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        birthdayw.setVisible(false);
        firstnamew.setVisible(false);
        lastnamew.setVisible(false);
        phonew.setVisible(false);
        emailw.setVisible(false);
        addressw.setVisible(false);
        passwordw.setVisible(false);
        w.setVisible(false);
    }
    @FXML
    private void handleImageUploadButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            Image newImage = new Image(selectedFile.toURI().toString());
            profilePic.setImage(newImage);
        }
    }

    @FXML
    private void registerClick(ActionEvent e){
        if (selectedFile != null) {
            try {
                String fileName = selectedFile.getName();
                String extension = fileName.substring(fileName.lastIndexOf("."));
                String newFileName = UUID.randomUUID().toString() + extension;
                imagePath = "src/main/resources/Images/ProfilePics/" + newFileName;
                File destination = new File(imagePath);
                InputStream source = new FileInputStream(selectedFile);
                OutputStream output = new FileOutputStream(destination);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = source.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
                source.close();
                output.close();
                System.out.println("Writing file to " + imagePath);
            } catch (IOException ex) {
                System.out.println("Error saving image: " + ex.getMessage());
            }
        }


        String email = this.txtEmail.getText();
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if(email.matches(emailRegex)){
            if(!this.pwdPassword.getText().isEmpty() && !this.txtName.getText().isEmpty() && !this.txtSurname.getText().isEmpty() &&
                    !(this.DateBirthdate.getValue() == null) && !this.txtPhone.getText().isEmpty() &&
                    !this.txtAddress.getText().isEmpty()){
                String password = this.pwdPassword.getText();
                if(password.length() > 7){
                    String name = this.txtName.getText();
                    String surname = this.txtSurname.getText();
                    LocalDate tempBirthdate = this.DateBirthdate.getValue();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String birthdate = tempBirthdate.format(formatter);
                    String phone = this.txtPhone.getText();
                    String address = this.txtAddress.getText();
                    System.out.println("ALL GOOD");
                    try {
                        System.out.println("Register -> AuthService");
                        User user = UserAuthService.registerTeacher(name, surname, birthdate, phone, address, email, password,"Teacher",imagePath );
                        if (user != null){
                            System.out.println("User Registered");
                            this.txtName.setText("");
                            this.txtSurname.setText("");
                            this.DateBirthdate.setValue(null);
                            this.txtPhone.setText("");
                            this.txtAddress.setText("");
                            this.txtEmail.setText("");
                            this.pwdPassword.setText("");
                            birthdayw.setVisible(false);
                            firstnamew.setVisible(false);
                            lastnamew.setVisible(false);
                            phonew.setVisible(false);
                            emailw.setVisible(false);
                            addressw.setVisible(false);
                            passwordw.setVisible(false);
                            w.setVisible(false);
                            
                        }
                    }catch (SQLException sqlException) {
                        System.out.println("Teacher couldn't register.");
                    }
                }else{
                    System.out.println("Password is too short!");
                    w.setText("Password is too short!");
                }
            }else{
                System.out.println("Please fill all text fields");
                w.setText("Please fill out all required fields");
                w.setVisible(true);
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
            }
        }else{
            System.out.println("Please check your email and try again!");
            emailw.setVisible(true);
            w.setText("Please fill out all required fields");
            w.setVisible(true);
        }


    }
    @FXML
    private void cancelClick(ActionEvent e){
        this.txtName.setText("");
        this.txtSurname.setText("");
        this.DateBirthdate.setValue(null);
        this.txtPhone.setText("");
        this.txtAddress.setText("");
        this.txtEmail.setText("");
        this.pwdPassword.setText("");
    }


}