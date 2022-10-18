package entity;

import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginsInserter implements Runnable {
    public void run() {
        String jdbcURL = "jdbc:mysql://localhost:3306/ThirdTaskFor_A1";
        String username = "root";
        String password = "12345";

        String csvFilePath = "C:\\Users\\Asus\\IdeaProjects\\ThirdTaskFor_A1-Unauthorized_deliveries-\\src\\main\\resources\\logins.csv";

        int batchSize = 20;

        Connection connection = null;

        ICsvBeanReader beanReader = null;
        CellProcessor[] processors = new CellProcessor[]{
                new NotNull(), // course name
                new NotNull(), // student name
                new NotNull(), // timestamp
                new NotNull(), // rating
                new NotNull()// comment
        };

        try {
            long start = System.currentTimeMillis();
            String sql;

            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);
            sql = "create table IF NOT exists logins(Application varchar(100),AppAccountName varchar(100),IsActive boolean,JobTitle varchar(100),Department varchar(100));";
            connection.prepareStatement(sql);
            sql = "INSERT INTO logins(Application, AppAccountName, IsActive, JobTitle, Department) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            beanReader = new CsvBeanReader(new FileReader(csvFilePath), CsvPreference.STANDARD_PREFERENCE);

            beanReader.getHeader(true); // skip header line

            String[] header = {"Application", "AppAccountName", "IsActive", "JobTitle", "Department"};
            Logins bean = null;

            int count = 0;

            while ((bean = beanReader.read(Logins.class, header, processors)) != null) {

                statement.setString(1, bean.getApplication().replace("\t",""));
                statement.setString(2, bean.getAppAccountName().replace("\t",""));
                statement.setBoolean(3, bean.getIsActive().replace("\t","").equals("True"));
                statement.setString(4, bean.getJobTitle().replace("\t",""));
                statement.setString(5, bean.getDepartment().replace("\t",""));

                statement.addBatch();

                if (count % batchSize == 0) {
                    statement.executeBatch();
                }
            }
            beanReader.close();
            statement.executeBatch();
            connection.commit();
            connection.close();

            long end = System.currentTimeMillis();
            System.out.println("Execution Time: " + (end - start));
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
