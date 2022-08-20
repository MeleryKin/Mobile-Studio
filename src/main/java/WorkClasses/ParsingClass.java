package WorkClasses;

import ComponentsDescription.ParameterData;

import javax.swing.*;
import java.util.ArrayList;

public class ParsingClass {

    public static boolean isAllowable(char chr){
        return (chr == '(') || (chr == ')') || (chr == '+') || (chr == '-') || (chr == '*') || (chr == '/');
    }

    public static boolean isAllowableToString(char chr){
        return (chr == '(') || (chr == ')') || (chr == '+');
    }

    public static boolean isNumber(char chr){
        return (int) chr >= (int) '0' && (int) chr <= (int) '9';
    }

    public static boolean isLetter(char chr){
        return (int) chr >= (int) 'A' && (int) chr <= (int) 'Z' || (int) chr >= (int) 'a' && (int) chr <= (int) 'z';
    }

    public static String checkExpressionType(String expression){
        expression = expression.replaceAll("\\s+","");
        StringBuilder word = new StringBuilder();
        boolean find_word = false;
        String ans = "";
        for (int i = 0; i < expression.length(); i++){
            if (find_word){
                if (isAllowable(expression.charAt(i))){
                    try {
                        int s;
                        s = Integer.parseInt(word.toString());
                        if (ans.compareTo("") == 0) ans = "Число";
                        else if (ans.compareTo("Строка") == 0) return "Error";

                    } catch (NumberFormatException e) {
                        if (ans.compareTo("") == 0) ans = "Строка";
                        else if (ans.compareTo("Число") == 0) return "Error";
                    }
                    word = new StringBuilder();
                    find_word = false;
                }
                else if (expression.charAt(i) == ']'){
                    word.append(expression.charAt(i));
                    if (word.charAt(0) != '['){
                        return "Error";
                    }
                    ParameterData data = RunClass.getParameter(word.substring(1, word.length()-1));
                    if (data == null) return "Error";
                    if (ans.compareTo("") == 0) ans = data.type;
                    else if (ans.compareTo(data.type) != 0) return "Error";
                    word = new StringBuilder();
                    find_word = false;
                }
                else {
                    word.append(expression.charAt(i));
                }
            }
            else {
                if (expression.charAt(i) == '['){
                    find_word = true;
                    word = new StringBuilder();
                    word.append(expression.charAt(i));
                }
                else if (isLetter(expression.charAt(i)) || isNumber(expression.charAt(i))){
                    find_word = true;
                    word.append(expression.charAt(i));
                }
            }
        }
        if (word.toString().compareTo("") != 0){
            try {
                int s;
                s = Integer.parseInt(word.toString());
                if (ans.compareTo("") == 0) ans = "Число";
                else if (ans.compareTo("Строка") == 0) return "Error";

            } catch (NumberFormatException e) {
                if (ans.compareTo("") == 0) ans = "Строка";
                else if (ans.compareTo("Число") == 0) return "Error";
            }
        }
        return ans;
    }

    public static boolean checkExpressionsCorrectness(String expression1, String expression2){
        String x1 = checkExpressionType(expression1);
        String x2 = checkExpressionType(expression2);
        if (x1.compareTo("Error") == 0 || x2.compareTo("Error") == 0 || x1.compareTo(x2) != 0){
            return false;
        }
        if (x1.compareTo("Число") == 0){
            if (!checkInt(expression1)) return false;
        }
        else if (!checkString(expression1)) return false;
        if (x2.compareTo("Число") == 0){
            if (!checkInt(expression2)) return false;
        }
        else if (!checkString(expression2)) return false;
        return true;
    }

    private static boolean checkInt(String expression){
        expression = expression.replaceAll("\\s+","");
        String last = "-";
        int hooks = 0;

        StringBuilder word = new StringBuilder();
        boolean find_word = false;
        StringBuilder number = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            if (find_word){
                if (expression.charAt(i) == ']'){
                    word = new StringBuilder();
                    find_word = false;
                    last = "var";
                }
                else {
                    word.append(expression.charAt(i));
                }
            }
            else if (expression.charAt(i) == '['){
                if (last.compareTo("var") == 0  || !number.toString().equals("")){
                    return false;
                }
                else find_word = true;
            }
            else if (expression.charAt(i) == '('){
                if (last.compareTo("var") == 0  || !number.toString().equals("")){
                    return false;
                }
                hooks = hooks + 1;
                last = "-";
            }
            else if (expression.charAt(i) == ')'){
                if (hooks == 0 || (last.compareTo("var") != 0  && number.toString().equals(""))){
                    return false;
                }
                else {
                    hooks = hooks - 1;
                    last = "var";
                    number = new StringBuilder();
                }
            }
            else if (isNumber(expression.charAt(i))){
                if (last.compareTo("var") == 0){
                    return false;
                }
                number.append(expression.charAt(i));
            }
            else if (isAllowable(expression.charAt(i))){
                if (expression.charAt(i) == '-' || last.compareTo("var") == 0 || !number.toString().equals("")){
                    last = "char";
                    number = new StringBuilder();
                }
                else return false;
            }
            else {
                return false;
            }
        }
        return word.toString().compareTo("") == 0 && hooks == 0 && (last.compareTo("var") == 0 || !number.toString().equals(""));
    }

    private static boolean checkString(String expression){
        expression = expression.replaceAll("\\s+","");
        String last = "-";
        int hooks = 0;

        StringBuilder word = new StringBuilder();
        boolean find_word = false;
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            if (find_word){
                if (expression.charAt(i) == ']'){
                    word = new StringBuilder();
                    find_word = false;
                    last = "var";
                }
                else {
                    word.append(expression.charAt(i));
                }
            }
            else if (expression.charAt(i) == '['){
                if (last.compareTo("var") == 0  || !str.toString().equals("")){
                    return false;
                }
                else find_word = true;
            }
            else if (expression.charAt(i) == '('){
                if (last.compareTo("var") == 0  || !str.toString().equals("")){
                    return false;
                }
                hooks = hooks + 1;
                last = "-";
            }
            else if (expression.charAt(i) == ')'){
                if (hooks == 0 || (last.compareTo("var") != 0  && str.toString().equals(""))){
                    return false;
                }
                else {
                    hooks = hooks - 1;
                    last = "var";
                    str = new StringBuilder();
                }
            }
            else if (!isAllowable(expression.charAt(i))){
                if (last.compareTo("var") == 0){
                    return false;
                }
                str.append(expression.charAt(i));
            }
            else if (isAllowableToString(expression.charAt(i))){
                if (expression.charAt(i) == '-' || last.compareTo("var") == 0 || !str.toString().equals("")){
                    last = "char";
                    str = new StringBuilder();
                }
                else return false;
            }
            else {
                return false;
            }
        }
        return word.toString().compareTo("") == 0 && hooks == 0 && (last.compareTo("var") == 0 || !str.toString().equals(""));
    }

    public static boolean checkFunctionCorrectness(String expression, String type){
        if (expression.charAt(0) == '='){
            if (checkExpressionType(expression.substring(1)).compareTo(type) != 0){
                return false;
            }
            if (type.compareTo("Число") == 0){
                return checkInt(expression.substring(1));
            }
            else return checkString(expression.substring(1));
        }
        else {
            return checkExpressionType(expression).compareTo("Строка") == 0;
        }
    }
}
