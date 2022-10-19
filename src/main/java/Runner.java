import inserters.LoginsInserterSuperCSV;
import inserters.PostingsInserterOpenCSV;
import tableSelectors.ActiveUserSelector;

public class Runner {
    public static void main(String[] args) {
        LoginsInserterSuperCSV loginsInserterSuperCSV = new LoginsInserterSuperCSV();
        PostingsInserterOpenCSV postingsInserterOpenCSV = new PostingsInserterOpenCSV();
        ActiveUserSelector activeUserSelector = new ActiveUserSelector();

        loginsInserterSuperCSV.run();
        postingsInserterOpenCSV.run();
        activeUserSelector.run();


    }
}
