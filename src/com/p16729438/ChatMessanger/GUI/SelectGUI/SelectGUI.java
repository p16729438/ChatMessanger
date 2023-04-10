package com.p16729438.ChatMessanger.GUI.SelectGUI;

import java.awt.Color;

import javax.swing.JOptionPane;

import com.p16729438.ChatMessanger.ChatMessanger;
import com.p16729438.ChatMessanger.Util.ChatMessangerFont;

public class SelectGUI extends JOptionPane {
    private ChatMessanger chatMessanger;

    public SelectGUI(ChatMessanger chatMessanger) {
        this.chatMessanger = chatMessanger;
        init();
    }

    private void init() {
        setBackground(Color.DARK_GRAY);
        setBorder(null);
        setFont(ChatMessangerFont.ChatMessangerFont);
        setForeground(Color.LIGHT_GRAY);
        setName("select");
        setVisible(true);
        String[] list = { "Client", "Server" };
        String str = (String) showInputDialog(chatMessanger.getChatGUI(), "", "옵션 선택", PLAIN_MESSAGE, null, list, "Client");
        chatMessanger.select(str);
    }
}