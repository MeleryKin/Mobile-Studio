package ComponentsDescription;

import org.json.JSONObject;

public class ConditionData {
    public String leftExpression;
    public String sign;
    public String rightExpression;

    public ConditionData(){
        leftExpression = "";
        sign = "=";
        rightExpression = "";
    }

    public ConditionData(JSONObject data){
        leftExpression = data.getString("LeftExpression");
        sign = data.getString("Sign");
        rightExpression = data.getString("RightExpression");
    }
}
