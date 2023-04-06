package com.p16729438.ChatMessanger.GUI.ChatGUI.Component.ClientList;

import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import com.p16729438.ChatMessanger.GUI.ChatGUI.ChatGUI;
import com.p16729438.ChatMessanger.GUI.ChatGUI.Event.ChatGUI.ChatGUIMouseListener;
import com.p16729438.ChatMessanger.Util.ChatMessangerFont;

public class ClientList extends JList<String> {
    private ChatGUI chatGUI;

    private DefaultListModel<String> model;

    public ClientList(ChatGUI chatGUI) {
        this.chatGUI = chatGUI;

        init();
    }

    private void init() {
        setName("clientlist");
        setBounds(770, 20, 150, 420);
        setBorder(null);
        setBackground(Color.LIGHT_GRAY);
        setSelectionBackground(Color.GRAY);
        setFont(ChatMessangerFont.ChatMessangerFont);
        setDragEnabled(false);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        model = new DefaultListModel<String>();
        setModel(model);

        addMouseListener(new ChatGUIMouseListener(chatGUI));
    }

    public void setClient(String nickname) {
        model.add(0, nickname);
    }

    public void addClient(String nickname) {
        if (!model.contains(nickname)) {
            model.addElement(nickname);
        }
    }

    public void removeClient(String nickname) {
        if (model.contains(nickname)) {
            model.removeElement(nickname);
        }
    }

    public ChatGUI getChatGUI() {
        return chatGUI;
    }
}