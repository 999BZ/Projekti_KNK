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
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    private VBox subjectCards;
Connection conn;
    @FXML
    private ChoiceBox<TeacherUser> teacherFilter;
    @FXML
    private ChoiceBox<Integer> gradeFilter;
    @FXML
    private ChoiceBox<Option> obligatoryFilter;

    private ObservableList<TeacherUser> optionsTeacher = FXCollections.observableArrayList();
    private ObservableList<Integer> optionsGrade = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12);
    private ObservableList<Option> optionsObligatory = FXCollections.observableArrayList(new Option("Obligatory",1), new Option("Elective",0));
    private ObservableList<Subject> subjectsList = FXCollections.observableArrayList();

    public SubjectsController() throws SQLException {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            conn = ConnectionUtil.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        gradeFilter.setOnAction(e->{
            ObservableList<TeacherUser> teachers = null;
            try {
                teachers = FetchData.getAllTeachers(gradeFilter.getValue());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            for (TeacherUser teacher:teachers
            ) {
                System.out.println(teacher.getName());
                optionsTeacher.add(teacher);
            }
            teacherFilter.getItems().clear();
            teacherFilter.setItems(teachers);
        });
        gradeFilter.setValue(0);

        ObservableList<TeacherUser> teachers = FXCollections.observableArrayList();
        teachers = FetchData.getAllTeachers();
        for (TeacherUser teacher:teachers
             ) {
            optionsTeacher.add(teacher);
        }
        teacherFilter.setItems(teachers);

        gradeFilter.setItems(optionsGrade);
        obligatoryFilter.setItems(optionsObligatory);


       subjectsList = FetchData.getAllSubjects();

        CardGenUtil.subjectsToFlowPane(subjectCards, subjectsList, this);
    }
    public void filterSubjects() throws SQLException {
        Connection conn = ConnectionUtil.getConnection();

        int selectedGradeOption = gradeFilter.getValue();
        TeacherUser selectedTeacherOption = teacherFilter.getValue();
        Option selectedObligatoryOption = obligatoryFilter.getValue();
        String query;
        if (selectedTeacherOption == null && selectedGradeOption == 0 && selectedObligatoryOption == null) {
            query = "SELECT * FROM subjects";
        } else if (selectedTeacherOption != null && selectedGradeOption == 0 && selectedObligatoryOption == null) {
            query = "SELECT s.*, MAX(c.C_ID) FROM subjects s JOIN classes c ON s.sb_id = c.c_id WHERE c.t_id = ? GROUP BY s.Sb_ID";
        } else if (selectedTeacherOption == null && selectedGradeOption != 0 && selectedObligatoryOption == null) {
            query = "SELECT * FROM subjects WHERE Sb_Glevel = ? GROUP BY subjects.Sb_ID";
        } else if (selectedTeacherOption == null && selectedGradeOption == 0 && selectedObligatoryOption != null) {
            query = "SELECT * FROM subjects WHERE Sb_Obligatory = ?  GROUP BY subjects.Sb_ID";
        } else if (selectedTeacherOption != null && selectedGradeOption != 0 && selectedObligatoryOption == null) {
            query = "SELECT s.*, MAX(c.C_ID) FROM subjects s JOIN classes c ON s.sb_id = c.sb_id WHERE c.t_id = ? AND s.Sb_GLevel = ? GROUP BY s.Sb_ID";
        } else if (selectedTeacherOption != null && selectedGradeOption == 0 && selectedObligatoryOption != null) {
            query = "SELECT s.*, MAX(c.C_ID) FROM subjects s JOIN classes c ON s.sb_id = c.c_id WHERE c.t_id = ? AND Sb_Obligatory = ? GROUP BY s.Sb_ID";
        } else if (selectedTeacherOption == null && selectedGradeOption != 0 && selectedObligatoryOption != null) {
            query = "SELECT * FROM subjects WHERE Sb_Glevel = ? AND Sb_Obligatory = ? GROUP BY subjects.Sb_ID";
        } else {
            query = "SELECT s.*, MAX(c.C_ID) FROM subjects s JOIN classes c ON s.sb_id = c.sb_id WHERE c.t_id = ? AND s.Sb_GLevel = ? AND Sb_Obligatory = ? GROUP BY s.Sb_ID";
        }

        PreparedStatement pstmt = conn.prepareStatement(query);

        int parameterIndex = 1;

        if (selectedTeacherOption != null) {
            pstmt.setInt(parameterIndex++, selectedTeacherOption.getID());
        }

        if (selectedGradeOption != 0) {
            pstmt.setInt(parameterIndex++, selectedGradeOption);
        }

        if (selectedObligatoryOption != null) {
            pstmt.setInt(parameterIndex++, selectedObligatoryOption.getValue());
        }

        subjectsList = FetchData.getAllSubjects(pstmt);
        CardGenUtil.subjectsToFlowPane(subjectCards, subjectsList, this);

    }
    public static class Option {
        private final String text;
        private final int value;

        public Option(String text, int value) {
            this.text = text;
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return text;
        }
    }
    @FXML
    public void openAddSubjects() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/AddSubject.fxml"));
        AnchorPane addSubjectPane = loader.load();
        AddSubjectController subjectController = loader.getController();
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(new Scene(addSubjectPane));
        subjectController.setAddSubjectsStage(dialogStage);
        dialogStage.showAndWait();
        if(subjectController.getConfimed()){
        subjectsList = FetchData.getAllSubjects();
        CardGenUtil.subjectsToFlowPane(subjectCards, subjectsList, this);}
    }
    @FXML
    public void clearFilters() throws IOException{
        teacherFilter.setValue(null);
        teacherFilter.setItems(FetchData.getAllTeachers());
        teacherFilter.setValue(null);
        for (TeacherUser teacher:FetchData.getAllTeachers()
             ) {
            System.out.println(teacher.getName());
        }
        gradeFilter.setValue(0);
        subjectsList = FetchData.getAllSubjects();
        CardGenUtil.subjectsToFlowPane(subjectCards, subjectsList, this);
    }
    @FXML
    public void teacherDynamicOptions() throws SQLException {

    }
}
