package Controllers;

import Models.Subject;
import Repository.SubjectRepository;
import Services.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class SubjectCardController {
    SubjectsController parentController;
    @FXML
    private Label gradeLvl ;
    @FXML
    private Label subjectName;
    @FXML Label subjectInfo;
    @FXML
    private Label obligatory;
    @FXML
    private Button assignButton;
    private ResourceBundle bundle;
    Subject subject;

    public void setData(Subject subject){
        gradeLvl.setText(String.valueOf(subject.getYear()));
        subjectInfo.setText(subject.getDescription());
        subjectName.setText(subject.getName());
        if(subject.isObligatory()){
                obligatory.setText("Obligatory");

            obligatory.setStyle("-fx-text-fill: #186021; -fx-font-weight: bold;");
        }else{
            if (LanguageUtil.getLanguage().equals("Albanian")) {
                obligatory.setText("Zgjedhore");
            } else {
                obligatory.setText("Elective");
            }
        }
        this.subject = subject;

        setLanguage();

        LanguageUtil.languageProperty().addListener((observable, oldValue, newValue) -> {
            setLanguage();
        });
    }

    public void setLanguage(){
        if (LanguageUtil.getLanguage().equals("Albanian")) {
            bundle = ResourceBundle.getBundle("Bilinguality.NameTags_sq", new Locale("sq"));
            if (obligatory.getText().equals("Obligatory")) {
                obligatory.setText("Obligative");}
            if (obligatory.getText().equals("Elective")) {
                obligatory.setText("Zgjedhore");}
        } else {
            bundle = ResourceBundle.getBundle("Bilinguality.NameTags_en", Locale.ENGLISH);
            if (obligatory.getText().equals("Obligative")) {
                obligatory.setText("Obligatory");}
            if (obligatory.getText().equals("Zgjedhore")) {
                obligatory.setText("Elective");}
        }

//        obligatory.setText(bundle.getString("obligatory"));

        assignButton.setText(bundle.getString("assign"));
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
        if (LanguageUtil.getLanguage().equals("Albanian")){
            if (GeneralUtil.setDialog("Kjo lëndë është duke u fshirë!")) {
                SubjectRepository.delete(subject);
                parentController.clearFilters();
            }
        } else {
            if (GeneralUtil.setDialog("This subject is being removed!")) {
                SubjectRepository.delete(subject);
                parentController.clearFilters();
            }
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
        assignStage.setResizable(false);
        asc.setAssignStage(assignStage);
        assignStage.showAndWait();
    }


}
