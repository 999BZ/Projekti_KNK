package Controllers;

import Models.Grade;
import Models.StudentUser;
import Models.User;
import Repository.UserRepository;
import Services.ConnectionUtil;
import Services.FetchData;
import Services.PasswordHasher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class StudentsStatisticsController implements Initializable {


    @FXML
    private Label lblaverage;

    @FXML
    private LineChart<String,Integer> GradesStats;
    @FXML
    private PieChart gradesPieChart;

    private Stage dialogStage;

    private String position;

    private StudentUser student;

    public StudentUser getStudent() {
        return student;
    }

    public void setStudent(StudentUser student) throws SQLException {
        this.student = student;
        Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
        this.position = preferences.get("position",null);
        GradesStats.getData().add(FetchData.getAllGrades(this.student.getID()));
        this.lblaverage.setText(Double.toString(FetchData.getAverageOfGrades(this.student.getID())));
        gradesPieChart.setData(getCountPerGradeEvaluation());
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void setLblaverage(String lblaverage) {
        this.lblaverage.setText(lblaverage);
    }

    public Label getLblaverage() {
        return lblaverage;
    }

    ObservableList<PieChart.Data> getCountPerGradeEvaluation() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT G_Value, COUNT(*) AS GradeCount\n" +
                    "FROM Grades g\n" +
                    "join Students s \n" +
                    "on g.S_ID=s.S_UID \n" +
                    "where s.S_UID = ? \n" +
                    "GROUP BY G_Value");
            stmt.setInt(1, this.student.getID());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int gradeValue = rs.getInt("G_Value");
                int gradeCount = rs.getInt("GradeCount");
                PieChart.Data data = new PieChart.Data(String.valueOf(gradeValue), gradeCount);
                pieChartData.add(data);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pieChartData;
    }






}
