package Repository;


import Models.User;
import Services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userRepository {

    public static User insert(User user) throws SQLException {
        String sql = "INSERT INTO users (username, salted_hash, salt) VALUES (?, ?, ?)";
        Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getSaltedPassword());
        statement.setString(3, user.getSalt());
        statement.executeUpdate();

        return userRepository.getByEmail(user.getEmail());
    }

    public static User getByEmail(String Email) throws SQLException {
        String sql = "SELECT * FROM Users WHERE Email=?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, Email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int ID = resultSet.getInt("ID");
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