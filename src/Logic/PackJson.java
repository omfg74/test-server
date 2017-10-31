package Logic;

import Objects.RegistrationData;
import org.json.simple.JSONObject;

public class PackJson {
    public JSONObject putNameSurname(RegistrationData user){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", user.getName());
        jsonObject.put("surname",user.getSurname());
        return jsonObject;
    }

    public JSONObject makeRegAnswer(Boolean b) {
        JSONObject jo = new JSONObject();
        jo.put("Answer",b);
        if(b){
            jo.put("Text ","Regitred");
        }else if(!b){
            jo.put("Text","The user already exists");
        }

        return jo;
    }
}
