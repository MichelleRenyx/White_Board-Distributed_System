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

    public Connection(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("User connected " + socket.getInetAddress().getHostAddress() + " " + socket.getPort());
            JsonParser parser = new JsonParser();

            String line;
            while ((line = dataInputStream.readUTF()) != null) {
                JsonObject receivedJson = parser.parse(line).getAsJsonObject();  // 使用 parse 方法
                String command = receivedJson.get("command").getAsString();
                System.out.println("Received command: " + command);
                switch (command) {
                    case "begin":
                        try {
                            // Share Board Information
                            ArrayList<JsonObject> records = LoginBoard.createMyBoard.createBoardListener.getRecords();

                            JsonObject drawRecords = new JsonObject();
                            drawRecords.add("records", new Gson().toJsonTree(records));
                            drawRecords.addProperty("command", "begin");
                            ConnectionManager.broadcast(drawRecords);

                            //Share User List
                            JsonObject userListJson = new JsonObject();
                            JsonArray usersArray = new JsonArray();
                            for (String userName : Server.users) {
                                usersArray.add(userName);
                            }
                            userListJson.add("usernames", usersArray);
                            userListJson.addProperty("command", "usersList");
                            ConnectionManager.addUsers(userListJson);
                            ConnectionManager.broadcast(userListJson);

                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Error in Connection begin");
                        }
                        break;
                    case "request":
                        JsonObject responseJson = new JsonObject();
                        String curName = receivedJson.get("username").getAsString();
                        if (Server.users.contains(curName)) {
                            responseJson.addProperty("command", "feedback");
                            responseJson.addProperty("response", "no");
                            responseJson.addProperty("message", "Username" + curName + "already taken.");
                        } else {
                            int ans = ConnectionManager.checkin(curName);
                            System.out.println("ans: " + ans);
                            if (ans == 0) {
                                Server.users.add(curName);
                                responseJson.addProperty("command", "feedback");

                                responseJson.addProperty("response", "yes");
                                responseJson.addProperty("message", "Username " + curName + "successfully added.");

                            } else {
                                responseJson.addProperty("command", "feedback");

                                responseJson.addProperty("response", "rejected");
                                responseJson.addProperty("message", "Request rejected.");

                            }
                        }
                        String responseJsonString = new Gson().toJson(responseJson);
                        System.out.println(responseJsonString);
                        dataOutputStream.writeUTF(responseJsonString);
                        System.out.println(dataOutputStream);
                        dataOutputStream.flush();
                        System.out.println("Response sent successfully");
                        break;

                    case "draw":
                        System.out.println("drawing");
                        ConnectionManager.broadcast(receivedJson);
                        ConnectionManager.canvasRepaint(receivedJson);
                        break;
                    case "over":
                        String leavingUser = receivedJson.get("username").getAsString();
                        socket.close();
                        this.name = leavingUser;
                        System.out.println("User" + this.name + "'s socket is closed");
                        break;
                    case "chat":
                        ConnectionManager.broadcast(receivedJson);
                        ManagerBoard.chatTextArea.append(receivedJson.get("username").getAsString() + ": " + receivedJson.get("message").getAsString() + "\n");
                        break;
                    case "clear":
                        ManagerBoard.canvas.removeAll();
                        ManagerBoard.canvas.update(ManagerBoard.canvas.getGraphics());
                        ManagerBoard.createBoardListener.clearRecords();
                        //ConnectionManager.broadcast(receivedJson);
                        break;
                }

            }
        } catch (SocketException e) {
            System.out.println("User" + this.name + " disconnected");
            clientLeave();

        } catch (IOException e) {
            System.out.println("User" + this.name + " disconnected");
        }

    }

    private void clientLeave() {
        // Remove the client and their information
        JsonObject message = new JsonObject();
        message.addProperty("command", "clientout");
        message.addProperty("username", this.name);
        message.addProperty("message", this.name + " has left the board.");
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
