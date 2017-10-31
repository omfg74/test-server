import Logic.*;
import Objects.RegistrationData;
import com.sun.org.apache.regexp.internal.RE;
import dbworker.DBWorker;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends ServerThread {
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
        currentThread().stop();
        try {
            incomeConnection.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }


    }
String type=null;
    String income = null;
    boolean exit = false;
    while (!exit){

    try {
        DataInputStream dataInputStream = new DataInputStream(incomeConnection.getInputStream());

        ParseJson parseJson = new ParseJson();
        income = dataInputStream.readUTF();
      type = parseJson.parseIncome(income);

    } catch (IOException e) {
        e.printStackTrace();
    }
    boolean registred = false;
if(type.equalsIgnoreCase("registr")){



        Registration registration = new Registration();
      registred = registration.registerNewUser(in,out,incomeConnection,server,income);
      PackJson packJson = new PackJson();
      JSONObject ans = packJson.makeRegAnswer(registred);
    System.out.println("RegAns "+ans);

    try {
        DataOutputStream dataOutputStream = new DataOutputStream(incomeConnection.getOutputStream());
    dataOutputStream.writeUTF(ans.toString());
    } catch (IOException e) {
        e.printStackTrace();
    }


}else if(type.equalsIgnoreCase("auth")){
    boolean authorised = false;
        while (!authorised) {
            Authorisation authorisation = new Authorisation();
           authorised=  authorisation.authorise(in,out,incomeConnection,server);
            try {
                DataOutputStream dataOutputStream = new DataOutputStream(incomeConnection.getOutputStream());
                dataOutputStream.writeBoolean(authorised);

            } catch (IOException e) {
                try {
                    currentThread().stop();
                    incomeConnection.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                currentThread().stop();
                e.printStackTrace();
            }

        }
}else if(type.equalsIgnoreCase("menu")){

        int command=-1;
        while (command==-1||command==1||command==2||!incomeConnection.isClosed()){
            try {
                DataInputStream dataInputStream = new DataInputStream(incomeConnection.getInputStream());
            command=dataInputStream.read();
                System.out.println(command);

            } catch (IOException e) {
                e.printStackTrace();
                try {
                    currentThread().stop();
                    incomeConnection.close();
                } catch (IOException e1) {


                }
            }
            if(command==2){
                CreateNewTask createNewTask = new CreateNewTask(incomeConnection);
                createNewTask.run();
            }else if(command==1){
                ListTasks listTasks = new ListTasks();
            }
        }


}else {
    System.exit(0);
}

    }
}
}

