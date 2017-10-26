
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ParseJson {
    public void parse(String auth){
        JSONObject jsonObject = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        try {
            jsonObject = (JSONObject) jsonParser.parse(auth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
