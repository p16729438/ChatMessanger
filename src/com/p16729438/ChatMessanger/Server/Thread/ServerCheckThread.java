package com.p16729438.ChatMessanger.Server.Thread;

import java.net.Socket;
import java.util.ArrayList;

public class ServerCheckThread extends Thread {
    private ServerHostThread hostThread;
    private ArrayList<Socket> connectionCheckList = new ArrayList<Socket>();

    public ServerCheckThread(ServerHostThread hostThread) {
        this.hostThread = hostThread;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                for (Socket socket : hostThread.getSocketList()) {
                    if (!connectionCheckList.contains(socket)) {
                        hostThread.disconnect(socket);
                    }
                }
                connectionCheckList.clear();
                Thread.sleep(10000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addCheckList(Socket socket) {
        if (!connectionCheckList.contains(socket)) {
            connectionCheckList.add(socket);
        }
    }
}