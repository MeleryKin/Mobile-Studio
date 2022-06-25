package ComponentsDescription;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConfigData {
    public String name;
    public String projectIcon;
    public String startScreen;
    public ArrayList<ParameterData> parameters;

    public ConfigData(){
        name = "unnamed";
        projectIcon = "default.png";
        startScreen = "Screen1";
        parameters = new ArrayList<>();
    }

    public ConfigData(JSONObject data){
        name = data.getString("ProjectName");
        projectIcon = data.getString("ProjectIcon");
        startScreen = data.getString("StartScreen");
        parameters = new ArrayList<>();
        JSONArray parametersData = data.getJSONArray("Parameters");
        for (int i = 0; i < parametersData.length(); i++){
            ParameterData s = new ParameterData(parametersData.getJSONObject(i));
            parameters.add(s);
        }
    }
}
