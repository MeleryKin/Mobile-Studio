package ComponentsDescription;

import org.json.JSONObject;

public class ComponentData {
    public String name;
    public int kx, ky;
    public int w, h;
    public String nextScreen;
    public String type;

    ComponentData(JSONObject data){
        name = data.getString("ComponentName");
        kx = data.getInt("CoordX");
        ky = data.getInt("CoordY");
        w = data.getInt("Width");
        h = data.getInt("Height");
        nextScreen = data.getString("NextScreen");
        type = data.getString("Type");
    }

    public ComponentData(){
        type = "";
    }

}
