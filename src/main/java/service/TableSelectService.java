package service;

import JDBCService.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableSelectService {
    public void get(int num, String typeOfPeriod, boolean isActiveStatus) {

        try {
            long start = System.currentTimeMillis();

            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();

            statement.execute("DROP TABLE IF EXISTS someNoteByDate");
            statement.execute("create table IF NOT exists someNoteByDate(matDoc varchar(100), item varchar(100),   docDate date, pstngDate date,  materialDescription varchar(100), quantity varchar(100), bUn varchar(100), amountLC varchar(100), crcy varchar(100), userName varchar(100),authorizeDelivery boolean);");


            String status;
            if (isActiveStatus) {
                status = " and authorizeDelivery = 1;";
            } else {
                status = ";";
            }
            statement.execute("insert into someNoteByDate SELECT * FROM postings WHERE pstngDate BETWEEN CURDATE() - INTERVAL " + num + " " + typeOfPeriod + " " + "AND CURDATE()" + status);

            connection.close();

            long end = System.currentTimeMillis();
            System.out.println("TableSelectService Execution Time: " + (end - start));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

