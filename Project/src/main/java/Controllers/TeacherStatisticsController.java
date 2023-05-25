package Controllers;

import Models.TeacherUser;
import Services.ConnectionUtil;
import Services.GeneralUtil;
import Services.LanguageUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class TeacherStatisticsController implements Initializable {
    @FXML
    private BarChart<String, Double> averageGradePerSubject;

    @FXML
    private BarChart<String , Integer> enrollmentsPerClass;

    @FXML
    private ProgressBar percentageOfStudentsGraded;
    @FXML
    private Label nrOfStudents;
    @FXML
    private Label users;
    @FXML
    private Label studentsGraded;
    @FXML
    private Label studentsEnrolled;
    @FXML
    private Label averageGrade;
    private TeacherUser teacher;
    private ResourceBundle bundle;


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

        //put the data in nrOfStudents Label
        nrOfStudents.setText(String.valueOf(getNumberOfStudentsForTeacher()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (LanguageUtil.getLanguage().equals("Albanian")){
            setAlbanian();
        } else {
            setEnglish();
        }
    }


    XYChart.Series<String, Integer> getEnrollmentsPerSubject(){
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

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
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(Enrollments.S_ID) AS TotalStudents,\n" +
                    "       COUNT(Grades.G_ID) AS GradedStudents,\n" +
                    "       (COUNT(Grades.G_ID) / COUNT(Enrollments.S_ID)) * 100 AS PercentageGraded\n" +
                    "FROM Enrollments\n" +
                    "JOIN Classes ON Enrollments.C_ID = Classes.C_ID\n" +
                    "JOIN Subjects ON Classes.Sb_ID = Subjects.Sb_ID\n" +
                    "JOIN Teachers ON Classes.T_ID = Teachers.T_UID\n" +
                    "LEFT JOIN Grades ON Enrollments.S_ID = Grades.S_ID AND Subjects.Sb_ID = Grades.Sb_ID\n" +
                    "WHERE Teachers.T_UID = ?;\n");
            System.out.println(this.teacher.getID());
            stmt.setInt(1, this.teacher.getID());

            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                result = rs.getDouble("PercentageGraded");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    int getNumberOfStudentsForTeacher(){
        int result=0;

        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(DISTINCT S_ID) AS Num_Students\n" +
                    "FROM Enrollments\n" +
                    "JOIN Classes ON Enrollments.C_ID = Classes.C_ID\n" +
                    "JOIN Teachers ON Classes.T_ID = Teachers.T_UID\n" +
                    "WHERE Teachers.T_UID = ?;\n");
            stmt.setInt(1, this.teacher.getID());
            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                result = rs.getInt("Num_Students");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void setAlbanian() {
        try {
            Locale locale = new Locale("sq");
            bundle = ResourceBundle.getBundle("Bilinguality.NameTags_sq", locale);

            updateLabels();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setEnglish() {
        try {
            Locale locale = Locale.ENGLISH;
            bundle = ResourceBundle.getBundle("Bilinguality.NameTags_en", locale);

            updateLabels();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private void updateLabels() {
        averageGrade.setText(bundle.getString("averageGrade"));
        studentsEnrolled.setText(bundle.getString("studentsEnrolled"));
        studentsGraded.setText(bundle.getString("studentsGraded"));
        users.setText(bundle.getString("students"));
    }
}



