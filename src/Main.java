import dbworker.DBWorker;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        DBWorker dbWorker = new DBWorker();
        Connection connection = dbWorker.getConnection();
//        dbWorker.createDB(connection);
Server server = new Server();
server.accept();
    }
}
