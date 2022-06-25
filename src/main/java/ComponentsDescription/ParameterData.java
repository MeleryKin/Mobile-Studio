package ComponentsDescription;

import org.json.JSONObject;

public class ParameterData {
    public String name;
    public String type;
    public String value;

    public ParameterData(){
        name = "";
        type = "";
        value = "";
    }

    public ParameterData(JSONObject data){
        name = data.getString("Name");
        type = data.getString("Type");
        value = data.getString("Value");
    }
}
