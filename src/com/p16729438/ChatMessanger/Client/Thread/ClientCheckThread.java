package com.p16729438.ChatMessanger.Client.Thread;

public class ClientCheckThread extends Thread {
    private ClientConnectThread connectThread;

    public ClientCheckThread(ClientConnectThread connectThread) {
        this.connectThread = connectThread;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                connectThread.sendCheck();
                Thread.sleep(10000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}