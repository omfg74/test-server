import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
    }
    try {
        in = new BufferedReader(new InputStreamReader(incomeConnection.getInputStream()));
        out = new PrintWriter(incomeConnection.getOutputStream(),true);
        String input,output;
        System.out.println("Waiting for data");
//        while ((input=in.readLine())!=null){
        //Авторизовани или нет
        input=in.readLine();
        //ПРоверяем регистрацию

        if(input.equalsIgnoreCase("y")){
            out.print("Registed");

            input=in.readLine();
            ParseJson parseJson = new ParseJson();
            parseJson.parse(input);

        }else {
            out.print("Unregistered");
            //ждем регистрацию
            input=in.readLine();

        }


            if (input.equalsIgnoreCase("q"))

            out.println("Server "+input);
            System.out.println(input);

//        }
        out.close();
        in.close();
        incomeConnection.close();
        server.close();
    } catch (IOException e) {
        e.printStackTrace();
    }


}
}

