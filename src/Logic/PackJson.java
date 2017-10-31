package Logic;

import Objects.RegistrationData;
import Objects.Task;
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
            jo.put("Text ","The user already exists");
        }

        return jo;
    }

    public JSONObject makeAuthAnswer(boolean b) {
        JSONObject jo = new JSONObject();
        jo.put("Answer",b);
        if(b){
            jo.put("Text ","Regitred");
        }else if(!b){
            jo.put("Text ","Authorisation Failed");
        }

        return jo;
    }

    public JSONObject packStartedTask(Task task, Long id) {
        JSONObject jo = new JSONObject();
        //возможно придется пилить type
        System.out.println(task.getName());
        jo.put("taskType",task.getName());
        jo.put("id",id);
        jo.put("status",task.getStatus());
        jo.put("result",task.getResult());

        return jo;
    }
}
