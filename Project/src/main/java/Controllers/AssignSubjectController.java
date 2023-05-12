package Controllers;

import Models.Classe;
import Models.StudentUser;
import Models.Subject;
import Models.TeacherUser;
import Repository.ClassRepository;
import Repository.SubjectRepository;
import Services.FetchData;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AssignSubjectController implements Initializable {
    private Stage assignStage;
    @FXML
    private Button assignButton;

    @FXML
    private TableColumn<Classe, Integer> assignedParalel;

    @FXML
    private TableColumn<Classe, String> assignedTeacher;

    @FXML
    private TableView<Classe> assignedTo;

    @FXML
    private Button cancelButton;

    @FXML
    private Label header;

    @FXML
    private ChoiceBox<Integer> paralel;

    @FXML
    private ImageView paralelw;

    @FXML
    private ChoiceBox<TeacherUser> teacher;
    private Subject subject;

    @FXML
    private ImageView teacherw;
    ObservableList<Classe> classesList;
    ObservableList<TeacherUser> allTeachers = FetchData.getAllTeachers();
    ObservableList<Integer> allParalels = FXCollections.observableArrayList(1, 2, 3);
    public void setAssignStage(Stage assignStage) {
        this.assignStage = assignStage;
    }
    @FXML
    void handleAssignButton(ActionEvent event) throws SQLException {
        if (teacher.getValue()!= null&& paralel.getValue()!=null) {
            Classe classe = new Classe(1,teacher.getValue().getID() , this.subject.getId(),paralel.getValue());
            ClassRepository.insert(classe);
            this.assignStage.close();

        } else {
            if (teacher.getValue()!=null) {
                teacherw.setVisible(true);
            }
            if (paralel.getValue()!=null) {
                paralelw.setVisible(true);
            }

        }
    }

    @FXML
    void handleCancelButton(ActionEvent event) {
    }

    public void setData(Subject subject) throws SQLException {
        this.subject = subject;
        setAssignedTeachersInTable();

    }

    public void setAssignedTeachersInTable() throws SQLException {
        this.classesList = FetchData.getSubjectClasses(subject);

        for (Classe classe : this.classesList) {
            this.assignedTeacher.setCellValueFactory(cellData -> {
                int teacherId = cellData.getValue().getTeacherId();
                TeacherUser teacher = FetchData.getTeacherByID(teacherId);
                return new SimpleStringProperty(teacher.getName()+ " "+ teacher.getSurname());
            });
            this.assignedParalel.setCellValueFactory(new PropertyValueFactory<>("paralel"));
            assignedTo.setItems(this.classesList);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        teacher.setItems(allTeachers);
        paralel.setItems(allParalels);
        paralelw.setVisible(false);
        teacherw.setVisible(false);
    }
}
