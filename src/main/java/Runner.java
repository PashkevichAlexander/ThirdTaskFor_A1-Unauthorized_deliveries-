import Inserters.PostingsInserter;

public class Runner {
    public static void main(String[] args) {
//        Thread myThread = new Thread(new LoginsInserter(),"LoginsInserter");
        Thread myThread = new Thread(new PostingsInserter(),"PostingsInserter");
        myThread.start();
    }
}
