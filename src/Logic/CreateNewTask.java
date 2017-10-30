package Logic;

import Objects.Task;
import dbworker.DBWorker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class CreateNewTask implements Runnable {
Socket incomeConnection;

    public CreateNewTask(Socket incomeConnection) {
this.incomeConnection = incomeConnection;
    }

    public void run() {
        try {
            DataInputStream dataInputStream = new DataInputStream(incomeConnection.getInputStream());
            String newTask = dataInputStream.readUTF();
            ParseJson parseJson = new ParseJson();
            Task task = parseJson.parseNewTask(newTask);
            DataOutputStream dataOutputStream = new DataOutputStream(incomeConnection.getOutputStream());
            dataOutputStream.writeUTF("STARTED");
            task.setResult("started");
            DBWorker dbWorker = new DBWorker();
            dbWorker.createNewTask(task);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
