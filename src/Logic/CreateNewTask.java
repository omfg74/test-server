package Logic;

import Objects.Production;
import Objects.RegistrationData;
import Objects.Task;
import dbworker.DBWorker;
import org.json.simple.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class CreateNewTask implements Runnable {
Socket incomeConnection;
RegistrationData user;
int command;
    public CreateNewTask(Socket incomeConnection, RegistrationData user, int command) {
this.incomeConnection = incomeConnection;
this.user = user;
this.command =command;
    }

    public void run() {
        Task task = new Task();
        try {
            task.setLogin(user.getLogin());
            if(command==1){
                task.setName("RENDER");
            }
            task.setStatus("RENDERING");
            task.setResult("0");
            DataOutputStream dataOutputStream = new DataOutputStream(incomeConnection.getOutputStream());
//            dataOutputStream.writeUTF("STARTED");



            DBWorker dbWorker = new DBWorker();
            Long id = dbWorker.createNewTask(task);
            Production production = new Production(id,incomeConnection,task);
            production.start();
            PackJson packJson = new PackJson();
            JSONObject jo =packJson.packStartedTask(task,id);
            dataOutputStream.writeUTF(jo.toString());



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
