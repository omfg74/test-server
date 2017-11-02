package Objects;

import Logic.PackJson;
import dbworker.DBWorker;
import org.json.simple.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Production extends Thread{
    Long id;
    Socket incomeConnection;
    Task task;
    public Production(Socket incomeConnection, Task task) {
//    this.id =id;
    this.incomeConnection = incomeConnection;
   this.task = task;
    }

    @Override
    public void run() {
        System.out.println("Production running");
        //todo
        try {
            for (int i = 0; i <5 ; i++) {//поменять на 180
                TimeUnit.SECONDS.sleep(1);
//                System.out.print(i+" ");

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Production stopped");
        DBWorker dbWorker = new DBWorker();
        task.setStatus("COMPLETE");
       dbWorker.updateTaskStatus(task);

        PackJson packJson = new PackJson();
        JSONObject jo = packJson.packUpdate(task);

        try {
            DataOutputStream dataOutputStream = new DataOutputStream(incomeConnection.getOutputStream());
dataOutputStream.writeUTF(jo.toString());
            System.out.println(jo);
//            dataOutputStream.writeUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
//    ArrayList tasks  = dbWorker.updateTaskStatus(id,task);
//
//    PackJson packJson = new PackJson();
//    JSONObject jo = packJson.packList(tasks);