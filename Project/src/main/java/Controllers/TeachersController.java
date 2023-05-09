package Controllers;

import Models.StudentUser;
import Models.TeacherUser;
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

    private ObservableList<TeacherUser> teachersList = FXCollections.observableArrayList();
    public void initialize() throws SQLException {
        Connection conn = ConnectionUtil.getConnection();
        PreparedStatement stmt =  conn.prepareStatement("SELECT  Teachers.T_Name, Teachers.t_Surname, Teachers.T_Birthdate, Teachers.T_Phone, Teachers.T_Address, Users.email, Users.salted_password, Users.Salt, Users.u_position, Users.u_profileimg\n" +
                "FROM Teachers\n" +
                "INNER JOIN Users ON Teachers.T_UID = Users.U_ID;");
        ResultSet rs = stmt.executeQuery();

        // Loop through the result set and create a Student object for each row
        while (rs.next()) {
            int id = rs.getInt("T_ID");
            String name = rs.getString("T_Name");
            String surname = rs.getString("T_Surname");
            String birthdate = rs.getString("T_Birthdate");
            String phone = rs.getString("T_Phone");
            String address = rs.getString("T_Address");
            String email = rs.getString("email");
            String salted_password = rs.getString("salted_password");
            String salt = rs.getString("salt");
            String position = rs.getString("u_position");
            String profile_pics = rs.getString("u_profileimg");

            TeacherUser teacher = new TeacherUser(id,  email,  salted_password,  salt,  position, profile_pics,  name,  surname,  birthdate,  phone,  address);
            teachersList.add(teacher);


            this.name.setCellValueFactory(new PropertyValueFactory<>("Name"));
            this.surname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
            this.birthdate.setCellValueFactory(new PropertyValueFactory<>("Birthdate"));
            this.phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
            this.email.setCellValueFactory(new PropertyValueFactory<>("Email"));
            this.address.setCellValueFactory(new PropertyValueFactory<>("Address"));

            teachersTable.setItems(teachersList);


        }
    }
}
