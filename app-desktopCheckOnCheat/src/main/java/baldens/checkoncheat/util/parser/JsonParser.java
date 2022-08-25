package baldens.checkoncheat.util.parser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonParser {
    private static Integer id;
    private static String token;
    private static String filename;
    private static String status;

    private JSONObject writeJsonSimpleToken() {
        JSONObject sampleObject = new JSONObject();
        sampleObject.put("id", id);
        sampleObject.put("token", token);

        return sampleObject;
    }

    private JSONObject writeJsonSimpleLogs() {
        JSONObject sampleObject = new JSONObject();
        sampleObject.put("status", status);
        return sampleObject;
    }

    public void build(boolean isLogs){
        try {
            JSONObject sampleObject;

            if(isLogs){
                sampleObject = writeJsonSimpleLogs();
            }else{
                sampleObject = writeJsonSimpleToken();
            }
            Files.write(Paths.get(getDirectoryProject()), sampleObject.toJSONString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear(){
        try {
            JSONObject sampleObject = new JSONObject();
            Files.write(Paths.get(getDirectoryProject()), sampleObject.toJSONString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object readJsonSimpleDemo() {
        FileReader reader;
        try {
            reader = new FileReader(getDirectoryProject());
            JSONParser jsonParser = new JSONParser();
            return jsonParser.parse(reader);
        }catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getDirectoryProject() throws IOException {
        StringBuilder route = new StringBuilder(new String(new File(".").getCanonicalPath().getBytes(StandardCharsets.UTF_8)));
        route.append("\\src\\main\\java\\baldens\\checkoncheat\\asset\\" + filename);
        return route.toString();
    }

    public void setId(Integer id) {
        JsonParser.id = id;
    }

    public void setToken(String token) {
        JsonParser.token = token;
    }

    public void setFilename(String filename) {
        JsonParser.filename = filename;
    }

    public void setStatus(String id) {
        JsonParser.status = id;
    }
}
