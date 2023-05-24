package Controllers;

import Models.Grade;
import Models.StudentUser;
import Models.Subject;
import Models.TeacherUser;
import Services.FetchData;
import Services.LanguageUtil;
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
import java.util.Locale;
import java.util.ResourceBundle;

public class StudentSubjectCardController implements Initializable {
    @FXML
    private Label gradeValue;
    @FXML
    private Label gradeLevel;

    @FXML
    private Label subjectName;

    @FXML
    private Label lblGrade;
    @FXML
    private Label lblSubject;
    private Subject subject;
    private StudentUser student;

    private TeacherUser teacher;

    private Connection conn;

    private ResourceBundle bundle;

    public void setData(Subject subject, StudentUser student){
        this.subject = subject;
        this.student = student;
        subjectName.setText(subject.getName());
        gradeLevel.setVisible(false);
        int grade;
        lblGrade.setPrefWidth(40);
        if (LanguageUtil.getLanguage().equals("Albanian")){
            lblGrade.setText("Nota: ");
        } else {
            lblGrade.setText("Grade:");
        }
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
    public void setData(Subject subject, TeacherUser teacher){
        this.subject = subject;
        this.teacher = teacher;
        lblGrade.setPrefWidth(50);
        subjectName.setText(subject.getName());
        if (LanguageUtil.getLanguage().equals("Albanian")){
            lblGrade.setText("Klasët: ");
        } else {
            lblGrade.setText("Classes:");
        }

        gradeLevel.setText(String.valueOf(subject.getYear()));
        String classes;
        try {
            classes = FetchData.getTeacherP(teacher, subject);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        gradeValue.setText(classes);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (LanguageUtil.getLanguage().equals("Albanian")){
            setAlbanian();
        } else {
            setEnglish();
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
        if(lblGrade.getText().equals("Grade:")){
            lblGrade.setText("Nota: ");
        }
        else if (lblGrade.getText().equals("Classes:")){
            lblGrade.setText("Klasët: ");
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

        if(lblGrade.getText().equals("Nota: ")){
            lblGrade.setText("Grade:");
        }
        else if (lblGrade.getText().equals("Klasët: ")){
            lblGrade.setText("Classes:");
        }
    }
    private void updateLabels() {
        lblSubject.setText(bundle.getString("subject")+": ");
    }


}
