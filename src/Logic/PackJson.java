package Logic;

import Objects.RegistrationData;
import Objects.Task;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
//        System.out.println(task.getName());
        jo.put("taskType",task.getName());
        jo.put("id",id);
        jo.put("status",task.getStatus());
        jo.put("result",task.getResult());

        return jo;
    }

    public JSONObject packList(ArrayList<Task> list) {
        JSONObject jo = new JSONObject();
        JSONArray ar = new JSONArray();
        for (int i = 0; i < list.size(); i++) {

            ar.add(list.get(i).getId());
            ar.add(list.get(i).getStatus());

            
        }
//        System.out.println(ar);
        jo.put("taskType","list");
        jo.put("content",ar);
        return jo;
    }

    public JSONObject packUpdate(Task task1) {
 JSONObject jo = new JSONObject();
// jo.put("task","list");
 jo.put("taskType","update");
 jo.put("id",task1.getId());
 jo.put("status",task1.getStatus());
//        System.out.println("jo status"+task1.getStatus());
 jo.put("result",task1.getResult());
 jo.put("name",task1.getName());


        return jo;
    }
}
