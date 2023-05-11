package Services;

import Controllers.StudentSubjectCardController;
import Controllers.SubjectCardController;
import Controllers.SubjectsController;
import Models.StudentUser;
import Models.Subject;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class CardGenUtil {
    public static void subjectsToFlowPane(VBox subjectCards, ObservableList<Subject> subjectsList, SubjectsController sc){
        subjectCards.getChildren().clear();
        for(int i = 0; i<subjectsList.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(CardGenUtil.class.getResource("/Main/SubjectCard.fxml"));
            System.out.println(subjectsList.get(i).getId());

            try {
                VBox hBox = fxmlLoader.load();
                SubjectCardController scc = fxmlLoader.getController();
                scc.setData(subjectsList.get(i));
                scc.setParentController(sc);
                subjectCards.getChildren().add(hBox);
            } catch (IOException e) {
                System.out.println(e);
            }
        }

    }
    public static void subjectsToFlowPaneStudents(VBox subjectCards, ObservableList<Subject> subjectsList, StudentUser student){
        subjectCards.getChildren().clear();
        for(int i = 0; i<subjectsList.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(CardGenUtil.class.getResource("/Main/StudentSubjectCard.fxml"));
            System.out.println(subjectsList.get(i).getId());

            try {
                HBox hBox = fxmlLoader.load();
                StudentSubjectCardController scc = fxmlLoader.getController();
                scc.setData(subjectsList.get(i), student);
                subjectCards.getChildren().add(hBox);
            } catch (IOException e) {
                System.out.println(e);
            }
        }

    }
}
