package ComponentsDescription;

import Panels.ScreensListPanel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProjectData {
    public ArrayList<ScreenData> screens;

    public ProjectData(){
        screens = new ArrayList<>();
        ScreenData s = new ScreenData();
        screens.add(s);
    }

    public ProjectData(JSONObject data){
        screens = new ArrayList<>();
        JSONArray screensData = data.getJSONArray("Screens");
        for (int i = 0; i < screensData.length(); i++){
            ScreenData s = new ScreenData(screensData.getJSONObject(i));
            screens.add(s);
        }
    }
}
