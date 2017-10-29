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
}
