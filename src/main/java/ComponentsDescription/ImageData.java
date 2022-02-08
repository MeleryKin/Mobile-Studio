package ComponentsDescription;

import org.json.JSONObject;

public class ImageData extends ComponentData{

    public String fileName;

    public ImageData(JSONObject data) {
        super(data);
        fileName = data.getString("FileName");
    }

    public ImageData(){
        super();
    }
}
