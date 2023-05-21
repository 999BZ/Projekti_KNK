package Controllers;

import Models.Enrollment;
import Models.Subject;
import Services.CardGenUtil;
import Services.FetchData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class GradesController implements Initializable {
    @FXML
    private VBox gradesCard;

    @FXML
    private Button clearFilters;

    @FXML
    private TextField searchInput;



    @FXML
    private ChoiceBox<Subject> subjectFilter;
    private int teacherId;

    private ObservableList<Enrollment> enrollmentsList = FXCollections.observableArrayList();
    private ObservableList<Subject> subjectsList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearFilters.setOnAction(e -> clearFilters());
        subjectFilter.setOnAction(e -> filterGrades());
        searchInput.setOnAction(e -> searchStudents());
        //get user id from preferences
        Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
        this.teacherId = preferences.getInt("userId",0);

        //get the teacher subjects
        try {
            subjectsList = FetchData.getTeacherSubjectsEnrolled(teacherId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            subjectFilter.setItems(subjectsList);
            subjectFilter.setValue(subjectsList.get(0));
            filterGrades();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void filterGrades(){
        System.out.println("Test");
        if(subjectFilter.getValue() != null){
            enrollmentsList = FetchData.getAllEnrollmentsBySubjectOfTeacher(subjectFilter.getValue().getId(), this.teacherId);
            gradesCard.getChildren().clear();
            CardGenUtil.gradesToFlowPane(gradesCard,enrollmentsList,this);
        }
    }
    @FXML
    public void clearFilters(){
        subjectFilter.setValue(null);
        enrollmentsList = FetchData.getAllTeacherEnrollments(teacherId);
        CardGenUtil.gradesToFlowPane(gradesCard,enrollmentsList,this);
    }

    @FXML
    public void searchStudents(){
        if(!searchInput.getText().isEmpty()){
            enrollmentsList = FetchData.searchAllTeacherEnrollments(this.teacherId, searchInput.getText());
            gradesCard.getChildren().clear();
            CardGenUtil.gradesToFlowPane(gradesCard,enrollmentsList,this);
        }
    }

}
