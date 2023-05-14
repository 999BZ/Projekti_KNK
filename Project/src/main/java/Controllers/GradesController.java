package Controllers;

import Models.Enrollment;
import Models.TeacherUser;
import Services.CardGenUtil;
import Services.FetchData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GradesController implements Initializable {
    @FXML
    private VBox gradesCard;

    private ObservableList<Enrollment> enrollmentsList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        enrollmentsList = FetchData.getAllEnrollments();
        CardGenUtil.gradesToFlowPane(gradesCard,enrollmentsList,this);
    }
}
