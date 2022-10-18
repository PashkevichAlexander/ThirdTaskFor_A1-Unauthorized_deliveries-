//import java.io.*;
//import java.sql.*;
//
//public class SimpleCsv2DbInserter {
//
//    public static void main(String[] args) {
//        String jdbcURL = "jdbc:mysql://localhost:3306/ThirdTaskFor_A1";
//        String username = "root";
//        String password = "12345";
//
//        String csvFilePath = "logins.csv";
//
//        int batchSize = 20;
//
//        Connection connection = null;
//
//        try {
//
//            connection = DriverManager.getConnection(jdbcURL, username, password);
//            System.out.println("a");
//            connection.setAutoCommit(false);
//            System.out.println("b");
//
//            String sql = "INSERT INTO review (Application,tAppAccountName,tIsActive,tJobTitle,tDepartment) VALUES (?, ?, ?, ?, ?)";
//            PreparedStatement statement = connection.prepareStatement(sql);
//
//            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
//            String lineText = null;
//
//            int count = 0;
//
//            lineReader.readLine(); // skip header line
//
//            while ((lineText = lineReader.readLine()) != null) {
//                String[] data = lineText.split(",");
//                String Application = data[0];
//                String tAppAccountName = data[1];
//                String tIsActive = data[2];
//                String tJobTitle = data[3];
//                String tDepartment = data.length == 5 ? data[4] : "";
//
//                statement.setString(1, Application);
//                statement.setString(2, tAppAccountName);
//
//                Timestamp sqlTimestamp = Timestamp.valueOf(tIsActive);
//                statement.setTimestamp(3, sqlTimestamp);
//
//                Float fRating = Float.parseFloat(tJobTitle);
//                statement.setFloat(4, fRating);
//
//                statement.setString(5, tDepartment);
//
//                statement.addBatch();
//
//                if (count % batchSize == 0) {
//                    statement.executeBatch();
//                }
//            }
//
//            lineReader.close();
//
//            // execute the remaining queries
//            statement.executeBatch();
//
//            connection.commit();
//            connection.close();
//
//        } catch (IOException ex) {
//            System.err.println(ex);
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//
//            try {
//                connection.rollback();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//}
