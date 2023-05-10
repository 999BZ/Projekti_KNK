package Controllers;

import Models.Subject;
import Models.TeacherUser;
import Services.CardGenUtil;
import Services.ConnectionUtil;
import Services.FetchData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.Flow;

public class SubjectsController implements Initializable {
    @FXML
    private FlowPane subjectCards;
    @FXML
    private ChoiceBox<TeacherUser> teacherFilter;
    @FXML
    private ChoiceBox<Integer> gradeFilter;

    private ObservableList<TeacherUser> optionsTeacher = FXCollections.observableArrayList();
    private ObservableList<Integer> optionsGrade = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12);;
    private ObservableList<Subject> subjectsList = FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gradeFilter.setValue(0);
        teacherFilter.setConverter(new StringConverter<TeacherUser>() {
            @Override
            public String toString(TeacherUser teacher) {
                return teacher != null ? (teacher.getName()+" "+teacher.getSurname()) : "";
            }

            @Override
            public TeacherUser fromString(String string) {
                return null;
            }
        });

        ObservableList<TeacherUser> teachers = FXCollections.observableArrayList();
        teachers = FetchData.getAllTeachers();
        for (TeacherUser teacher:teachers
             ) {
            optionsTeacher.add(teacher);
        }
        teacherFilter.setItems(teachers);

        gradeFilter.setItems(optionsGrade);


       subjectsList = FetchData.getAllSubjects();

        CardGenUtil.subjectsToFlowPane(subjectCards, subjectsList);
    }
    public void filterSubjects() throws SQLException {
        Connection conn = ConnectionUtil.getConnection();

        int selectedGradeOption = gradeFilter.getValue();
        TeacherUser selectedTeacherOption = teacherFilter.getValue();
        String query;
        if(selectedTeacherOption == null && selectedGradeOption == 0){
            query = "SELECT * FROM subjects";
        } else if (selectedTeacherOption != null && selectedGradeOption == 0) {
            query = "SELECT * FROM subjects s JOIN classes c ON s.sb_id = c.sb_id WHERE c.t_id = ?";
        } else if (selectedTeacherOption == null && selectedGradeOption !=0 ) {
            query = "SELECT * FROM subjects WHERE Sb_Glevel = ?";
        } else {
            query = "SELECT * FROM subjects s JOIN classes c ON s.sb_id = c.sb_id WHERE c.t_id = ? AND s.Sb_GLevel = ?";
        }

        PreparedStatement pstmt = conn.prepareStatement(query);

        if(selectedTeacherOption != null && selectedGradeOption == 0){
            pstmt.setInt(1, selectedTeacherOption.getID());
        } else if (selectedTeacherOption == null && selectedGradeOption !=0 ) {
            pstmt.setInt(1, selectedGradeOption);
        } else if (selectedTeacherOption != null && selectedGradeOption != 0) {
            pstmt.setInt(1, selectedTeacherOption.getID());
            pstmt.setInt(2, selectedGradeOption);
        }

        subjectsList = FetchData.getAllSubjects(pstmt);
        CardGenUtil.subjectsToFlowPane(subjectCards, subjectsList);

    }
}
