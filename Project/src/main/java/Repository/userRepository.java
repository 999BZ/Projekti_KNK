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
        String UserSql = "INSERT INTO Users (Email, Salted_Password,Salt,U_Position) VALUES (?, ?, ?, ?)";
        String StudentSql="INSERT INTO Students (S_ID,S_Name, S_Surname,S_Birthdate,S_Phone,S_Address,S_GLevel,S_UID) VALUES (1,?, ?, ?, ?, ?, ?,1)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement Userstatement = connection.prepareStatement(UserSql);

        Userstatement.setString(1, user.getEmail());
        Userstatement.setString(2, user.getSaltedPassword());
        Userstatement.setString(3, user.getSalt());
        Userstatement.setString(4, user.getPosition());
        Userstatement.executeUpdate();

        PreparedStatement Studentstatement = connection.prepareStatement(StudentSql);

        Studentstatement.setString(2, user.getName());
        Studentstatement.setString(3, user.getSurname());
        Studentstatement.setString(4, user.getBirthdate());
        Studentstatement.setString(5, user.getPhone());
        Studentstatement.setString(6, user.getAddress());
        Studentstatement.setInt(7, user.getYear());
        Studentstatement.executeUpdate();

        if (userRepository.getByEmail(user.getEmail()) != null){
            System.out.println("Registered successfully");
        }

        return userRepository.getByEmail(user.getEmail());
    }

    public static User getByEmail(String Email) throws SQLException {
        System.out.println("Trying Email");
        String sql = "SELECT * FROM Users WHERE Email=?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, Email);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Got Result");
            if (resultSet.next()) {
                System.out.println("Result got");
                int ID = resultSet.getInt("U_ID");
                String saltedHash = resultSet.getString("Salted_password");
                String salt = resultSet.getString("Salt");
                String User_Position = resultSet.getString("U_Position");
                System.out.println("Returning User");
                return new User(ID, Email, saltedHash, salt,User_Position);
            } else {
                System.out.println("No Result");
                return null;
            }
        }
    }
}