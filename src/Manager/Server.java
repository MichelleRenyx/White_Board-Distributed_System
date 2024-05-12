package Manager;


import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static List<String> users = new ArrayList<>();
    public static List<Connection> connections = new ArrayList<>();
    private static int clientId = 0;
    public static void launchServer(String address, int port, String username) {
        Connection c1 = null;
        ServerSocket server = null;
        users.add(username);
        try {
            server = new ServerSocket(port);
            Socket client;
            while (true) {
                client = server.accept();
                clientId++;
                System.out.println("Client " + clientId + " request to connect");
                c1 = new Connection(client);
                connections.add(c1);

                c1.start();
            }
        } catch (Exception e) {
            System.out.println("connection error");
            System.exit(1);
        }
    }
}
