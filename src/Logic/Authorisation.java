package Logic;

import Objects.RegistrationData;
import dbworker.DBWorker;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Authorisation {

    public boolean authorise(BufferedReader in, PrintWriter out, Socket incomeConnection, ServerSocket server, String incomeJson) {
     boolean authorised = false;
        try {
//            in = new BufferedReader(new InputStreamReader(incomeConnection.getInputStream()));
            out = new PrintWriter(incomeConnection.getOutputStream(),true);
//            DataInputStream dataInputStream = new DataInputStream(incomeConnection.getInputStream());
//            String authData =dataInputStream.readUTF();
            RegistrationData user = new RegistrationData();
            ParseJson parseJson = new ParseJson();
            System.out.println(incomeJson);
            user = parseJson.parseAuthData(incomeJson);
            DBWorker dbWorker = new DBWorker();
           authorised= dbWorker.authorise(user);
//            DataOutputStream dataOutputStream1 = new DataOutputStream(incomeConnection.getOutputStream());
            user = dbWorker.getNameSurname(user);
            PackJson packJson = new PackJson();
            JSONObject jo=packJson.putNameSurname(user);
//            dataOutputStream1.writeBoolean(authorised);
//            dataOutputStream1.writeUTF(jo.toString());
//            dataOutputStream1.flush();
            System.out.println("Retunable user"+jo);

            
        } catch (IOException e) {
            e.printStackTrace();
            authorised =false;

        }

        return authorised;
    }

}
