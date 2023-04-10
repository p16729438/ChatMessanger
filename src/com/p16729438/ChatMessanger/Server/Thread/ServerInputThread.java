package com.p16729438.ChatMessanger.Server.Thread;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import com.p16729438.ChatMessanger.Util.ChatMessangerTimeStamp;

public class ServerInputThread extends Thread {
    private ServerHostThread hostThread;
    private Socket socket;

    public ServerInputThread(ServerHostThread hostThread, Socket socket) {
        this.hostThread = hostThread;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String str;
            while (!isInterrupted() && (str = dis.readUTF()) != null) {
                inputData(str);
            }
            dis.close();
            if (hostThread.getServer().getChatMessanger().usingGUI()) {
                hostThread.getServer().getChatMessanger().getChatGUI().getClientListScrollPane().getClientList().removeClient(hostThread.getNickname(socket));
            }
            hostThread.disconnect(socket);
        } catch (IOException e) {
            if (hostThread.getServer().getChatMessanger().usingGUI()) {
                hostThread.getServer().getChatMessanger().getChatGUI().getClientListScrollPane().getClientList().removeClient(hostThread.getNickname(socket));
            }
            hostThread.disconnect(socket);
        }
    }

    private void inputData(String str) {
        try {
            if (str.split(";", -1)[0].equalsIgnoreCase("check")) {
                hostThread.getCheckThread().addCheckList(socket);
            } else if (str.split(";", -1)[0].equalsIgnoreCase("chat")) {
                String chat = "";
                for (int i = 1; i < str.split(";", -1).length; i++) {
                    if (i == 1) {
                        chat = str.split(";", -1)[i];
                    } else if (i > 1) {
                        chat += ";" + str.split(";", -1)[i];
                    }
                }
                hostThread.sendChat(hostThread.getNickname(socket) + ";" + chat);
                hostThread.getServer().getChatMessanger().output(ChatMessangerTimeStamp.getTimeStamp() + hostThread.getNickname(socket) + ": " + chat);
            } else if (str.split(";", -1)[0].equalsIgnoreCase("nickname")) {
                String nickname = str.substring(9);
                if (!hostThread.hasNickname(socket)) {
                    if (nickname.length() >= 1 && nickname.length() <= 16) {
                        if (!nickname.contains("Server") && !nickname.contains("null") && !nickname.contains(";") && !nickname.contains("\n") && !nickname.contains(" ")) {
                            try {
                                Long.parseLong(nickname);
                                if (nickname.startsWith("-")) {
                                    if (!hostThread.existNickname(nickname)) {
                                        hostThread.sendNickname("true", socket);
                                        hostThread.setNickname(socket, nickname);
                                    } else {
                                        hostThread.sendNickname("false;already", socket);
                                    }
                                } else {
                                    hostThread.sendNickname("false;number", socket);
                                }
                            } catch (NumberFormatException e) {
                                if (!hostThread.existNickname(nickname)) {
                                    hostThread.sendNickname("true", socket);
                                    hostThread.setNickname(socket, nickname);
                                } else {
                                    hostThread.sendNickname("false;already", socket);
                                }
                            }
                        } else {
                            hostThread.sendNickname("false;invalid", socket);
                        }
                    } else {
                        hostThread.sendNickname("false;length", socket);
                    }
                } else {
                    hostThread.sendNickname("false;twice", socket);
                }
            }
            /*
             * else if(str.split(";", -1)[0].equalsIgnoreCase("password")) { String nickname = str.split(";", -1)[1]; String password = str.split(";", -1)[2]; hostThread.set }
             */
            else {
                hostThread.getServer().getChatMessanger().output(ChatMessangerTimeStamp.getTimeStamp() + "알 수 없는 형식의 정보 수신됨: " + str);
            }
        } catch (IndexOutOfBoundsException e) {
            hostThread.getServer().getChatMessanger().output(ChatMessangerTimeStamp.getTimeStamp() + "알 수 없는 형식의 정보 수신됨: " + str);
        }
    }
}