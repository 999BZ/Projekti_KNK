package Controllers;

import Models.StudentUser;
import Models.TeacherUser;
import Services.FetchData;
import Services.LanguageUtil;
import Services.WindowSizeUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class TeachersController {
    @FXML
    private BorderPane rootPane;
    @FXML
    private void goToAddTeachers() {
        // Load the Courses page FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/AddTeacher.fxml"));
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
    private TableView<TeacherUser> teachersTable;

    @FXML
    private TableColumn<StudentUser, String> name;

    @FXML
    private TableColumn<StudentUser, String> surname;

    @FXML
    private TableColumn<StudentUser, String> birthdate;

    @FXML
    private TableColumn<StudentUser, Integer> id;

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
    private Button addTeacherButton;
    private ResourceBundle bundle;

    private ObservableList<TeacherUser> teachersList = FXCollections.observableArrayList();
    public void initialize() throws SQLException {
        teachersList = FetchData.getAllTeachers();
        fillTable();

        if (LanguageUtil.getLanguage().equals("Albanian")){
            setAlbanian();
        } else {
            setEnglish();
        }

        searchInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                teachersList.clear();
                teachersList.addAll(FetchData.searchAllTeachers(searchInput.getText()));
                try {
                    fillTable();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    @FXML
    public void searchTeachers(){
        if(!searchInput.getText().isEmpty()) {
            teachersList.clear();
            teachersList.addAll(FetchData.searchAllTeachers(searchInput.getText()));
            try {
                fillTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private void showTeacherInfo (TeacherUser teacher) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/TeacherInfo.fxml"));
            Parent root = loader.load();
            TeacherInfoController controller = loader.getController();
            controller.setTeacher(teacher);
            Scene scene = new Scene(root, WindowSizeUtils.windowWidth, WindowSizeUtils.windowHeight);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void fillTable() throws SQLException {
        for (TeacherUser teacher: teachersList) {
            this.name.setCellValueFactory(new PropertyValueFactory<>("Name"));
            this.surname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
            this.birthdate.setCellValueFactory(new PropertyValueFactory<>("Birthdate"));
            this.phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
            this.email.setCellValueFactory(new PropertyValueFactory<>("Email"));
            this.address.setCellValueFactory(new PropertyValueFactory<>("Address"));
            this.gender.setCellValueFactory(new PropertyValueFactory<>("Gender"));

            teachersTable.setItems(teachersList);


            teachersTable.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) { // double click
                    TeacherUser selectedTeacher = teachersTable.getSelectionModel().getSelectedItem();
                    if (selectedTeacher != null) {
                        showTeacherInfo(teacher);
                    }
                }
            });
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
        addTeacherButton.setText(bundle.getString("addStudent"));
    }
}
