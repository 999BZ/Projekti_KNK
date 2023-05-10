package Controllers;

import Models.TeacherUser;
import Models.User;
import Repository.UserRepository;
import Services.UserAuthService;
import Services.WindowSizeUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.UUID;

public class TeacherInfoController implements Initializable {
    @FXML
    private BorderPane rootPane;
    User localTeacher;
    @FXML
    private Stage primaryStage;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField phone;
    @FXML
    private TextField address;
    @FXML
    private DatePicker birthday;
    @FXML
    private TextField email;
    @FXML
    private ImageView profilePic;
    @FXML
    private Button editButton;
    @FXML
    private Button updatePhoto;
    @FXML
    private Button removeButton;
    private TeacherUser teacher;
    private File selectedFile;
    private boolean isEditable = false;
    private String imagePath;
    private String oldImagePath;

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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        removeButton.setVisible(false);
        updatePhoto.setVisible(false);
    }
    @FXML
    private void handleRemoveButton() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/Dialog.fxml"));
        AnchorPane dialogPane = loader.load();
        DialogController dialogController = loader.getController();
        dialogController.setLabelText("Remove the Teacher from the System");
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(new Scene(dialogPane));
        dialogController.setDialogStage(dialogStage);
        dialogStage.showAndWait();

        if (dialogController.isConfirmClicked()) {
            localTeacher= UserRepository.getByEmail(email.getText());
            UserRepository.delete(localTeacher);
            String relativePath = localTeacher.getProfileImg();
            File oldImage = new File(relativePath);
            oldImage.delete();
            loader = new FXMLLoader(getClass().getResource("/Main/Teachers.fxml"));
            try {
                Parent root = loader.load();
                Scene scene = new Scene(root, WindowSizeUtils.windowWidth, WindowSizeUtils.windowHeight);
                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleEditButton() throws SQLException {
        if (!isEditable) {
            firstname.setEditable(true);
            lastname.setEditable(true);
            phone.setEditable(true);
            birthday.setEditable(true);
            address.setEditable(true);
            email.setEditable(true);
            updatePhoto.setVisible(true);
            removeButton.setVisible(true);
            editButton.setText("Save");
            isEditable = true;
        } else {

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

                    String relativePath = localTeacher.getProfileImg();
//                                .replace("src/main/resources", "");
                    File oldImage = new File(relativePath);
                    oldImage.delete();
                    System.out.println(oldImage.getAbsolutePath());

                } catch (IOException ex) {
                    System.out.println("Error saving image: " + ex.getMessage());
                }

            }
            else {
                localTeacher = UserRepository.getByEmail(email.getText());
                imagePath = localTeacher.getProfileImg();
            }


            String emailValue = this.email.getText();
            String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
            if(emailValue.matches(emailRegex)){
                if( !this.firstname.getText().isEmpty() && !this.lastname.getText().isEmpty() &&
                        !(this.birthday.getValue() == null) && !this.phone.getText().isEmpty() &&
                        !this.address.getText().isEmpty()){
                    String nameValue = this.firstname.getText();
                    String surnameValue = this.lastname.getText();
                    LocalDate tempBirthdateValue = this.birthday.getValue();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String birthdateValue = tempBirthdateValue.format(formatter);
                    String phoneValue = this.phone.getText();
                    String addressValue = this.address.getText();
                    System.out.println("ALL GOOD");
                    try {
                        System.out.println("Register -> AuthService");
                        User user = UserAuthService.updateTeacher(teacher.getID(), nameValue, surnameValue, birthdateValue, phoneValue, addressValue, emailValue,"Teacher", imagePath);
                        firstname.setEditable(false);
                        lastname.setEditable(false);
                        phone.setEditable(false);
                        birthday.setEditable(false);
                        address.setEditable(false);
                        email.setEditable(false);
                        updatePhoto.setVisible(false);
                        editButton.setText("Edit or Delete Teacher");
                        isEditable = false;
                        removeButton.setVisible(true);
                    }catch (SQLException sqlException) {
                        System.out.println("Teacher couldn't be updated.");
                    }
                }else{
                    System.out.println("Please fill all text fields");
                }
            }else{
                System.out.println("Please check your email and try again!");

            }

        }
    }

    public void setTeacher(TeacherUser teacher) {
        this.teacher = teacher;
        if (teacher != null) {
            firstname.setText(teacher.getName());
            lastname.setText(teacher.getSurname());
            phone.setText(teacher.getPhone());
            birthday.setValue(LocalDate.parse(teacher.getBirthdate()));
            address.setText(teacher.getAddress());
            email.setText(teacher.getEmail());

            try {
                String relativePath = teacher.getProfileImg().replace("src/main/resources", "");
                Image image = new Image(getClass().getResourceAsStream(relativePath));
                profilePic.setImage(image);
            } catch (Exception e)  {
                System.out.println("Error loading image: " + e.getMessage());

            }
        }
        localTeacher = teacher;
    }



}

