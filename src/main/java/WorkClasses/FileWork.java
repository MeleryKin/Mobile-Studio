package WorkClasses;

import ComponentsDescription.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.Scanner;

public class FileWork {

    public static JSONObject readProjectData(String fileName) throws IOException {
        fileName += "/project.json";
        FileReader fr = new FileReader(fileName);
        Scanner scanner = new Scanner(fr);
        StringBuilder ans = new StringBuilder();
        while (scanner.hasNextLine()){
            ans.append(scanner.nextLine());
        }
        fr.close();
        return new JSONObject(ans.toString());
    }

    public static JSONObject readConfigData(String fileName) throws IOException {
        fileName += "/config.json";
        FileReader fr = new FileReader(fileName);
        Scanner scanner = new Scanner(fr);
        StringBuilder ans = new StringBuilder();
        while (scanner.hasNextLine()){
            ans.append(scanner.nextLine());
        }
        fr.close();
        return new JSONObject(ans.toString());
    }

    public static void saveConfigData(ConfigData configData, String path) throws IOException, JSONException{
        JSONObject config = new JSONObject();
        config.put("ProjectName", configData.name);
        config.put("ProjectIcon", configData.projectIcon);
        config.put("StartScreen", configData.startScreen);

        JSONArray jsonArray = new JSONArray();
        for (ParameterData param : configData.parameters){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Name", param.name);
            jsonObject.put("Type", param.type);
            jsonObject.put("Value", param.value);
            jsonArray.put(jsonObject);
        }
        config.put("Parameters", jsonArray);
        writeFile(config.toString(), path, "config.json");
    }

    public static void saveProjectData(ProjectData projectData, String path) throws IOException{
        JSONObject project = new JSONObject();
        JSONArray screens = new JSONArray();
        for (ScreenData screenData : projectData.screens){
            JSONObject screen = new JSONObject();
            screen.put("ScreenName", screenData.name);
            screen.put("IndexOfComponents", screenData.indexComp);
            JSONArray color = new JSONArray();
            for (int x : screenData.color){
                color.put(x);
            }
            screen.put("Color", color);
            JSONArray components = new JSONArray();
            for (ComponentData componentData : screenData.components){
                JSONObject component = new JSONObject();
                component.put("ComponentName", componentData.name);
                component.put("CoordX", componentData.kx);
                component.put("CoordY", componentData.ky);
                component.put("Width", componentData.w);
                component.put("Height", componentData.h);
                component.put("Type", componentData.type);
                if (componentData.type.compareTo("TextField") == 0){
                    component.put("Text", ((TextFieldData)componentData).text);
                    color = new JSONArray();
                    for (int x : ((TextFieldData)componentData).colorFont){
                        color.put(x);
                    }
                    component.put("ColorFont", color);
                }
                if (componentData.type.compareTo("Button") == 0){
                    component.put("Text", ((ButtonData)componentData).text);
                    color = new JSONArray();
                    for (int x : ((ButtonData)componentData).colorBackground){
                        color.put(x);
                    }
                    component.put("ColorBackground", color);
                    color = new JSONArray();
                    for (int x : ((ButtonData)componentData).colorFont){
                        color.put(x);
                    }
                    component.put("ColorFont", color);
                }
                if (componentData.type.compareTo("Image") == 0){
                    component.put("FileName", ((ImageData)componentData).fileName);
                }
                JSONArray transitions = new JSONArray();
                for (TransitionData transitionData : componentData.transitions){
                    JSONObject transition = new JSONObject();
                    JSONArray conditions = new JSONArray();
                    for (ConditionData conditionData : transitionData.conditions){
                        JSONObject condition = new JSONObject();
                        condition.put("LeftExpression", conditionData.leftExpression);
                        condition.put("Sign", conditionData.sign);
                        condition.put("RightExpression", conditionData.rightExpression);
                        conditions.put(condition);
                    }
                    transition.put("Conditions", conditions);
                    transition.put("NextScreen", transitionData.screen);
                    JSONArray functions = new JSONArray();
                    for (FunctionData functionData : transitionData.functions){
                        JSONObject function = new JSONObject();
                        function.put("VarName", functionData.varName);
                        function.put("Expression", functionData.expression);
                        functions.put(function);
                    }
                    transition.put("Functions", functions);
                    transitions.put(transition);
                }
                component.put("Transitions", transitions);
                components.put(component);
            }
            screen.put("Components", components);
            JSONArray transitions = new JSONArray();
            for (TransitionData transitionData : screenData.transitions){
                JSONObject transition = new JSONObject();
                JSONArray conditions = new JSONArray();
                for (ConditionData conditionData : transitionData.conditions){
                    JSONObject condition = new JSONObject();
                    condition.put("LeftExpression", conditionData.leftExpression);
                    condition.put("Sign", conditionData.sign);
                    condition.put("RightExpression", conditionData.rightExpression);
                    conditions.put(condition);
                }
                transition.put("Conditions", conditions);
                transition.put("NextScreen", transitionData.screen);
                JSONArray functions = new JSONArray();
                for (FunctionData functionData : transitionData.functions){
                    JSONObject function = new JSONObject();
                    function.put("VarName", functionData.varName);
                    function.put("Expression", functionData.expression);
                    functions.put(function);
                }
                transition.put("Functions", functions);
                transitions.put(transition);
            }
            screen.put("Transitions", transitions);
            screens.put(screen);
        }
        project.put("Screens", screens);
        writeFile(project.toString(), path, "project.json");
    }

    private static void writeFile(String save, String path, String fileName) throws IOException {
        File file = new File(path, fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(save.getBytes());
        fos.close();
    }
}
