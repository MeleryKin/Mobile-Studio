package ComponentsDescription;

import WorkClasses.RunClass;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TransitionData {

    public ArrayList<ConditionData> conditions;
    public String screen;
    public ArrayList<FunctionData> functions;

    public TransitionData(){
        conditions = new ArrayList<>();
        screen = RunClass.getScreen(0).name;
        functions = new ArrayList<>();
    }

    public TransitionData(JSONObject data){
        JSONArray conditionsArray = data.getJSONArray("Conditions");
        conditions = new ArrayList<>();
        for (int i = 0; i < conditionsArray.length(); i++){
            JSONObject x = conditionsArray.getJSONObject(i);
            ConditionData nc = new ConditionData(x);
            conditions.add(nc);
        }
        screen = data.getString("NextScreen");
        JSONArray functionsArray = data.getJSONArray("Functions");
        functions = new ArrayList<>();
        for (int i = 0; i < functionsArray.length(); i++){
            JSONObject x = functionsArray.getJSONObject(i);
            FunctionData nc = new FunctionData(x);
            functions.add(nc);
        }
    }
}
