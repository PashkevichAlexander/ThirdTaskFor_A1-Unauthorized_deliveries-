package JDBCService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    String jdbcURL = "jdbc:mysql://localhost:3306/ThirdTaskFor_A1";
    String username = "root";
    String password = "12345";

    private static Connection connection = null;
    private static DBConnection instance = null;

    private DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }

        return instance;
    }

    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
