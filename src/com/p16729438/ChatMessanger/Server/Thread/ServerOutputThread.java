package com.p16729438.ChatMessanger.Server.Thread;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ServerOutputThread extends Thread {
    private ServerHostThread hostThread;

    private Socket socket;

    private ArrayList<String> messageQueue = new ArrayList<String>();

    public ServerOutputThread(ServerHostThread hostThread, Socket socket) {
        this.hostThread = hostThread;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            while (!hostThread.isInterrupted() && !isInterrupted()) {
                if (!messageQueue.isEmpty()) {
                    dos.writeUTF(messageQueue.get(0));
                    dos.flush();
                    messageQueue.remove(0);
                }
            }
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMessageQueue(String str) {
        messageQueue.add(str);
    }
}