package com.p16729438.ChatMessanger.Server.Thread;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import com.p16729438.ChatMessanger.Server.Server;
import com.p16729438.ChatMessanger.Util.ChatMessangerTimeStamp;

public class ServerHostThread extends Thread {
    private Server server;
    private boolean ready;
    private ServerSocket serverSocket;
    private int port;
    private ArrayList<Socket> socketList = new ArrayList<Socket>();
    private ServerCheckThread checkThread;
    private HashMap<Socket, ServerInputThread> inputThreads = new HashMap<Socket, ServerInputThread>();
    private HashMap<Socket, ServerOutputThread> outputThreads = new HashMap<Socket, ServerOutputThread>();
    private HashMap<Socket, String> nicknameMap = new HashMap<Socket, String>();
    // private HashMap<String, String> nicknamePasswordMap = new HashMap<String, String>();
    private ArrayList<String> chatRecord = new ArrayList<String>();

    public ServerHostThread(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        try {
            server.getChatMessanger().output(ChatMessangerTimeStamp.getTimeStamp() + "(IP 주소: " + InetAddress.getLocalHost().getHostAddress() + ":" + port + "): 서버 여는중");
            serverSocket = new ServerSocket(port);
            server.getChatMessanger().output(ChatMessangerTimeStamp.getTimeStamp() + "(IP 주소: " + InetAddress.getLocalHost().getHostAddress() + ":" + port + "): 서버 열림");
            server.getChatMessanger().output(ChatMessangerTimeStamp.getTimeStamp() + "연결 대기중...");
            checkThread = new ServerCheckThread(this);
            checkThread.start();
            ready = true;
            server.getChatMessanger().setReadyGUI(true);
            while (!isInterrupted()) {
                Socket socket = serverSocket.accept();
                socketList.add(socket);
                ServerInputThread inputThread = new ServerInputThread(this, socket);
                ServerOutputThread outputThread = new ServerOutputThread(this, socket);
                inputThreads.put(socket, inputThread);
                outputThreads.put(socket, outputThread);
                server.getChatMessanger().output(ChatMessangerTimeStamp.getTimeStamp() + "(IP 주소: " + socket.getInetAddress().getHostAddress() + "): 연결됨");
                inputThread.start();
                outputThread.start();
                checkThread.addCheckList(socket);
            }
        } catch (BindException e) {
            try {
                server.getChatMessanger().output(ChatMessangerTimeStamp.getTimeStamp() + "(IP 주소: " + InetAddress.getLocalHost().getHostAddress() + ":" + port + "): 이미 사용중");
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(Socket socket) {
        if (getNickname(socket) != null) {
            server.getChatMessanger().output(ChatMessangerTimeStamp.getTimeStamp() + getNickname(socket) + "(IP 주소: " + socket.getInetAddress().getHostAddress() + "): 연결 종료됨");
            sendDisconnectInfo(getNickname(socket), socket);
            removeIOThread(socket);
            removeNickname(socket);
            socketList.remove(socket);
        } else {
            server.getChatMessanger().output(ChatMessangerTimeStamp.getTimeStamp() + "null(IP 주소: " + socket.getInetAddress().getHostAddress() + "): 연결 종료됨");
            removeIOThread(socket);
            socketList.remove(socket);
        }
    }

    public void sendChat(String str) {
        String chat = "chat;" + ChatMessangerTimeStamp.getTimeStamp() + ";" + str;
        String chatRecord = chat.split(";", -1)[1] + chat.split(";", -1)[2] + ": ";
        for (int i = 3; i < chat.split(";", -1).length; i++) {
            if (i == 3) {
                chatRecord += chat.split(";", -1)[i];
            } else {
                chatRecord += ";" + chat.split(";", -1)[i];
            }
        }
        addChatRecord(chatRecord);
        server.getChatMessanger().output(chatRecord);
        for (Socket socket : outputThreads.keySet()) {
            if (nicknameMap.containsKey(socket)) {
                outputThreads.get(socket).addMessageQueue(chat);
            }
        }
    }

    public void sendNickname(String str, Socket socket) {
        outputThreads.get(socket).addMessageQueue("nickname;" + ChatMessangerTimeStamp.getTimeStamp() + ";" + str);
    }

    public void sendConnectInfo(String str, Socket client) {
        String connectInfo = "connectinfo;" + ChatMessangerTimeStamp.getTimeStamp() + ";" + str;
        addChatRecord(connectInfo.split(";", -1)[1] + str + ": 연결됨");
        for (Socket socket : outputThreads.keySet()) {
            if (nicknameMap.containsKey(socket)) {
                if (client != socket) {
                    outputThreads.get(socket).addMessageQueue(connectInfo);
                }
            }
        }
    }

    public void sendDisconnectInfo(String str, Socket client) {
        String disconnectInfo = "disconnectinfo;" + ChatMessangerTimeStamp.getTimeStamp() + ";" + str;
        addChatRecord(disconnectInfo.split(";", -1)[1] + str + ": 연결 종료됨");
        for (Socket socket : outputThreads.keySet()) {
            if (nicknameMap.containsKey(socket)) {
                if (client != socket) {
                    outputThreads.get(socket).addMessageQueue(disconnectInfo);
                }
            }
        }
    }

    public void sendClientListInfo(Socket socket) {
        String clientList = "";
        for (String str : nicknameMap.values()) {
            if (clientList.equalsIgnoreCase("")) {
                clientList = str;
            } else {
                clientList += ";" + str;
            }
        }
        outputThreads.get(socket).addMessageQueue("clientlistinfo;" + ChatMessangerTimeStamp.getTimeStamp() + ";" + clientList);
    }

    public void sendChatRecord(Socket socket) {
        for (String str : chatRecord) {
            outputThreads.get(socket).addMessageQueue("chatrecord;" + ChatMessangerTimeStamp.getTimeStamp() + ";" + str);
        }
    }

    public void addChatRecord(String str) {
        if (chatRecord.size() == 1024) {
            chatRecord.remove(0);
        }
        chatRecord.add(str);
    }

    public String getNickname(Socket socket) {
        return nicknameMap.get(socket);
    }

    public void setNickname(Socket socket, String nickname) {
        nicknameMap.put(socket, nickname);
        if (server.getChatMessanger().usingGUI()) {
            server.getChatMessanger().getChatGUI().getClientListScrollPane().getClientList().addClient(nickname);
        }
        server.getChatMessanger().output(ChatMessangerTimeStamp.getTimeStamp() + "(IP 주소: " + socket.getInetAddress().getHostAddress() + "): 닉네임 설정: " + nickname);
        sendClientListInfo(socket);
        sendChatRecord(socket);
        sendConnectInfo(nickname, socket);
    }

    private void removeNickname(Socket socket) {
        nicknameMap.remove(socket);
    }

    public boolean existNickname(String nickname) {
        return nicknameMap.values().contains(nickname);
    }

    public boolean hasNickname(Socket socket) {
        return nicknameMap.keySet().contains(socket);
    }
    // public void setPassword(String nickname, String password) {
    // nicknamePasswordMap.put(nickname, password);
    // }

    private void removeIOThread(Socket socket) {
        inputThreads.get(socket).interrupt();
        outputThreads.get(socket).interrupt();
        inputThreads.remove(socket);
        outputThreads.remove(socket);
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Server getServer() {
        return server;
    }

    public ServerCheckThread getCheckThread() {
        return checkThread;
    }

    public ArrayList<Socket> getSocketList() {
        return socketList;
    }

    public boolean isReady() {
        return ready;
    }
}