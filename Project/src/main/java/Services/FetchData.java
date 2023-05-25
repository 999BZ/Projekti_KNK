package Services;

import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FetchData {
    public static ObservableList<TeacherUser> getAllTeachers() {
        ObservableList<TeacherUser> teachersList = FXCollections.observableArrayList();
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT  Teachers.T_Name, Teachers.t_Surname, Teachers.T_Birthdate, Teachers.T_Phone, Teachers.T_Gender, Teachers.T_Address, Users.email,Users.U_ID, Users.salted_password, Users.Salt, Users.u_position, Users.u_profileimg\n" +
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
                String gender = rs.getString("T_Gender");

                TeacherUser teacher = new TeacherUser(id, email, salted_password, salt, position, profile_pics, name, surname, birthdate, phone, address,gender);
                teachersList.add(teacher);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teachersList;
    }

    public static ObservableList<TeacherUser> searchAllTeachers(String string) {
        ObservableList<TeacherUser> teachersList = FXCollections.observableArrayList();
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT *\n" +
                    "FROM Teachers\n" +
                    "INNER JOIN Users ON Teachers.T_UID = Users.U_ID\n" +
                    "WHERE Teachers.T_Name LIKE ? OR Teachers.T_Surname LIKE ?;");
            stmt.setString(1, "%" + string + "%");
            stmt.setString(2, "%" + string + "%");
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
                String gender = rs.getString("T_Gender");

                TeacherUser teacher = new TeacherUser(id, email, salted_password, salt, position, profile_pics, name, surname, birthdate, phone, address,gender);
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
            PreparedStatement stmt = conn.prepareStatement("SELECT Students.S_UID, Students.S_Name, Students.S_Surname, Students.S_Gender, Students.S_Birthdate, Students.S_Phone, Students.S_Address, Students.S_GLevel, Students.S_Paralel, Users.email, Users.salted_password, Users.Salt, Users.u_position, Users.u_profileimg\n" +
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
                String gender = rs.getString("S_Gender");

                StudentUser student = new StudentUser(id, email, salted_password, salt, position, profile_pic, name, surname, birthdate, phone, address, year, paralel,gender);
                studentsList.add(student);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return studentsList;
    }
    public static ObservableList<StudentUser> searchAllStudents(String string) {
        ObservableList<StudentUser> studentsList = FXCollections.observableArrayList();
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * " +
                    "FROM Students \n" +
                    "join users on Students.S_UID = users.u_id  " +
                    "WHERE S_Name LIKE ? OR S_Surname LIKE ?");
            stmt.setString(1, "%" + string + "%");
            stmt.setString(2, "%" + string + "%");
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
                String gender = rs.getString("S_Gender");

                StudentUser student = new StudentUser(id, email, salted_password, salt, position, profile_pic, name, surname, birthdate, phone, address, year, paralel,gender);
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
        ps = conn.prepareStatement("SELECT T_UID, T_Name, T_Surname, T_Birthdate, T_Surname, T_Birthdate, T_Phone, T_Gender, T_Address, u.email, u.salted_password, u.salt, u.u_position, u.u_profileimg FROM Teachers\n" +
                "                                JOIN Classes ON Teachers.T_UID = Classes.T_ID\n" +
                "                                JOIN Users u ON u.U_ID = Teachers.T_UID \n" +
                "                                JOIN Subjects ON Classes.Sb_ID = Subjects.Sb_ID \n" +
                "                WHERE Sb_GLevel = ?\n" +
                "                group by Teachers.T_UID");
        ps.setInt(1, grade);
            ResultSet rs = ps.executeQuery();

            // Loop through the result set and create a Teacher object for each row
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
                String gender = rs.getString("T_Gender");

                TeacherUser teacher = new TeacherUser(id, email, salted_password, salt, position, profile_pics, name, surname, birthdate, phone, address,gender);
                teachersList.add(teacher);
            }
        return teachersList;
    }

    public static ObservableList<TeacherUser> getTeachersBySubjectName(String subjName) {
        ObservableList<TeacherUser> teachersList = FXCollections.observableArrayList();
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT T_UID, T_Name, T_Birthdate, T_Surname, T_Phone, T_Gender, T_Address, u.email, u.salted_password, u.salt, u.u_position, u.U_ID, u.u_profileimg FROM Teachers\n" +
                    "                                JOIN Classes ON Teachers.T_UID = Classes.T_ID\n" +
                    "                                JOIN Users u ON u.U_ID = Teachers.T_UID \n" +
                    "                                JOIN Subjects ON Classes.Sb_ID = Subjects.Sb_ID \n" +
                    "                WHERE Sb_Name = ?\n" +
                    "                group by Teachers.T_UID");
            stmt.setString(1, subjName);
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
                String gender = rs.getString("T_Gender");

                TeacherUser teacher = new TeacherUser(id, email, salted_password, salt, position, profile_pics, name, surname, birthdate, phone, address,gender);
                teachersList.add(teacher);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teachersList;
    }

    public static ObservableList<StudentUser> getStudentsByGLevel(String Glevel) {
        int Level = Integer.parseInt(Glevel);
        ObservableList<StudentUser> studentsList = FXCollections.observableArrayList();
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT S_UID, S_Name, S_Surname, S_Paralel, S_Birthdate, S_Phone, S_GLevel, S_Gender, S_Address, u.email, u.salted_password, u.salt, u.u_position, u.U_ID, u.u_profileimg FROM Students\n" +
                    "                                JOIN Users u ON u.U_ID = Students.S_UID \n" +
                    "                WHERE S_GLevel = ?\n" +
                    "                group by Students.S_UID");
            stmt.setInt(1, Level);
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
                String gender = rs.getString("S_Gender");

                StudentUser student = new StudentUser(id, email, salted_password, salt, position, profile_pic, name, surname, birthdate, phone, address, year, paralel, gender);
                studentsList.add(student);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return studentsList;
    }

    public static ObservableList<StudentUser> getStudentsByGLevelParalel(String Glevel,String Paralel) {
        int Level = Integer.parseInt(Glevel);
        int SParalel = Integer.parseInt(Paralel);
        ObservableList<StudentUser> studentsList = FXCollections.observableArrayList();
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT S_UID, S_Name, S_Surname, S_Paralel, S_Birthdate, S_Phone, S_GLevel, S_Gender, S_Address, u.email, u.salted_password, u.salt, u.u_position, u.U_ID, u.u_profileimg FROM Students\n" +
                    "                                JOIN Users u ON u.U_ID = Students.S_UID \n" +
                    "                WHERE S_GLevel = ? AND S_Paralel = ?\n" +
                    "                group by Students.S_UID");
            stmt.setInt(1, Level);
            stmt.setInt(2, SParalel);
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
                String gender = rs.getString("S_Gender");

                StudentUser student = new StudentUser(id, email, salted_password, salt, position, profile_pic, name, surname, birthdate, phone, address, year, paralel, gender);
                studentsList.add(student);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return studentsList;
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
    public static int getStudentGradeId(StudentUser student, Subject subject) throws SQLException {
        String query = "SELECT G_ID FROM Grades " +
                "WHERE S_ID = ? AND Sb_ID = ?";
        Connection conn = ConnectionUtil.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, student.getID());
        pstmt.setInt(2, subject.getId());
        ResultSet rs = pstmt.executeQuery();
        int gradeID = 0;
        while (rs.next()) {
            gradeID = rs.getInt("G_ID");
        }
        return gradeID;
    }

    public static String getTeacherP(TeacherUser teacher, Subject subject) throws SQLException {
        String query = "SELECT C_Paralel FROM Classes " +
                "WHERE T_ID = ? AND Sb_ID = ? " +
                "ORDER BY C_Paralel ASC";
        Connection conn = ConnectionUtil.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, teacher.getID());
        pstmt.setInt(2, subject.getId());
        ResultSet rs = pstmt.executeQuery();
        int P;
        String Parallels = "";

        if(rs.next()) {
            P = rs.getInt("C_Paralel");
            Parallels += P;
        }
        while (rs.next()) {
            P = rs.getInt("C_Paralel");
            Parallels += ", " + P;
        }

        return Parallels;
    }

    public static ObservableList<Subject> getStudentSubjects(StudentUser student) throws SQLException {
        ObservableList<Subject> subjectsList = FXCollections.observableArrayList();
        try {
            Connection conn = ConnectionUtil.getConnection();
            String query = "SELECT s.Sb_ID, s.Sb_Name, s.Sb_Description, s.Sb_Glevel, s.Sb_Obligatory " +
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

    public static ObservableList<Subject> getTeacherSubjects(TeacherUser teacher) throws SQLException {
        ObservableList<Subject> subjectsList = FXCollections.observableArrayList();
        try {
            Connection conn = ConnectionUtil.getConnection();
            String query = "SELECT s.Sb_ID, s.Sb_Name, s.Sb_Description, s.Sb_Glevel, s.Sb_Obligatory " +
                    "FROM Subjects s " +
                    "JOIN Classes c ON s.Sb_ID = c.Sb_ID " +
                    "JOIN Teachers t ON c.T_ID = t.T_UID " +
                    "WHERE t.T_UID = ? " +
                    "GROUP BY s.Sb_ID";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, teacher.getID());
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

    public static ObservableList<Subject> getTeacherSubjectsEnrolled(int ID) throws SQLException {
        ObservableList<Subject> subjectsList = FXCollections.observableArrayList();
        try {
            Connection conn = ConnectionUtil.getConnection();
            String query = "SELECT DISTINCT Subjects.*\n" +
                    "FROM Subjects\n" +
                    "JOIN Classes ON Subjects.Sb_ID = Classes.Sb_ID\n" +
                    "JOIN Enrollments ON Classes.C_ID = Enrollments.C_ID\n" +
                    "WHERE Classes.T_ID = ?;";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, ID);
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

    public static int getStudentIdFromEnrollment(int enrollmentID) {
        int studentID = 0;
        try {
            Connection conn = ConnectionUtil.getConnection();
            String query = "SELECT S_ID FROM Enrollments WHERE E_ID = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, enrollmentID);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                studentID = rs.getInt("S_ID");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return studentID;
    }

    public static Subject getSubjectFromEnrollmentId(int enrollmentId) throws SQLException {
        Subject subject = null;
        Connection conn = ConnectionUtil.getConnection();
        String query = "SELECT Subjects.* FROM Enrollments \n" +
                "                           JOIN Classes ON Enrollments.C_ID = Classes.C_ID \n" +
                "                           JOIN Subjects ON Classes.Sb_ID = Subjects.Sb_ID \n" +
                "                           WHERE E_ID = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, enrollmentId);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            int subjectId = rs.getInt("Sb_ID");
             String subjectName = rs.getString("Sb_Name");
            String subjectDescription = rs.getString("Sb_Description");
            int gradeLevel = rs.getInt("Sb_GLevel");
            boolean obligatory = rs.getBoolean("Sb_Obligatory");
            subject = new Subject(subjectId,subjectName,subjectDescription,gradeLevel,obligatory);
        }
        rs.close();
        statement.close();
        conn.close();
        return subject;
    }
    public static StudentUser getStudentFromEnrollment(int enrollmentId) throws SQLException {
        StudentUser student = null;
        try {
            Connection conn = ConnectionUtil.getConnection();
            String query = "SELECT s.*, u.* FROM enrollments e INNER JOIN students s ON e.S_ID = s.S_UID inner join users u on s.s_uid = u.u_id WHERE e.E_ID = ?;";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, enrollmentId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("S_UID");
                String name = rs.getString("S_Name");
                String surname = rs.getString("S_Surname");
                String birthdate = rs.getDate("S_Birthdate").toLocalDate().toString();
                String phone = rs.getString("S_Phone");
                String address = rs.getString("S_Address");
                int gradeLevel = rs.getInt("S_GLevel");
                int paralel = rs.getInt("S_Paralel");
                String email = rs.getString("Email");
                String password = rs.getString("Salted_Password");
                String salt = rs.getString("Salt");
                String position = rs.getString("U_Position");
                String profileImage = rs.getString("U_ProfileImg");
                String gender = rs.getString("S_Gender");

                student = new StudentUser(id, email, password, salt, position, profileImage, name, surname, birthdate, phone, address, gradeLevel, paralel,gender);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return student;
    }


    public static ObservableList<Enrollment> getAllTeacherEnrollments() {
        ObservableList<Enrollment> enrollmentsList = FXCollections.observableArrayList();
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * from Enrollments");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("E_ID");
                int classId = rs.getInt("C_ID");
                int studentId = rs.getInt("S_ID");
                String date = rs.getDate("E_Date").toLocalDate().toString();

                Enrollment enrollment = new Enrollment(id, studentId, classId, date);
                enrollmentsList.add(enrollment);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return enrollmentsList;
    }

    public static ObservableList<Enrollment> getAllTeacherEnrollments(int ID) {
        ObservableList<Enrollment> enrollmentsList = FXCollections.observableArrayList();
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT *\n" +
                    "FROM Enrollments\n" +
                    "JOIN Classes ON Enrollments.C_ID = Classes.C_ID\n" +
                    "WHERE Classes.T_ID = ?;");
            stmt.setInt(1, ID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("E_ID");
                int classId = rs.getInt("C_ID");
                int studentId = rs.getInt("S_ID");
                String date = rs.getDate("E_Date").toLocalDate().toString();

                Enrollment enrollment = new Enrollment(id, studentId, classId, date);
                enrollmentsList.add(enrollment);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return enrollmentsList;
    }

    public static ObservableList<Enrollment> searchAllTeacherEnrollments(int ID, String searchString) {
        ObservableList<Enrollment> enrollmentsList = FXCollections.observableArrayList();
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT Enrollments.*, Classes.C_ID, Classes.T_ID\n" +
                    "FROM Enrollments\n" +
                    "JOIN Classes ON Enrollments.C_ID = Classes.C_ID\n" +
                    "JOIN Students ON Enrollments.S_ID = Students.S_UID\n" +
                    "WHERE Classes.T_ID = ?\n" +
                    "AND (Students.S_Name LIKE ? OR Students.S_Surname LIKE ?);");
            stmt.setInt(1, ID);
            stmt.setString(2, "%" + searchString + "%");
            stmt.setString(3, "%" + searchString + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("E_ID");
                int classId = rs.getInt("C_ID");
                int studentId = rs.getInt("S_ID");
                String date = rs.getDate("E_Date").toLocalDate().toString();

                Enrollment enrollment = new Enrollment(id, studentId, classId, date);
                enrollmentsList.add(enrollment);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return enrollmentsList;
    }


    public static ObservableList<Enrollment> getAllEnrollmentsBySubjectOfTeacher(int subjectId, int teacherId) {
        ObservableList<Enrollment> enrollmentsList = FXCollections.observableArrayList();
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT Enrollments.*\n" +
                    "FROM Enrollments\n" +
                    "JOIN Classes ON Enrollments.C_ID = Classes.C_ID\n" +
                    "JOIN Subjects ON Classes.Sb_ID = Subjects.Sb_ID\n" +
                    "JOIN Teachers ON Classes.T_ID = Teachers.T_UID\n" +
                    "WHERE Subjects.Sb_ID = ?\n" +
                    "  AND Teachers.T_UID = ?;\n");
            stmt.setInt(1, subjectId);
            stmt.setInt(2, teacherId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("E_ID");
                int classId = rs.getInt("C_ID");
                int studentId = rs.getInt("S_ID");
                String date = rs.getDate("E_Date").toLocalDate().toString();

                Enrollment enrollment = new Enrollment(id, studentId, classId, date);
                enrollmentsList.add(enrollment);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return enrollmentsList;
    }

    public static boolean hasStudentSetElectiveSubject(StudentUser student) throws SQLException {
        int EnrollmentCount = 0;
        Connection conn = ConnectionUtil.getConnection();
        PreparedStatement ps  = conn.prepareStatement("SELECT COUNT(*) AS EnrollmentCount\n" +
                "                FROM Enrollments\n" +
                "                INNER JOIN Classes ON Enrollments.C_ID = Classes.C_ID\n" +
                "                INNER JOIN Subjects ON Classes.Sb_ID = Subjects.Sb_ID\n" +
                "                inner join Students on Enrollments.S_ID = Students.S_UID\n" +
                "                WHERE Students.S_UID = ?\n" +
                "                AND Subjects.Sb_Obligatory = 0;");
        ps.setInt(1, student.getID());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            EnrollmentCount = rs.getInt("EnrollmentCount");
        }
        if(EnrollmentCount > 0){
            return true;
        }else{
            return false;
        }
    }
    public static double getAverageOfGrades(int id) throws SQLException {
        try (Connection conn = ConnectionUtil.getConnection()) {
            String query = "SELECT S_ID, AVG(G_Value) AS AverageGrade\n" +
                    "FROM Grades\n" +
                    "WHERE S_ID = ? " +
                    "GROUP BY S_ID; ";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                double average = rs.getDouble("AverageGrade");
                return average;
            } else {
                // Handle case where no grades are found for the specified student ID
                return 0.0; // or any other appropriate default value
            }
        }
    }
    public static XYChart.Series<String,Integer> getAllGrades(int S_ID) {
        XYChart.Series<String,Integer> series = new XYChart.Series<>();
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * from Grades WHERE S_ID = ?;");
            stmt.setInt(1,S_ID);
            ResultSet rs = stmt.executeQuery();

            int i = 0;
            while (rs.next()) {
                i++;
                int id = rs.getInt("G_ID");
                int std_id= rs.getInt("S_ID");
                int sbj_id = rs.getInt("Sb_ID");
                int gradeV = rs.getInt("G_Value");


                Grade grade = new Grade(id, std_id, sbj_id,gradeV);
                XYChart.Data<String, Integer> data = new XYChart.Data<>(String.valueOf(i),gradeV);
                series.getData().add(data);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return series;
    }
}
