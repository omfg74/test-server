package Logic;

import Objects.RegistrationData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Iterator;

public class ParseJson {
    public RegistrationData parse(String auth){
        JSONObject jsonObject = new JSONObject();
        RegistrationData registrationData = new RegistrationData();
        JSONParser jsonParser = new JSONParser();
        try {
            jsonObject =  (JSONObject)jsonParser.parse(auth);
            String name = (String) jsonObject.get("Name");
            String surname = (String) jsonObject.get("Surname");
            String login = (String) jsonObject.get("Login");
            String password = (String) jsonObject.get("Password");

            registrationData.setName(name);
            registrationData.setSurname(surname);
            registrationData.setLogin(login);
            registrationData.setPassword(password);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return registrationData;
    }

    public RegistrationData parseAuthData(String authData) {
        RegistrationData user = new RegistrationData();
        JSONParser jsonParser = new JSONParser();
        try {
            System.out.println(authData);
            JSONObject jsonObject =  (JSONObject)jsonParser.parse(authData);
            String login = (String) jsonObject.get("login");
            System.out.println(login);
            String password = (String) jsonObject.get("pass");
            System.out.println(password);
            user.setLogin(login);
            user.setPassword(password);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return user;
    }
}
