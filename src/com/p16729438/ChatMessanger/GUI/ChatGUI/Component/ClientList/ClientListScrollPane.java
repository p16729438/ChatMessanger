package com.p16729438.ChatMessanger.GUI.ChatGUI.Component.ClientList;

import java.awt.Color;

import javax.swing.JScrollPane;

import com.p16729438.ChatMessanger.GUI.ChatGUI.ChatGUI;
import com.p16729438.ChatMessanger.GUI.ChatGUI.Component.Output.OutputVerticalScrollBar;
import com.p16729438.ChatMessanger.GUI.ChatGUI.Event.ChatGUI.ChatGUIMouseListener;
import com.p16729438.ChatMessanger.Util.ChatMessangerFont;

public class ClientListScrollPane extends JScrollPane {
    private ChatGUI chatGUI;

    private ClientList clientList;

    public ClientListScrollPane(ChatGUI chatGUI) {
        this.chatGUI = chatGUI;

        init();
    }

    private void init() {
        setName("clientlistscrollpane");
        setBounds(770, 20, 150, 420);
        setBorder(null);
        setBackground(Color.LIGHT_GRAY);
        setFont(ChatMessangerFont.ChatMessangerFont);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBar(new OutputVerticalScrollBar(chatGUI));
        clientList = new ClientList(chatGUI);
        setViewportView(clientList);

        addMouseListener(new ChatGUIMouseListener(chatGUI));
    }

    public ClientList getClientList() {
        return clientList;
    }

    public ChatGUI getChatGUI() {
        return chatGUI;
    }
}