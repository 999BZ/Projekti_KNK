package Controllers;

import Models.TeacherStudent;
import Services.ConnectionUtil;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class StudentsController {
    @FXML
    private BorderPane rootPane;

    @FXML
    private TableView<TeacherStudent> studentsTable;

    @FXML
    private TableColumn<TeacherStudent, String> name;

    @FXML
    private TableColumn<TeacherStudent, String> surname;

    @FXML
    private TableColumn<TeacherStudent, String> birthdate;

    @FXML
    private TableColumn<TeacherStudent, Integer> year;
    @FXML
    private TableColumn<TeacherStudent, Integer> id;

    @FXML
    private TableColumn<TeacherStudent, String> phone;

    @FXML
    private TableColumn<TeacherStudent, String> email;

    @FXML
    private TableColumn<TeacherStudent, String> address;

    private ObservableList<TeacherStudent> studentsList = FXCollections.observableArrayList();

    public void initialize() throws SQLException {
        Connection conn = ConnectionUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT Students.S_UID, Students.S_Name, Students.S_Surname, Students.S_Birthdate, Students.S_Phone, Students.S_Address, Students.S_GLevel, Users.email, Users.salted_password, Users.Salt, Users.u_position, Users.u_profileimg\n" +
                "            FROM Students\n" +
                "            INNER JOIN Users ON Students.S_UID = Users.U_ID;");
        ResultSet rs = stmt.executeQuery();

        // Loop through the result set and create a Student object for each row
        while (rs.next()) {
            int id = rs.getInt("S_UID");
            String name = rs.getString("S_Name");
            String surname = rs.getString("S_Surname");
            String birthdate = rs.getString("S_Birthdate");
            int year = rs.getInt("S_GLevel");
            String phone = rs.getString("S_Phone");
            String address = rs.getString("S_Address");
            int gradeLevel = rs.getInt("S_GLevel");
            String email = rs.getString("email");
            String salted_password = rs.getString("salted_password");
            String salt = rs.getString("salt");
            String position = rs.getString("u_position");
            String profile_pic = rs.getString("u_profileimg");

            TeacherStudent student = new TeacherStudent(id, email, salted_password, salt, position, profile_pic, name, surname, birthdate, phone, address, year);
            studentsList.add(student);


            this.name.setCellValueFactory(new PropertyValueFactory<>("Name"));
            this.surname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
            this.birthdate.setCellValueFactory(new PropertyValueFactory<>("Birthdate"));
            this.year.setCellValueFactory(new PropertyValueFactory<>("Year"));
            this.phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
            this.email.setCellValueFactory(new PropertyValueFactory<>("Email"));
            this.address.setCellValueFactory(new PropertyValueFactory<>("Address"));

            studentsTable.setItems(studentsList);

            studentsTable.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) { // double click
                    TeacherStudent selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
                    if (selectedStudent != null) {
                        showStudentInfo(selectedStudent);
                    }
                }
            });

        }
    }

    private void showStudentInfo(TeacherStudent student) {
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




    public StudentsController() {}
}
