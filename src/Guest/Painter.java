package Guest;

import com.google.gson.JsonObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Painter extends JPanel {
    private ArrayList<JsonObject> recordList = new ArrayList<>();
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        draw( (Graphics2D) g, this.recordList);
    }
    public void updateRecords(ArrayList<JsonObject> recordList) {
        this.recordList = recordList;
    }
    public void draw(Graphics2D graphics2D, ArrayList<JsonObject> jsonObjectArrayList){
        for (JsonObject record : recordList) {
            String type = record.get("type").getAsString();
            Stroke stroke = new BasicStroke(5);
            Color color = new Color(record.get("color").getAsInt());
            int startX = record.get("startX").getAsInt();
            int startY = record.get("startY").getAsInt();
            int endX = record.get("endX").getAsInt();
            int endY = record.get("endY").getAsInt();
            graphics2D.setColor(color);
            graphics2D.setStroke(stroke);

            switch (type) {
                case "line":
                    graphics2D.drawLine(startX, startY, endX, endY);
                    break;
                case "rectangle":
                    graphics2D.drawRect(Math.min(startX, endX), Math.min(startY,endY), Math.abs(startX - endX), Math.abs(startY - endY));
                    break;
                case "oval":
                    int width = Math.abs(endX - startX);
                    int height = Math.abs(endY - startY);
                    int ovalStartX = Math.min(startX, endX);
                    int ovalStartY = Math.min(startY, endY);
                    graphics2D.drawOval(ovalStartX, ovalStartY, width, height);
                    break;
                case "circle":
                    int radius = (int) Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
                    graphics2D.drawOval(startX - radius, startY - radius, 2 * radius, 2 * radius);
                    break;
                case "text":
                    String text = record.get("text").getAsString();
                    Font font = new Font(null, Font.PLAIN, 20);
                    graphics2D.setFont(font);
                    graphics2D.drawString(text, endX, endY);
                    break;
                case "pen":
                    graphics2D.drawLine(startX, startY, endX, endY);
                    break;
                case "eraser":
                    graphics2D.setColor(Color.WHITE);
                    graphics2D.setStroke(new BasicStroke(10));
                    graphics2D.drawLine(startX, startY, endX, endY);
                    break;
            }
        }
    }

    public void broadcast(JsonObject drawRecord) {
    }
}
