import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class ComplexCsv2DbInserter {

    public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/ThirdTaskFor_A1";
        String username = "root";
        String password = "12345";

        String csvFilePath = "C:\\Users\\Asus\\IdeaProjects\\ThirdTaskFor_A1-Unauthorized_deliveries-\\src\\main\\resources\\aboba.csv";

        int batchSize = 20;

        Connection connection = null;

        ICsvBeanReader beanReader = null;
        CellProcessor[] processors = new CellProcessor[] {
                new NotNull(), // course name
                new NotNull(), // student name
                new ParseTimestamp(), // timestamp
                new ParseDouble(), // rating
                new Optional()// comment
        };

        try {
            long start = System.currentTimeMillis();
            String sql;

            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);

            sql = "drop database if exists ThirdTaskFor_A1;";
            connection.prepareStatement(sql);

            sql = "CREATE DATABASE IF NOT EXISTS ThirdTaskFor_A1;";
            connection.prepareStatement(sql);

            sql = "create table IF NOT exists thirdtaskfor_a1(" +
                    "course_name varchar(50)," +
                    " student_name varchar(50)," +
                    "timestamp timestamp," +
                    "rating double," +
                    "comment varchar(200));";
            connection.prepareStatement(sql);

            sql = "INSERT INTO thirdtaskfor_a1(course_name, student_name, timestamp, rating, comment) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            beanReader = new CsvBeanReader(new FileReader(csvFilePath),
                    CsvPreference.STANDARD_PREFERENCE);

            beanReader.getHeader(true); // skip header line

            String[] header = {"courseName", "studentName", "timestamp", "rating", "comment"};
            Review bean = null;

            int count = 0;

            while ((bean = beanReader.read(Review.class, header, processors)) != null) {
                String courseName = bean.getCourseName();
                String studentName = bean.getStudentName();
                Timestamp timestamp = bean.getTimestamp();
                double rating = bean.getRating();
                String comment = bean.getComment();

                statement.setString(1, courseName);
                statement.setString(2, studentName);

                statement.setTimestamp(3, timestamp);

                statement.setDouble(4, rating);

                statement.setString(5, comment);

                statement.addBatch();

                if (count % batchSize == 0) {
                    statement.executeBatch();
                }
            }

            beanReader.close();

            // execute the remaining queries
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