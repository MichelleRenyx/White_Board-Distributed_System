package Manager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class LoginBoard {
    static String address;
    static int port;
    static String Username;
    private JFrame frame;
    private JButton loginButton;
    private JTextField textField;
    private JLabel nameLabel;
    private JLabel MyWhiteBoard;
    public static ManagerBoard createMyBoard;

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
            System.out.println("Input format Error");
            System.exit(1);
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
        initialize();
    }
    private void initialize() {
        // TODO: place custom component creation code here
        frame = new JFrame("MyWhiteBoard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(1000, 500, 450, 300);
        frame.getContentPane().setLayout(null);

        nameLabel = new JLabel("Username:");
        nameLabel.setBounds(200, 200, 100, 50);
        frame.getContentPane().add(nameLabel);

        textField = new JTextField();
        textField.setBounds(100, 100, 300, 50);
        frame.getContentPane().add(textField);

        loginButton = new JButton("LOGIN");
        loginButton.setBounds(200, 25, 100, 50);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("Login")) {
                    username = textField.getText();
                    frame.dispose();
                    try {
                        createMyBoard = new ManagerBoard(textField);
                        createMyBoard.setFrame(createMyBoard);

                    } catch (Exception e1) {
                        e1.printStackTrace();
                        System.out.println("loginButton Error");
                    }
                }
            }
        });

        frame.getContentPane().add(loginButton);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
    }
}