package Logic;

import Objects.RegistrationData;
import dbworker.DBWorker;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Authorisation {
    public boolean authorise(BufferedReader in, PrintWriter out, Socket incomeConnection, ServerSocket server) {
     boolean authorised = false;
        try {
            in = new BufferedReader(new InputStreamReader(incomeConnection.getInputStream()));
            out = new PrintWriter(incomeConnection.getOutputStream(),true);
            
            String authData =in.readLine();
            RegistrationData user = new RegistrationData();
            ParseJson parseJson = new ParseJson();
            System.out.println(authData);
            user = parseJson.parseAuthData(authData);
            DBWorker dbWorker = new DBWorker();
           authorised= dbWorker.authorise(user);
            DataOutputStream dataOutputStream = new DataOutputStream(incomeConnection.getOutputStream());
            user = dbWorker.getNameSurname(user);
            PackJson packJson = new PackJson();
            JSONObject jo=packJson.putNameSurname(user);
            dataOutputStream.writeBoolean(true);
            dataOutputStream.writeUTF(jo.toString());
            dataOutputStream.flush();
            
            
        } catch (IOException e) {
            e.printStackTrace();
            authorised =false;
        }

        return authorised;
    }
}
