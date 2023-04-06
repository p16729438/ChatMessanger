package com.p16729438.ChatMessanger.GUI.ChatGUI.Event.ChatGUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.p16729438.ChatMessanger.GUI.ChatGUI.ChatGUI;

public class ChatGUIMouseListener implements MouseListener {
    private ChatGUI chatGUI;

    public ChatGUIMouseListener(ChatGUI chatGUI) {
        this.chatGUI = chatGUI;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (chatGUI.getTitle().equalsIgnoreCase("ChatMessanger - New Message") && chatGUI.isActive()) {
            chatGUI.setTitle("ChatMessanger");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (chatGUI.getTitle().equalsIgnoreCase("ChatMessanger - New Message") && chatGUI.isActive()) {
            chatGUI.setTitle("ChatMessanger");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}