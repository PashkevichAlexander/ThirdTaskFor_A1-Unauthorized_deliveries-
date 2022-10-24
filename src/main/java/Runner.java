import inserters.LoginsInserterSuperCSV;
import inserters.PostingsInserterOpenCSV;
import service.TableSelectService;
import tableSelectors.ActiveUserSelector;

import java.sql.SQLException;

public class Runner {
    public static void main(String[] args) throws SQLException {


        LoginsInserterSuperCSV loginsInserterSuperCSV = new LoginsInserterSuperCSV();
        PostingsInserterOpenCSV postingsInserterOpenCSV = new PostingsInserterOpenCSV();
        ActiveUserSelector activeUserSelector = new ActiveUserSelector();
        TableSelectService tableSelectService = new TableSelectService();

        loginsInserterSuperCSV.run();
        postingsInserterOpenCSV.run();
        activeUserSelector.run();

//      Value of smth
        int num = 1;
//      typeOfPeriod(day,month,quarter,year)
        String typeOfPeriod = "quarter";
//      This status affects the selection(true = with authorizeDelivery, false = without authorizeDelivery)
        Boolean isActiveStatus = true;
        tableSelectService.get(num,typeOfPeriod,isActiveStatus);


    }
}

