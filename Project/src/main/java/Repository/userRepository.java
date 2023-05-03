package Repository;


import Models.StudentUser;
import Models.TeacherUser;
import Models.User;
import Services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userRepository {

    public static User insert(User user) throws SQLException {
        if(user instanceof StudentUser student){
            String UserSql = "INSERT INTO Users (Email, Salted_Password, Salt, U_Position) VALUES (?, ?, ?, ?)";
            String StudentSql="INSERT INTO Students (S_Name, S_Surname, S_Birthdate, S_Phone, S_Address, S_GLevel, S_UID) VALUES (?, ?, ?, ?, ?, ?, (SELECT U_ID FROM Users WHERE Email = ?))";

            try(Connection connection = ConnectionUtil.getConnection();
                PreparedStatement UserStatement = connection.prepareStatement(UserSql)
            ) {
                UserStatement.setString(1, student.getEmail());
                UserStatement.setString(2, student.getSaltedPassword());
                UserStatement.setString(3, student.getSalt());
                UserStatement.setString(4, student.getPosition());
                UserStatement.executeUpdate();
                PreparedStatement StudentStatement = connection.prepareStatement(StudentSql);

                StudentStatement.setString(1, student.getName());
                StudentStatement.setString(2, student.getSurname());
                StudentStatement.setString(3, student.getBirthdate());
                StudentStatement.setString(4, student.getPhone());
                StudentStatement.setString(5, student.getAddress());
                StudentStatement.setInt(6, student.getYear());
                StudentStatement.setString(7, student.getEmail());
                StudentStatement.executeUpdate();

            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            return userRepository.getByEmail(user.getEmail());
        }else if(user instanceof TeacherUser teacher){
            String UserSql = "INSERT INTO Users (Email, Salted_Password, Salt, U_Position) VALUES (?, ?, ?, ?)";
            String TeacherSql = "INSERT INTO Teachers (T_Name, T_Surname, T_Birthdate, T_Phone, T_Address, T_Grade, T_UID) VALUES (?, ?, ?, ?, ?, ?, (SELECT U_ID FROM Users WHERE Email = ?))";

            try(Connection connection = ConnectionUtil.getConnection();
                PreparedStatement UserStatement = connection.prepareStatement(UserSql)
            ) {
                UserStatement.setString(1, teacher.getEmail());
                UserStatement.setString(2, teacher.getSaltedPassword());
                UserStatement.setString(3, teacher.getSalt());
                UserStatement.setString(4, teacher.getPosition());
                UserStatement.executeUpdate();

                PreparedStatement TeacherStatement = connection.prepareStatement(TeacherSql);
                TeacherStatement.setString(1, teacher.getName());
                TeacherStatement.setString(2, teacher.getSurname());
                TeacherStatement.setString(3, teacher.getBirthdate());
                TeacherStatement.setString(4, teacher.getPhone());
                TeacherStatement.setString(5, teacher.getAddress());
                TeacherStatement.setInt(6, teacher.getYear());
                TeacherStatement.setString(7, teacher.getEmail());
                TeacherStatement.executeUpdate();
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            return userRepository.getByEmail(user.getEmail());
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
                return new User(ID, Email, saltedHash, salt,User_Position);
            } else {
                return null;
            }
        }
    }
}