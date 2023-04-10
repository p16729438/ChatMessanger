package com.p16729438.ChatMessanger.Server.GUI.PortGUI;

import java.awt.Color;

import javax.swing.JOptionPane;

import com.p16729438.ChatMessanger.Server.Server;
import com.p16729438.ChatMessanger.Util.ChatMessangerFont;

public class ServerPortGUI extends JOptionPane {
    private Server server;

    public ServerPortGUI(Server server) {
        this.server = server;
        init();
    }

    private void init() {
        setName("serverportgui");
        setBorder(null);
        setBackground(Color.DARK_GRAY);
        setForeground(Color.LIGHT_GRAY);
        setFont(ChatMessangerFont.ChatMessangerFont);
        setVisible(true);
        int port = selectPort();
        server.getHostThread().setPort(port);
    }

    private int selectPort() {
        try {
            String portStr = (String) showInputDialog(server.getChatMessanger().getChatGUI(), "", "포트 번호 입력", PLAIN_MESSAGE, null, null, null);
            if (portStr != null) {
                int port = Integer.parseInt(portStr);
                if (port >= 0 && port <= 65535) {
                    return port;
                } else {
                    server.getChatMessanger().getChatGUI().getOutputScrollPane().getOutputTextArea().appendText("숫자만 입력하세요 (범위: 0~65535)");
                    return selectPort();
                }
            } else {
                server.getChatMessanger().getChatGUI().setVisible(false);
                server.getChatMessanger().getChatGUI().dispose();
                return 0;
            }
        } catch (NumberFormatException e) {
            server.getChatMessanger().getChatGUI().getOutputScrollPane().getOutputTextArea().appendText("숫자만 입력하세요 (범위: 0~65535)");
            return selectPort();
        }
    }
}