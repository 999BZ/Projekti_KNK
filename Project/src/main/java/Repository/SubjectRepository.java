package Repository;


import Models.Subject;
import Services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
}