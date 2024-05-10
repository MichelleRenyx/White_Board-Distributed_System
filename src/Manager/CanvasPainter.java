package Manager;

import com.google.gson.JsonObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CanvasPainter extends JPanel {
    private ArrayList<JsonObject> recordList = new ArrayList<>();
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        draw( (Graphics2D) g, this.recordList);
    }
    public void draw(Graphics2D graphics2D, ArrayList<JsonObject> jsonObjectArrayList){
        for (JsonObject record : recordList) {
            String type = record.get("type").getAsString();
            Color color = new Color(record.get("color").getAsInt());
            int startX = record.get("startX").getAsInt();
            int startY = record.get("startY").getAsInt();
            int endX = record.get("endX").getAsInt();
            int endY = record.get("endY").getAsInt();
            graphics2D.setColor(color);

            switch (type) {
                case "line":
                    graphics2D.drawLine(startX, startY, endX, endY);
                    break;
                case "rectangle":
                    graphics2D.drawRect(Math.min(startX, endX), Math.min(startY,endY), Math.abs(startX - endX), Math.abs(startY - endY));
                    break;
                case "oval":
                    graphics2D.drawOval(startX, startY, Math.abs(endX - startX), Math.abs(endY - startY));
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
            }
        }
    }
}
