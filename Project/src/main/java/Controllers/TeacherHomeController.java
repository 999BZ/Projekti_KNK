package Controllers;

import Models.StudentUser;
import Models.TeacherUser;
import Services.ConnectionUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherHomeController {
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

    private ObservableList<StudentUser> studentsList = FXCollections.observableArrayList();

    public void initialize(TeacherUser teacher) throws SQLException {
        Connection conn = ConnectionUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT s.S_ID, s.S_Name, s.S_Surname, s.S_Birthdate, " +
                "s.S_Phone, s.S_Address, s.S_GLevel, sb.Sb_Name\n" +
                "FROM Students s " +
                "INNER JOIN Enrollments e ON e.S_ID = s.S_ID\n" +
                "INNER JOIN Subjects sb ON sb.Sb_ID = c.Sb_ID\n" +
                "INNER JOIN Classes c ON c.C_ID = e.C_ID\n" +
                "WHERE c.T_ID = ?;");
        stmt.setInt(1, teacher.getID());
        ResultSet rs = stmt.executeQuery();

        // Loop through the result set and create a Student object for each row
        while (rs.next()) {
            int id = rs.getInt("S_ID");
            String name = rs.getString("S_Name");
            String surname = rs.getString("S_Surname");
            String birthdate = rs.getString("S_Birthdate");
            String phone = rs.getString("S_Phone");
            String address = rs.getString("S_Address");
            int gradeLevel = rs.getInt("S_GLevel");
            String email = rs.getString("email");

            String profile_pic = rs.getString("u_profileimg");

            StudentUser student = new StudentUser(id, email, null, null, null, profile_pic, name, surname, birthdate, phone, address, gradeLevel);
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
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToHome(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/TeacherHome.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToStudents(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/Students.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToAddStudents(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/AddStudent.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToClasses(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/Courses.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToLogin(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/Login.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToProfile(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/StudentInfo.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
