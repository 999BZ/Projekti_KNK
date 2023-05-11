package Repository;

import Models.Class;
import Services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClassRepository {

    public static void insert(Class clas) throws SQLException {

        String UserSql = "INSERT INTO Classes (T_ID, Sb_ID, C_Paralel) VALUES (?, ?, ?, ?)";
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

    public static void update(Class clas) throws SQLException {

        String UserSql = "UPDATE  Classes set T_ID=?, Sb_ID=?, C_Paralel=? where Sb_ID = ? ";
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
}
