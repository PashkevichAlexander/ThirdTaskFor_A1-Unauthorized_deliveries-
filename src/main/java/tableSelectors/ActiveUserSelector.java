package tableSelectors;

import JDBCService.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ActiveUserSelector {
    public void run() {


        try {
            long start = System.currentTimeMillis();

            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();

            statement.execute("ALTER TABLE postings ADD COLUMN authorizeDelivery boolean AFTER userName;");
            statement.execute("UPDATE postings INNER JOIN logins ON postings.userName <> logins.AppAccountName SET postings.authorizeDelivery = 0;");
            statement.execute("UPDATE postings INNER JOIN logins ON postings.userName = logins.AppAccountName SET postings.authorizeDelivery = 1 WHERE logins.IsActive = 1;");


            connection.close();

            long end = System.currentTimeMillis();
            System.out.println("ActiveUserSelector Execution Time: " + (end - start));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
