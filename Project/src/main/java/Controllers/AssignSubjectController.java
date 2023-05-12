package Controllers;

import Models.Classe;
import Models.StudentUser;
import Models.Subject;
import Models.TeacherUser;
import Repository.ClassRepository;
import Repository.SubjectRepository;
import Services.FetchData;
import Services.GeneralUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AssignSubjectController implements Initializable {
    private Stage assignStage;
    @FXML
    private Button assignButton;
    @FXML
    TableColumn<Classe, Void> unAssign;

    @FXML
    private TableColumn<Classe, Integer> assignedParalel;

    @FXML
    private TableColumn<Classe, String> assignedTeacher;

    @FXML
    private TableView<Classe> assignedTo;
    @FXML
    private Label w;
    @FXML
    private Label success;

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
            if(!ClassRepository.exists(new Classe(1, teacher.getValue().getID(),this.subject.getId(), paralel.getValue()))){
            Classe classe = new Classe(1,teacher.getValue().getID() , this.subject.getId(),paralel.getValue());
            ClassRepository.insert(classe);
            success.setVisible(true);
            w.setVisible(false);
            setAssignedTeachersInTable();}
            else {
                w.setVisible(true);
            }

        } else {
            if (teacher.getValue()==null) {
                teacherw.setVisible(true);
            }
            else{
                teacherw.setVisible(false);
            }
            if (paralel.getValue()==null) {
                paralelw.setVisible(true);
            }
            else{
            paralelw.setVisible(false);
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
        success.setVisible(false);
        w.setVisible(false);
        teacher.setItems(allTeachers);
        paralel.setItems(allParalels);
        paralelw.setVisible(false);
        teacherw.setVisible(false);
        unAssignFunctionallity();
    }

    public void unAssignFunctionallity() {
        unAssign.setCellFactory(param -> new TableCell<Classe, Void>() {
             Button unassignButton = new Button("✖");

            {
                unassignButton.setStyle("-fx-background-radius: 25px; -fx-padding: 0 15px 0 15px; -fx-background-color: #722e2e; -fx-font-weight: bold");
                unassignButton.setOnAction(event -> {
                    Classe classe = getTableView().getItems().get(getIndex());

                    try {
                        if (GeneralUtil.setDialog("Are you sure you unAssign class?")){
                        try {
                            ClassRepository.delete(classe);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        getTableView().getItems().remove(classe);}
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(unassignButton);
                }
            }
        });
    }
}
