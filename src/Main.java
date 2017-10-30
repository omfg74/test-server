import dbworker.DBWorker;

import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DBWorker dbWorker = new DBWorker();
        System.out.println("Create new DB y/n:");
        Scanner sc = new Scanner(System.in);
        String answer = sc.nextLine();
        if (answer.equalsIgnoreCase("y")){
        Connection connection = dbWorker.getConnection();
        dbWorker.createDB(connection);
        dbWorker.createTables();
        }else {
            Connection connection = dbWorker.workConnection();

        }

        ServerThread serverThread = new ServerThread();
        serverThread.run();


    }

}
