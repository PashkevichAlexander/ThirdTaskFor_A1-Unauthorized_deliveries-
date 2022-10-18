import entity.LoginsInserter;

public class Runner {
    public static void main(String[] args) {
//        Thread myThread = new Thread(new ComplexCsv2DbInserter(),"ComplexCsv2DbInserter");
        Thread myThread = new Thread(new LoginsInserter(),"LoginsInserter");
        myThread.start();
    }
}
