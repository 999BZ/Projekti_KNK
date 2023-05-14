package Repository;


import Models.Subject;
import Services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectRepository {

    public static void insert(Subject subject) throws SQLException {

            String UserSql = "INSERT INTO Subjects (Sb_Name, Sb_Description, Sb_GLevel, Sb_Obligatory) VALUES (?, ?, ?, ?)";
            try(Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UserSql)
            ) {
                statement.setString(1, subject.getName());
                statement.setString(2, subject.getDescription());
                statement.setInt(3, subject.getYear());
                statement.setBoolean(4, subject.isObligatory());

                statement.executeUpdate();

            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
    }

    public static void update(Subject subject) throws SQLException {

        String UserSql = "UPDATE  Subjects set Sb_Name=?, Sb_Description=?, Sb_GLevel=?, Sb_Obligatory=? where Sb_ID = ? ";
        try(Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(UserSql)
        ) {
            statement.setString(1, subject.getName());
            statement.setString(2, subject.getDescription());
            statement.setInt(3, subject.getYear());
            statement.setBoolean(4, subject.isObligatory());
            statement.setInt(5, subject.getId());


            statement.executeUpdate();

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void delete(Subject subject) throws SQLException {

        String UserSql = "Delete from Subjects where Sb_ID = ? ";
        try(Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(UserSql)
        ) {
            statement.setInt(1, subject.getId());


            statement.executeUpdate();

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Subject getByID(int  id) throws SQLException {
        String sql = "SELECT * FROM Subjects WHERE Sb_ID = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int ID = resultSet.getInt("Sb_ID");
                String name = resultSet.getString("Sb_Name");
                String description = resultSet.getString("Sb_Description");
                int GLevel = resultSet.getInt("Sb_GLevel");
                Boolean Obligatory = resultSet.getBoolean("Sb_Obligatory");
                return new Subject(ID,name, description,GLevel, Obligatory );
            } else {
                return null;
            }
        }
    }

}