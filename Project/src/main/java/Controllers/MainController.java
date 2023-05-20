package Controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import Services.ConnectionUtil;
import Services.GeneralUtil;
import Services.LanguageUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MainController {
    @FXML
    private BorderPane rootPane;
    @FXML
    private AnchorPane sidenav;
    @FXML
    private Label nrOfStudents;
    @FXML
    private Label nrOfTeachers;
    @FXML
    private Label nrOfSubjects;
    @FXML
    private Label nrOfGrades;

    @FXML
    private Label nrOfUsers;
    @FXML
    private Label nrOfEnrollments;
    @FXML
    private Label students;
    @FXML
    private Label teachers;
    @FXML
    private Label subjects;
    @FXML
    private Label grades;
    @FXML
    private Label users;
    @FXML
    private Label enrollments;
    @FXML
    private StackedBarChart<String, Integer> enrollmentsOverTime;
    @FXML
    private BarChart<String, Integer> studentsPerGradeLevel;
    @FXML
    private PieChart countOfGradeEvaluations;
    private ResourceBundle bundle;


    public void initialize() {
        if (LanguageUtil.getLanguage().equals("Albanian")){
            setAlbanian();
        } else {
            setEnglish();
        }

        LanguageUtil.languageProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Albanian")) {
                setAlbanian();
            } else {
                setEnglish();
            }
        });

        Preferences prefs = Preferences.userNodeForPackage(LoginController.class);

        // Get the user ID from the preferences
        int userId = prefs.getInt("userId", 0);
        // Get the user position from the preferences
        String userPosition = prefs.get("position", null);

        if(userPosition.equals("Admin")){
            sidenav.getStyleClass().clear();
            sidenav.getStyleClass().add("profile");
        }



        try {
            // get the number of students from the database
            nrOfStudents.setText(String.valueOf(getStudentCount()));

            //get the number of teachers from the database
            nrOfTeachers.setText(String.valueOf(getTeacherCount()));

            //get the number of subjects from the database
            nrOfSubjects.setText(String.valueOf(getSubjectCount()));

            //get the number of users from the database
            nrOfUsers.setText(String.valueOf(getUserCount()));

            //get the number of enrollments from the database
            nrOfEnrollments.setText(String.valueOf(getEnrollmentCount()));

            //get the number of grades from the database
            nrOfGrades.setText(String.valueOf(getGradeCount()));

            //get the enrollments over time from the database
            XYChart.Series<String, Integer> series = getEnrollmentsPerWeek();
            series.setName("Enrollment Count");
            enrollmentsOverTime.getData().add(series);
            enrollmentsOverTime.getXAxis().setLabel("Week");
            enrollmentsOverTime.getYAxis().setLabel("Enrollment Count");
            GeneralUtil.colorizeChartDataSeries(series);

            //get students per grade level
            XYChart.Series<String, Integer> series2 = getStudentsPerGradeLevel();
            studentsPerGradeLevel.getData().add(series2);
            studentsPerGradeLevel.getXAxis().setLabel("Grade Level");
            studentsPerGradeLevel.getYAxis().setLabel("Student Count");
            GeneralUtil.colorizeChartDataSeries(series2);


            //get count per grade evaluation
            countOfGradeEvaluations.setData(getCountPerGradeEvaluation());

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    XYChart.Series<String, Integer> getEnrollmentsPerWeek(){
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Enrollment Count");

        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT WEEK(E_Date) AS WeekNumber, COUNT(*) AS EnrollmentCount " +
                    "FROM Enrollments " +
                    "GROUP BY WEEK(E_Date) " +
                    "ORDER BY WEEK(E_Date)");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int weekNumber = rs.getInt("WeekNumber");
                int enrollmentCount = rs.getInt("EnrollmentCount");
                System.out.println("Week number: " + weekNumber + " Enrollment count: " + enrollmentCount);
                series.getData().add(new XYChart.Data<>(String.valueOf(weekNumber), enrollmentCount));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return series;
    }

    int getSubjectCount() throws SQLException {
        int numberOfSubjects = 0;
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS number_of_subjects FROM subjects");
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            numberOfSubjects = resultSet.getInt("number_of_subjects");
        }
        return numberOfSubjects;
    }

    int getAdminCount() throws SQLException {
        int numberOfAdmins = 0;
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS number_of_admins FROM users where u_position='Admin'");
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            numberOfAdmins = resultSet.getInt("number_of_admins");
        }
        return numberOfAdmins;
    }

    int getTeacherCount() throws SQLException {
        int numberOfTeachers = 0;
        Connection connection = ConnectionUtil.getConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS number_of_teachers FROM users where u_position='Teacher'");
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            numberOfTeachers = resultSet.getInt("number_of_teachers");
        }
        return numberOfTeachers;
    }

    int getStudentCount() throws SQLException {
        int numberOfStudents = 0;
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS number_of_students FROM users where u_position='Student'");
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            numberOfStudents = resultSet.getInt("number_of_students");
        }
        return numberOfStudents;
    }

    int getEnrollmentCount() {
        int numberOfEnrollments = 0;
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS number_of_enrollments FROM enrollments");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numberOfEnrollments = resultSet.getInt("number_of_enrollments");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberOfEnrollments;
    }
    int getGradeCount() {
        int numberOfGrades = 0;
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS number_of_grades FROM grades");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numberOfGrades = resultSet.getInt("number_of_grades");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberOfGrades;
    }

    int getUserCount(){
        int numberOfUsers = 0;
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS number_of_users FROM users");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numberOfUsers = resultSet.getInt("number_of_users");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberOfUsers;
    }

    XYChart.Series<String, Integer> getStudentsPerGradeLevel(){
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Enrollment Count");

        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT S_GLevel, COUNT(*) AS StudentCount\n" +
                    "FROM Students\n" +
                    "GROUP BY S_GLevel;");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int number = rs.getInt("StudentCount");
                int gradeLevel = rs.getInt("S_GLevel");
                XYChart.Data<String, Integer> data = new XYChart.Data<>(String.valueOf(gradeLevel), number);
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

    ObservableList<PieChart.Data> getCountPerGradeEvaluation() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT G_Value, COUNT(*) AS GradeCount\n" +
                    "FROM Grades\n" +
                    "GROUP BY G_Value;");
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
        students.setText(bundle.getString("students"));
        teachers.setText(bundle.getString("teachers"));
        subjects.setText(bundle.getString("subjects"));
        grades.setText(bundle.getString("grades"));
        users.setText(bundle.getString("users"));
        enrollments.setText(bundle.getString("enrollments"));
        countOfGradeEvaluations.setTitle(bundle.getString("countOfGradeEvaluations"));
    }
}





