import Logic.*;
import Objects.RegistrationData;
import com.sun.org.apache.regexp.internal.RE;
import dbworker.DBWorker;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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

    boolean exit = false;
    while (!exit){
        String type=null;
        String income = null;
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

            Authorisation authorisation = new Authorisation();
           authorised=  authorisation.authorise(in,out,incomeConnection,server,income);
    try {
        PackJson packJson = new PackJson();
        JSONObject ans = packJson.makeAuthAnswer(authorised);
        System.out.println("Authans "+ans);
        DataOutputStream dataOutputStream = new DataOutputStream(incomeConnection.getOutputStream());
        dataOutputStream.writeUTF(ans.toString());
    } catch (IOException e) {
        e.printStackTrace();
    }
    if(authorised){

           }



}else if(type.equalsIgnoreCase("menu")){

        int command=-1;
//        while (command==-1||command==1||command==2||!incomeConnection.isClosed()){
            RegistrationData user = new RegistrationData();
            ParseJson parseJson = new ParseJson();
            JSONObject jo= parseJson.parseCommand(income);

            System.out.println(income);

                command = Integer.parseInt(jo.get("item").toString());


            user.setLogin((String)jo.get("login"));

            if(command==1){
                CreateNewTask createNewTask = new CreateNewTask(incomeConnection,user,command);
                createNewTask.run();
                command=-1;//сильно под вопростом
            }else if(command==2){
                ListTasks listTasks = new ListTasks();
            }
        }


else {
    //Вставать на слушателя
    System.exit(0);
}


}}}


