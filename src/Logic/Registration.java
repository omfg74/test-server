package Logic;

import Objects.RegistrationData;
import dbworker.DBWorker;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Registration {
    boolean registred = false;
    public boolean registerNewUser(BufferedReader in, PrintWriter out, Socket incomeConnection, ServerSocket server, String income){
//        try {
//            in = new BufferedReader(new InputStreamReader(incomeConnection.getInputStream()));
//            out = new PrintWriter(incomeConnection.getOutputStream(),true);
//            String input,output;
//            System.out.println("Waiting for data");
//        while ((input=in.readLine())!=null){
            //Зарегистрирован или нет

//            input= in.readLine();
            //ПРоверяем регистрацию
//            System.out.println(input);

//            if(input.equalsIgnoreCase("y")){
//                System.out.print("Registed");
//                registred = true;

//                input=in.readLine();


//            }else {
//                System.out.println("Unregistered");
            //ждем регистрацию

//                DataInputStream dataInputStream = new DataInputStream(incomeConnection.getInputStream());
//                String inpu1t =dataInputStream.readUTF();//получаем данные для регистрации
//                System.out.println(inpu1t);
            ParseJson parseJson = new ParseJson();
            RegistrationData registrationData = parseJson.parse(income);
            DBWorker dbWorker = new DBWorker();
            dbWorker.workConnection();
            Boolean exists = dbWorker.checkIfUserExists(registrationData.getLogin());
            if (!exists) {
                System.out.println("Creating new User");
//                    DataOutputStream dataOutputStream = new DataOutputStream(incomeConnection.getOutputStream());
//                    dataOutputStream.writeBoolean(true);
//                    dataOutputStream.flush();
                dbWorker.createNewUser(registrationData);
                registred = true;
            } else {
                System.out.println("User Exists");
//                    DataOutputStream dataOutputStream = new DataOutputStream(incomeConnection.getOutputStream());
//                    dataOutputStream.writeBoolean(false);
                registred = false;
            }


//        }


//            if (input.equalsIgnoreCase("q")){}



//        }

//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return registred;
    }
}
