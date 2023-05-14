package Repository;

import Models.Grade;
import Models.Subject;
import Models.User;
import Services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GradeRepository {

    public static void insert(Grade grade) throws SQLException {

        String UserSql = "INSERT INTO Grades  (S_ID, Sb_ID, G_Value) VALUES (?, ?, ?)";
        try(Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(UserSql)
        ) {
            statement.setInt(1, grade.getStudentId());
            statement.setInt(2, grade.getSubjectId());
            statement.setInt(3, grade.getGrade());

            statement.executeUpdate();

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void update(Grade grade) throws SQLException {

        String UserSql = "UPDATE  Grades set S_ID=?, Sb_ID=?, G_Value = ? where G_ID = ? ";
        try(Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(UserSql)
        ) {
            statement.setInt(1, grade.getStudentId());
            statement.setInt(2, grade.getSubjectId());
            statement.setInt(3, grade.getGrade());
            statement.setInt(4, grade.getId());
            statement.executeUpdate();

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
