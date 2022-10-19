package inserters;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import entity.Employee;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class PostingsInserterOpenCSV {
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void run() {
        String jdbcURL = "jdbc:mysql://localhost:3306/ThirdTaskFor_A1";
        String username = "root";
        String password = "12345";

        String csvFilename = "C:\\Users\\Asus\\IdeaProjects\\ThirdTaskFor_A1-Unauthorized_deliveries-\\src\\main\\resources\\postings.csv";

        Connection connection = null;
        try {
            long start = System.currentTimeMillis();
            String sql;

            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);

            try (Statement statement = connection.createStatement()) {
                statement.execute("DROP TABLE IF EXISTS postings");
            } catch (SQLException exception) {
                exception.printStackTrace();
            }

            try (Statement statement = connection.createStatement()) {
                statement.execute("create table IF NOT exists postings( matDoc varchar(100), item varchar(100),   docDate varchar(100), pstngDate varchar(100),  materialDescription varchar(100), quantity varchar(100), bUn varchar(100), amountLC varchar(100), crcy varchar(100), userName varchar(100));");
            } catch (SQLException exception) {
                exception.printStackTrace();
            }

            sql = "INSERT INTO postings(matDoc, item, docDate, pstngDate, materialDescription, quantity, bUn, amountLC, crcy, userName) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            CsvToBean csv = new CsvToBean();

            CSVReader csvReader = new CSVReader(new FileReader(csvFilename), ';', '"', 2);

            List list = csv.parse(setColumMapping(), csvReader);

            for (Object object : list) {
                preparedStatement.setString(1, ((Employee) object).getMatDoc().replace("\t",""));
                preparedStatement.setString(2, ((Employee) object).getItem().replace("\t",""));
                preparedStatement.setString(3, ((Employee) object).getDocDate().replace("\t",""));
                preparedStatement.setString(4, ((Employee) object).getPstngDate().replace("\t",""));
                preparedStatement.setString(5, ((Employee) object).getMaterialDescription().replace("\t",""));
                preparedStatement.setString(6, ((Employee) object).getQuantity().replace("\t",""));
                preparedStatement.setString(7, ((Employee) object).getBUn().replace("\t",""));
                preparedStatement.setString(8, ((Employee) object).getAmountLC().replace("\t",""));
                preparedStatement.setString(9, ((Employee) object).getCrcy().replace("\t",""));
                preparedStatement.setString(10, ((Employee) object).getUserName().replace("\t",""));
                preparedStatement.addBatch();
                preparedStatement.executeBatch();
            }

            csvReader.close();
            preparedStatement.executeBatch();
            connection.commit();
            connection.close();

            long end = System.currentTimeMillis();
            System.out.println("PostingsInserterOpenCSV Execution Time: " + (end - start));
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
