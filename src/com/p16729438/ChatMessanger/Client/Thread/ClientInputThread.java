package com.p16729438.ChatMessanger.Client.Thread;

import java.io.DataInputStream;
import java.io.IOException;

public class ClientInputThread extends Thread {
    private ClientConnectThread connectThread;

    public ClientInputThread(ClientConnectThread connectThread) {
        this.connectThread = connectThread;
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(connectThread.getSocket().getInputStream());
            String str;
            while (!isInterrupted() && (str = dis.readUTF()) != null) {
                inputData(str);
            }
            dis.close();
            connectThread.getClient().getChatMessanger().output("Server: 연결 종료");
            connectThread.disconnect();
        } catch (IOException e) {
            connectThread.getClient().getChatMessanger().output("Server: 연결 종료");
            connectThread.disconnect();
        }
    }

    private void inputData(String str) {
        try {
            if (str.split(";", -1)[0].equalsIgnoreCase("chat")) {
                String chat = "";
                for (int i = 3; i < str.split(";", -1).length; i++) {
                    if (i == 3) {
                        chat = str.split(";", -1)[i];
                    } else if (i > 3) {
                        chat += ";" + str.split(";", -1)[i];
                    }
                }
                connectThread.getClient().getChatMessanger()
                        .output(str.split(";", -1)[1] + str.split(";", -1)[2] + ": " + chat);
            } else if (str.split(";", -1)[0].equalsIgnoreCase("nickname")) {
                if (connectThread.isRequestingNickname()) {
                    if (str.split(";", -1)[2].equalsIgnoreCase("true")) {
                        connectThread.setNickname();
                    } else if (str.split(";", -1)[2].equalsIgnoreCase("false")) {
                        if (str.split(";", -1)[3].equalsIgnoreCase("twice")) {
                            connectThread.getClient().getChatMessanger()
                                    .output(str.split(";", -1)[1] + "닉네임은 한 번만 설정할 수 있습니다.");
                        } else if (str.split(";", -1)[3].equalsIgnoreCase("length")) {
                            connectThread.getClient().getChatMessanger()
                                    .output(str.split(";", -1)[1] + "닉네임이 너무 짧거나 깁니다. (1자~16자)");
                            connectThread.clearRequestNickname();
                        } else if (str.split(";", -1)[3].equalsIgnoreCase("invalid")) {
                            connectThread.getClient().getChatMessanger().output(str.split(";", -1)[1]
                                    + "닉네임에 사용할 수 없는 글자가 포함되어 있습니다. (\"Server\", \"null\", \";\", 공백, 줄바꿈 사용 불가)");
                            connectThread.clearRequestNickname();
                        } else if (str.split(";", -1)[3].equalsIgnoreCase("number")) {
                            connectThread.getClient().getChatMessanger()
                                    .output(str.split(";", -1)[1] + "닉네임은 숫자로만 이루어질 수 없습니다.");
                            connectThread.clearRequestNickname();
                        } else if (str.split(";", -1)[3].equalsIgnoreCase("already")) {
                            connectThread.getClient().getChatMessanger()
                                    .output(str.split(";", -1)[1] + "이미 존재하는 닉네임입니다.");
                            connectThread.clearRequestNickname();
                        } else {
                            connectThread.getClient().getChatMessanger().output("알 수 없는 형식의 정보 수신됨: " + str);
                        }
                    } else {
                        connectThread.getClient().getChatMessanger().output("알 수 없는 형식의 정보 수신됨: " + str);
                    }
                } else {
                    connectThread.getClient().getChatMessanger().output("알 수 없는 형식의 정보 수신됨: " + str);
                }
            } else if (str.split(";", -1)[0].equalsIgnoreCase("connectinfo")) {
                String nickname = str.split(";", -1)[2];
                if (connectThread.getClient().getChatMessanger().usingGUI()) {
                    connectThread.getClient().getChatMessanger().getChatGUI().getClientListScrollPane().getClientList()
                            .addClient(nickname);
                }
                connectThread.getClient().getChatMessanger().output(str.split(";", -1)[1] + nickname + ": 연결됨");
            } else if (str.split(";", -1)[0].equalsIgnoreCase("disconnectinfo")) {
                String nickname = str.split(";", -1)[2];
                if (connectThread.getClient().getChatMessanger().usingGUI()) {
                    connectThread.getClient().getChatMessanger().getChatGUI().getClientListScrollPane().getClientList()
                            .removeClient(nickname);
                }
                connectThread.getClient().getChatMessanger().output(str.split(";", -1)[1] + nickname + ": 연결 종료");
            } else if (str.split(";", -1)[0].equalsIgnoreCase("clientlistinfo")) {
                if (connectThread.getClient().getChatMessanger().usingGUI()) {
                    for (int i = 2; i < str.split(";", -1).length; i++) {
                        connectThread.getClient().getChatMessanger().getChatGUI().getClientListScrollPane()
                                .getClientList().addClient(str.split(";", -1)[i]);
                    }
                } else {
                    int clientCount = 0;
                    String clientListStr = "";
                    for (int i = 2; i < str.split(";", -1).length; i++) {
                        if (clientListStr.equalsIgnoreCase("")) {
                            clientListStr = str.split(";", -1)[i];
                        } else {
                            clientListStr += ", " + str.split(";", -1)[i];
                        }
                        clientCount++;
                    }
                    connectThread.getClient().getChatMessanger()
                            .output("현재 접속중 (" + clientCount + "): " + clientListStr);
                }
            } else if (str.split(";", -1)[0].equalsIgnoreCase("chatrecord")) {
                String chatRecord = "";
                for (int i = 2; i < str.split(";", -1).length; i++) {
                    if (i == 2) {
                        chatRecord = str.split(";", -1)[i];
                    } else if (i > 2) {
                        chatRecord += ";" + str.split(";", -1)[i];
                    }
                }
                connectThread.getClient().getChatMessanger().output(chatRecord);
            } else {
                connectThread.getClient().getChatMessanger().output("알 수 없는 형식의 정보 수신됨: " + str);
            }
        } catch (IndexOutOfBoundsException e) {
            connectThread.getClient().getChatMessanger().output("알 수 없는 형식의 정보 수신됨: " + str);
        }
    }
}
