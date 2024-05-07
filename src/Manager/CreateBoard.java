package Manager;

import java.awt.*;

public class CreateBoard {
    static String address;
    static int port;
    static String Username;
    public static Manager createMyBoard;

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
                new CreateBoard();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Server.launchServer(address, port, Username);
    }
    public CreateBoard() {
        initialize();
    }

    private void initialize() {
    }
}
