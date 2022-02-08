package ComponentsDescription;

import org.json.JSONObject;

public class ButtonData extends ComponentData {

    public String text;
    public String color;

    public ButtonData(JSONObject data) {
        super(data);
        text = data.getString("Text");
        color = data.getString("Color");
    }

    public ButtonData(){
        super();
    }
}
