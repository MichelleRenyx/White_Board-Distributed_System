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
    //JsonObject drawRecord = new JsonObject();
    CanvasPainter canvasPainter = new CanvasPainter();
    int startX, startY, endX, endY;
    static Color color = Color.BLACK;
    Object type = "line";
    public Listener(){}
    public Listener(JFrame frame, CanvasPainter canvasPainter) {
        this.frame = frame;
        this.canvasPainter = canvasPainter;
    }


    public void clearRecords() {
        records.clear();
        canvasPainter.updateRecords(records);
        canvasPainter.repaint();
    }

    public ArrayList<JsonObject> getRecords() {
        return records;
    }

    public void update(JsonObject drawRecord) {
        // Update the canvas with the new drawing
        System.out.println("Updating canvas "+ drawRecord);
        records.add(drawRecord);
    }


    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("color".equals(e.getActionCommand())) {
            Color curColor = JColorChooser.showDialog(frame, "Choose a color", color);
            if (curColor != null) {
                color = curColor;
            }
        } else {
            type = e.getActionCommand();
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
        JsonObject drawRecord = new JsonObject();
        drawRecord.addProperty("type", type.toString());
        drawRecord.addProperty("command", "draw");
        drawRecord.addProperty("startX", startX);
        drawRecord.addProperty("startY", startY);
        drawRecord.addProperty("endX", endX);
        drawRecord.addProperty("endY", endY);
        drawRecord.addProperty("color", color.getRGB());
        if ("text".equals(type)) {
            drawRecord.addProperty("text", JOptionPane.showInputDialog(frame, "Enter your text"));
            drawRecord.addProperty("fontSize", 20);
        }
        records.add(drawRecord);
        canvasPainter.updateRecords(records);
        canvasPainter.repaint();
        try {
            ConnectionManager.broadcast(drawRecord);
        } catch (Exception ex) {
            System.out.println("Error in Listener mouseReleased broadcast");
        }
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
        JsonObject drawRecord = new JsonObject();
        if(type.equals("pen")){
            drawRecord.addProperty("type", "pen");
            drawRecord.addProperty("color", color.getRGB());
            drawRecord.addProperty("command", "draw");
            drawRecord.addProperty("startX", startX);
            drawRecord.addProperty("startY", startY);
            drawRecord.addProperty("endX", endX);
            drawRecord.addProperty("endY", endY);
            startX = endX;
            startY = endY;
        } else if (type.equals("eraser")) {

            drawRecord.addProperty("type", "line");
            drawRecord.addProperty("color", Color.WHITE.getRGB());
            drawRecord.addProperty("command", "draw");
            drawRecord.addProperty("startX", startX);
            drawRecord.addProperty("startY", startY);
            drawRecord.addProperty("endX", endX);
            drawRecord.addProperty("endY", endY);
            startX = endX;
            startY = endY;
        } else {
            return;
        }
        records.add(drawRecord);
        canvasPainter.updateRecords(records);
        canvasPainter.repaint();
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

//    private void drawShape() {
//
//        graphics2D.setColor(color);
//        graphics2D.setStroke(new BasicStroke(1));
//        switch (Objects.requireNonNull(type).toString()) {
//            case "line":
//                graphics2D.drawLine(startX, startY, endX, endY);
//                break;
//            case "rectangle":
//                graphics2D.drawRect(Math.min(startX, endX), Math.min(startY,endY), Math.abs(startX - endX), Math.abs(startY - endY));
//                break;
//            case "oval":
//                graphics2D.drawOval(startX, startY, Math.abs(endX - startX), Math.abs(endY - startY));
//                break;
//            case "circle":
//                int d = (int) Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
//                graphics2D.drawOval(startX, startY, d*2, d*2);
//                break;
//            case "text":
//                // Handle text drawing logic here
//                String text = JOptionPane.showInputDialog(frame, "Enter your text");
//                if(text != null) {
//                    Font font = new Font(null, Font.PLAIN, 20);
//                    graphics2D.setFont(font);
//                    graphics2D.drawString(text, endX, endY);
//                }
//                break;
//            default:
//                return;
//        }
//        recordDrawingDetails();
//        try {
//            ConnectionManager.broadcast(drawRecord);
//        } catch (Exception e) {
//            System.out.println("Error in broadcast draw");
//            e.printStackTrace();
//        }
//    }
//
//    private void recordDrawingDetails() {
//        JsonObject drawRecord = new JsonObject();
//        drawRecord.addProperty("type", type.toString());
//        drawRecord.addProperty("command", "draw");
//        drawRecord.addProperty("startX", startX);
//        drawRecord.addProperty("startY", startY);
//        drawRecord.addProperty("endX", endX);
//        drawRecord.addProperty("endY", endY);
//        drawRecord.addProperty("color", color.getRGB());
//        if ("text".equals(type)) {
//            drawRecord.addProperty("text", JOptionPane.showInputDialog(frame, "Enter your text"));
//            drawRecord.addProperty("fontSize", 20);
//        }
//        records.add(drawRecord);
//    }

//    public void setGraphic(Graphics g) {
//        this.graphics2D = (Graphics2D) g;
//    }
}
