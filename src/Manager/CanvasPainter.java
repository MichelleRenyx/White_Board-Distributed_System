package Manager;

import com.google.gson.JsonObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CanvasPainter extends JPanel {
    private ArrayList<JsonObject> recordList = new ArrayList<>();
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        draw(g2d, recordList);
    }
    public void setRecordList(ArrayList<JsonObject> recordList) {
        this.recordList = recordList;
        repaint();
    }
    public void removeAll() {
    }

    public void update() {
    }

    public void repaint() {
    }
    public void draw(Graphics2D graphics2D, ArrayList<JsonObject> jsonObjectArrayList){}
}
