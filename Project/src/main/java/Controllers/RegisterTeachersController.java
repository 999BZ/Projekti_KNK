package Controllers;

import Models.User;
import Services.LanguageUtil;
import Services.GeneralUtil;
import Services.UserAuthService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class RegisterTeachersController implements Initializable  {
    private Stage primaryStage;

    @FXML
    private Button register;
    @FXML
    private Button reset;
    @FXML
    private Button addProfilePic;
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
    @FXML
    private Label name;
    @FXML
    private Label surname;
    @FXML
    private Label birthdate;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label password;
    @FXML
    private Label regTeacher;

    private String imagePath;
    private ResourceBundle bundle;

    @FXML
    private ChoiceBox<String> genderChoice;

    @FXML
    private ImageView genderW;

    File selectedFile;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setWarnings(false);

        LanguageUtil.languageProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Albanian")) {
                setAlbanian();
            } else {
                setEnglish();
            }
        });
        genderChoice.getItems().addAll("M","F");
    }
    @FXML
    private void handleImageUploadButton(ActionEvent event) throws IOException {
        selectedFile = GeneralUtil.handleImageUpdate(profilePic);
    }

    @FXML
    private void registerClick(ActionEvent e) throws IOException{
        setWarnings(false);
        if (selectedFile != null) {
            imagePath = GeneralUtil.savePhoto(selectedFile);
        }

        if(!this.pwdPassword.getText().isEmpty() && !this.txtName.getText().isEmpty() && !this.txtSurname.getText().isEmpty() &&
                !(this.DateBirthdate.getValue() == null) && !this.txtPhone.getText().isEmpty() &&
                !this.txtAddress.getText().isEmpty() && this.genderChoice.getValue() != null){
            String email = this.txtEmail.getText();
            String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
            if(email.matches(emailRegex)){
                String password = this.pwdPassword.getText();
                if(password.length() > 7){
                    String name = this.txtName.getText();
                    String surname = this.txtSurname.getText();
                    LocalDate tempBirthdate = this.DateBirthdate.getValue();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String birthdate = tempBirthdate.format(formatter);
                    String phone = this.txtPhone.getText();
                    String address = this.txtAddress.getText();
                    String gender = this.genderChoice.getValue();
                    System.out.println("ALL GOOD");
                    if (GeneralUtil.setDialog("Add the Teacher to the System")) {
                        try {
                            System.out.println("Register -> AuthService");
                            User user = UserAuthService.registerTeacher(name, surname, birthdate, phone,gender,address, email, password,"Teacher",imagePath );
                            if (user != null){
                                System.out.println("User Registered");
                                cancelClick(new ActionEvent());
                                setWarnings(false);
                                GeneralUtil.goToFXML("/Main/Teachers.fxml",(Stage) txtName.getScene().getWindow());

                            }
                        }catch (SQLException sqlException) {
                            System.out.println("Teacher couldn't register.");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }else{
                    System.out.println("Password is too short!");
                    w.setText("Password is too short!");
                }
            }else{
                System.out.println("Please check your email and try again!");
                emailw.setVisible(true);
                w.setText("Please fill out all required fields");
                w.setVisible(true);
                checkWarnings();
            }
        }else{
            System.out.println("Please fill all text fields");
            w.setText("Please fill out all required fields");
            w.setVisible(true);
            checkWarnings();

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
        this.genderChoice.setValue(null);
    }
    public void setWarnings(boolean set){
        birthdayw.setVisible(set);
        firstnamew.setVisible(set);
        lastnamew.setVisible(set);
        phonew.setVisible(set);
        emailw.setVisible(set);
        addressw.setVisible(set);
        passwordw.setVisible(set);
        genderW.setVisible(set);
        w.setVisible(set);
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
        if (this.txtAddress.getText().isEmpty()) {
            addressw.setVisible(true);
        }
        if (this.txtEmail.getText().isEmpty()) {
            emailw.setVisible(true);
        }
        if (this.pwdPassword.getText().isEmpty()) {
            passwordw.setVisible(true);
        }
        if (this.genderChoice.getValue() == null) {
            genderW.setVisible(true);
        }
    }

    public void setAlbanian() {
        try {
            Locale locale = new Locale("sq");
            bundle = ResourceBundle.getBundle("Bilinguality.NameTags_sq", locale);

            updateLabels();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setEnglish() {
        try {
            Locale locale = Locale.ENGLISH;
            bundle = ResourceBundle.getBundle("Bilinguality.NameTags_en", locale);

            updateLabels();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private void updateLabels() {
        name.setText(bundle.getString("name") + ":");
        surname.setText(bundle.getString("surname") + ":");
        birthdate.setText(bundle.getString("birthdate") + ":");
        phone.setText(bundle.getString("phone") + ":");
        address.setText(bundle.getString("address") + ":");
        email.setText(bundle.getString("email") + ":");
        password.setText(bundle.getString("password") + ":");
        register.setText(bundle.getString("register"));
        reset.setText(bundle.getString("reset"));
        w.setText(bundle.getString("w"));
        addProfilePic.setText(bundle.getString("addProfilePic"));
        regTeacher.setText(bundle.getString("regTeacher"));
    }
}