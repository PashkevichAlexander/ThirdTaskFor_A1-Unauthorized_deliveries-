package Inserters;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import entity.Employee;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PostingsInserter implements Runnable {
//    @SuppressWarnings({"rawtypes", "unchecked"})
    public void run() {
        String jdbcURL = "jdbc:mysql://localhost:3306/ThirdTaskFor_A1";
        String username = "root";
        String password = "12345";

        String csvFilename = "C:\\Users\\Asus\\IdeaProjects\\ThirdTaskFor_A1-Unauthorized_deliveries-\\src\\main\\resources\\postings.csv";

        int batchSize = 20;

        Connection connection = null;
        try {
            long start = System.currentTimeMillis();
            String sql;

            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);
//
            sql = "INSERT INTO postings(matDoc, item, docDate, pstngDate, materialDescription, quantity, bUn, amountLC, crcy, userName) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            CsvToBean csv = new CsvToBean();

            CSVReader csvReader = new CSVReader(new FileReader(csvFilename), ';', '"', 2);

            List list = csv.parse(setColumMapping(), csvReader);

            int count = 0;

            for (Object object : list) {
//            Employee employee = (Employee) object;
                System.out.println(((Employee) object).getBUn());
                statement.setString(1, ((Employee) object).getMatDoc());
                statement.setString(2, ((Employee) object).getItem());
                statement.setString(3, ((Employee) object).getDocDate());
                statement.setString(4, ((Employee) object).getPstngDate());
                statement.setString(5, ((Employee) object).getMaterialDescription());
                statement.setString(6, ((Employee) object).getQuantity());
                statement.setString(7, ((Employee) object).getBUn());
                statement.setString(8, ((Employee) object).getAmountLC());
                statement.setString(9, ((Employee) object).getCrcy());
                statement.setString(10, ((Employee) object).getUserName());
                statement.addBatch();
                if (count % batchSize == 0) {
                    statement.executeBatch();
                }
            }

            csvReader.close();
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

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static ColumnPositionMappingStrategy setColumMapping() {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(Employee.class);
        String[] columns = new String[]{"matDoc", "item", "docDate", "pstngDate", "materialDescription", "quantity", "bUn", "amountLC", "crcy", "userName"};
        strategy.setColumnMapping(columns);
        return strategy;
    }
}
