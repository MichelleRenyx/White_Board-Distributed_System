package Manager;

import com.google.gson.*;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Connection extends Thread {
    public Socket socket;
    public String name;
    public DataInputStream dataInputStream;
    public DataOutputStream dataOutputStream;
    private boolean removeUser;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            JsonParser parser = new JsonParser();

            String line;
            label:
            while ((line = dataInputStream.readUTF()) != null) {
                JsonObject receivedJson = parser.parse(line).getAsJsonObject();  // 使用 parse 方法
                String command = receivedJson.get("command").getAsString();

                switch (command) {
                    case "begin":
                        try {
                            ArrayList<JsonObject> records = LoginBoard.createMyBoard.createBoardListener.getRecords();

                            JsonObject drawRecords = new JsonObject();
                            drawRecords.add("records", new Gson().toJsonTree(records));
                            ConnectionManager.broadcast(drawRecords);


                            JsonObject userListJson = new JsonObject();
                            JsonArray usersArray = new JsonArray();
                            for (String userName : Server.users) {
                                usersArray.add(userName);
                            }
                            userListJson.add("usernames", usersArray);

                            ConnectionManager.addUsers(userListJson);

                            ConnectionManager.broadcast(userListJson);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Error in Connection begin");
                        }
                        break;
                    case "request":
                        String curName = receivedJson.get("username").getAsString();
                        if (Server.users.contains(curName)) {
                            JsonObject responseJson = new JsonObject();
                            responseJson.addProperty("response", "no");
                            responseJson.addProperty("message", "Username already taken.");
                            dataOutputStream.writeUTF(responseJson.toString());
                        } else {
                            int ans = ConnectionManager.checkin(curName);
                            if (ans == JOptionPane.YES_OPTION) {
                                if (!Server.users.contains(curName)) {
                                    Server.users.add(curName);
                                    JsonObject responseJson = new JsonObject();
                                    responseJson.addProperty("response", "yes");
                                    responseJson.addProperty("message", "Username successfully added.");
                                    dataOutputStream.writeUTF(responseJson.toString());
                                } else {
                                    JsonObject responseJson = new JsonObject();
                                    responseJson.addProperty("response", "no");
                                    responseJson.addProperty("message", "Failed to add username. Already exists.");
                                    dataOutputStream.writeUTF(responseJson.toString());
                                }
                            } else {
                                JsonObject responseJson = new JsonObject();
                                responseJson.addProperty("response", "rejected");
                                responseJson.addProperty("message", "Request rejected.");
                                dataOutputStream.writeUTF(responseJson.toString());
                            }
                        }
                        dataOutputStream.flush();
                        break;

                    case "draw":
                        ConnectionManager.broadcast(receivedJson);
                        ConnectionManager.canvasRepaint(receivedJson);
                        break;
                    case "over":
                        socket.close();
                        break label;
                    case "chat":
                        ConnectionManager.broadcast(receivedJson);
                        ManagerBoard.chatArea.append("\n" + receivedJson.get("username").getAsString() + ": " + receivedJson.get("message").getAsString());
                        break;
                    case "clear":
                        ManagerBoard.canvas.removeAll();
                        ManagerBoard.canvas.update();
                        ManagerBoard.createBoardListener.clearRecords();
                        break;
                }

            }
        } catch (SocketException e) {
            System.out.println("User" + this.name + " disconnected");
            if (!this.removeUser) {
                clientLeave();
            }
        } catch (IOException e) {
            System.out.println("User" + this.name + " disconnected");
        }

    }

    private void clientLeave() {
        // Remove the client and their information
        JsonObject message = new JsonObject();
        message.addProperty("command", "clientout");
        message.addProperty("username", name);
        message.addProperty("message", name + " has left the board.");
        try {
            ConnectionManager.broadcast(message);
        } catch (Exception e) {
            System.out.println("Error in Connection clientLeave broadcast method");
            e.printStackTrace();
        }
        try {
            ConnectionManager.clientOut(message);
        } catch (Exception e1) {
            System.out.println("Error in Connection clientLeave clientOut method)");
            e1.printStackTrace();
        }
    }

}
