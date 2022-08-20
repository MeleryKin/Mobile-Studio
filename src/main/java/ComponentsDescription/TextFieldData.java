package ComponentsDescription;

import org.json.JSONArray;
import org.json.JSONObject;

public class TextFieldData extends ComponentData{

    public String text;
    public int[] colorFont;

    public TextFieldData(JSONObject data) {
        super(data);
        text = data.getString("Text");
        colorFont = new int[3];
        JSONArray jColor = data.getJSONArray("ColorFont");
        for (int i = 0; i < 3; i++){
            int o = jColor.getInt(i);
            colorFont[i] = o;
        }
    }

    public TextFieldData(){
        super();
    }
}
