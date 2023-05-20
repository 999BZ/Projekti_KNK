package Controllers;

import Models.StudentUser;
import Services.ConnectionUtil;
import Services.FetchData;
import Services.LanguageUtil;
import Services.WindowSizeUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class StudentsController {

    private  ObservableList<StudentUser> studentsList = FXCollections.observableArrayList();
    @FXML
    private BorderPane rootPane;
    @FXML
    private TableView<StudentUser> studentsTable;
    @FXML
    private TableColumn<StudentUser, String> name;
    @FXML
    private TableColumn<StudentUser, String> surname;
    @FXML
    private TableColumn<StudentUser, String> birthdate;
    @FXML
    private TableColumn<StudentUser, Integer> year;
    @FXML
    private TableColumn<StudentUser, Integer> paralel;
    @FXML
    private TableColumn<StudentUser, String> phone;
    @FXML
    private TableColumn<StudentUser, String> email;
    @FXML
    private TableColumn<StudentUser, String> address;
    @FXML
    private TableColumn<StudentUser, String> gender;
    @FXML
    private TextField searchInput;

    @FXML
    private Label lblLevel;
    @FXML
    private ChoiceBox<String> levelChoice;
    @FXML
    private Label lblParalel;
    @FXML
    private ChoiceBox<String> paralelChoice;
    @FXML
    private Button addStudentButton;
    private ResourceBundle bundle;

    public StudentsController() {
    }

    public void initialize() throws SQLException {
        this.studentsList = FetchData.getAllStudents();
        fillTable();
        searchInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                studentsList.clear();
                studentsList.addAll(FetchData.searchAllStudents(searchInput.getText()));
                try {
                    fillTable();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        this.levelChoice.getItems().addAll("All","1","2","3","4","5","6","7","8","9","10","11","12");
        this.paralelChoice.getItems().addAll("All","1","2","3");

        this.levelChoice.setValue("All");
        this.paralelChoice.setValue("All");
        this.paralelChoice.setVisible(false);
        this.lblParalel.setVisible(false);

        this.levelChoice.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals("All")) {
                this.paralelChoice.setVisible(true);
                this.lblParalel.setVisible(true);
            }else{
                this.paralelChoice.setVisible(false);
                this.lblParalel.setVisible(false);
            }
        });

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
    }
    public void fillTable() throws SQLException {

        for (StudentUser student:studentsList) {
            this.name.setCellValueFactory(new PropertyValueFactory<>("Name"));
            this.surname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
            this.birthdate.setCellValueFactory(new PropertyValueFactory<>("Birthdate"));
            this.year.setCellValueFactory(new PropertyValueFactory<>("Year"));
            this.phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
            this.email.setCellValueFactory(new PropertyValueFactory<>("Email"));
            this.address.setCellValueFactory(new PropertyValueFactory<>("Address"));
            this.paralel.setCellValueFactory(new PropertyValueFactory<>("Paralel"));
            this.gender.setCellValueFactory(new PropertyValueFactory<>("Gender"));

            studentsTable.setItems(studentsList);

            studentsTable.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    StudentUser selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
                    if (selectedStudent != null) {
                        showStudentInfo(selectedStudent);
                    }
                }
            });
        }
    }

    private void showStudentInfo(StudentUser student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/StudentInfo.fxml"));
            Parent root = loader.load();
            StudentInfoController controller = loader.getController();
            controller.setStudent(student);
            Scene scene = new Scene(root, WindowSizeUtils.windowWidth, WindowSizeUtils.windowHeight);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void filterStudents() throws SQLException {
        String levelChoice = this.levelChoice.getValue();
        String paralelChoice = this.paralelChoice.getValue();
        this.studentsList.clear();
        if(levelChoice.equals("All")){
            this.studentsList = FetchData.getAllStudents();
        }else if(paralelChoice.equals("All")){
            this.studentsList = FetchData.getStudentsByGLevel(levelChoice);
        }else {
            this.studentsList = FetchData.getStudentsByGLevelParalel(levelChoice, paralelChoice);
        }
        fillTable();
    }

    // ...
    @FXML
    private void goToAddStudents() {
        // Load the Courses page FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/AddStudent.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root, WindowSizeUtils.windowWidth, WindowSizeUtils.windowHeight);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void searchStudents(ActionEvent event) throws SQLException {
        if(!searchInput.getText().isEmpty()) {
            this.studentsList.clear();
            this.studentsList = FetchData.searchAllStudents(searchInput.getText());
            fillTable();
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
        name.setText(bundle.getString("name"));
        surname.setText(bundle.getString("surname"));
        birthdate.setText(bundle.getString("birthdate"));
        phone.setText(bundle.getString("phone"));
        address.setText(bundle.getString("address"));
        email.setText(bundle.getString("email"));
        year.setText(bundle.getString("year"));
        paralel.setText(bundle.getString("paralel"));
        addStudentButton.setText(bundle.getString("addStudent"));
    }
}
