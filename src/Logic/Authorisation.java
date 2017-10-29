package Logic;

import Objects.RegistrationData;
import dbworker.DBWorker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
            user = parseJson.parseAuthData(authData,user);
            DBWorker dbWorker = new DBWorker();
           authorised= dbWorker.authorise(user);
            
            
        } catch (IOException e) {
            e.printStackTrace();
            authorised =false;
        }

        return authorised;
    }
}
