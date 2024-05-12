package Guest;




import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.Socket;

public class JoinBoard {
    static String address;
    static Socket socket;
    static ConnectionGuest connectionGuest;
    static int port;
    static String username;
    static GuestBoard createMyBoard;

    public static void main(String[] args) {
        if (args.length >= 3) {
            try {
                address = args[0];
                port = Integer.parseInt(args[1]);
                username = args[2];
            } catch (Exception e) {
                System.out.println("Input Error: Please input valid address, port number and user name.");
                System.exit(1);
            }
        } else {
            System.out.println("Guest launch by default.");
            address = "localhost";
            port = 6666;
            username = "Guest";
        }

        try {
            socket = new Socket(address, port);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection error");
            System.exit(1);
        }
        connectionGuest = new ConnectionGuest(socket);
        EventQueue.invokeLater(() -> {
            try {
                new JoinBoard();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        connectionGuest.launch();

//        joinBoard = new Guest(address, port, Username);
    }
    public JoinBoard() {
        initComponents();
    }

    private void login(ActionEvent e) {
        // TODO add your code here
        String name = textField.getText();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(myWhiteBoard, "Please enter a username.");
            return;
        }
        JsonObject requestJson = new JsonObject();
        requestJson.addProperty("username", name);
        requestJson.addProperty("command", "request");

        try {
            String jsonString = new Gson().toJson(requestJson);
            connectionGuest.dataOutputStream.writeUTF(jsonString);
            connectionGuest.dataOutputStream.flush();

            // Wait for server response
            String responseJsonString = connectionGuest.dataInputStream.readUTF();
            JsonParser parser = new JsonParser();
            JsonObject responseJson = parser.parse(responseJsonString).getAsJsonObject();
            String responseStatus = responseJson.get("response").getAsString();
            String message = responseJson.get("message").getAsString();

            switch (responseStatus) {
                case "no":
                    JOptionPane.showMessageDialog(myWhiteBoard, message);
                    textField.setText("");
                    break;
                case "rejected":
                    JOptionPane.showMessageDialog(myWhiteBoard, message);
                    try {
                        connectionGuest.dataOutputStream.close();
                        socket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    System.exit(1);
                    break;
                case "yes":
                    myWhiteBoard.dispose();
                    createMyBoard = new GuestBoard(connectionGuest, name);
                    break;
                default:
                    JOptionPane.showMessageDialog(myWhiteBoard, "Unexpected response, please try again.");
                    break;
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(myWhiteBoard, "Failed to communicate with server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    public void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Yuxin Ren
        Listener listener = new Listener();

        myWhiteBoard = new JFrame();
        nameLabel = new JLabel();
        textField = new JTextField();
        loginButton = new JButton();

        //======== myWhiteBoard ========
        {
            var myWhiteBoardContentPane = myWhiteBoard.getContentPane();
            myWhiteBoardContentPane.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[fill]" +
                            "[fill]" +
                            "[fill]" +
                            "[156,fill]",
                    // rows
                    "[]" +
                            "[63]" +
                            "[44]" +
                            "[]"));

            //---- nameLabel ----
            nameLabel.setText("Username");
            myWhiteBoardContentPane.add(nameLabel, "cell 0 1 2 1");
            myWhiteBoardContentPane.add(textField, "cell 2 1 2 1");

            //---- loginButton ----
            loginButton.setText("LOGIN");
            loginButton.addActionListener(e -> login(e));
            myWhiteBoardContentPane.add(loginButton, "cell 2 3");
            myWhiteBoard.pack();
            myWhiteBoard.setLocationRelativeTo(null);
            myWhiteBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            myWhiteBoard.setVisible(true);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Yuxin Ren
    private JFrame myWhiteBoard;
    private JLabel nameLabel;
    private JTextField textField;
    private JButton loginButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
