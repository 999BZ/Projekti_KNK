package Controllers;

import Models.Grade;
import Models.StudentUser;
import Models.Subject;
import Services.FetchData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentSubjectCardController implements Initializable {
    @FXML
    private Label gradeValue;

    @FXML
    private Label subjectName;
    private Subject subject;
    private StudentUser student;
    private Connection conn;

    public void setData(Subject subject, StudentUser student){
        this.subject = subject;
        this.student = student;
        subjectName.setText(subject.getName());
        int grade;
        try {
            grade = FetchData.getStudentGrade(student, subject);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(grade == 0){
            gradeValue.setText("Not Graded");
            gradeValue.setStyle("-fx-text-fill: red;-fx-background-color: white");
        }
        else{
            gradeValue.setText(String.valueOf(grade));
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
