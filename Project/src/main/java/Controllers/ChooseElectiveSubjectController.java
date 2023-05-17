package Controllers;

import Models.*;
import Repository.ClassRepository;
import Repository.EnrollmentRepository;
import Services.ConnectionUtil;
import Services.FetchData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChooseElectiveSubjectController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmButton;

    @FXML
    private ChoiceBox<Subject> chooseSubject;
    @FXML
    private ImageView w;
    private Stage stage;

    private ObservableList<Subject> optionsSubjects = FXCollections.observableArrayList();

    private StudentUser student;
    private StudentInfoController parentController;

    public void setParentController(StudentInfoController parentController){
        this.parentController = parentController;

    }
    @FXML
    void handleCancelButton(ActionEvent event) {
        this.stage.close();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    void handleConfirmButton(ActionEvent event) throws SQLException {
        if(chooseSubject.getValue()!=null){
            Classe clas = ClassRepository.subjectClassOfStudent(chooseSubject.getValue(), this.student);
            EnrollmentRepository.enrollStudentToSubject(this.student, clas);
            parentController.setStudent(this.student);
            this.stage.close();
        }
        else {
            w.setVisible(true);
        }
    }
    public void setData(StudentUser student) throws SQLException {
        this.student = student;
        Connection conn = ConnectionUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT DISTINCT Subjects.*\n" +
                "FROM Subjects\n" +
                "WHERE Sb_GLevel = (SELECT S_GLevel FROM Students WHERE S_UID = ?)\n" +
                "  AND Sb_Obligatory = 0;");
        ps.setInt(1,this.student.getID());
        optionsSubjects = FetchData.getAllSubjects(ps);
        chooseSubject.setItems(optionsSubjects);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        w.setVisible(false);
    }
}
