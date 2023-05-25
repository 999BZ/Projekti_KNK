package Repository;

import Models.Classe;
import Models.StudentUser;
import Models.Subject;
import Services.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClassRepository {
    public static void insert(Classe clas) throws SQLException {
        String UserSql = "INSERT INTO Classes (T_ID, Sb_ID, C_Paralel) VALUES (?, ?, ?)";
        try(Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(UserSql)
        ) {
            statement.setInt(1, clas.getTeacherId());
            statement.setInt(2, clas.getSubjectId());
            statement.setInt(3, clas.getParalel());

            statement.executeUpdate();

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void update(Classe clas) throws SQLException {
        String UserSql = "UPDATE  Classes set T_ID=?, Sb_ID=?, C_Paralel=? where C_ID = ? ";
        try(Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(UserSql)
        ) {
            statement.setInt(1, clas.getTeacherId());
            statement.setInt(2, clas.getSubjectId());
            statement.setInt(3,clas.getParalel());
            statement.setInt(4, clas.getId());

            statement.executeUpdate();

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void delete(Classe clas) throws SQLException {
        String UserSql = "DELETE FROM  Classes  where C_ID = ? ";
        try(Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(UserSql)) {

            statement.setInt(1, clas.getId());
            statement.executeUpdate();

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static boolean exists(Classe classe) throws SQLException {
        Connection conn = ConnectionUtil.getConnection();
        String query = "SELECT * FROM Classes WHERE Sb_ID=? AND C_Paralel=? LIMIT 1";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, classe.getSubjectId());
        stmt.setInt(2, classe.getParalel());
        ResultSet rs = stmt.executeQuery();

        return rs.next();
//        if (rs.next()) {
//            int count = rs.getInt(1);
//            return count > 0;
//        } else {
//            return false;
//        }
    }

    public static Classe subjectClassOfStudent(Subject subject, StudentUser student){
        Classe clas = null;
        try {
            Connection conn = ConnectionUtil.getConnection();
            String query = "SELECT * FROM Classes c join Subjects s on c.Sb_ID = s.Sb_ID" +
                    " WHERE c.Sb_ID = ? and s.Sb_GLevel = ? and c.C_Paralel =?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, subject.getId());
            statement.setInt(2, subject.getYear());
            statement.setInt(3, student.getParalel());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("C_ID");
                int teacherId = rs.getInt("T_ID");
                int subjectId = rs.getInt("Sb_ID");
                int paralel = rs.getInt("C_Paralel");

                clas = new Classe(id, teacherId, subjectId, paralel);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return clas;
    }
}
