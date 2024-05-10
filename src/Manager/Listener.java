package Manager;

import com.google.gson.JsonObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

public class Listener implements ActionListener, MouseListener, MouseMotionListener {
    JFrame frame;
    ArrayList<JsonObject> records = new ArrayList<>();
    JsonObject drawRecord = new JsonObject();
    Graphics2D graphics2D;
    int startX, startY, endX, endY;
    static Color color = Color.BLACK;
    Object type = "line";
    public Listener(){}
    public Listener(JFrame frame) {
        this.frame = frame;
    }


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


    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("color")) {
            color = JColorChooser.showDialog(frame, "Choose a color", color);
            JFrame colorFrame = new JFrame("Color");
            colorFrame.setSize(200, 200);
            colorFrame.setVisible(true);
            colorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            colorFrame.setLocationRelativeTo(null);
            Color currentColor = JColorChooser.showDialog(colorFrame, "Choose a color", color);
            if(currentColor != null) {
                color = currentColor;
            }
        } else {
            this.type = e.getActionCommand();
        }
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        startX = e.getX();
        startY = e.getY();
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        endX = e.getX();
        endY = e.getY();
        drawShape();
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  {@code MOUSE_DRAGGED} events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     * <p>
     * Due to platform-dependent Drag&amp;Drop implementations,
     * {@code MOUSE_DRAGGED} events may not be delivered during a native
     * Drag&amp;Drop operation.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        endX = e.getX();
        endY = e.getY();
        if(type.equals("pen")){
            graphics2D.setColor(color);
            graphics2D.setStroke(new BasicStroke(1));
            graphics2D.drawLine(startX, startY, endX, endY);
            recordDrawingDetails();
            startX = endX;
            startY = endY;
        } else if (type.equals("eraser")) {
            graphics2D.setColor(Color.WHITE);
            graphics2D.setStroke(new BasicStroke(3));
            graphics2D.drawLine(startX, startY, endX, endY);
            recordDrawingDetails();
            startX = endX;
            startY = endY;
        } else {
            return;
        }
        try{
            ConnectionManager.broadcast(drawRecord);
        } catch (Exception ex){
            System.out.println("Error in broadcast draw");
        }

    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e) {

    }

    private void drawShape() {
        graphics2D = (Graphics2D) frame.getGraphics();
        graphics2D.setColor(color);
        graphics2D.setStroke(new BasicStroke(1));
        switch (Objects.requireNonNull(type).toString()) {
            case "line":
                graphics2D.drawLine(startX, startY, endX, endY);
                break;
            case "rectangle":
                graphics2D.drawRect(Math.min(startX, endX), Math.min(startY,endY), Math.abs(startX - endX), Math.abs(startY - endY));
                break;
            case "oval":
                int width = Math.abs(endX - startX);
                int height = Math.abs(endY - startY);
                graphics2D.drawOval(startX, startY, width, height*2);
                break;
            case "circle":
                graphics2D.drawOval(startX, startY, endX, endY);
                int d = (int) Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
                graphics2D.drawOval(startX, startY, d, d);
                break;
            case "text":
                // Handle text drawing logic here
                String text = JOptionPane.showInputDialog(frame, "Enter your text");
                if(text != null) {
                    Font font = new Font(null, Font.PLAIN, 20);
                    graphics2D.setFont(font);
                    graphics2D.drawString(text, endX, endY);
                }
                break;
            default:
                return;
        }
        recordDrawingDetails();
        try {
            ConnectionManager.broadcast(drawRecord);
        } catch (Exception e) {
            System.out.println("Error in broadcast draw");
            e.printStackTrace();
        }
    }

    private void recordDrawingDetails() {
        drawRecord.addProperty("type", type.toString());
        drawRecord.addProperty("command", "draw");
        drawRecord.addProperty("startX", startX);
        drawRecord.addProperty("startY", startY);
        drawRecord.addProperty("endX", endX);
        drawRecord.addProperty("endY", endY);
        drawRecord.addProperty("color", color.toString());
        records.add(drawRecord);
    }

}
