package inserters;

import JDBCService.DBConnection;
import entity.Logins;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class LoginsInserterSuperCSV {
    public void run() {
        String csvFilePath = "C:\\Users\\Asus\\IdeaProjects\\ThirdTaskFor_A1-Unauthorized_deliveries-\\src\\main\\resources\\logins.csv";

        ICsvBeanReader beanReader;
        CellProcessor[] processors = new CellProcessor[]{
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull()
        };

        try {
            long start = System.currentTimeMillis();

            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();

            statement.execute("DROP TABLE IF EXISTS logins");
            statement.execute("create table IF NOT exists logins(Application varchar(100),AppAccountName varchar(100),IsActive boolean,JobTitle varchar(100),Department varchar(100))");

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO logins(Application, AppAccountName, IsActive, JobTitle, Department) VALUES (?, ?, ?, ?, ?)");

            beanReader = new CsvBeanReader(new FileReader(csvFilePath), CsvPreference.STANDARD_PREFERENCE);

            beanReader.getHeader(true);

            String[] header = {"Application", "AppAccountName", "IsActive", "JobTitle", "Department"};
            Logins bean;

            while ((bean = beanReader.read(Logins.class, header, processors)) != null) {

                preparedStatement.setString(1, bean.getApplication().replace("\t", ""));
                preparedStatement.setString(2, bean.getAppAccountName().replace("\t", ""));
                preparedStatement.setBoolean(3, bean.getIsActive().replace("\t", "").equals("True"));
                preparedStatement.setString(4, bean.getJobTitle().replace("\t", ""));
                preparedStatement.setString(5, bean.getDepartment().replace("\t", ""));

                preparedStatement.addBatch();
                preparedStatement.executeBatch();
            }
            beanReader.close();
            connection.close();

            long end = System.currentTimeMillis();
            System.out.println("LoginsInserterSuperCSV Execution Time: " + (end - start));
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
