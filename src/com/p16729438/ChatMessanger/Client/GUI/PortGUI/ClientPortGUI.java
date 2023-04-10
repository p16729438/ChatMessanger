package com.p16729438.ChatMessanger.Client.GUI.PortGUI;

import java.awt.Color;

import javax.swing.JOptionPane;

import com.p16729438.ChatMessanger.Client.Client;
import com.p16729438.ChatMessanger.Util.ChatMessangerData;
import com.p16729438.ChatMessanger.Util.ChatMessangerFont;

public class ClientPortGUI extends JOptionPane {
    private Client client;

    public ClientPortGUI(Client client) {
        this.client = client;

        init();
    }

    private void init() {
        if (client.getChatMessanger().getChatGUI().isVisible()) {
            setName("clientportgui");
            setBorder(null);
            setBackground(Color.DARK_GRAY);
            setForeground(Color.LIGHT_GRAY);
            setFont(ChatMessangerFont.ChatMessangerFont);
            setVisible(true);

            int port = selectPort();
            client.getConnectThread().setPort(port);
            ChatMessangerData.setPortData(String.valueOf(port));
        }
    }

    private int selectPort() {
        try {
            String portStr = (String) showInputDialog(client.getChatMessanger().getChatGUI(), "", "포트 번호 입력",
                    PLAIN_MESSAGE, null, null, ChatMessangerData.getPortData());
            if (portStr != null) {
                int port = Integer.parseInt(portStr);
                if (port >= 0 && port <= 65535) {
                    return port;
                }
                client.getChatMessanger().getChatGUI().getOutputScrollPane().getOutputTextArea()
                        .appendText("숫자만 입력하세요 (범위: 0~65535)");
                return selectPort();
            } else {
                client.getChatMessanger().getChatGUI().setVisible(false);
                client.getChatMessanger().getChatGUI().dispose();
                return 0;
            }
        } catch (NumberFormatException e) {
            client.getChatMessanger().getChatGUI().getOutputScrollPane().getOutputTextArea()
                    .appendText("숫자만 입력하세요 (범위: 0~65535)");
            selectPort();
            return selectPort();
        }
    }
}