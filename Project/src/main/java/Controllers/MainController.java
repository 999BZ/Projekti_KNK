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
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainController {
    @FXML
    private Label userName;
    private double previousWidth;
    private double previousHeight;

    @FXML
    private BorderPane rootPane;


    public void initialize() {
        Preferences prefs = Preferences.userNodeForPackage(LoginController.class);

        WindowSizeUtils.loadWindowSize("loginWindow", rootPane);

        // Get the user ID from the preferences
        int userId = prefs.getInt("userId", 0);
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

        // Set the user name to the label text
        userName.setText("Welcome " + userNameFromDB);
    }
    @FXML
    public void handleWindowClose() {
        WindowSizeUtils.saveWindowSize("loginWindow", rootPane);
    }
}
