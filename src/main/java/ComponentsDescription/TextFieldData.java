package ComponentsDescription;

import org.json.JSONObject;

public class TextFieldData extends ComponentData{

    public String text;

    public TextFieldData(JSONObject data) {
        super(data);
        text = data.getString("Text");
    }

    public TextFieldData(){
        super();
    }
}
