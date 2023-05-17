package Controllers;

import Models.User;
import Repository.UserRepository;
import Services.ConnectionUtil;
import Services.PasswordHasher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class StudentsStatisticsController implements Initializable {

    @FXML
    private Label lblaverage;

    private Stage dialogStage;

    private String position;

    private int U_ID;
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setUserId(int U_ID){this.U_ID=U_ID;}


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
        this.position = preferences.get("position",null);

    }

    public void setLblaverage(String lblaverage) {
        this.lblaverage.setText(lblaverage);
    }

    public Label getLblaverage() {
        return lblaverage;
    }
}
