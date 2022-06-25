package ComponentsDescription;

import org.json.JSONArray;
import org.json.JSONObject;

public class ButtonData extends ComponentData {

    public String text;
    public int[] colorBackground;
    public int[] colorFont;

    public ButtonData(JSONObject data) {
        super(data);
        text = data.getString("Text");
        colorBackground = new int[3];
        colorFont = new int[3];
        JSONArray jColor = data.getJSONArray("ColorBackground");
        for (int i = 0; i < 3; i++){
            int o = jColor.getInt(i);
            colorBackground[i] = o;
        }
        jColor = data.getJSONArray("ColorFont");
        for (int i = 0; i < 3; i++){
            int o = jColor.getInt(i);
            colorFont[i] = o;
        }
    }

    public ButtonData(){
        super();
    }
}
