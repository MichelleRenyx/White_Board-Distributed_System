/*
 * Created by JFormDesigner on Thu May 09 11:54:58 AEST 2024
 */

package Manager;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import net.miginfocom.swing.*;

/**
 * @author yuxinr
 */
public class LoginBoard {
    static String address;
    static int port;
    static String Username;
    static String name;
    static ManagerBoard createMyBoard;

    public static void main(String[] args) {
        if (args.length == 3) {
            try {
                address = args[0];
                port = Integer.parseInt(args[1]);
                Username = args[2];
            } catch (Exception e) {
                System.out.println("Input Error: Please input valid address, port number and user name.");
                System.exit(1);
            }
        } else {
            address = "localhost";
            port = 6666;
            Username = "default";
            System.out.println("Default address: localhost, port: 6666, user name: default");
//            System.out.println("Input format Error");
//            System.exit(1);
        }
        EventQueue.invokeLater(() -> {
            try {
                new LoginBoard();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Server.launchServer(address, port, Username);
    }
    public LoginBoard() {
        initComponents();
    }

    private void login(ActionEvent e) {
        // TODO add your code here
        name = textField.getText();
        myWhiteBoard.dispose();
        try {
            createMyBoard = new ManagerBoard(name);
            createMyBoard.setFrame(createMyBoard);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Yuxin Ren
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
            myWhiteBoard.setLocationRelativeTo(myWhiteBoard.getOwner());
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
