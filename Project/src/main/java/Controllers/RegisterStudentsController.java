package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import Models.User;
import Services.UserAuthService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.UUID;

public class RegisterStudentsController implements Initializable {

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

    public void initialize(URL url, ResourceBundle rb) {
        txtYear.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 12, 0));
    }

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField pwdPassword;
    @FXML
    private ImageView profilePic;
    private String imagePath;

    File selectedFile;
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
                        User user = UserAuthService.registerStudent(name, surname, birthdate, phone, address, year, email, password,"Student", imagePath);
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
                        System.out.println("Student couldn't register.");
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


}
