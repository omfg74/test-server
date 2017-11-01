package Logic;

import Objects.RegistrationData;
import Objects.Task;
import dbworker.DBWorker;
import org.json.simple.JSONObject;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ListTasks {
    public JSONObject makeNewList(Socket incomeConnection, RegistrationData user, int command) {
        Task task = new Task();
       ArrayList <Task> tasks = new ArrayList();
        DBWorker dbWorker = new DBWorker();
        tasks = dbWorker.createList(user);
        PackJson packJson = new PackJson();
        JSONObject jo = packJson.packList(tasks);
        return jo;
    }
}
