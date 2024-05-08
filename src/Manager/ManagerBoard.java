package Manager;

import javax.swing.*;
import java.awt.*;

public class ManagerBoard {
    static Listener createBoardListener;
    public JFrame frame;
    private String file = ".save/whiteboard";
    static ManagerBoard createMyBoard;
    static CanvasPainter canvas;
    public JList list;
    public static int circleX, circleY;
}
