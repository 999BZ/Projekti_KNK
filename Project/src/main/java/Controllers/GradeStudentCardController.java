package Controllers;

import Models.Grade;
import Models.StudentUser;
import Models.Subject;
import Repository.GradeRepository;
import Services.FetchData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GradeStudentCardController implements Initializable {

    @FXML
    private Label grade;

    @FXML
    private Button gradeButton;

    @FXML
    private ChoiceBox<Integer> gradeChoiceBox;

    @FXML
    private Label notGraded;

    @FXML
    private ImageView profilePic;

    @FXML
    private Label studentName;

    @FXML
    private Label subjectName;
    private StudentUser student;
    private Subject subject;
    private boolean gradingMode = false;
    private int gradeValue;
    private Grade gradeObject;
    private GradesController parentController;
    private ObservableList<Integer> optionsGradeValue = FXCollections.observableArrayList(1,2,3,4,5);

    @FXML
    void gradeStudent(ActionEvent event) {
        gradeChoiceBox.setVisible(true);
        gradeButton.setText("Finish");
        gradeButton.setOnAction(e->{
            gradeChoiceBox.setVisible(false);
            gradeValue = gradeChoiceBox.getValue();
            if(gradeValue!=0){
                try {
                    gradeObject = new Grade(1,student.getID(), subject.getId(),gradeValue);
                    GradeRepository.insert(gradeObject);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                gradeButton.setText("Edit");
                gradeButton.setOnAction(ex->{
                    editAction();
                });
                notGraded.setVisible(false);
                grade.setVisible(true);
                grade.setText(String.valueOf(gradeObject.getGrade()));
            }
            else{
                 grade.setVisible(false);
                 notGraded.setVisible(true);
                 gradeButton.setText("Grade");
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gradeChoiceBox.setItems(optionsGradeValue);
        gradeChoiceBox.setVisible(false);

    }
    public void setParentController(GradesController parentController){
        this.parentController = parentController;
    }

    public void setData(StudentUser student, Subject subject) throws SQLException {
        this.student = student;
        this.subject = subject;
try {
    String relativePath = student.getProfileImg().replace("src/main/resources", "");
    InputStream inputStream = getClass().getResourceAsStream(relativePath);
    if (inputStream != null) {
        Image image = new Image(inputStream);
        profilePic.setImage(image);
    }
}catch (Exception e){
}

        studentName.setText(student.getName()+" "+student.getSurname());
        subjectName.setText(subject.getName());
        gradeValue = FetchData.getStudentGrade(student,subject);
        if(gradeValue == 0){
            notGraded.setVisible(true);
            grade.setVisible(false);

        }
        else {
            notGraded.setVisible(false);
            grade.setText(String.valueOf(gradeValue));
            gradeButton.setText("Edit");
            gradeButton.setOnAction(e->{
                editAction();
            });
        }


    }
    public void editAction() {
        gradeChoiceBox.setVisible(true);
        gradeButton.setText("Finish");
        gradeButton.setOnAction(e->{
            try {
                gradeObject = new Grade(FetchData.getStudentGradeId(student,subject), student.getID(), subject.getId(), gradeChoiceBox.getValue());
                GradeRepository.update(gradeObject);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            gradeButton.setText("Edit");
            gradeChoiceBox.setVisible(false);
            grade.setText(String.valueOf(gradeObject.getGrade()));
        });

    }
}