package com.p16729438.ChatMessanger.Client;

import com.p16729438.ChatMessanger.ChatMessanger;
import com.p16729438.ChatMessanger.Client.GUI.AddressGUI.AddressGUI;
import com.p16729438.ChatMessanger.Client.GUI.PortGUI.ClientPortGUI;
import com.p16729438.ChatMessanger.Client.Thread.ClientConnectThread;

public class Client {
    private ChatMessanger chatMessanger;

    private ClientConnectThread connectThread;

    public Client(ChatMessanger chatMessanger) {
        this.chatMessanger = chatMessanger;
        connectThread = new ClientConnectThread(this);
        new AddressGUI(this);
        new ClientPortGUI(this);
        if (chatMessanger.getChatGUI().isVisible()) {
            connectThread.start();
            while (!connectThread.isReady()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Client(ChatMessanger chatMessanger, String address, int port) {
        this.chatMessanger = chatMessanger;
        connectThread = new ClientConnectThread(this);
        connectThread.setAddress(address);
        connectThread.setPort(port);
        connectThread.start();
        while (!connectThread.isReady()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public ClientConnectThread getConnectThread() {
        return connectThread;
    }

    public ChatMessanger getChatMessanger() {
        return chatMessanger;
    }
}