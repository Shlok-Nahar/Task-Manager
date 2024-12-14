import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONObject;

public class FileManager {
    public void saveToFile(String filePath, JSONObject data) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(data.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject loadFromFile(String filePath) {
        StringBuilder jsonData = new StringBuilder();
        try (FileReader reader = new FileReader(filePath)) {
            int i;
            while ((i = reader.read()) != -1) {
                jsonData.append((char) i);
            }
            return new JSONObject(jsonData.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
