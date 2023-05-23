package Controllers;

import Models.Subject;
import Models.TeacherUser;
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
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class TeacherInfoController implements Initializable {
    @FXML
    private Label lblPhone;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblBirthdate;
    @FXML
    private Label lblAddress;
    @FXML
    private Label lblGender;
    @FXML
    private Label teacherSubjects;
    @FXML
    private Label teacherInfo;
    @FXML
    private BorderPane rootPane;
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
    private ImageView addressw;
    @FXML
    private ImageView genderw;
    @FXML
    private Label w;
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
    private ChoiceBox<String> gender;
    @FXML
    private ImageView profilePic;
    @FXML
    private Button editButton;
    @FXML
    private Button updateProfilePic;
    @FXML
    private Button removeButton;
    @FXML
    private Button reset;
    private TeacherUser teacher;
    private File selectedFile;
    private boolean isEditable = false;
    private String imagePath;
    private String oldImagePath;
    private String userPosition;
    @FXML
    private AnchorPane sidenav;

    @FXML
    private Button statistics;
    private ResourceBundle bundle;
    private ObservableList<Subject> subjectsList = FXCollections.observableArrayList();

    private int T_ID;


    @FXML
    private VBox subjectsBox;
    @FXML
    private void handleStatisticsButton(ActionEvent event) throws IOException {
    }
    @FXML
    private void handleImageUploadButton(ActionEvent event) throws IOException {
       selectedFile =  GeneralUtil.handleImageUpdate(profilePic);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (LanguageUtil.getLanguage().equals("Albanian")){
            setAlbanian();
        } else {
            setEnglish();
        }

        LanguageUtil.languageProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Albanian")) {
                setAlbanian();
            } else {
                setEnglish();
            }
        });

        statistics.setOnAction(e->{
            try {
                handleStatisticsButton();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // get the user position
        Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
        this.userPosition = preferences.get("position", null);
        if(this.userPosition.equals("Admin")){
            editButton.setVisible(true);
            reset.setVisible(false);
        }else{
            editButton.setVisible(false);
            sidenav.getStyleClass().clear();
            sidenav.getStyleClass().add("profile");
        }
        setEditable(false);
        setWarnings(false);

        this.gender.getItems().addAll("M","F");
    }
    @FXML
    private void handleRemoveButton() throws IOException, SQLException {
        if (LanguageUtil.getLanguage().equals("Albanian")){
            if (GeneralUtil.setDialog("Largo profesorin nga sistemi?")) {
                UserRepository.delete(localTeacher);
                String relativePath = localTeacher.getProfileImg();
                File oldImage = new File(relativePath);
                oldImage.delete();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/Teachers.fxml"));
                try {
                    Parent root = loader.load();
                    Scene scene = new Scene(root, WindowSizeUtils.windowWidth, WindowSizeUtils.windowHeight);
                    Stage stage = (Stage) rootPane.getScene().getWindow();
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (GeneralUtil.setDialog("Remove the Teacher from the System?")) {
                UserRepository.delete(localTeacher);
                String relativePath = localTeacher.getProfileImg();
                File oldImage = new File(relativePath);
                oldImage.delete();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/Teachers.fxml"));
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
    }

    @FXML
    private void handleEditButton() throws SQLException, IOException {
        if (!isEditable) {
            setEditable(true);
        } else {

            if (selectedFile != null) {
                if (selectedFile != null) {
                    imagePath = GeneralUtil.savePhoto(selectedFile);
                    System.out.println(imagePath);
                    String relativePath = localTeacher.getProfileImg();

                    File oldImage;
                    if(relativePath!=null){
                        oldImage = new File(relativePath);
                        oldImage.delete();
                    }
                }
            }
            else {
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
                    if(GeneralUtil.setDialog("Edit Teacher's info")){
                        try {
                            System.out.println("Register -> AuthService");
                            User user = UserAuthService.updateTeacher(teacher.getID(), nameValue, surnameValue, birthdateValue, phoneValue,this.gender.getValue(), addressValue, emailValue,"Teacher", imagePath);
                            setWarnings(false);
                        }catch (SQLException sqlException) {
                            System.out.println("Teacher couldn't be updated.");
                        }
                        setEditable(false);
                        w.setVisible(false);
                        setWarnings(false);
                    }
                }else{
                    System.out.println("Please fill all text fields");
                    w.setText("Please fill out all required fields");
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

    public void setTeacher(TeacherUser teacher) {
        this.teacher = teacher;
        try {
            subjectsList = FetchData.getTeacherSubjects(teacher);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        CardGenUtil.subjectsToFlowPaneTeachers(subjectsBox, subjectsList, teacher);
        if (teacher != null) {
            firstname.setText(teacher.getName());
            lastname.setText(teacher.getSurname());
            phone.setText(teacher.getPhone());
            birthday.setValue(LocalDate.parse(teacher.getBirthdate()));
            address.setText(teacher.getAddress());
            email.setText(teacher.getEmail());
            this.T_ID = teacher.getID();
            this.gender.setValue(teacher.getGender());
            try {
                String relativePath = teacher.getProfileImg().replace("src/main/resources", "");
                InputStream inputStream = getClass().getResourceAsStream(relativePath);
                if (inputStream != null) {
                    Image image = new Image(inputStream);
                    profilePic.setImage(image);
                } else {
                    System.out.println("Error loading image: " + teacher.getProfileImg());
                }
                inputStream.close();

            } catch (Exception e)  {
                System.out.println("Error loading image: " + e.getMessage());

            }
        }
        localTeacher = teacher;
    }

    public void handleStatisticsButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(GeneralUtil.class.getResource("/Main/TeacherStatistics.fxml"));
        AnchorPane statisticsPane = loader.load();
        TeacherStatisticsController statisticsController = loader.getController();
        Stage StatisticStage = new Stage();
        StatisticStage.initModality(Modality.APPLICATION_MODAL);
        StatisticStage.setScene(new Scene(statisticsPane));
        StatisticStage.setResizable(false);
        statisticsController.setTeacher(this.teacher);
        StatisticStage.showAndWait();
    }

    public void setEditable(boolean set){
        firstname.setEditable(set);
        lastname.setEditable(set);
        phone.setEditable(set);
        birthday.setDisable(!set);
        address.setEditable(set);
        email.setEditable(set);
        updateProfilePic.setVisible(set);
        removeButton.setVisible(set);
        reset.setVisible(set);
        gender.setDisable(!set);
        if(set) {
            if (LanguageUtil.getLanguage().equals("Albanian")){
                editButton.setText("Ruaj");
            } else {
                editButton.setText("Save");
            }
        }else{
            if (LanguageUtil.getLanguage().equals("Albanian")){
                editButton.setText("Ndrysho ose fshij mësuesin");
            } else {
                editButton.setText("Edit or Delete Teacher");
            }
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
        addressw.setVisible(set);
        w.setVisible(set);
        genderw.setVisible(set);
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
        if (this.email.getText().isEmpty()){
            emailw.setVisible(true);
        }
        if (this.gender.getValue() == null){
            genderw.setVisible(true);
        }
    }

    @FXML
    public void changePassword() throws IOException {
        GeneralUtil.setChangePassword(this.T_ID);
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
        lblPhone.setText(bundle.getString("phone"));
        lblEmail.setText(bundle.getString("email"));
        lblBirthdate.setText(bundle.getString("birthdate"));
        lblAddress.setText(bundle.getString("address"));
        lblGender.setText(bundle.getString("gender"));
        removeButton.setText(bundle.getString("remove"));
        editButton.setText(bundle.getString("editRemoveTeacher"));
        reset.setText(bundle.getString("reset"));
        w.setText(bundle.getString("w"));
        statistics.setText(bundle.getString("statistics"));
        teacherSubjects.setText(bundle.getString("teacherSubjects"));
        teacherInfo.setText(bundle.getString("teacherInfo"));
        updateProfilePic.setText(bundle.getString("updateProfilePic"));
    }
}

