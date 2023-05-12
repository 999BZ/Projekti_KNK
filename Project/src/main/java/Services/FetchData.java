package Services;

import Models.Classe;
import Models.StudentUser;
import Models.Subject;
import Models.TeacherUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FetchData {
    public static ObservableList<TeacherUser> getAllTeachers() {
        ObservableList<TeacherUser> teachersList = FXCollections.observableArrayList();
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT  Teachers.T_Name, Teachers.t_Surname, Teachers.T_Birthdate, Teachers.T_Phone, Teachers.T_Address, Users.email,Users.U_ID, Users.salted_password, Users.Salt, Users.u_position, Users.u_profileimg\n" +
                    "FROM Teachers\n" +
                    "INNER JOIN Users ON Teachers.T_UID = Users.U_ID;");
            ResultSet rs = stmt.executeQuery();

            // Loop through the result set and create a Student object for each row
            while (rs.next()) {
                int id = rs.getInt("U_ID");
                String name = rs.getString("T_Name");
                String surname = rs.getString("T_Surname");
                String birthdate = rs.getString("T_Birthdate");
                String phone = rs.getString("T_Phone");
                String address = rs.getString("T_Address");
                String email = rs.getString("email");
                String salted_password = rs.getString("salted_password");
                String salt = rs.getString("salt");
                String position = rs.getString("u_position");
                String profile_pics = rs.getString("u_profileimg");

                TeacherUser teacher = new TeacherUser(id, email, salted_password, salt, position, profile_pics, name, surname, birthdate, phone, address);
                teachersList.add(teacher);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teachersList;
    }

    public static ObservableList<StudentUser> getAllStudents() {
        ObservableList<StudentUser> studentsList = FXCollections.observableArrayList();
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT Students.S_UID, Students.S_Name, Students.S_Surname, Students.S_Birthdate, Students.S_Phone, Students.S_Address, Students.S_GLevel, Students.S_Paralel, Users.email, Users.salted_password, Users.Salt, Users.u_position, Users.u_profileimg\n" +
                    "            FROM Students\n" +
                    "            INNER JOIN Users ON Students.S_UID = Users.U_ID;");
            ResultSet rs = stmt.executeQuery();

            // Loop through the result set and create a Student object for each row
            while (rs.next()) {
                int id = rs.getInt("S_UID");
                String name = rs.getString("S_Name");
                String surname = rs.getString("S_Surname");
                String birthdate = rs.getString("S_Birthdate");
                int year = rs.getInt("S_GLevel");
                String phone = rs.getString("S_Phone");
                String address = rs.getString("S_Address");
                int gradeLevel = rs.getInt("S_GLevel");
                int paralel = rs.getInt("S_Paralel");
                String email = rs.getString("email");
                String salted_password = rs.getString("salted_password");
                String salt = rs.getString("salt");
                String position = rs.getString("u_position");
                String profile_pic = rs.getString("u_profileimg");


                StudentUser student = new StudentUser(id, email, salted_password, salt, position, profile_pic, name, surname, birthdate, phone, address, year, paralel);
                studentsList.add(student);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return studentsList;
    }

    public static ObservableList<Subject> getAllSubjects() {
        ObservableList<Subject> subjectsList = FXCollections.observableArrayList();
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * from Subjects");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Sb_ID");
                String name = rs.getString("Sb_Name");
                String description = rs.getString("Sb_Description");
                int gLevel = rs.getInt("Sb_GLevel");
                boolean obligatory = rs.getBoolean("Sb_Obligatory");

                Subject subject = new Subject(id, name, description, gLevel, obligatory);
                subjectsList.add(subject);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return subjectsList;
    }

    public static ObservableList<Subject> getAllSubjects(PreparedStatement pstmt) {
        ObservableList<Subject> subjectsList = FXCollections.observableArrayList();
        try {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Sb_ID");
                String name = rs.getString("Sb_Name");
                String description = rs.getString("Sb_Description");
                int gLevel = rs.getInt("Sb_GLevel");
                boolean obligatory = rs.getBoolean("Sb_Obligatory");

                Subject subject = new Subject(id, name, description, gLevel, obligatory);
                subjectsList.add(subject);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return subjectsList;
    }

    public static ObservableList<TeacherUser> getAllTeachers(int grade) throws SQLException {
        ObservableList<TeacherUser> teachersList = FXCollections.observableArrayList();
        Connection conn = ConnectionUtil.getConnection();
        PreparedStatement ps = null;
        ps = conn.prepareStatement("SELECT T_UID, T_Name, T_Surname, T_Birthdate, T_Surname, T_Birthdate, T_Phone, T_Address, u.email, u.salted_password, u.salt, u.u_position, u.u_profileimg FROM Teachers\n" +
                "                                JOIN Classes ON Teachers.T_UID = Classes.T_ID\n" +
                "                                JOIN Users u ON u.U_ID = Teachers.T_UID \n" +
                "                                JOIN Subjects ON Classes.Sb_ID = Subjects.Sb_ID \n" +
                "                WHERE Sb_GLevel = ?\n" +
                "                group by Teachers.T_UID");
        ps.setInt(1, grade);
            ResultSet rs = ps.executeQuery();

            // Loop through the result set and create a Student object for each row
            while (rs.next()) {
                int id = rs.getInt("T_UID");
                String name = rs.getString("T_Name");
                String surname = rs.getString("T_Surname");
                String birthdate = rs.getString("T_Birthdate");
                String phone = rs.getString("T_Phone");
                String address = rs.getString("T_Address");
                String email = rs.getString("email");
                String salted_password = rs.getString("salted_password");
                String salt = rs.getString("salt");
                String position = rs.getString("u_position");
                String profile_pics = rs.getString("u_profileimg");

                TeacherUser teacher = new TeacherUser(id, email, salted_password, salt, position, profile_pics, name, surname, birthdate, phone, address);
                teachersList.add(teacher);
            }
        return teachersList;
    }

    public static int getStudentGrade(StudentUser student, Subject subject) throws SQLException {
        String query = "SELECT G_Value FROM Grades " +
                "WHERE S_ID = ? AND Sb_ID = ?";
        Connection conn = ConnectionUtil.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, student.getID());
        pstmt.setInt(2, subject.getId());
        ResultSet rs = pstmt.executeQuery();
        int grade;
        if (rs.next()) {
            grade = rs.getInt("G_Value");
        } else {
            grade = 0;
        }
        return grade;
    }

    public static ObservableList<Subject> getStudentSubjects(StudentUser student) throws SQLException {
        ObservableList<Subject> subjectsList = FXCollections.observableArrayList();
        try {
            Connection conn = ConnectionUtil.getConnection();
            String query = "SELECT s.Sb_ID, s.Sb_Name, s.Sb_Description, s.Sb_Glevel " +
                    "FROM Subjects s " +
                    "JOIN Classes c ON s.Sb_ID = c.Sb_ID " +
                    "JOIN Enrollments e ON c.C_ID = e.C_ID " +
                    "WHERE e.S_ID = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, student.getID());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Sb_ID");
                String name = rs.getString("Sb_Name");
                String description = rs.getString("Sb_Description");
                int gLevel = rs.getInt("Sb_GLevel");
                boolean obligatory = rs.getBoolean("Sb_Obligatory");

                Subject subject = new Subject(id, name, description, gLevel, obligatory);
                subjectsList.add(subject);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return subjectsList;
    }
    public static ObservableList<Classe> getSubjectClasses(Subject subject) throws SQLException {
        ObservableList<Classe> classesList = FXCollections.observableArrayList();
        try {
            Connection conn = ConnectionUtil.getConnection();
            String query = "SELECT * FROM Classes WHERE Sb_ID = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, subject.getId());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("C_ID");
                int teacherId = rs.getInt("T_ID");
                int subjectId = rs.getInt("Sb_ID");
                int paralel = rs.getInt("C_Paralel");

                Classe clas = new Classe(id, teacherId, subjectId, paralel);
                classesList.add(clas);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return classesList;
    }
    public static TeacherUser getTeacherByID(int ID) {
        TeacherUser teacher = null;
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT  Teachers.T_Name, Teachers.t_Surname, Teachers.T_Birthdate, Teachers.T_Phone, Teachers.T_Address, Users.email,Users.U_ID, Users.salted_password, Users.Salt, Users.u_position, Users.u_profileimg\n" +
                    "                    FROM Teachers\n" +
                    "                    INNER JOIN Users ON Teachers.T_UID = Users.U_ID\n" +
                    "                    where T_UID = ?");
            stmt.setInt(1,ID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("U_ID");
                String name = rs.getString("T_Name");
                String surname = rs.getString("T_Surname");
                String birthdate = rs.getString("T_Birthdate");
                String phone = rs.getString("T_Phone");
                String address = rs.getString("T_Address");
                String email = rs.getString("email");
                String salted_password = rs.getString("salted_password");
                String salt = rs.getString("salt");
                String position = rs.getString("u_position");
                String profile_pics = rs.getString("u_profileimg");

                teacher = new TeacherUser(id, email, salted_password, salt, position, profile_pics, name, surname, birthdate, phone, address);

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teacher;
    }


}
