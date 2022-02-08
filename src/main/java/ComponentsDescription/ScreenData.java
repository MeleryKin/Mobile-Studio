package ComponentsDescription;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ScreenData {
    public String name;
    public int indexComp;
    public ArrayList<ComponentData> components;
    public String color;
    public String nextScreen;

    ScreenData(){
        name = "Screen1";
        indexComp = 0;
        components = new ArrayList<>();
    }

    ScreenData(JSONObject data){
        name = data.getString("ScreenName");
        indexComp = data.getInt("IndexOfComponents");
        color = data.getString("Color");
        nextScreen = data.getString("NextScreen");
        components = new ArrayList<>();
        JSONArray componentsArray = data.getJSONArray("Components");
        for (int i = 0; i < componentsArray.length(); i++){
            JSONObject x = componentsArray.getJSONObject(i);
            String type = x.getString("Type");
            ComponentData nw = null;
            if (type.compareTo("TextField") == 0){
                nw = new TextFieldData(x);
            }
            else if (type.compareTo("Button") == 0){
                nw = new ButtonData(x);
            }
            else if (type.compareTo("Image") == 0){
                nw = new ImageData(x);
            }
            else if (type.compareTo("Video") == 0){
                nw = new VideoData(x);
            }
            components.add(nw);
        }
    }
}
