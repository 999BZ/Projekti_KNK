package Repository;


import Models.AdminUser;
import Models.StudentUser;
import Models.TeacherUser;
import Models.User;
import Services.ConnectionUtil;

import java.sql.*;

public class UserRepository {

    public static User insert(User user) throws SQLException {
        if(user instanceof StudentUser student){
            String UserSql = "INSERT INTO Users (Email, Salted_Password, Salt, U_Position, U_ProfileImg) VALUES (?, ?, ?, ?, ?)";
            String StudentSql="INSERT INTO Students (S_Name, S_Surname, S_Birthdate, S_Phone, S_Address, S_GLevel, S_Paralel, S_UID, S_Gender) VALUES (?, ?, ?, ?, ?,?, ?, (SELECT U_ID FROM Users WHERE Email = ?), ?)";

            try(Connection connection = ConnectionUtil.getConnection();
                PreparedStatement UserStatement = connection.prepareStatement(UserSql)
            ) {
                UserStatement.setString(1, student.getEmail());
                UserStatement.setString(2, student.getSaltedPassword());
                UserStatement.setString(3, student.getSalt());
                UserStatement.setString(4, student.getPosition());
                UserStatement.setString(5, student.getProfileImg());
                UserStatement.executeUpdate();
                PreparedStatement StudentStatement = connection.prepareStatement(StudentSql);

                StudentStatement.setString(1, student.getName());
                StudentStatement.setString(2, student.getSurname());
                StudentStatement.setString(3, student.getBirthdate());
                StudentStatement.setString(4, student.getPhone());
                StudentStatement.setString(5, student.getAddress());
                StudentStatement.setInt(6, student.getYear());
                StudentStatement.setInt(7,student.getParalel());
                StudentStatement.setString(8, student.getEmail());
                StudentStatement.setString(9, student.getGender());
                StudentStatement.executeUpdate();

            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            return UserRepository.getByEmail(user.getEmail());
        }else if(user instanceof TeacherUser teacher){
            String UserSql = "INSERT INTO Users (Email, Salted_Password, Salt, U_Position,  U_ProfileImg) VALUES (?, ?, ?, ?, ?)";
            String TeacherSql = "INSERT INTO Teachers (T_Name, T_Surname, T_Birthdate, T_Phone, T_Address, T_UID, T_Gender) VALUES (?, ?, ?, ?, ?, (SELECT U_ID FROM Users WHERE Email = ?), ?)";

            try(Connection connection = ConnectionUtil.getConnection();
                PreparedStatement UserStatement = connection.prepareStatement(UserSql)
            ) {
                UserStatement.setString(1, teacher.getEmail());
                UserStatement.setString(2, teacher.getSaltedPassword());
                UserStatement.setString(3, teacher.getSalt());
                UserStatement.setString(4, teacher.getPosition());
                UserStatement.setString(5, teacher.getProfileImg());

                UserStatement.executeUpdate();

                PreparedStatement TeacherStatement = connection.prepareStatement(TeacherSql);
                TeacherStatement.setString(1, teacher.getName());
                TeacherStatement.setString(2, teacher.getSurname());
                TeacherStatement.setString(3, teacher.getBirthdate());
                TeacherStatement.setString(4, teacher.getPhone());
                TeacherStatement.setString(5, teacher.getAddress());
                TeacherStatement.setString(6, teacher.getEmail());
                TeacherStatement.setString(7,teacher.getGender());
                TeacherStatement.executeUpdate();
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            return UserRepository.getByEmail(user.getEmail());
        }else if(user instanceof AdminUser admin){
            String UserSql = "INSERT INTO Users (Email, Salted_Password, Salt, U_Position,  U_ProfileImg) VALUES (?, ?, ?, ?, ?)";
            String AdminSql = "INSERT INTO Admins (A_Name, A_Surname, A_Phone, A_UID) VALUES (?, ?, ?, (SELECT U_ID FROM Users WHERE Email = ?))";

            try(Connection connection = ConnectionUtil.getConnection();
                PreparedStatement UserStatement = connection.prepareStatement(UserSql)
            ) {
                UserStatement.setString(1, admin.getEmail());
                UserStatement.setString(2, admin.getSaltedPassword());
                UserStatement.setString(3, admin.getSalt());
                UserStatement.setString(4, admin.getPosition());
                UserStatement.setString(5, admin.getProfileImg());

                UserStatement.executeUpdate();

                PreparedStatement AdminStatement = connection.prepareStatement(AdminSql);
                AdminStatement.setString(1, admin.getName());
                AdminStatement.setString(2, admin.getSurname());
                AdminStatement.setString(3, admin.getPhone());
                AdminStatement.setString(4, admin.getEmail());
                AdminStatement.executeUpdate();
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            return UserRepository.getByEmail(user.getEmail());
        }

        return null;
    }

    public static User getByEmail(String Email) throws SQLException {
        String sql = "SELECT * FROM Users WHERE Email = ?";
        try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, Email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int ID = resultSet.getInt("U_ID");
                String saltedHash = resultSet.getString("Salted_password");
                String salt = resultSet.getString("Salt");
                String User_Position = resultSet.getString("U_Position");
                String User_ProfileImg = resultSet.getString("U_ProfileImg");
                return new User(ID, Email, saltedHash, salt,User_Position, User_ProfileImg);
            } else {
                return null;
            }
        }
    }
    public static User getByID(int  U_ID) throws SQLException {
        String sql = "SELECT * FROM Users WHERE U_ID = ?";
        try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, U_ID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int ID = resultSet.getInt("U_ID");
                String saltedHash = resultSet.getString("Salted_password");
                String salt = resultSet.getString("Salt");
                String User_Position = resultSet.getString("U_Position");
                String User_ProfileImg = resultSet.getString("U_ProfileImg");
                String User_Email = resultSet.getString("Email");
                return new User(ID, User_Email, saltedHash, salt,User_Position, User_ProfileImg);
            } else {
                return null;
            }
        }
    }
    public static User update(User user) throws SQLException {
        if(user instanceof StudentUser student){
            String UserSql = "UPDATE Users set Email=?, U_Position=?, U_ProfileImg=? where U_ID = ?";
            String StudentSql = "UPDATE Students set S_Name=?, S_Surname=?, S_Birthdate=?, S_Phone=?, S_Address=?, S_GLevel=?, S_Paralel=?, S_Gender=?  where S_UID=?";

            try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement UserStatement = connection.prepareStatement(UserSql);
                 PreparedStatement StudentStatement = connection.prepareStatement(StudentSql)) {

                UserStatement.setString(1, student.getEmail());
                UserStatement.setString(2, student.getPosition());
                UserStatement.setString(3, student.getProfileImg());
                UserStatement.setInt(4, student.getID());

                UserStatement.executeUpdate();

                StudentStatement.setString(1, student.getName());
                StudentStatement.setString(2, student.getSurname());
                StudentStatement.setString(3, student.getBirthdate());
                StudentStatement.setString(4, student.getPhone());
                StudentStatement.setString(5, student.getAddress());
                StudentStatement.setInt(6, student.getYear());
                StudentStatement.setInt(7, student.getParalel());
                StudentStatement.setString(8, student.getGender());
                StudentStatement.setInt(9, student.getID());

                StudentStatement.executeUpdate();

        }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            return UserRepository.getByEmail(user.getEmail());
        }else if(user instanceof TeacherUser teacher){
            String UserSql = "UPDATE Users set Email=?, U_ProfileImg=? where U_ID=?";
            String TeacherSql = "UPDATE Teachers set T_Name=?, T_Surname=?, T_Birthdate=?, T_Phone=?, T_Address=?, T_Gender=?  where T_UID = ?";

            try(Connection connection = ConnectionUtil.getConnection();
                PreparedStatement UserStatement = connection.prepareStatement(UserSql)
            ) {
                UserStatement.setString(1, teacher.getEmail());
                UserStatement.setString(2, teacher.getProfileImg());
                UserStatement.setInt(3, teacher.getID());

                UserStatement.executeUpdate();

                PreparedStatement TeacherStatement = connection.prepareStatement(TeacherSql);
                TeacherStatement.setString(1, teacher.getName());
                TeacherStatement.setString(2, teacher.getSurname());
                TeacherStatement.setString(3, teacher.getBirthdate());
                TeacherStatement.setString(4, teacher.getPhone());
                TeacherStatement.setString(5, teacher.getAddress());
                TeacherStatement.setString(6, teacher.getGender());
                TeacherStatement.setInt(7, teacher.getID());


                TeacherStatement.executeUpdate();
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }


            return UserRepository.getByEmail(user.getEmail());
        }
        else if(user instanceof AdminUser admin){
            String UserSql = "UPDATE Users set Email=?, Salted_Password=?, Salt=?, U_Position=?,  U_ProfileImg=? where U_ID=?";
            String TeacherSql = "UPDATE Admins set A_Name=?, A_Surname=?,T_Phone=? where T_UID = ?";

            try(Connection connection = ConnectionUtil.getConnection();
                PreparedStatement UserStatement = connection.prepareStatement(UserSql)
            ) {
                UserStatement.setString(1, admin.getEmail());
                UserStatement.setString(2, admin.getSaltedPassword());
                UserStatement.setString(3, admin.getSalt());
                UserStatement.setString(4, admin.getPosition());
                UserStatement.setString(5, admin.getProfileImg());
                UserStatement.setInt(6, admin.getID());

                UserStatement.executeUpdate();

                PreparedStatement AdminStatement = connection.prepareStatement(TeacherSql);
                AdminStatement.setString(1, admin.getName());
                AdminStatement.setString(2, admin.getSurname());
                AdminStatement.setString(3, admin.getPhone());
                UserStatement.setInt(4, admin.getID());
                AdminStatement.executeUpdate();
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }


            return UserRepository.getByEmail(user.getEmail());
        }

        return null;

    }
    public static User delete(User user) throws SQLException {
        String UserSql = "Delete from Users where U_ID=?";

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement UserStatement = connection.prepareStatement(UserSql)
        ) {
            UserStatement.setInt(1, user.getID());
            UserStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return UserRepository.getByEmail(user.getEmail());
    }

    public static TeacherUser getTeacherByUserID(int userID) throws SQLException {
        TeacherUser teacher = null;
        Connection connection = ConnectionUtil.getConnection();
        String query = "SELECT T.*, U.* FROM Teachers T " +
                "INNER JOIN Users U ON T.T_UID = U.U_ID " +
                "WHERE T.T_UID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userID);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String name = resultSet.getString("T_Name");
            String surname = resultSet.getString("T_Surname");
            String birthdate = resultSet.getDate("T_Birthdate").toLocalDate().toString();
            String phone = resultSet.getString("T_Phone");
            String address = resultSet.getString("T_Address");
            String email = resultSet.getString("Email");
            String saltedPassword = resultSet.getString("salted_password");
            String salt = resultSet.getString("salt");
            String position = resultSet.getString("U_Position");
            String profileImg = resultSet.getString("U_ProfileImg");
            String gender = resultSet.getString("T_Gender");

            teacher = new TeacherUser(userID, email, saltedPassword, salt, position, profileImg, name, surname, birthdate, phone, address,gender);
        }

        resultSet.close();
        statement.close();

        return teacher;
    }

    public static StudentUser getStudentByUserID(int userID) throws SQLException {
        StudentUser student = null;
        Connection connection = ConnectionUtil.getConnection();
        String query = "SELECT S.*, U.* FROM Students S " +
                "INNER JOIN Users U ON S.S_UID = U.U_ID " +
                "WHERE S.S_UID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userID);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String name = resultSet.getString("S_Name");
            String surname = resultSet.getString("S_Surname");
            String birthdate = resultSet.getDate("S_Birthdate").toLocalDate().toString();
            String phone = resultSet.getString("S_Phone");
            int gradeLvl = resultSet.getInt("S_GLevel");
            int paralel = resultSet.getInt("S_Paralel");
            String address = resultSet.getString("S_Address");
            String email = resultSet.getString("Email");
            String saltedPassword = resultSet.getString("salted_password");
            String salt = resultSet.getString("salt");
            String position = resultSet.getString("U_Position");
            String profileImg = resultSet.getString("U_ProfileImg");
            String gender = resultSet.getString("S_Gender");


            student = new StudentUser(userID, email, saltedPassword, salt, position, profileImg, name, surname, birthdate, phone, address, gradeLvl, paralel,gender);
        }

        resultSet.close();
        statement.close();

        return student;
    }
}