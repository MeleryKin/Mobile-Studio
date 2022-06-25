package ComponentsDescription;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ScreenData {
    public String name;
    public int indexComp;
    public ArrayList<ComponentData> components;
    public int[] color;
    public String nextScreen;

    public ScreenData(){
        name = "Screen1";
        indexComp = 0;
        color = new int[]{255, 255, 255};
        components = new ArrayList<>();
    }

    ScreenData(JSONObject data){
        name = data.getString("ScreenName");
        indexComp = data.getInt("IndexOfComponents");
        JSONArray jColor = data.getJSONArray("Color");
        color = new int[3];
        color[0] = jColor.getInt(0);
        color[1] = jColor.getInt(1);
        color[2] = jColor.getInt(2);
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
