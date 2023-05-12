package Controllers;

import Models.Subject;
import Repository.SubjectRepository;
import Services.CardGenUtil;
import Services.FetchData;
import Services.GeneralUtil;
import Services.WindowSizeUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SubjectCardController {
    SubjectsController parentController;
    @FXML
    private Label gradeLvl ;
   @FXML
   private Label subjectName;
   @FXML Label subjectInfo;
   Subject subject;

    public void setData(Subject subject){
        gradeLvl.setText(String.valueOf(subject.getYear()));
        subjectInfo.setText(subject.getDescription());
        subjectName.setText(subject.getName());
        this.subject = subject;
    }
    @FXML
    public void editSubject() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/AddSubject.fxml"));
        AnchorPane addSubjectPane = loader.load();
        AddSubjectController subjectController = loader.getController();
        subjectController.setEditMode(subject);
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(new Scene(addSubjectPane));
        subjectController.setAddSubjectsStage(dialogStage);
        dialogStage.showAndWait();
        parentController.clearFilters();
    }
    @FXML
    public void removeSubject() throws IOException, SQLException {

        if (GeneralUtil.setDialog("This subject is being removed!")) {
            SubjectRepository.delete(subject);
            parentController.clearFilters();
        }
    }

    public void setParentController(SubjectsController parentController) {
        this.parentController = parentController;
    }
    @FXML
    public void assignTeacher() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/AssignSubject.fxml"));
        AnchorPane assignPane = loader.load();
        AssignSubjectController asc = loader.getController();
        asc.setData(this.subject);
        Stage assignStage = new Stage();
        assignStage.initModality(Modality.APPLICATION_MODAL);
        assignStage.setScene(new Scene(assignPane));
        asc.setAssignStage(assignStage);
        assignStage.showAndWait();
    }


}
