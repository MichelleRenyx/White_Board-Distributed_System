package Guest;

import com.google.gson.Gson;
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
    private boolean loginResponseReceived = false;
    private String loginResponseStatus = null;
    private String loginResponseMessage = null;
    private String otherName = null;
    private String otherChat = null;

    // Synchronized methods for handling login response
    public synchronized void setLoginResponse(String status, String message) {
        this.loginResponseStatus = status;
        this.loginResponseMessage = message;
        this.loginResponseReceived = true;
        notifyAll();  // Notify waiting threads that login response has been received
    }

    public boolean isLoginResponseReceived() {
        return loginResponseReceived;
    }

    public String getLoginResponseMessage() {
        return loginResponseMessage;
    }

    public String getLoginResponseStatus() {
        return loginResponseStatus;
    }

    public String getOtherName() {
        return otherName;
    }
    public String getOtherChat() {
        return otherChat;
    }
    public void setOtherChat(String otherName, String otherChat) {
        this.otherName = otherName;
        this.otherChat = otherChat;
    }
    public ConnectionGuest(Socket socket) {


        try {
            this.socket = socket;
            dataInputStream = new DataInputStream(this.socket.getInputStream());
            System.out.println("flag" + dataInputStream);
            dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }


    public void launch() {
        try {

            label:
            while (true) {
                System.out.println("waiting for data");
                String line = dataInputStream.readUTF();  // 读取服务器发送的数据
                System.out.println(line);
                if (line == null) break;
                JsonParser parser = new JsonParser();
                JsonObject receivedJson = parser.parse(line).getAsJsonObject();  // 解析接收到的 JSON 字符串
                String command = receivedJson.get("command").getAsString();
                System.out.println(command);// 获取命令类型


                switch (command) {
                    case "draw":
                        // 更新画布
                        GuestBoard.createBoardListener.update(receivedJson);
                        GuestBoard.canvas.repaint();
                        break;
                    case "chat":
                        // 更新聊天区域
                        String message = receivedJson.get("message").getAsString();
                        JoinBoard.createMyBoard.chatTextArea.append(receivedJson.get("username").getAsString() + ": " + message + "\n");
                        break;
                    case "begin":
                        // 初始化画布
                        JsonArray rs = receivedJson.get("records").getAsJsonArray();
                        for (int i = 0; i < rs.size(); i++) {
                            GuestBoard.createBoardListener.update(rs.get(i).getAsJsonObject());
                        }
                        GuestBoard.canvas.repaint();
                        break;
                    case "usersList":
                        // 更新用户列表
                        if(JoinBoard.createMyBoard != null){
                            JsonArray userList = receivedJson.get("usernames").getAsJsonArray();
                            JoinBoard.createMyBoard.setListData(userList);
                        }
                        break;
//                    case "delete":
//                        // 用户被删除的通知
//                        String deletedUser = receivedJson.get("username").getAsString();
//                        JOptionPane.showMessageDialog(JoinBoard.createMyBoard.guestBoard, deletedUser + " has been deleted.");
//                        JsonArray userList = receivedJson.get("usernames").getAsJsonArray();
//                        JoinBoard.createMyBoard.setListData(userList);
//                        break;
                    case "feedback":
                        // 反馈处理
                        String response = receivedJson.get("response").getAsString();
                        String m = receivedJson.get("message").getAsString();
                        setLoginResponse(response, m);
                        break;
                    case "clientout":
                        // 用户退出处理

                        String userLeft = receivedJson.get("username").getAsString();
                        JOptionPane.showMessageDialog(JoinBoard.createMyBoard.guestBoard, userLeft + " leaves");
                        break label;
                    case "clear":
                        // 清空画布并重置记录
                        GuestBoard.canvas.removeAll();
                        GuestBoard.canvas.updateUI();
                        GuestBoard.createBoardListener.clearRecords();
                        break;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(JoinBoard.createMyBoard.guestBoard, "Disconnected from server.");
            System.out.println("Connection lost: " + e.getMessage());
        }
    }


}
