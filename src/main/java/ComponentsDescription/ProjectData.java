package ComponentsDescription;

import Panels.ScreensListPanel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProjectData {
    public ArrayList<ScreenData> screens;
    public boolean saved;
    public String path;

    public ProjectData(){
        screens = new ArrayList<>();
        ScreenData s = new ScreenData();
        screens.add(s);
        saved = true;
        path = "";
    }

    public ProjectData(JSONObject data, String pathProject){
        screens = new ArrayList<>();
        JSONArray screensData = data.getJSONArray("Screens");
        for (int i = 0; i < screensData.length(); i++){
            ScreenData s = new ScreenData(screensData.getJSONObject(i));
            screens.add(s);
        }
        saved = true;
        path = pathProject;
    }
}
