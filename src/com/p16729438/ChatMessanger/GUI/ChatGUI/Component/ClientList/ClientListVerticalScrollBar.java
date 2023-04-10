package com.p16729438.ChatMessanger.GUI.ChatGUI.Component.ClientList;

import java.awt.Color;

import javax.swing.JScrollBar;

import com.p16729438.ChatMessanger.GUI.ChatGUI.ChatGUI;
import com.p16729438.ChatMessanger.GUI.ChatGUI.Event.ChatGUI.ChatGUIMouseListener;
import com.p16729438.ChatMessanger.Util.ChatMessangerFont;

public class ClientListVerticalScrollBar extends JScrollBar {
    private ChatGUI chatGUI;

    public ClientListVerticalScrollBar(ChatGUI chatGUI) {
        this.chatGUI = chatGUI;
        init();
    }

    private void init() {
        setName("clientlistverticalscrollbar");
        setBackground(Color.LIGHT_GRAY);
        setForeground(Color.LIGHT_GRAY);
        setBorder(null);
        setFont(ChatMessangerFont.ChatMessangerFont);
        setOrientation(VERTICAL);
        setUnitIncrement(30);
        addMouseListener(new ChatGUIMouseListener(chatGUI));
    }

    public ChatGUI getChatGUI() {
        return chatGUI;
    }
}