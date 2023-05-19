package Controllers;
import Models.User;
import Services.ConnectionUtil;
import Services.LanguageUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Repository.UserRepository;
import Services.PasswordHasher;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class ChangePasswordController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmButton;

    @FXML
    private Label dialogLabel;
    @FXML
    private Label changingPassword;

    @FXML
    private AnchorPane dialogPane;

    @FXML
    private Label lblNewPassword;

    @FXML
    private Label lblOldPassword;

    @FXML
    private Label lblRepNewPassword;

    @FXML
    private PasswordField pwRepNewPassword;

    @FXML
    private PasswordField pwdNewPassword;

    @FXML
    private PasswordField pwdOldPassword;

    @FXML
    private Label lblInfo;
    private ResourceBundle bundle;

    private Stage dialogStage;

    private String position;

    private int U_ID;
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setUserId(int U_ID){this.U_ID=U_ID;}

    @FXML
    private void handleConfirmButton() throws SQLException {
        String oldPassword = pwdOldPassword.getText();
        String newPassword = pwdNewPassword.getText();
        String reNewPassword = pwRepNewPassword.getText();
        User user;
        if(this.position.equals("Student")){
            user = UserRepository.getStudentByUserID(this.U_ID);
        }else{
            user = UserRepository.getTeacherByUserID(this.U_ID);
        }
        if (this.position.equals("Admin") || PasswordHasher.compareSaltedHash(oldPassword, user.getSalt(), user.getSaltedPassword())) {
            if (newPassword.length() > 7){
                if(newPassword.equals(reNewPassword)) {
                    String salt = PasswordHasher.generateSalt();
                    String hashedPassword = PasswordHasher.generateSaltedHash(newPassword, salt);
                    String sql = "UPDATE users SET Salted_Password = ?, Salt = ? WHERE U_ID = ?";
                    try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, hashedPassword);
                        statement.setString(2, salt);
                        statement.setInt(3, this.U_ID);
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    dialogStage.close();
                }else{
                    lblInfo.setText("Passwords must match!");
                    lblInfo.setVisible(true);
                }
            }else{
                lblInfo.setText("Password is too short!");
                lblInfo.setVisible(true);
            }
        }else{
            lblInfo.setText("Passwords does not match account password!");
            lblInfo.setVisible(true);
        }
    }

    @FXML
    private void handleCancelButton() {
        dialogStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblInfo.setVisible(false);
        Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
        this.position = preferences.get("position",null);
        if(this.position.equals("Admin")){
            pwdOldPassword.setVisible(false);
            lblOldPassword.setVisible(false);
        }

        LanguageUtil.languageProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Albanian")) {
                setAlbanian();
            } else {
                setEnglish();
            }
        });
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
        changingPassword.setText(bundle.getString("changingPassword"));
        lblOldPassword.setText(bundle.getString("lblOldPassword") + ":");
        lblNewPassword.setText(bundle.getString("lblNewPassword") + ":");
        lblRepNewPassword.setText(bundle.getString("lblRepNewPassword") + ":");
        confirmButton.setText(bundle.getString("confirm"));
        cancelButton.setText(bundle.getString("cancel"));
    }
}
