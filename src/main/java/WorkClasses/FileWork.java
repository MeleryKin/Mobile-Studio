package WorkClasses;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class FileWork {

    public static JSONObject readProjectData(String fileName) throws IOException {
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
        FileReader fr = new FileReader(fileName);
        Scanner scanner = new Scanner(fr);
        StringBuilder ans = new StringBuilder();
        while (scanner.hasNextLine()){
            ans.append(scanner.nextLine());
        }
        fr.close();
        return new JSONObject(ans.toString());
    }
}
