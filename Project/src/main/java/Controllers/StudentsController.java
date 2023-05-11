package Controllers;

import Models.StudentUser;
import Services.ConnectionUtil;
import Services.FetchData;
import Services.WindowSizeUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    private TableColumn<StudentUser, Integer> id;
    @FXML
    private TableColumn<StudentUser, String> phone;
    @FXML
    private TableColumn<StudentUser, String> email;
    @FXML
    private TableColumn<StudentUser, String> address;

    public StudentsController() {
    }

    public void initialize() throws SQLException {
        studentsList = FetchData.getAllStudents();


        for (StudentUser student:studentsList) {


            this.name.setCellValueFactory(new PropertyValueFactory<>("Name"));
            this.surname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
            this.birthdate.setCellValueFactory(new PropertyValueFactory<>("Birthdate"));
            this.year.setCellValueFactory(new PropertyValueFactory<>("Year"));
            this.phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
            this.email.setCellValueFactory(new PropertyValueFactory<>("Email"));
            this.address.setCellValueFactory(new PropertyValueFactory<>("Address"));

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
}
