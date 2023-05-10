package Controllers;

import Models.Subject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class SubjectCardController {
    @FXML
    private Label gradeLvl = new Label();
   @FXML
   private Label subjectName = new Label();
   @FXML Text subjectInfo = new Text();

    public void setData(Subject subject){
        gradeLvl.setText(String.valueOf(subject.getYear()));
        subjectInfo.setText(subject.getDescription());
        subjectName.setText(subject.getName());
    }
}
