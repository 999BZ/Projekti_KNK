package Repository;

import Models.Classe;
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
            statement.setInt(3,clas.getParalel());

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
            PreparedStatement statement = connection.prepareStatement(UserSql)
        ) {
;
            statement.setInt(1, clas.getId());

            statement.executeUpdate();

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static boolean exists(Classe classe) throws SQLException {
        Connection conn = ConnectionUtil.getConnection();
        String query = "SELECT COUNT(*) FROM Classes WHERE T_ID=? AND Sb_ID=? AND C_Paralel=?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, classe.getTeacherId());
        stmt.setInt(2, classe.getSubjectId());
        stmt.setInt(3, classe.getParalel());
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0;
        } else {
            return false;
        }
    }

}
