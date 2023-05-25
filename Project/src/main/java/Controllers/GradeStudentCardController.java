package Controllers;

import Models.Grade;
import Models.StudentUser;
import Models.Subject;
import Repository.GradeRepository;
import Services.FetchData;
import Services.GeneralUtil;
import Services.LanguageUtil;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
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
    private ResourceBundle bundle;
    private ObservableList<Integer> optionsGradeValue = FXCollections.observableArrayList(1,2,3,4,5);

    @FXML
    void gradeStudent(ActionEvent event) {
        gradeChoiceBox.setVisible(true);
        gradeButton.setText("Finish");
        gradeButton.setOnAction(e->{

            gradeValue = gradeChoiceBox.getValue();
            try {
                    if (gradeValue != 0) {
                        if(GeneralUtil.setDialog("Grade this student with "+gradeValue+"?")) {
                            try {
                                gradeObject = new Grade(1, student.getID(), subject.getId(), gradeValue);
                                GradeRepository.insert(gradeObject);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            gradeButton.setText("Edit");
                            gradeButton.setOnAction(ex -> {
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
                            gradeButton.setOnAction(ex -> {
                                gradeStudent(ex);
                            });
                        }
                    } else {
                        grade.setVisible(false);
                        notGraded.setVisible(true);
                        gradeButton.setText("Grade");
                        gradeButton.setOnAction(ex -> {
                            gradeStudent(ex);
                        });
                    }
                gradeChoiceBox.setVisible(false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gradeChoiceBox.setItems(optionsGradeValue);
        gradeChoiceBox.setValue(0);
        gradeChoiceBox.setVisible(false);

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

    public void setAlbanian() {
        try {
            Locale locale = new Locale("sq");
            bundle = ResourceBundle.getBundle("Bilinguality.NameTags_sq", locale);

            updateLabels();
        } catch (Exception e) {
            System.out.println(e);
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
    }
    private void updateLabels() {
        notGraded.setText(bundle.getString("notGraded"));
        if (gradeButton.getText().equals("Edit") || gradeButton.getText().equals("Ndrysho")) {
            gradeButton.setText(bundle.getString("edit"));
        } else if (gradeButton.getText().equals("Grade") || gradeButton.getText().equals("Notë")) {
            gradeButton.setText(bundle.getString("grade"));
        } else {
            gradeButton.setText(bundle.getString("finish"));
        }
    }

    public void editAction() {
        gradeChoiceBox.setVisible(true);
        gradeButton.setText("Finish");
        gradeButton.setOnAction(e->{
            if(gradeChoiceBox.getValue()!=null) {
                try {
                    String a;
                    if(LanguageUtil.getLanguage().equals("Albanian")){
                        a = "Ruaj ndryshimet?";
                    } else {
                        a = "Save changes?";
                    }
                    if(GeneralUtil.setDialog(a)) {
                        try {
                            gradeObject = new Grade(FetchData.getStudentGradeId(student, subject), student.getID(), subject.getId(), gradeChoiceBox.getValue());
                            GradeRepository.update(gradeObject);
                            grade.setText(String.valueOf(gradeObject.getGrade()));
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
                gradeButton.setText("Edit");
                gradeButton.setOnAction(ep->{
                    editAction();
                });
                gradeChoiceBox.setVisible(false);
        });

    }
}