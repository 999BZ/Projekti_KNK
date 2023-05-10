package Services;

import Controllers.SubjectCardController;
import Models.Subject;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class CardGenUtil {
    public static void subjectsToFlowPane(FlowPane subjectCards,  ObservableList<Subject> subjectsList ){
        subjectCards.getChildren().clear();
        for(int i = 0; i<subjectsList.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(CardGenUtil.class.getResource("/Main/SubjectCard.fxml"));
            System.out.println(subjectsList.get(i).getId());

            try {
                VBox hBox = fxmlLoader.load();
                SubjectCardController scc = fxmlLoader.getController();
                scc.setData(subjectsList.get(i));
                subjectCards.getChildren().add(hBox);
            } catch (IOException e) {
                System.out.println(e);
            }
        }

    }
}
