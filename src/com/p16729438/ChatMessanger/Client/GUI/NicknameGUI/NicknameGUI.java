package com.p16729438.ChatMessanger.Client.GUI.NicknameGUI;

import java.awt.Color;

import javax.swing.JOptionPane;

import com.p16729438.ChatMessanger.Client.Client;
import com.p16729438.ChatMessanger.Util.ChatMessangerData;
import com.p16729438.ChatMessanger.Util.ChatMessangerFont;

public class NicknameGUI extends JOptionPane {
    private Client client;

    public NicknameGUI(Client client) {
        this.client = client;

        init();
    }

    private void init() {
        if (client.getChatMessanger().getChatGUI().isVisible()) {
            setName("nicknamegui");
            setBorder(null);
            setBackground(Color.DARK_GRAY);
            setForeground(Color.LIGHT_GRAY);
            setFont(ChatMessangerFont.ChatMessangerFont);
            setVisible(true);

            String nickname = (String) showInputDialog(client.getChatMessanger().getChatGUI(), "", "닉네임 입력",
                    PLAIN_MESSAGE, null, null, ChatMessangerData.getNicknameData());
            client.getConnectThread().requestNickname(nickname);
        }
    }
}