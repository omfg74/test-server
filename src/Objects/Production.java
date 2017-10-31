package Objects;

import dbworker.DBWorker;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Production extends Thread{
    Long id;
    Socket incomeConnection;
    public Production(Long id, Socket incomeConnection, Task task) {
    this.id =id;
    this.incomeConnection = incomeConnection;
    }

    @Override
    public void run() {
        System.out.println("Produstion running");
        //todo
        try {
            for (int i = 0; i <5 ; i++) {//поменять на 180
                TimeUnit.SECONDS.sleep(1);
                System.out.print(i+" ");

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Production stopped");
        DBWorker dbWorker = new DBWorker();
        dbWorker.updateTaskStatus(id);

        try {
            DataOutputStream dataOutputStream = new DataOutputStream(incomeConnection.getOutputStream());

//            dataOutputStream.writeUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
