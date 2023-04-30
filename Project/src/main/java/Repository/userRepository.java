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
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getSaltedPassword());
        statement.setString(3, user.getSalt());
        statement.executeUpdate();

        return userRepository.getByUsername(user.getUsername());
    }

    public static User getByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username=?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String saltedHash = resultSet.getString("salted_hash");
                String salt = resultSet.getString("salt");
                return new User(id, username, saltedHash, salt);
            } else {
                return null;
            }
        }
    }
}