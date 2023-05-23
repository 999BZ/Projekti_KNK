package Controllers;

import Models.Subject;
import Repository.SubjectRepository;
import Services.LanguageUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddSubjectController implements Initializable {

    @FXML
    private ImageView descriptionw;

    @FXML
    private ImageView gradeLvlw;

    @FXML
    private ImageView namew;
    @FXML
    private AnchorPane addSubjectsPane;
    @FXML
    private Button cancelButton;

    @FXML
    private Button addSubject;
    @FXML
    private Label header;
    @FXML
    private Label subjectNameL;
    @FXML
    private Label subjectDesc;
    @FXML
    private Label gradeLevel;

    @FXML
    private TextArea subjectDescription;
    @FXML
    private CheckBox obligatory;
    @FXML
    private Spinner<Integer> subjectGradeLvl = new Spinner<>(0,12,0,1);
    private ResourceBundle bundle;

    private Stage addSubjectsStage;
    private boolean editMode = false;
    private boolean confirmed = false;
    Subject subject;


    @FXML
    private TextField subjectName;
    public void setAddSubjectsStage(Stage addSubjectsStage) {
        this.addSubjectsStage = addSubjectsStage;
    }

    @FXML
    void handleAddSubject(ActionEvent event) throws SQLException {
        if(editMode == false) {
            if (subjectGradeLvl.getValue() != 0 && !subjectDescription.getText().isEmpty() && !subjectName.getText().isEmpty()) {
                Subject subject = new Subject(1, subjectName.getText(), subjectDescription.getText(), subjectGradeLvl.getValue(), obligatory.isSelected());
                SubjectRepository.insert(subject);
                confirmed = true;
                this.addSubjectsStage.close();

            } else {
                if (subjectName.getText().isEmpty()) {
                    namew.setVisible(true);
                }
                if (subjectDescription.getText().isEmpty()) {
                    descriptionw.setVisible(true);
                }
                if (subjectGradeLvl.getValue() == 0) {

                    gradeLvlw.setVisible(true);
                }
            }
        }
        else{
            if (subjectGradeLvl.getValue() != 0 && !subjectDescription.getText().isEmpty() && !subjectName.getText().isEmpty()) {
                Subject subject = new Subject(this.subject.getId(), subjectName.getText(), subjectDescription.getText(), subjectGradeLvl.getValue(), obligatory.isSelected());
                SubjectRepository.update(subject);
                this.addSubjectsStage.close();
            } else {
                if (subjectName.getText().isEmpty()) {
                    namew.setVisible(true);
                }
                if (subjectDescription.getText().isEmpty()) {
                    descriptionw.setVisible(true);
                }
                if (subjectGradeLvl.getValue() == 0) {

                    gradeLvlw.setVisible(true);
                }
            }
        }
    }

    @FXML
    void handleCancelButton(ActionEvent event) {
        this.addSubjectsStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        namew.setVisible(false);
        descriptionw.setVisible(false);
        gradeLvlw.setVisible(false);
        subjectGradeLvl.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 12, 0));

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
    public void setEditMode(Subject subject){
        this.subject = subject;
        this.editMode = true;
        addSubject.setText("Save Changes");
        header.setText("Edit Subject");
        subjectGradeLvl.getValueFactory().setValue(subject.getYear());
        subjectName.setText(subject.getName());
        subjectDescription.setText(subject.getDescription());
        obligatory.setSelected(subject.isObligatory());
    }

    public boolean getConfimed(){
        return confirmed;
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
        header.setText(bundle.getString("addSubject"));
        subjectNameL.setText(bundle.getString("subjectName") + ":");
        subjectDesc.setText(bundle.getString("subjectDesc") + ":");
        gradeLevel.setText(bundle.getString("gradeLevel") + ":");
        obligatory.setText(bundle.getString("obligatory"));
        addSubject.setText(bundle.getString("addSubject"));
        cancelButton.setText(bundle.getString("cancel"));
    }
}
