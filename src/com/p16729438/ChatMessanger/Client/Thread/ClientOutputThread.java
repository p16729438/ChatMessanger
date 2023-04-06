package com.p16729438.ChatMessanger.Client.Thread;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ClientOutputThread extends Thread {
    private ClientConnectThread connectThread;

    private ArrayList<String> messageQueue = new ArrayList<String>();

    public ClientOutputThread(ClientConnectThread connectThread) {
        this.connectThread = connectThread;
    }

    @Override
    public void run() {
        try {
            DataOutputStream dos = new DataOutputStream(connectThread.getSocket().getOutputStream());
            while (!isInterrupted()) {
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