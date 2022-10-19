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
                statement.execute("create table IF NOT exists isActiveTable(AppAccountName varchar(100),IsActive boolean);");
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            try (Statement statement = connection.createStatement()) {
                statement.execute("UPDATE postings INNER JOIN logins ON postings.userName <> logins.AppAccountName SET postings.authorizeDelivery = 0;");
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            try (Statement statement = connection.createStatement()) {
                statement.execute("UPDATE postings INNER JOIN logins ON postings.userName = logins.AppAccountName SET postings.authorizeDelivery = 1 WHERE logins.IsActive = 1;");
            } catch (SQLException exception) {
                exception.printStackTrace();
            }

            connection.commit();
            connection.close();

            long end = System.currentTimeMillis();
            System.out.println("ActiveUserSelector Execution Time: " + (end - start));
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
