package Repository;


import Models.RegisterUser;
import Models.User;
import Services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userRepository {

    public static User insert(RegisterUser user) throws SQLException {
        String UserSql = "INSERT INTO Users (Email, Salted_Password, Salt, U_Position) VALUES (?, ?, ?, ?)";
        String StudentSql="INSERT INTO Students (S_Name, S_Surname, S_Birthdate, S_Phone, S_Address, S_GLevel, S_UID) VALUES (?, ?, ?, ?, ?, ?, (SELECT U_ID FROM Users WHERE Email = ?))";

        try(Connection connection = ConnectionUtil.getConnection();
            PreparedStatement UserStatement = connection.prepareStatement(UserSql)
        ) {
            UserStatement.setString(1, user.getEmail());
            UserStatement.setString(2, user.getSaltedPassword());
            UserStatement.setString(3, user.getSalt());
            UserStatement.setString(4, user.getPosition());
            UserStatement.executeUpdate();
            PreparedStatement StudentStatement = connection.prepareStatement(StudentSql);

            StudentStatement.setString(1, user.getName());
            StudentStatement.setString(2, user.getSurname());
            StudentStatement.setString(3, user.getBirthdate());
            StudentStatement.setString(4, user.getPhone());
            StudentStatement.setString(5, user.getAddress());
            StudentStatement.setInt(6, user.getYear());
            StudentStatement.setString(7, user.getEmail());
            StudentStatement.executeUpdate();

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return userRepository.getByEmail(user.getEmail());
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