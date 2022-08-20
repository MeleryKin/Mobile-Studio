package ComponentsDescription;

import org.json.JSONObject;

public class FunctionData {
    public String varName;
    public String expression;

    public FunctionData(){
        varName = "=";
        expression = "";
    }

    public FunctionData(JSONObject data){
        varName = data.getString("VarName");
        expression = data.getString("Expression");
    }
}
