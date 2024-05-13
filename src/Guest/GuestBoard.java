package Guest;



import Manager.LoginBoard;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.StringWriter;

public class GuestBoard {
    public JFrame guestBoard;
    static Listener createBoardListener;

    static GuestBoard createMyBoard;
    static ConnectionGuest connectionGuest;
    static Painter canvas;
    //static Graphics2D canvasGraphics;

    private int width = 800, height = 600;
    public GuestBoard(ConnectionGuest connectionGuest, String guestName) {
        this.connectionGuest = connectionGuest;
        initComponents(guestName);
    }
    public void setListData(JsonArray users){
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (int i = 0; i < users.size(); i++) {
            listModel.addElement(users.get(i).getAsString());
        }
        userList.setModel(listModel);
    }
    private void initComponents(String name){


        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Yuxin Ren
        guestBoard = new JFrame("White Board - GUEST - " + name);
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
        panelButton = new JPanel();
        panelRight = new JPanel();
        panelChat = new JPanel();

        canvas = new Painter();
        createBoardListener = new Listener(guestBoard, canvas);
        canvas.setBackground(Color.white);
        canvas.addMouseListener(createBoardListener);
        canvas.addMouseMotionListener(createBoardListener);
        //createBoardListener.setGraphic(canvas.getGraphics());
        //======== managerBoard ========
        {
            var guestBoardContentPane = guestBoard.getContentPane();
            guestBoardContentPane.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[68,fill]" +
                            "[253,fill]" +
                            "[73,fill]",
                    // rows
                    "[]0" +
                            "[]0" +
                            "[263]" +
                            "[116]0" +
                            "[]0" +
                            "[]0" +
                            "[0]"));
            //======== panelButton ========
            {
                panelButton.setLayout(new MigLayout(
                        "hidemode 3",
                        // columns
                        "[fill]" +
                                "[fill]",
                        // rows
                        "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[]" +
                                "[144]0" +
                                "[]0" +
                                "[]"));

                //---- lineButton ----
                lineButton.setText("line");
                lineButton.setActionCommand("line");
                lineButton.addActionListener(createBoardListener);
                panelButton.add(lineButton, "cell 0 0");

                //---- recButton ----
                recButton.setText("rectangle");
                recButton.setActionCommand("rectangle");
                recButton.addActionListener(createBoardListener);
                panelButton.add(recButton, "cell 0 1");

                //---- circleButton ----
                circleButton.setText("circle");
                circleButton.setActionCommand("circle");
                circleButton.addActionListener(createBoardListener);
                panelButton.add(circleButton, "cell 0 2");

                //---- ovalButton ----
                ovalButton.setText("oval");
                ovalButton.setActionCommand("oval");
                ovalButton.addActionListener(createBoardListener);
                panelButton.add(ovalButton, "cell 0 3");

                //---- penButton ----
                penButton.setText("pen");
                penButton.setActionCommand("pen");
                penButton.addActionListener(createBoardListener);
                panelButton.add(penButton, "cell 0 5");

                //---- textButton ----
                textButton.setText("text");
                textButton.setActionCommand("text");
                textButton.addActionListener(createBoardListener);
                panelButton.add(textButton, "cell 0 7");

                //---- colorButton ----
                colorButton.setText("color");
                colorButton.setActionCommand("color");
                colorButton.addActionListener(createBoardListener);
                panelButton.add(colorButton, "cell 0 8");

                //---- eraserButton ----
                eraserButton.setText("eraser");
                eraserButton.setActionCommand("eraser");
                eraserButton.addActionListener(createBoardListener);
                panelButton.add(eraserButton, "cell 0 9");

                //---- clearButton ----
                clearButton.setText("CLEAR");
                clearButton.addActionListener(e -> clear(e));
                panelButton.add(clearButton, "cell 0 10");

            }
            guestBoardContentPane.add(panelButton, "cell 0 2 1 2");

            //======== drawingBoard ========
            {
                drawingBoard.setBackground(Color.white);
                drawingBoard.setLayout(new BorderLayout());
                drawingBoard.add(canvas, BorderLayout.CENTER);

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
            guestBoardContentPane.add(drawingBoard, "cell 1 2,grow");


            //======== panelRight ========
            {
                panelRight.setLayout(new MigLayout(
                        "hidemode 3",
                        // columns
                        "[fill]" +
                                "[fill]" +
                                "[fill]",
                        // rows
                        "[]" +
                                "[]" +
                                "[]" +
                                "[]"));

                //======== scrollPane1 ========
                {
                    scrollPane1.setViewportView(userList);
                }
                panelRight.add(scrollPane1, "cell 0 1");
            }
            guestBoardContentPane.add(panelRight, "cell 2 2");






            //======== panelChat ========
            {
                panelChat.setLayout(null);

                //======== scrollPane2 ========
                {
                    //---- chatTextArea ----
                    chatTextArea.setEditable(false);
                    scrollPane2.setViewportView(chatTextArea);
                }
                panelChat.add(scrollPane2);
                scrollPane2.setBounds(120, 0, 345, 45);
                panelChat.add(chatInputTextField);
                chatInputTextField.setBounds(120, 52, 220, chatInputTextField.getPreferredSize().height);

                //---- sendButton ----
                sendButton.setText("SEND");
                sendButton.addActionListener(e -> sendListener(e));
                panelChat.add(sendButton);
                sendButton.setBounds(new Rectangle(new Point(347, 52), sendButton.getPreferredSize()));

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panelChat.getComponentCount(); i++) {
                        Rectangle bounds = panelChat.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panelChat.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panelChat.setMinimumSize(preferredSize);
                    panelChat.setPreferredSize(preferredSize);
                }
            }
            guestBoardContentPane.add(panelChat, "cell 0 3 3 1,grow");


            guestBoard.pack();
            guestBoard.setLocationRelativeTo(null);
            guestBoard.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            guestBoard.setVisible(true);
            guestBoard.addWindowListener(new WindowAdapter() {
                /**
                 * Invoked when a window is in the process of being closed.
                 * The close operation can be overridden at this point.
                 *
                 * @param e
                 */
                @Override
                public void windowClosing(WindowEvent e) {
                    exitApplication();
                }
            });

        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
        JsonObject j = new JsonObject();
        j.addProperty("command", "begin");
        String jsonString = new Gson().toJson(j);
        try {
            connectionGuest.dataOutputStream.writeUTF(jsonString);
            connectionGuest.dataOutputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error in GuestBoard initialize old records");
        }
    }

    private void exitApplication() {
        try {
            JsonObject jsonOver = new JsonObject();
            jsonOver.addProperty("command", "over");
            jsonOver.addProperty("username", JoinBoard.name);
            String jsonString = new Gson().toJson(jsonOver);
            System.out.println(jsonString);
            connectionGuest.dataOutputStream.writeUTF(jsonString);
            connectionGuest.dataOutputStream.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connectionGuest.socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }

    private void sendListener(ActionEvent e) {
        String message = chatInputTextField.getText();
        JsonObject jsonMsg = new JsonObject();
        jsonMsg.addProperty("command", "chat");
        jsonMsg.addProperty("username", JoinBoard.name);
        jsonMsg.addProperty("message", message);
//        chatTextArea.append("\n" + JoinBoard.name + ": " + message);
        chatInputTextField.setText("");
        try {
            String jsonString = new Gson().toJson(jsonMsg);
            if(connectionGuest != null && connectionGuest.dataOutputStream != null){
                connectionGuest.dataOutputStream.writeUTF(jsonString);
                connectionGuest.dataOutputStream.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error in ManagerBoard sendListener");
        }
    }

    private void clear(ActionEvent e) {
        // TODO add your code here
        canvas.removeAll();
        canvas.update(canvas.getGraphics());
        createBoardListener.clearRecords();

        JsonObject clearCommand = new JsonObject();
        clearCommand.addProperty("command", "clear");
        String jsonString = new Gson().toJson(clearCommand);
        try {
            connectionGuest.dataOutputStream.writeUTF(jsonString);
            connectionGuest.dataOutputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error in ManagerBoard clearMethod");
        }
    }



    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Yuxin Ren
    private JButton lineButton;
    private JPanel drawingBoard;
    private JComboBox<String> menu;
    private JButton recButton;
    private JButton circleButton;
    private JScrollPane scrollPane1;
    protected JList<String> userList;
    private JButton ovalButton;
    private JButton penButton;
    private JButton textButton;
    private JButton colorButton;
    private JScrollPane scrollPane2;
    protected JTextArea chatTextArea;
    private JButton eraserButton;
    private JButton clearButton;
    private JTextField chatInputTextField;
    private JButton sendButton;
    private JPanel panelButton;
    private JPanel panelRight;
    private JPanel panelChat;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on


    public void setFrame(GuestBoard createMyBoard) {
        this.createMyBoard = createMyBoard;
    }
}
