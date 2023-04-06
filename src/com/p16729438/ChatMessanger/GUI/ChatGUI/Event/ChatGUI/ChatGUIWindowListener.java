package com.p16729438.ChatMessanger.GUI.ChatGUI.Event.ChatGUI;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import com.p16729438.ChatMessanger.GUI.ChatGUI.ChatGUI;

public class ChatGUIWindowListener implements WindowListener {
    private ChatGUI chatGUI;

    public ChatGUIWindowListener(ChatGUI chatGUI) {
        this.chatGUI = chatGUI;
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        if (chatGUI.getTitle().equalsIgnoreCase("ChatMessanger - New Message")) {
            chatGUI.setTitle("ChatMessanger");
        }
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}