package ComponentsDescription;

import org.json.JSONObject;

public class VideoData extends ComponentData{
    public String fileName;

    public VideoData(JSONObject data) {
        super(data);
        fileName = data.getString("FileName");
    }

    public VideoData(){
        super();
    }
}
