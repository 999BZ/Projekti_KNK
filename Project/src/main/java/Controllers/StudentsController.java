package Controllers;

import Models.StudentUser;
import Services.ConnectionUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class StudentsController {
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
    public void initialize() throws SQLException {
        Connection conn = ConnectionUtil.getConnection();
        PreparedStatement stmt =  conn.prepareStatement("SELECT Students.S_id, Students.S_Name, Students.S_Surname, Students.S_Birthdate, Students.S_Phone, Students.S_Address, Students.S_GLevel, Users.email, Users.salted_password, Users.Salt, Users.u_position, Users.u_profileimg\n" +
                "            FROM Students\n" +
                "            INNER JOIN Users ON Students.S_UID = Users.U_ID;");
        ResultSet rs = stmt.executeQuery();

        // Loop through the result set and create a Student object for each row
        while (rs.next()) {
            int id = rs.getInt("S_ID");
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

            StudentUser student = new StudentUser(id,  email,  salted_password,  salt,  position, profile_pic,  name,  surname,  birthdate,  phone,  address, year);
            studentsList.add(student);


            this.name.setCellValueFactory(new PropertyValueFactory<>("Name"));
            this.surname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
            this.birthdate.setCellValueFactory(new PropertyValueFactory<>("Birthdate"));
            this.year.setCellValueFactory(new PropertyValueFactory<>("Year"));
            this.phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
            this.email.setCellValueFactory(new PropertyValueFactory<>("Email"));
            this.address.setCellValueFactory(new PropertyValueFactory<>("Address"));

            studentsTable.setItems(studentsList);


        }
    }
    @FXML
    private void goToAddStudents() {
        // Load the Courses page FXML file
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



    public StudentsController() {}
}
