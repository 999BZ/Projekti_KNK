package Controllers;

import Models.Subject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class SubjectCardController {
    @FXML
    private Label gradeLvl ;
   @FXML
   private Label subjectName;
   @FXML Label subjectInfo;

    public void setData(Subject subject){
        gradeLvl.setText(String.valueOf(subject.getYear()));
        subjectInfo.setText(subject.getDescription());
        subjectName.setText(subject.getName());
    }
}
