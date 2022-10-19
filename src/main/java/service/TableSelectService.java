package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TableSelectService {
    public void get(int num,String typeOfPeriod,boolean isActiveStatus){
        String jdbcURL = "jdbc:mysql://localhost:3306/ThirdTaskFor_A1";
        String username = "root";
        String password = "12345";

        Connection connection = null;

        try {
            long start = System.currentTimeMillis();

            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);

            try (Statement statement = connection.createStatement()) {
                statement.execute("DROP TABLE IF EXISTS someNoteByDate");
            } catch (SQLException exception) {
                exception.printStackTrace();
            }

            try (Statement statement = connection.createStatement()) {
                statement.execute("create table IF NOT exists someNoteByDate(matDoc varchar(100), item varchar(100),   docDate date, pstngDate date,  materialDescription varchar(100), quantity varchar(100), bUn varchar(100), amountLC varchar(100), crcy varchar(100), userName varchar(100),authorizeDelivery boolean);");
            } catch (SQLException exception) {
                exception.printStackTrace();
            }

            try (Statement statement = connection.createStatement()) {

                String status;
                if(isActiveStatus){
                    status = " and authorizeDelivery = 1;";
                } else {
                    status =";";
                }

                String sql = "insert into someNoteByDate SELECT * FROM postings WHERE pstngDate BETWEEN CURDATE() - INTERVAL "+ num + " "+typeOfPeriod+ " "+ "AND CURDATE()"+ status;
                statement.execute(sql);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }



            connection.commit();
            connection.close();

            long end = System.currentTimeMillis();
            System.out.println("TableSelectService Execution Time: " + (end - start));
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
