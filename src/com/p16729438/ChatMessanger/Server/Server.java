package com.p16729438.ChatMessanger.Server;

import com.p16729438.ChatMessanger.ChatMessanger;
import com.p16729438.ChatMessanger.Server.GUI.PortGUI.ServerPortGUI;
import com.p16729438.ChatMessanger.Server.Thread.ServerHostThread;

public class Server {
    private ChatMessanger chatMessanger;
    private ServerHostThread hostThread;

    public Server(ChatMessanger chatMessanger) {
        this.chatMessanger = chatMessanger;
        hostThread = new ServerHostThread(this);
        new ServerPortGUI(this);
        if (chatMessanger.getChatGUI().isVisible()) {
            hostThread.start();
            while (!hostThread.isReady()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Server(ChatMessanger chatMessanger, int port) {
        this.chatMessanger = chatMessanger;
        hostThread = new ServerHostThread(this);
        hostThread.setPort(port);
        hostThread.start();
        while (!hostThread.isReady()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public ServerHostThread getHostThread() {
        return hostThread;
    }

    public ChatMessanger getChatMessanger() {
        return chatMessanger;
    }
}
