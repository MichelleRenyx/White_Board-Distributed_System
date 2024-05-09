/*
 * Created by JFormDesigner on Thu May 09 12:31:41 AEST 2024
 */

package Manager;

import java.awt.event.*;
import java.io.StringWriter;
import javax.swing.*;

import com.google.gson.JsonObject;
import net.miginfocom.swing.*;

/**
 * @author yuxinr
 */
public class ManagerBoard {
    public static StringWriter chatArea;
    static Listener createBoardListener;
    public JFrame frame;
    private String file = ".save/whiteboard";
    static ManagerBoard createMyBoard;
    static CanvasPainter canvas;
    public static int circleX, circleY;
    ImageIcon line = new ImageIcon(ManagerBoard.class.getResource("line.png"));
    ImageIcon circle = new ImageIcon(ManagerBoard.class.getResource("circle.png"));
    ImageIcon rectangle = new ImageIcon(ManagerBoard.class.getResource("rectangle.png"));
    ImageIcon text = new ImageIcon(ManagerBoard.class.getResource("text.png"));
    ImageIcon eraser = new ImageIcon(ManagerBoard.class.getResource("eraser.png"));
    ImageIcon color = new ImageIcon(ManagerBoard.class.getResource("color.png"));
    ImageIcon oval = new ImageIcon(ManagerBoard.class.getResource("oval.png"));
    ImageIcon pen = new ImageIcon(ManagerBoard.class.getResource("pen.png"));
    ImageIcon triangle = new ImageIcon(ManagerBoard.class.getResource("triangle.png"));
    ImageIcon icons[] = {line, circle, rectangle, text, eraser, oval, pen, triangle};

    public ManagerBoard(String managerName) {
        initComponents(managerName);
    }

    public int showRequest(String guestName) {
        return JOptionPane.showConfirmDialog(null, guestName + "want to join your white board", "Request", JOptionPane.INFORMATION_MESSAGE);
    }

    private void menu(ActionEvent e) {
        // TODO add your code here
        if (menu.getSelectedItem().equals("Save")) {
            saveFile();
        } else if (menu.getSelectedItem().equals("SaveAs")) {
            FrameSaveFile saveAs = new FrameSaveFile(createMyBoard);
            saveAs.frameSaveAs.setVisible(true);
        } else if (menu.getSelectedItem().equals("Open")) {
            FrameOpenFile saveAs = new FrameOpenFile(createMyBoard);
            saveAs.frameOpen.setVisible(true);
        } else if (menu.getSelectedItem().equals("Exit")) {
            System.exit(1);
        }
    }

    private void saveFile() {
    }

    private void clear(ActionEvent e) {
        // TODO add your code here
        canvas.removeAll();
        canvas.update();
        createBoardListener.clearRecords();

        JsonObject clearCommand = new JsonObject();
        clearCommand.addProperty("command", "clear");
        try {
            ConnectionManager.broadcast(clearCommand);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error in ManagerBoard clearMethod");
        }
    }

    private void initComponents(String name){
            // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
            // Generated using JFormDesigner Educational license - Yuxin Ren
            managerBoard = new JFrame();
            menu = new JComboBox<>();
            clearButton = new JButton();
            scrollPane1 = new JScrollPane();
            userList = new JList();
            canvas = new CanvasPainter();

        //======== managerBoard ========
        {
            var managerBoardContentPane = managerBoard.getContentPane();
            managerBoardContentPane.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]" +
                "[]"));

            //---- menu ----
            menu.setModel(new DefaultComboBoxModel<>(new String[] {
                "Save",
                "SaveAs",
                "Open",
                "Exit"
            }));
            menu.addActionListener(e -> menu(e));
            managerBoardContentPane.add(menu, "cell 13 0");

            //---- clearButton ----
            clearButton.setText("CLEAR");
            clearButton.addActionListener(e -> clear(e));
            managerBoardContentPane.add(clearButton, "cell 13 1");

            //======== scrollPane1 ========
            {
                scrollPane1.setViewportView(userList);
            }
            managerBoardContentPane.add(scrollPane1, "cell 13 3");
            managerBoard.pack();
            managerBoard.setLocationRelativeTo(managerBoard.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Yuxin Ren
    private JFrame managerBoard;
    private JComboBox<String> menu;
    private JButton clearButton;
    private JScrollPane scrollPane1;
    private JList userList;

    public void setFrame(ManagerBoard createMyBoard) {
    }
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
