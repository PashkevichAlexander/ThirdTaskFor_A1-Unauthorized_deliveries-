package tableSelectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ActiveUserSelector {
    public void run(){
        String jdbcURL = "jdbc:mysql://localhost:3306/ThirdTaskFor_A1";
        String username = "root";
        String password = "12345";

        Connection connection = null;

        try {
            long start = System.currentTimeMillis();

            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                statement.execute("ALTER TABLE postings ADD COLUMN authorizeDelivery boolean AFTER userName;");
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            try (Statement statement = connection.createStatement()) {
                statement.execute("UPDATE postings INNER JOIN logins ON postings.userNAme = logins.AppAccountName SET postings.authorizeDelivery = true WHERE logins.IsActive = true;");
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            try (Statement statement = connection.createStatement()) {
                statement.execute("UPDATE postings INNER JOIN logins ON postings.userName != logins.AppAccountName SET postings.authorizeDelivery = false;");
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            long end = System.currentTimeMillis();
            System.out.println("Execution Time: " + (end - start));
        }catch (SQLException ex) {
            ex.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

//ALTER TABLE postings ADD COLUMN authorizeDelivery boolean AFTER userName;
//
//        UPDATE postings
//        INNER JOIN logins ON postings.userNAme = logins.AppAccountName
//        SET postings.authorizeDelivery = true
//        WHERE logins.IsActive = true;
//
//        UPDATE postings
//        INNER JOIN logins ON postings.userName != logins.AppAccountName
//        SET postings.authorizeDelivery = false;
//
//        select * from postings;
