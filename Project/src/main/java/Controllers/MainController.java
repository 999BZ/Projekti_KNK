package Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import Services.ConnectionUtil;
import Services.WindowSizeUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainController {
    @FXML
    private BorderPane rootPane;
    @FXML
    private AnchorPane sidenav;
    @FXML
    private Label nrOfStudents;
    @FXML
    private Label nrOfTeachers;
    @FXML
    private Label nrOfSubjects;



    public void initialize() {
        Preferences prefs = Preferences.userNodeForPackage(LoginController.class);

        // Get the user ID from the preferences
        int userId = prefs.getInt("userId", 0);
        // Get the user position from the preferences
        String userPosition = prefs.get("position", null);

        if(userPosition.equals("Admin")){
            sidenav.getStyleClass().clear();
            sidenav.getStyleClass().add("profile");
        }


        // get the number of students from the database
        int numberOfStudents = 0;
        try {
            // Create a connection to the database
            Connection connection = ConnectionUtil.getConnection();

            // Create a prepared statement to retrieve the number of students
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS number_of_students FROM users where u_position='Student'");

            // Execute the query and retrieve the result set
            ResultSet resultSet = statement.executeQuery();

            // Retrieve the number of students from the result set
            if (resultSet.next()) {
                numberOfStudents = resultSet.getInt("number_of_students");
            }
            nrOfStudents.setText(String.valueOf(numberOfStudents));

            //get the number of teachers from the database
            int numberOfTeachers = 0;
            statement = connection.prepareStatement("SELECT COUNT(*) AS number_of_teachers FROM users where u_position='Teacher'");
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numberOfTeachers = resultSet.getInt("number_of_teachers");
            }

            nrOfTeachers.setText(String.valueOf(numberOfTeachers));

            //get the number of admins from the database
            int numberOfAdmins = 0;
            statement = connection.prepareStatement("SELECT COUNT(*) AS number_of_admins FROM users where u_position='Admin'");
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numberOfAdmins = resultSet.getInt("number_of_admins");
            }
            //get the number of subjects from the database
            int numberOfSubjects = 0;
            statement = connection.prepareStatement("SELECT COUNT(*) AS number_of_subjects FROM subjects");
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numberOfSubjects = resultSet.getInt("number_of_subjects");
            }

            nrOfSubjects.setText(String.valueOf(numberOfSubjects));


            // Close the database connection and statement
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Perform a database query to get the name of the user
        String userNameFromDB = "";
        try {

            // Create a connection to the database
            Connection connection = ConnectionUtil.getConnection();

            // Create a prepared statement to retrieve the user name based on the user ID
            PreparedStatement statement = connection.prepareStatement("SELECT COALESCE( a.a_name, t.t_name, s.s_name) AS name\n" +
                    "FROM users u\n" +
                    "LEFT JOIN admins a ON u.u_id = a.a_uid\n" +
                    "LEFT JOIN teachers t ON u.u_id = t.t_uid\n" +
                    "LEFT JOIN students s ON u.u_id = s.s_uid\n" +
                    "WHERE u.u_id = ?\n");
            statement.setInt(1, userId);

            // Execute the query and retrieve the result set
            ResultSet resultSet = statement.executeQuery();

            // Retrieve the user name from the result set
            if (resultSet.next()) {
                userNameFromDB = resultSet.getString("name");
            }

            // Close the database connection and statement
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set the user name to the label tex
    }
}
