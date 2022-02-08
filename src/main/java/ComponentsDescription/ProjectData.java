package ComponentsDescription;

import Panels.ScreensListPanel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProjectData {
    public String name;
    public String projectIcon;
    public String startScreen;
    public ArrayList<ScreenData> screens;

    public ProjectData(){
        name = "unnamed";
        projectIcon = "default.png";
        startScreen = "Screen1";
        screens = new ArrayList<>();
        ScreenData s = new ScreenData();
        screens.add(s);
    }

    public ProjectData(JSONObject data){
        name = data.getString("ProjectName");
        projectIcon = data.getString("ProjectIcon");
        startScreen = data.getString("StartScreen");
        screens = new ArrayList<>();
        JSONArray screensData = data.getJSONArray("Screens");
        for (int i = 0; i < screensData.length(); i++){
            ScreenData s = new ScreenData(screensData.getJSONObject(i));
            screens.add(s);
        }
    }
}
