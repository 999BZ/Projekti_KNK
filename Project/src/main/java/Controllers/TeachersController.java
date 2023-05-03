package Controllers;

import Models.StudentUser;
import Services.ConnectionUtil;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeachersController {
    @FXML
    private BorderPane rootPane;
    @FXML
    private void goToAddTeachers() {
        // Load the Courses page FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/AddTeacher.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        PreparedStatement stmt =  conn.prepareStatement("SELECT Teachers.T_id, Teachers.T_Name, Teachers.t_Surname, Teachers.T_Birthdate, Teachers.T_Phone, Teachers.T_Address, Teachers.T_Grade, Users.email, Users.salted_password, Users.Salt, Users.u_position\n" +
                "            FROM Teachers\n" +
                "            INNER JOIN Users ON Teachers.T_UID = Users.U_ID;");
        ResultSet rs = stmt.executeQuery();

        // Loop through the result set and create a Student object for each row
        while (rs.next()) {
            int id = rs.getInt("T_ID");
            String name = rs.getString("T_Name");
            String surname = rs.getString("T_Surname");
            String birthdate = rs.getString("T_Birthdate");
            int year = rs.getInt("T_GLevel");
            String phone = rs.getString("T_Phone");
            String address = rs.getString("T_Address");
            int gradeLevel = rs.getInt("T_Grade");
            String email = rs.getString("email");
            String salted_password = rs.getString("salted_password");
            String salt = rs.getString("salt");
            String position = rs.getString("u_position");

            StudentUser student = new StudentUser(id,  email,  salted_password,  salt,  position,  name,  surname,  birthdate,  phone,  address, year);
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
}
