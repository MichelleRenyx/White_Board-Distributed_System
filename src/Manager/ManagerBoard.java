/*
 * Created by JFormDesigner on Thu May 09 12:31:41 AEST 2024
 */

package Manager;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
    //static Graphics2D canvasGraphics;

    private int width = 800, height = 600;

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
            FrameOpenFile openFile = new FrameOpenFile(createMyBoard);
            openFile.frameOpenFile.setVisible(true);
        } else if (menu.getSelectedItem().equals("Exit")) {
            System.exit(1);
        }
    }
    public void saveFile(String file) {
        this.file = "./" + file;
        this.saveFile();
    }
    public void saveFile() {
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new FileOutputStream(file));
        } catch (IOException e1) {
            System.out.println("Error opening the file " + file + ".");
            return;
        }

        ArrayList<JsonObject> recordList = createBoardListener.getRecords();  // Fetch the list of JSON objects
        outputStream.println("[");  // Start of JSON array
        for (int i = 0; i < recordList.size(); i++) {
            outputStream.print(recordList.get(i).toString());  // Print each JSON record
            if (i < recordList.size() - 1) {
                outputStream.println(",");  // Add comma between objects, except after the last one
            } else {
                outputStream.println();
            }
        }
        outputStream.println("]");  // End of JSON array

        outputStream.flush();  // Make sure to flush the PrintWriter to ensure all data is written
        outputStream.close();  // Close the stream to release resources

        System.out.println(file + " saved");  // Log the save location
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

    private void line(ActionEvent e) {
        // TODO add your code here
    }
    private void recListener(ActionEvent e) {
        // TODO add your code here
    }

    private void circleListener(ActionEvent e) {
        // TODO add your code here
    }

    private void ovalListener(ActionEvent e) {
        // TODO add your code here
    }

    private void penListener(ActionEvent e) {
        // TODO add your code here
    }

    private void colorListener(ActionEvent e) {
        // TODO add your code here
    }

    private void eraserListener(ActionEvent e) {
        // TODO add your code here
    }

    private void sendListener(ActionEvent e) {
        // TODO add your code here
    }

    private void initComponents(String name){
        canvas = new CanvasPainter();

            // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
            // Generated using JFormDesigner Educational license - Yuxin Ren
            managerBoard = new JFrame();
            lineButton = new JButton();
            drawingBoard = new JPanel();
            menu = new JComboBox<>();
            recButton = new JButton();
            circleButton = new JButton();
            scrollPane1 = new JScrollPane();
            userList = new JList();
            ovalButton = new JButton();
            penButton = new JButton();
            textButton = new JButton();
            colorButton = new JButton();
            scrollPane2 = new JScrollPane();
            chatTextArea = new JTextArea();
            eraserButton = new JButton();
            clearButton = new JButton();
            chatInputTextField = new JTextField();
            sendButton = new JButton();

            //======== managerBoard ========
            {
                var managerBoardContentPane = managerBoard.getContentPane();
                managerBoardContentPane.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[75,fill]" +
                    "[335,fill]" +
                    "[60,fill]",
                    // rows
                    "[31]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]0" +
                    "[]" +
                    "[]0" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]"));

                //---- lineButton ----
                lineButton.setText("line");
                lineButton.addActionListener(e -> line(e));
                managerBoardContentPane.add(lineButton, "cell 0 0");

                //======== drawingBoard ========
                {
                    drawingBoard.setAutoscrolls(true);
                    drawingBoard.setBackground(Color.white);
                    drawingBoard.setLayout(null);

                    {
                        // compute preferred size
                        Dimension preferredSize = new Dimension();
                        for(int i = 0; i < drawingBoard.getComponentCount(); i++) {
                            Rectangle bounds = drawingBoard.getComponent(i).getBounds();
                            preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                            preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                        }
                        Insets insets = drawingBoard.getInsets();
                        preferredSize.width += insets.right;
                        preferredSize.height += insets.bottom;
                        drawingBoard.setMinimumSize(preferredSize);
                        drawingBoard.setPreferredSize(preferredSize);
                    }
                }
                managerBoardContentPane.add(drawingBoard, "cell 1 0 1 8,grow");

                //---- menu ----
                menu.setModel(new DefaultComboBoxModel<>(new String[] {
                    "Save",
                    "SaveAs",
                    "Open",
                    "Exit"
                }));
                menu.addActionListener(e -> menu(e));
                managerBoardContentPane.add(menu, "cell 2 0");

                //---- recButton ----
                recButton.setText("rectangle");
                recButton.addActionListener(e -> recListener(e));
                managerBoardContentPane.add(recButton, "cell 0 1");

                //---- circleButton ----
                circleButton.setText("circle");
                circleButton.addActionListener(e -> circleListener(e));
                managerBoardContentPane.add(circleButton, "cell 0 2");

                //======== scrollPane1 ========
                {
                    scrollPane1.setViewportView(userList);
                }
                managerBoardContentPane.add(scrollPane1, "cell 2 1 1 9,growy");

                //---- ovalButton ----
                ovalButton.setText("oval");
                ovalButton.addActionListener(e -> ovalListener(e));
                managerBoardContentPane.add(ovalButton, "cell 0 3");

                //---- penButton ----
                penButton.setText("pen");
                penButton.addActionListener(e -> penListener(e));
                managerBoardContentPane.add(penButton, "cell 0 5");

                //---- textButton ----
                textButton.setText("text");
                textButton.addActionListener(e -> textListener(e));
                managerBoardContentPane.add(textButton, "cell 0 7");

                //---- colorButton ----
                colorButton.setText("color");
                colorButton.addActionListener(e -> colorListener(e));
                managerBoardContentPane.add(colorButton, "cell 0 8");

                //======== scrollPane2 ========
                {

                    //---- chatTextArea ----
                    chatTextArea.setEditable(false);
                    scrollPane2.setViewportView(chatTextArea);
                }
                managerBoardContentPane.add(scrollPane2, "cell 1 8 1 2,growy");

                //---- eraserButton ----
                eraserButton.setText("eraser");
                eraserButton.addActionListener(e -> eraserListener(e));
                managerBoardContentPane.add(eraserButton, "cell 0 9");

                //---- clearButton ----
                clearButton.setText("CLEAR");
                clearButton.addActionListener(e -> clear(e));
                managerBoardContentPane.add(clearButton, "cell 0 10");
                managerBoardContentPane.add(chatInputTextField, "cell 1 10");

                //---- sendButton ----
                sendButton.setText("SEND");
                sendButton.addActionListener(e -> sendListener(e));
                managerBoardContentPane.add(sendButton, "cell 2 10");
                managerBoard.pack();
                managerBoard.setLocationRelativeTo(null);
                managerBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                managerBoard.setVisible(true);
            }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    private void textListener(ActionEvent e) {
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Yuxin Ren
    private JFrame managerBoard;
    private JButton lineButton;
    private JPanel drawingBoard;
    private JComboBox<String> menu;
    private JButton recButton;
    private JButton circleButton;
    private JScrollPane scrollPane1;
    private JList userList;
    private JButton ovalButton;
    private JButton penButton;
    private JButton textButton;
    private JButton colorButton;
    private JScrollPane scrollPane2;
    private JTextArea chatTextArea;
    private JButton eraserButton;
    private JButton clearButton;
    private JTextField chatInputTextField;
    private JButton sendButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on


    public void setFrame(ManagerBoard createMyBoard) {
        this.createMyBoard = createMyBoard;
    }

    public void saveImage(String filename, String type) {
        BufferedImage targetImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = targetImg.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);

        //canvas.draw(g, createBoardListener.getRecords());
        try {
            // 根据用户选择的格式保存图像
            if (type.equals("jpg")) {
                ImageIO.write(targetImg, "JPEG", new File("./" + filename + ".jpg"));
            } else if (type.equals("png")) {
                ImageIO.write(targetImg, "PNG", new File("./" + filename + ".png"));
            } else {
                System.out.println("Not Support File Type：" + type);
                return;
            }
            System.out.println("Image Saved As：" + filename + "." + type);
        } catch (IOException e1) {
            System.out.println("Error in ManagerBoard saveImage：" + e1.getMessage());
        }
    }
    public void openFile(String file) {
        file = "./" + file;
        JsonParser parser = new JsonParser();
        try (FileReader reader = new FileReader(file)) {
            JsonElement elem = parser.parse(reader);
            JsonArray jsonArray = elem.getAsJsonArray();

            createBoardListener.clearRecords();
            for (JsonElement je : jsonArray) {
                JsonObject jo = je.getAsJsonObject();
                createBoardListener.update(jo);
            }

            JsonObject newMessage = new JsonObject();
            newMessage.addProperty("command", "clear");
            ConnectionManager.broadcast(newMessage);

            ArrayList<JsonObject> rl = createBoardListener.getRecords();
            ConnectionManager.broadcastBatch(rl);  // Broadcast all records to all clients

            canvas.repaint();
        } catch (FileNotFoundException e) {
            System.out.println(file + " not found");
        } catch (IOException e) {
            System.out.println("Error in ManagerBoard openFile readFile: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error in ManagerBoard processFile readFile: " + e.getMessage());
        }
    }
}
