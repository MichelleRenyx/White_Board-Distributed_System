package Guest;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectionGuest{
    public Socket socket;

    public DataInputStream dataInputStream = null;
    public DataOutputStream dataOutputStream = null;

    public ConnectionGuest(Socket socket) {


        try {
            this.socket = socket;
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }

    public void launch() {
        try {
            while (true) {
                String line = dataInputStream.readUTF();  // 读取服务器发送的数据
                if (line == null) break;
                JsonParser parser = new JsonParser();
                JsonObject receivedJson = parser.parse(line).getAsJsonObject();  // 解析接收到的 JSON 字符串
                String command = receivedJson.get("command").getAsString();  // 获取命令类型
                switch (command) {
                    case "draw":
                        // 更新画布
                        GuestBoard.createBoardListener.update(receivedJson);
                        GuestBoard.canvas.repaint();
                        break;
                    case "chat":
                        // 更新聊天区域
                        String message = receivedJson.get("message").getAsString();
                        JoinBoard.createMyBoard.chatTextArea.append(message + "\n");
                        break;
                    case "usersList":
                        // 更新用户列表
                        if(JoinBoard.createMyBoard != null){
                            JsonArray userList = receivedJson.get("usernames").getAsJsonArray();
                            JoinBoard.createMyBoard.setListData(userList);
                        }
                        break;
                    case "delete":
                        // 用户被删除的通知
                        String deletedUser = receivedJson.get("username").getAsString();
                        JOptionPane.showMessageDialog(JoinBoard.createMyBoard.guestBoard, deletedUser + " has been deleted.");
                        JsonArray userList = receivedJson.get("usernames").getAsJsonArray();
                        JoinBoard.createMyBoard.setListData(userList);
                        break;
                    case "feedback":
                        // 反馈处理
                        String feedback = receivedJson.get("status").getAsString();
                        if ("no".equals(feedback)) {
                            JOptionPane.showMessageDialog(JoinBoard.createMyBoard.guestBoard, "Username already taken.");
                        } else if ("yes".equals(feedback)) {
                            JOptionPane.showMessageDialog(JoinBoard.createMyBoard.guestBoard, "Username successfully added.");
                        } else if ("rejected".equals(feedback)) {
                            JOptionPane.showMessageDialog(JoinBoard.createMyBoard.guestBoard, "Request rejected.");
                        }
                        break;
                    case "clientout":
                        // 用户退出处理
                        String userLeft = receivedJson.get("username").getAsString();
                        JOptionPane.showMessageDialog(JoinBoard.createMyBoard.guestBoard, userLeft + " leaves");
                        break;
                    case "new":
                        // 清空画布并重置记录
                        GuestBoard.canvas.removeAll();
                        GuestBoard.canvas.updateUI();
                        GuestBoard.createBoardListener.clearRecords();
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Connection lost: " + e.getMessage());
        }
    }


}
