package Controllers;

import Models.Grade;
import Models.User;
import Repository.UserRepository;
import Services.ConnectionUtil;
import Services.FetchData;
import Services.PasswordHasher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class StudentsStatisticsController implements Initializable {


    private ArrayList<Grade> gradesList;

    public ArrayList<Grade> getGradesList() {
        return gradesList;
    }

    public void setGradesList(ArrayList<Grade> gradesList) {
        this.gradesList = gradesList;
    }

    @FXML
    private Label lblaverage;

    @FXML
    private LineChart<Number,Number> GradesStats;

    private Stage dialogStage;

    private String position;

    private int U_ID;
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setUserId(int U_ID){this.U_ID=U_ID;}


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
        this.position = preferences.get("position",null);
        this.gradesList = FetchData.getAllGrades(this.U_ID);
        XYChart.Series<Number,Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Data Series");
        for (int i = 0; i < gradesList.size(); i++) {
            int data = gradesList.get(i).getGrade();
            dataSeries.getData().add(new XYChart.Data<>(i, data));
        }
        GradesStats.getData().add(dataSeries);
        if (gradesList.isEmpty()){
            System.out.println("No grades there");
        }

    }


    public void setLblaverage(String lblaverage) {
        this.lblaverage.setText(lblaverage);
    }

    public Label getLblaverage() {
        return lblaverage;
    }






}
