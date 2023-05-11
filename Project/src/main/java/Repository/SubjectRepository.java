package Repository;


import Models.Subject;
import Services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SubjectRepository {

    public static void insert(Subject subject) throws SQLException {

            String UserSql = "INSERT INTO Subjects (Sb_Name, Sb_Description, Sb_GLevel) VALUES (?, ?, ?)";
            try(Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UserSql)
            ) {
                statement.setString(1, subject.getName());
                statement.setString(2, subject.getDescription());
                statement.setInt(3, subject.getYear());

                statement.executeUpdate();

            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
    }

    public static void update(Subject subject) throws SQLException {

        String UserSql = "UPDATE  Subjects set Sb_Name=?, Sb_Description=?, Sb_GLevel=? where Sb_ID = ? ";
        try(Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(UserSql)
        ) {
            statement.setString(1, subject.getName());
            statement.setString(2, subject.getDescription());
            statement.setInt(3, subject.getYear());
            statement.setInt(4, subject.getId());


            statement.executeUpdate();

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}