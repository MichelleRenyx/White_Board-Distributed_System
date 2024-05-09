package Manager;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class Listener {
    ArrayList<JsonObject> records = new ArrayList<>();
    public void clearRecords() {
        records.clear();
    }

    public ArrayList<JsonObject> getRecords() {
        return records;
    }

    public void update(JsonObject drawRecord) {
        records.add(drawRecord);
        String type = drawRecord.get("type").getAsString();
        String command = drawRecord.get("command").getAsString();
        int startX = drawRecord.get("startX").getAsInt();
        int startY = drawRecord.get("startY").getAsInt();
        int endX = drawRecord.get("endX").getAsInt();
        int endY = drawRecord.get("endY").getAsInt();
        String color = drawRecord.get("color").getAsString();
/*JsonObject drawCommand = new JsonObject();
drawCommand.addProperty("type", "draw");
drawCommand.addProperty("command", "line");
drawCommand.addProperty("startX", 100);
drawCommand.addProperty("startY", 100);
drawCommand.addProperty("endX", 200);
drawCommand.addProperty("endY", 200);
drawCommand.addProperty("color", "red");*/
        // Handle drawing logic here
    }
}
