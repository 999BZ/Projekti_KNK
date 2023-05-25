package Services;

import Controllers.*;
import Models.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Pagination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;

public class CardGenUtil {
    public static void subjectsToFlowPane(Pagination pg,int subjectsPerPage, VBox subjectCards, ObservableList<Subject> subjectsList, SubjectsController sc){
        pg.setPageFactory(pageIndex -> {
            VBox pageContent = new VBox();

            int startIndex = pageIndex * subjectsPerPage;
            int endIndex = Math.min(startIndex + subjectsPerPage, subjectsList.size());

            for (int i = startIndex; i < endIndex; i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(CardGenUtil.class.getResource("/Main/SubjectCard.fxml"));

                try {
                    VBox hBox = fxmlLoader.load();
                    SubjectCardController scc = fxmlLoader.getController();
                    scc.setData(subjectsList.get(i));
                    scc.setParentController(sc);
                    pageContent.getChildren().add(hBox);
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
            return pageContent;
        });

        subjectCards.getChildren().clear();
        subjectCards.getChildren().addAll(pg);
    }

    public static void gradesToFlowPane(VBox subjectCards, ObservableList<Enrollment> enrollmentsList, GradesController gc){
        subjectCards.getChildren().clear();
        for (Enrollment enrollment : enrollmentsList) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(CardGenUtil.class.getResource("/Main/GradeStudentCard.fxml"));

            try {
                VBox hBox = fxmlLoader.load();
                GradeStudentCardController scc = fxmlLoader.getController();
                scc.setData(FetchData.getStudentFromEnrollment(enrollment.getId()), FetchData.getSubjectFromEnrollmentId(enrollment.getId()));
                scc.setParentController(gc);
                subjectCards.getChildren().add(hBox);
            } catch (IOException e) {
                System.out.println(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void subjectsToFlowPaneStudents(VBox subjectCards, ObservableList<Subject> subjectsList, StudentUser student){
        subjectCards.getChildren().clear();
        for (Subject subject : subjectsList) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(CardGenUtil.class.getResource("/Main/StudentSubjectCard.fxml"));
            System.out.println(subject.getId());

            try {
                HBox hBox = fxmlLoader.load();
                StudentSubjectCardController scc = fxmlLoader.getController();
                scc.setData(subject, student);
                subjectCards.getChildren().add(hBox);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public static void subjectsToFlowPaneTeachers(VBox subjectCards, ObservableList<Subject> subjectsList, TeacherUser teacher){
        subjectCards.getChildren().clear();
        for (Subject subject : subjectsList) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(CardGenUtil.class.getResource("/Main/StudentSubjectCard.fxml"));
            System.out.println(subject.getId());

            try {
                HBox hBox = fxmlLoader.load();
                StudentSubjectCardController scc = fxmlLoader.getController();
                scc.setData(subject, teacher);
                subjectCards.getChildren().add(hBox);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
