package Controllers;

import Models.Grade;
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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

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
    private ImageView genderw;
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
    private ChoiceBox<String> gender;
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
    private Button updateProfilePic;
    @FXML
    private Button removeButton;
    @FXML
    private Button chooseSubjectButton;
    @FXML
    private AnchorPane sidenav;
    @FXML
    private Button reset;
    private StudentUser student;
    private File selectedFile;
    private boolean isEditable = false;
    private String imagePath;
    private String oldImagePath;
    private StudentUser studentUser;
    private ArrayList<Grade> gradesList;

    private int S_ID;
    private double average;


    private String userPosition;
    private ObservableList<Subject> subjectsList = FXCollections.observableArrayList();

    @FXML
    private void handleImageUploadButton(ActionEvent event) throws IOException {
      selectedFile = GeneralUtil.handleImageUpdate(profilePic);
    }

    @FXML
    private void handleStatisticsButton(ActionEvent event) throws IOException {
        try {
            this.average = FetchData.getAverageOfGrades(this.S_ID);
            System.out.println(this.average);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        GeneralUtil.getStatistics(this.S_ID,this.average);

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //get user position
        Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
        this.userPosition = preferences.get("position", null);
        System.out.println(this.userPosition);
        if(this.userPosition.equals("Admin")){
            editButton.setVisible(true);
            chooseSubjectButton.setVisible(false);

        }else{
            reset.setVisible(true);
            editButton.setVisible(false);
            sidenav.getStyleClass().clear();
            sidenav.getStyleClass().add("profile");

        }
        if(this.userPosition.equals("Student")){
            editButton.setVisible(false);
        }

        setWarnings(false);
        gradeLvl.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 12, 0));

        paralel.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 3, 0));
        Image oldImage =profilePic.getImage();
        oldImagePath = oldImage.getUrl();
        setEditable(false);

        this.gender.getItems().addAll("M","F");




    }

    @FXML
    private void handleRemoveButton() throws IOException, SQLException {

        if (GeneralUtil.setDialog("Remove the Student from the System")) {
            UserRepository.delete(localStudent);
            String relativePath = localStudent.getProfileImg();
            if(relativePath!=null){
            File oldImage = new File(relativePath);
            oldImage.delete();}
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/Students.fxml"));
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
    private void handleEditButton() throws SQLException, IOException {
        if (!isEditable) {
            setEditable(true);
        } else {

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
                            String gender = this.gender.getValue();
                            int yearValue = this.gradeLvl.getValue();
                            int paralelValue = this.paralel.getValue();
                            System.out.println("ALL GOOD");
                            if(GeneralUtil.setDialog("Edit Student's info")){
                                try {
                                    System.out.println("Register -> AuthService");
                                    User user = UserAuthService.updateStudent(student.getID(), nameValue, surnameValue, birthdateValue, phoneValue,gender, addressValue, yearValue,paralelValue, emailValue,"Student", imagePath);
                                }catch (SQLException sqlException) {
                                    System.out.println("Student couldn't be updated.");
                                }
                                setEditable(false);
                                w.setVisible(false);
                                setWarnings(false);
                            }
                    }else{
                        System.out.println("Please fill all text fields");
                        w.setVisible(true);
                        checkWarnings();
                    }

                }else{
                    System.out.println("Please check your email and try again!");
                    emailw.setVisible(true);
                    w.setVisible(true);
                    checkWarnings();
                }

            }
        }

    public void setStudent(StudentUser student) {
        this.student = student;

        //setting the student subjects to the scrollpane
        try {
            subjectsList = FetchData.getStudentSubjects(student);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        CardGenUtil.subjectsToFlowPaneStudents(subjectsBox, subjectsList, student);

        //checking if student has elected elective subject(to or not to add the choose button)
        try {
            if(!FetchData.hasStudentSetElectiveSubject(this.student) && this.userPosition.equals("Student")){
                System.out.println("Student has not set elective subject");
                chooseSubjectButton.setVisible(true);
            }else{
                System.out.println("Student has set elective subject");
                chooseSubjectButton.setVisible(false);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //filling the information
        if (student != null) {
            firstname.setText(student.getName());
            lastname.setText(student.getSurname());
            phone.setText(student.getPhone());
            birthday.setValue(LocalDate.parse(student.getBirthdate()));
            address.setText(student.getAddress());
            email.setText(student.getEmail());
            this.S_ID = student.getID();
            this.gender.setValue(student.getGender());
            this.gradesList = FetchData.getAllGrades(this.S_ID);
            for (int i=0;i<this.gradesList.size();i++){
                System.out.println(gradesList.get(i).getGrade());
            }
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

    public void chooseSubject() throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/ChooseElectiveSubject.fxml"));
        AnchorPane addSubjectPane = loader.load();
        ChooseElectiveSubjectController subjectController = loader.getController();
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(new Scene(addSubjectPane));
        subjectController.setStage(dialogStage);
        subjectController.setParentController(this);
        subjectController.setData(this.student);
        dialogStage.showAndWait();
    }


    public void setEditable(boolean set){
        firstname.setEditable(set);
        lastname.setEditable(set);
        phone.setEditable(set);
        birthday.setDisable(!set);
        address.setEditable(set);
        email.setEditable(set);
        gradeLvl.setDisable(!set);
        paralel.setDisable(!set);
        updateProfilePic.setVisible(set);
        removeButton.setVisible(set);
        gender.setDisable(!set);
        if(this.userPosition.equals("Admin")) {
            reset.setVisible(set);
        }
        if(set) {
            editButton.setText("Save");
        }else{
            editButton.setText("Edit or Delete Student");
        }
        isEditable = set;
    }
    public void setWarnings(boolean set){
        removeButton.setVisible(set);
        birthdayw.setVisible(set);
        firstnamew.setVisible(set);
        lastnamew.setVisible(set);
        phonew.setVisible(set);
        emailw.setVisible(set);
        gradeLvlw.setVisible(set);
        paralelw.setVisible(set);
        addressw.setVisible(set);
        genderw.setVisible(set);
        w.setVisible(set);
    }

    public void checkWarnings(){
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
        if (this.gender.getValue() == null){
            genderw.setVisible(true);
        }
    }
    @FXML
    public void changePassword() throws IOException {
        GeneralUtil.setChangePassword(this.S_ID);
    }


}
