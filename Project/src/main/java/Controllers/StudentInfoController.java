package Controllers;

import Models.StudentUser;
import Models.Subject;
import Models.User;
import Repository.UserRepository;
import Services.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class StudentInfoController implements Initializable {
    @FXML
    private VBox subjectsBox;
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
    private ImageView paralelw;
    @FXML
    private ImageView addressw;
    @FXML
    private Label w;

    @FXML
    private BorderPane rootPane;
    User localStudent;
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
    private Spinner<Integer> gradeLvl;
    @FXML
    private Spinner<Integer> paralel;
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
    private StudentUser student;
    private File selectedFile;
    private boolean isEditable = false;
    private String imagePath;
    private String oldImagePath;
    private StudentUser studentUser;
    private ObservableList<Subject> subjectsList = FXCollections.observableArrayList();

    @FXML
    private void handleImageUploadButton(ActionEvent event) throws IOException {
      selectedFile = GeneralUtil.handleImageUpdate(profilePic);


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        birthdayw.setVisible(false);
        firstnamew.setVisible(false);
        lastnamew.setVisible(false);
        phonew.setVisible(false);
        emailw.setVisible(false);
        gradeLvlw.setVisible(false);
        paralelw.setVisible(false);
        addressw.setVisible(false);
        w.setVisible(false);
        gradeLvl.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 12, 0));
        gradeLvl.setDisable(true);
        paralel.setDisable(true);
        paralel.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 3, 0));
        Image oldImage =profilePic.getImage();
        oldImagePath = oldImage.getUrl();
        updatePhoto.setVisible(false);
        removeButton.setVisible(false);
    }
    @FXML
    private void handleRemoveButton() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/Dialog.fxml"));
        AnchorPane dialogPane = loader.load();
        DialogController dialogController = loader.getController();
        dialogController.setLabelText("Remove the Student from the System");
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(new Scene(dialogPane));
        dialogController.setDialogStage(dialogStage);
        dialogStage.showAndWait();

        if (dialogController.isConfirmClicked()) {
            UserRepository.delete(localStudent);
            String relativePath = localStudent.getProfileImg();
            if(relativePath!=null){
            File oldImage = new File(relativePath);
            oldImage.delete();}
            loader = new FXMLLoader(getClass().getResource("/Main/Students.fxml"));
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
            gradeLvl.setDisable(false);
            paralel.setDisable(false);
            updatePhoto.setVisible(true);
            removeButton.setVisible(true);
            editButton.setText("Save");
            isEditable = true;
        } else {
            removeButton.setVisible(false);
            birthdayw.setVisible(false);
            firstnamew.setVisible(false);
            lastnamew.setVisible(false);
            phonew.setVisible(false);
            emailw.setVisible(false);
            gradeLvlw.setVisible(false);
            paralelw.setVisible(false);
            addressw.setVisible(false);
            w.setVisible(false);
            System.out.println("TEst");

                if (selectedFile != null) {
                    imagePath = GeneralUtil.savePhoto(selectedFile);
                    String relativePath = localStudent.getProfileImg();

                    File oldImage;
                    if(relativePath!=null){
                        oldImage = new File(relativePath);
                        oldImage.delete();
                    }
                }
                else {
                    imagePath = localStudent.getProfileImg();
                }


                String emailValue = this.email.getText();
                String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
                if(emailValue.matches(emailRegex)){
                    if( !this.firstname.getText().isEmpty() && !this.lastname.getText().isEmpty() &&
                            !(this.birthday.getValue() == null) && !this.phone.getText().isEmpty() &&
                            !this.address.getText().isEmpty() && this.gradeLvl.getValue() != null && this.paralel.getValue() != null){
                            String nameValue = this.firstname.getText();
                            String surnameValue = this.lastname.getText();
                            LocalDate tempBirthdateValue = this.birthday.getValue();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            String birthdateValue = tempBirthdateValue.format(formatter);
                            String phoneValue = this.phone.getText();
                            String addressValue = this.address.getText();
                            int yearValue = this.gradeLvl.getValue();
                            int paralelValue = this.paralel.getValue();
                            System.out.println("ALL GOOD");
                            try {
                                System.out.println("Register -> AuthService");
                                User user = UserAuthService.updateStudent(student.getID(), nameValue, surnameValue, birthdateValue, phoneValue, addressValue, yearValue,paralelValue, emailValue,"Student", imagePath);
                                firstname.setEditable(false);
                                lastname.setEditable(false);
                                phone.setEditable(false);
                                birthday.setEditable(false);
                                address.setEditable(false);
                                email.setEditable(false);
                                gradeLvl.setDisable(true);
                                paralel.setDisable(true);
                                updatePhoto.setVisible(false);
                                editButton.setText("Edit or Delete Student");
                                isEditable = false;
                                removeButton.setVisible(false);
                                birthdayw.setVisible(false);
                                firstnamew.setVisible(false);
                                lastnamew.setVisible(false);
                                phonew.setVisible(false);
                                emailw.setVisible(false);
                                gradeLvlw.setVisible(false);
                                paralelw.setVisible(false);
                                addressw.setVisible(false);
                                w.setVisible(false);
                            }catch (SQLException sqlException) {
                                System.out.println("Student couldn't be updated.");
                            }

                    }else{
                        System.out.println("Please fill all text fields");
                        w.setVisible(true);
                        if (this.firstname.getText().isEmpty()) {
                            firstnamew.setVisible(true);
                        }
                        if (this.lastname.getText().isEmpty()) {
                            lastnamew.setVisible(true);
                        }
                        if (this.birthday.getValue() == null) {
                            birthdayw.setVisible(true);
                        }
                        if (this.phone.getText().isEmpty()) {
                            phonew.setVisible(true);
                        }
                        if (this.address.getText().isEmpty()) {
                            addressw.setVisible(true);
                        }
                        if (this.gradeLvl.getValue() == null) {
                            gradeLvlw.setVisible(true);
                        }
                        if (this.paralel.getValue() == null) {
                            paralelw.setVisible(true);
                        }
                        if (this.email.getText().isEmpty()){
                            emailw.setVisible(true);
                        }
                    }

                }else{
                    System.out.println("Please check your email and try again!");
                    emailw.setVisible(true);
                    w.setVisible(true);
                    if (this.firstname.getText().isEmpty()) {
                        firstnamew.setVisible(true);
                    }
                    if (this.lastname.getText().isEmpty()) {
                        lastnamew.setVisible(true);
                    }
                    if (this.birthday.getValue() == null) {
                        birthdayw.setVisible(true);
                    }
                    if (this.phone.getText().isEmpty()) {
                        phonew.setVisible(true);
                    }
                    if (this.address.getText().isEmpty()) {
                        addressw.setVisible(true);
                    }
                    if (this.gradeLvl.getValue() == null) {
                        gradeLvlw.setVisible(true);
                    }
                    if (this.paralel.getValue() == null) {
                        paralelw.setVisible(true);
                    }
                    if (this.email.getText().isEmpty()){
                        emailw.setVisible(true);
                    }
                }

            }
        }

    public void setStudent(StudentUser student) {
        this.student = student;
        try {
            subjectsList = FetchData.getStudentSubjects(student);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Subject subject:subjectsList
             ) {
            System.out.println(subject.getName());
        }
        CardGenUtil.subjectsToFlowPaneStudents(subjectsBox, subjectsList, student);
        if (student != null) {
            firstname.setText(student.getName());
            lastname.setText(student.getSurname());
            phone.setText(student.getPhone());
            birthday.setValue(LocalDate.parse(student.getBirthdate()));
            address.setText(student.getAddress());
            email.setText(student.getEmail());
            if (student.getYear() != 0) {
                gradeLvl.getValueFactory().setValue(student.getYear());            }
            if (student.getParalel() != 0) {
                paralel.getValueFactory().setValue(student.getParalel());            }
            try {
                String relativePath = student.getProfileImg().replace("src/main/resources", "");
                InputStream inputStream = getClass().getResourceAsStream(relativePath);
                if (inputStream != null) {
                    Image image = new Image(inputStream);
                    profilePic.setImage(image);
                } else {
                    System.out.println("Error loading image: " + student.getProfileImg());
                }
                inputStream.close();
            } catch (Exception e)  {
                System.out.println("Error loading image: " + e.getMessage());

            }
        }
        localStudent = student;
    }



}
