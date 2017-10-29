import Logic.Authorisation;
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

        }
        int command=-1;
        while (command==-1||command==1||command==2){

        }


}
}

