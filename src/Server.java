import Logic.Authorisation;
import Logic.CreateNewTask;
import Logic.ListTasks;
import Logic.Registration;
import Objects.RegistrationData;
import com.sun.org.apache.regexp.internal.RE;
import dbworker.DBWorker;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int port = 3535;
    ServerSocket server;
    Socket incomeConnection;
    BufferedReader in = null;
    PrintWriter out = null;
    public Server() {
    try{
    server = new ServerSocket(port);
}catch(IOException e){
        System.out.println("The port is bisy");}

}
public void accept(){
    try {
        System.out.println("Waiting for connection");
        incomeConnection= server.accept();
        System.out.println("Client connected");
    } catch (IOException e) {
        System.out.println("Can't accept!");
        e.printStackTrace();

        try {
            incomeConnection.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }


    }

        boolean registred = false;

        while (!registred){

        Registration registration = new Registration();
      registred = registration.registerNewUser(in,out,incomeConnection,server);

        }
    boolean authorised = false;
        while (!authorised) {
            Authorisation authorisation = new Authorisation();
           authorised=  authorisation.authorise(in,out,incomeConnection,server);
            try {
                DataOutputStream dataOutputStream = new DataOutputStream(incomeConnection.getOutputStream());
                dataOutputStream.writeBoolean(authorised);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        int command=-1;
        while (command==-1||command==1||command==2||!incomeConnection.isClosed()){
            try {
                DataInputStream dataInputStream = new DataInputStream(incomeConnection.getInputStream());
            command=dataInputStream.read();
                System.out.println(command);
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    incomeConnection.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if(command==1){
                CreateNewTask createNewTask = new CreateNewTask(incomeConnection);
                createNewTask.run();
            }else if(command==2){
                ListTasks listTasks = new ListTasks();
            }
        }


}
}

