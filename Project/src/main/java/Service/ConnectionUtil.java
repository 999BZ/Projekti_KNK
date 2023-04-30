package Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = "jdbc:mysql://localhost:s3306/lems";
            String user = "root";
            String password = "root";
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }
}
