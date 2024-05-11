package Guest;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectionGuest extends Thread{
    public Socket socket;

    public DataInputStream dataInputStream = null;
    public static DataOutputStream dataOutputStream = null;
    String s;
    String getS(){
        return s;
    }
    public ConnectionGuest(Socket socket) {
        s = "wait";

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
                        GuestBoard.Listener.update(receivedJson);
                        Guest.canvasPainter.repaint();
                        break;
                    case "chat":
                        // 更新聊天区域
                        String message = receivedJson.get("message").getAsString();
                        JoinBoard.joinBoard.append(message + "\n");
                        break;
                    case "usersList":
                        // 更新用户列表
                        JsonArray userList = receivedJson.getAsJsonArray("users");
                        Join.gui.setListData(userList);
                        break;
                    case "delete":
                        // 用户被删除的通知
                        String deletedUser = receivedJson.get("username").getAsString();
                        JOptionPane.showMessageDialog(Join.gui.frame, deletedUser + " has been kicked out by the manager.");
                        Join.gui.setListData(userList);  // 假设我们已更新用户列表
                        break;
                    case "kick":
                        // 被踢出通知
                        JOptionPane.showMessageDialog(Join.gui.frame, "You have been kicked out by the manager.");
                        break;
                    case "feedback":
                        // 反馈处理
                        String feedback = receivedJson.get("status").getAsString();
                        if ("no".equals(feedback)) {
                            JOptionPane.showMessageDialog(Join.gui.frame, "Username already taken.");
                        } else if ("yes".equals(feedback)) {
                            JOptionPane.showMessageDialog(Join.gui.frame, "Username successfully added.");
                        } else if ("rejected".equals(feedback)) {
                            JOptionPane.showMessageDialog(Join.gui.frame, "Request rejected.");
                        }
                        break;
                    case "clientout":
                        // 用户退出处理
                        String userLeft = receivedJson.get("username").getAsString();
                        JOptionPane.showMessageDialog(Join.gui.frame, userLeft + " leaves");
                        break;
                    case "new":
                        // 清空画布并重置记录
                        Guest.canvasPointer.removeAll();
                        Guest.canvasPointer.updateUI();
                        Guest.Listener.clearRecord();
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Connection lost: " + e.getMessage());
        }
    }


    public void resetS() {
        s = "wait";
    }
}
