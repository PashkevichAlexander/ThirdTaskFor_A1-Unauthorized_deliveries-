package inserters;

import JDBCService.DBConnection;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import entity.Post;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PostingsInserterOpenCSV {
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void run() {
        String csvFilename = "C:\\Users\\Asus\\IdeaProjects\\ThirdTaskFor_A1-Unauthorized_deliveries-\\src\\main\\resources\\postings.csv";

        try {
            long start = System.currentTimeMillis();

            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();

            statement.execute("DROP TABLE IF EXISTS postings");
            statement.execute("create table IF NOT exists postings( matDoc varchar(100), item varchar(100),   docDate date , pstngDate date,  materialDescription varchar(100), quantity varchar(100), bUn varchar(100), amountLC varchar(100), crcy varchar(100), userName varchar(100));");

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO postings(matDoc, item, docDate, pstngDate, materialDescription, quantity, bUn, amountLC, crcy, userName) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            CsvToBean csv = new CsvToBean();

            CSVReader csvReader = new CSVReader(new FileReader(csvFilename), ';', '"', 2);

            List list = csv.parse(setColumMapping(), csvReader);

            for (Object object : list) {
                String pstngDate =((Post) object).getPstngDate().replace("\t","").replace(".","-");
                String docDate =((Post) object).getDocDate().replace("\t","").replace(".","-");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                preparedStatement.setString(1, ((Post) object).getMatDoc().replace("\t",""));
                preparedStatement.setString(2, ((Post) object).getItem().replace("\t",""));
                preparedStatement.setDate(3, Date.valueOf(LocalDate.parse(docDate, formatter).format(formatter2)));
                preparedStatement.setDate(4, Date.valueOf(LocalDate.parse(pstngDate, formatter).format(formatter2)));
                preparedStatement.setString(5, ((Post) object).getMaterialDescription().replace("\t",""));
                preparedStatement.setString(6, ((Post) object).getQuantity().replace("\t",""));
                preparedStatement.setString(7, ((Post) object).getBUn().replace("\t",""));
                preparedStatement.setString(8, ((Post) object).getAmountLC().replace("\t",""));
                preparedStatement.setString(9, ((Post) object).getCrcy().replace("\t",""));
                preparedStatement.setString(10, ((Post) object).getUserName().replace("\t",""));
                preparedStatement.addBatch();
                preparedStatement.executeBatch();
            }

            csvReader.close();
            preparedStatement.executeBatch();
            connection.close();

            long end = System.currentTimeMillis();
            System.out.println("PostingsInserterOpenCSV Execution Time: " + (end - start));
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static ColumnPositionMappingStrategy setColumMapping() {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(Post.class);
        String[] columns = new String[]{"matDoc", "item", "docDate", "pstngDate", "materialDescription", "quantity", "bUn", "amountLC", "crcy", "userName"};
        strategy.setColumnMapping(columns);
        return strategy;
    }
}
