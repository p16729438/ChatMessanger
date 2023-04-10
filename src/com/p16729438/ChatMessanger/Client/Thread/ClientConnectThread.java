package com.p16729438.ChatMessanger.Client.Thread;

import java.io.IOException;
import java.net.Socket;

import com.p16729438.ChatMessanger.Client.Client;
import com.p16729438.ChatMessanger.Client.GUI.NicknameGUI.NicknameGUI;
import com.p16729438.ChatMessanger.Util.ChatMessangerData;

public class ClientConnectThread extends Thread {
    private Client client;
    private boolean ready;
    private Socket socket;
    private String address;
    private int port;
    private ClientCheckThread checkThread;
    private ClientInputThread inputThread;
    private ClientOutputThread outputThread;
    private String nickname;
    private boolean requestingNickname;
    private String requestNickname;

    public ClientConnectThread(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            client.getChatMessanger().output("(IP 주소: " + address + ":" + port + "): 연결 시도");
            socket = new Socket(address, port);
            ready = true;
            client.getChatMessanger().output("(IP 주소: " + address + ":" + port + "): 연결됨");
            inputThread = new ClientInputThread(this);
            outputThread = new ClientOutputThread(this);
            inputThread.start();
            outputThread.start();
            checkThread = new ClientCheckThread(this);
            checkThread.start();
            if (client.getChatMessanger().usingGUI()) {
                new NicknameGUI(client);
            } else {
                System.out.print("닉네임 입력: ");
            }
        } catch (IOException e) {
            client.getChatMessanger().output("(IP 주소:" + address + ":" + port + "): 연결 실패");
        }
    }

    public void disconnect() {
        checkThread.interrupt();
        inputThread.interrupt();
        outputThread.interrupt();
        checkThread = null;
        inputThread = null;
        outputThread = null;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void sendCheck() {
        outputThread.addMessageQueue("check;");
    }

    public void sendChat(String str) {
        outputThread.addMessageQueue("chat;" + str);
    }

    public void sendPassword(String password) {
        outputThread.addMessageQueue("password;" + nickname + ";" + password);
    }

    public void setNickname() {
        if (requestingNickname) {
            nickname = requestNickname;
            requestingNickname = false;
            requestNickname = null;
            if (client.getChatMessanger().usingGUI()) {
                client.getChatMessanger().getChatGUI().getClientListScrollPane().getClientList().setClient(nickname);
            }
            client.getChatMessanger().output("닉네임을 " + nickname + "(으)로 설정했습니다.");
            if (client.getChatMessanger().usingGUI()) {
                ChatMessangerData.setNicknameData(nickname);
            }
        }
        client.getChatMessanger().setReadyGUI(true);
    }

    public String getNickname() {
        return nickname;
    }

    public void clearRequestNickname() {
        requestingNickname = false;
        requestNickname = null;
        if (client.getChatMessanger().usingGUI()) {
            new NicknameGUI(client);
        } else {
            System.out.print("닉네임 입력: ");
        }
    }

    public void requestNickname(String nickname) {
        if (!requestingNickname) {
            requestingNickname = true;
            requestNickname = nickname;
            outputThread.addMessageQueue("nickname;" + nickname);
        }
    }

    public boolean isRequestingNickname() {
        return requestingNickname;
    }

    public Client getClient() {
        return client;
    }

    public Socket getSocket() {
        return socket;
    }

    public ClientInputThread getInputThread() {
        return inputThread;
    }

    public ClientOutputThread getOutputThread() {
        return outputThread;
    }

    public boolean isReady() {
        return ready;
    }
}