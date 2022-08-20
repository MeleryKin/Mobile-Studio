package ComponentsDescription;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ComponentData {
    public String name;
    public int kx, ky;
    public int w, h;
    public String type;
    public ArrayList<TransitionData> transitions;

    ComponentData(JSONObject data){
        name = data.getString("ComponentName");
        kx = data.getInt("CoordX");
        ky = data.getInt("CoordY");
        w = data.getInt("Width");
        h = data.getInt("Height");
        type = data.getString("Type");
        transitions = new ArrayList<>();
        JSONArray transitionsArray = data.getJSONArray("Transitions");
        for (int i = 0; i < transitionsArray.length(); i++){
            JSONObject x = transitionsArray.getJSONObject(i);
            TransitionData nt = new TransitionData(x);
            transitions.add(nt);
        }
    }

    public ComponentData(){
        type = "";
        transitions = new ArrayList<>();
    }

}
