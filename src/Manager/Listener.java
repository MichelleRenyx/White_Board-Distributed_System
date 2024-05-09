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
        String type = drawRecord.get("type").getAsString();
        int startX = drawRecord.get("startX").getAsInt();
        int startY = drawRecord.get("startY").getAsInt();
        int endX = drawRecord.get("endX").getAsInt();
        int endY = drawRecord.get("endY").getAsInt();
        String color = drawRecord.get("color").getAsString();

        // Handle drawing logic here
    }
}
