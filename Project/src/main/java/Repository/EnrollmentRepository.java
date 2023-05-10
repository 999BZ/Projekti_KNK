package Repository;

import Models.Enrollment;
import Services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EnrollmentRepository {
    public static void insert(Enrollment enrollment) throws SQLException {

        String UserSql = "INSERT INTO Enrollments (S_ID, C_ID, E_Date) VALUES (?, ?, ?)";
        try(Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(UserSql)
        ) {
            LocalDate currentDate = LocalDate.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            String formattedDate = currentDate.format(formatter);
            statement.setInt(1, enrollment.getStudentId());
            statement.setInt(2, enrollment.getClassId());
            statement.setString(3, formattedDate);

            statement.executeUpdate();

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void update(Enrollment enrollment) throws SQLException {

        String UserSql = "UPDATE  Enrollments S_ID=?, C_ID=?, E_Date = ? where E_ID = ? ";
        try(Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(UserSql)
        ) {
            statement.setInt(1, enrollment.getStudentId());
            statement.setInt(2, enrollment.getClassId());
            statement.setString(3, enrollment.getEnrollmentDate());
            statement.setInt(4, enrollment.getId());
            statement.executeUpdate();

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
