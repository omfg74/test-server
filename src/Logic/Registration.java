package Logic;

import Objects.RegistrationData;
import dbworker.DBWorker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Registration {
    boolean registred = false;
    public boolean registerNewUser(BufferedReader in, PrintWriter out, Socket incomeConnection, ServerSocket server){
        try {
            in = new BufferedReader(new InputStreamReader(incomeConnection.getInputStream()));
            out = new PrintWriter(incomeConnection.getOutputStream(),true);
            String input,output;
            System.out.println("Waiting for data");
//        while ((input=in.readLine())!=null){
            //Зарегистрирован или нет

            input= in.readLine();
            //ПРоверяем регистрацию
            System.out.println(input);

            if(input.equalsIgnoreCase("y")){
                System.out.print("Registed");
                registred = true;

//                input=in.readLine();


            }else {
                System.out.println("Unregistered");
                //ждем регистрацию

                input=in.readLine();
                System.out.println(input);
                ParseJson parseJson = new ParseJson();
                RegistrationData registrationData = parseJson.parse(input);
                DBWorker dbWorker = new DBWorker();
                dbWorker.workConnection();
                Boolean exists = dbWorker.checkIfUserExists(registrationData.getLogin());
                if(!exists){
                    System.out.println("Creating new User");
                    out.println(false);
                    dbWorker.createNewUser(registrationData);
                    registred = true;
                }else {
                    System.out.println("User Exists");
                    out.println(true);
                    registred =false;
                }



            }


//            if (input.equalsIgnoreCase("q")){}



//        }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return registred;
    }
}
