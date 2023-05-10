package Controllers;

import Models.Subject;
import Services.ConnectionUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SubjectsController implements Initializable {
    @FXML
    private FlowPane subjectCards;
    private ObservableList<Subject> subjectsList = FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialize");

        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * from Subjects");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Sb_ID");
                String name = rs.getString("Sb_Name");
                String description = rs.getString("Sb_Description");
                int gLevel = rs.getInt("Sb_GLevel");

                Subject subject = new Subject(id, name, description, gLevel);
                subjectsList.add(subject);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        for(int i = 0; i<subjectsList.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/Main/SubjectCard.fxml"));
            System.out.println(subjectsList.get(i).getId());

            try {
                VBox hBox = fxmlLoader.load();
                SubjectCardController scc = new SubjectCardController();
                scc.setData(subjectsList.get(i));
                subjectCards.getChildren().add(hBox);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
