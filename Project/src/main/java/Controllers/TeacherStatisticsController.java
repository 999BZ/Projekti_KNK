package Controllers;

import Models.TeacherUser;
import Services.ConnectionUtil;
import Services.GeneralUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TeacherStatisticsController implements Initializable {
    @FXML
    private BarChart<String, Double> averageGradePerSubject;

    @FXML
    private BarChart<String , Integer> enrollmentsPerClass;

    @FXML
    private ProgressBar percentageOfStudentsGraded;

    private TeacherUser teacher;


    public void setTeacher(TeacherUser teacher){
        this.teacher = teacher;

        //put the chart data in the enrollmentsPerSubjectChart
        XYChart.Series<String, Integer> series = getEnrollmentsPerSubject();
        enrollmentsPerClass.getData().add(series);
        GeneralUtil.colorizeChartDataSeries(series);



        //put the chart data in the averageGradePerStudent
        XYChart.Series<String, Double> series1 = getAverageGradePerSubject();
        averageGradePerSubject.getData().add(series1);
        GeneralUtil.colorizeChartDataSeries(series1);

        //put the data in percentage
        double percentage = getPercentageOfStudentsGraded()/100;
        percentageOfStudentsGraded.setProgress(percentage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    XYChart.Series<String, Integer> getEnrollmentsPerSubject(){
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Enrollment Count");

        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT E.C_ID, COUNT(*) AS EnrollmentCount, S.Sb_Name, S.Sb_GLevel, C.C_Paralel\n" +
                    "FROM Enrollments E\n" +
                    "JOIN Classes C ON E.C_ID = C.C_ID\n" +
                    "JOIN Subjects S ON C.Sb_ID = S.Sb_ID\n" +
                    "WHERE C.T_ID = ?\n" +
                    "GROUP BY E.C_ID;");
            stmt.setInt(1, this.teacher.getID());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int number = rs.getInt("EnrollmentCount");
                String classe = rs.getString("Sb_Name")+" ("+rs.getString("Sb_GLevel")+"/"+rs.getString("C_Paralel")+")";
                XYChart.Data<String, Integer> data = new XYChart.Data<>(classe, number);
                series.getData().add(data);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return series;
    }


    XYChart.Series<String, Double> getAverageGradePerSubject(){
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName("Enrollment Count");

        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT S.Sb_Name, AVG(G.G_Value) AS AverageGrade\n" +
                    "FROM Grades G\n" +
                    "JOIN Subjects S ON G.Sb_ID = S.Sb_ID\n" +
                    "WHERE G.Sb_ID IN (\n" +
                    "    SELECT Sb_ID\n" +
                    "    FROM Subjects\n" +
                    "    WHERE Sb_ID IN (\n" +
                    "        SELECT Sb_ID\n" +
                    "        FROM Classes\n" +
                    "        WHERE T_ID = ?\n" +
                    "    )\n" +
                    ")\n" +
                    "GROUP BY G.Sb_ID;");
            stmt.setInt(1, this.teacher.getID());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                double averageGrade = rs.getInt("AverageGrade");
                String subjectName = rs.getString("Sb_Name");
                XYChart.Data<String, Double> data = new XYChart.Data<>(subjectName, averageGrade);
                series.getData().add(data);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return series;
    }


    double getPercentageOfStudentsGraded(){
        double result=0;

        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT (COUNT(DISTINCT E.S_ID) / (SELECT COUNT(DISTINCT E.S_ID) FROM Enrollments E\n" +
                    "    JOIN Classes C ON E.C_ID = C.C_ID\n" +
                    "    WHERE C.T_ID = ?\n" +
                    ")) * 100 AS PercentageFinishedGrading\n" +
                    "FROM Enrollments E\n" +
                    "JOIN Classes C ON E.C_ID = C.C_ID\n" +
                    "JOIN Subjects S ON C.Sb_ID = S.Sb_ID\n" +
                    "WHERE C.T_ID =?\n" +
                    "AND EXISTS (\n" +
                    "    SELECT 1\n" +
                    "    FROM Grades G\n" +
                    "    WHERE G.S_ID = E.S_ID\n" +
                    "    AND G.Sb_ID = S.Sb_ID\n" +
                    ");");
            stmt.setInt(1, this.teacher.getID());
            stmt.setInt(2, this.teacher.getID());
            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                result = rs.getDouble("PercentageFinishedGrading");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


}



