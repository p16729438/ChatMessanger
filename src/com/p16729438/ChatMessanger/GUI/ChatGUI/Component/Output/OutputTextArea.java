package com.p16729438.ChatMessanger.GUI.ChatGUI.Component.Output;

import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import com.p16729438.ChatMessanger.GUI.ChatGUI.ChatGUI;
import com.p16729438.ChatMessanger.GUI.ChatGUI.Event.ChatGUI.ChatGUIMouseListener;
import com.p16729438.ChatMessanger.Util.ChatMessangerFont;
import com.p16729438.ChatMessanger.Util.ChatMessangerSound;

public class OutputTextArea extends JTextArea {
    private ChatGUI chatGUI;

    public OutputTextArea(ChatGUI chatGUI) {
        this.chatGUI = chatGUI;

        init();
    }

    private void init() {
        setName("outputtextarea");
        setBounds(20, 20, 720, 420);
        setBorder(null);
        setBackground(Color.LIGHT_GRAY);
        setSelectionColor(Color.GRAY);
        setFont(ChatMessangerFont.ChatMessangerFont);
        setAutoscrolls(true);
        setEditable(false);
        setLineWrap(true);
        setWrapStyleWord(true);
        ((DefaultCaret) getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        addMouseListener(new ChatGUIMouseListener(chatGUI));
    }

    public void appendText(String str) {
        if (getText().isEmpty()) {
            setText(str);
        } else {
            append("\n" + str);
            setCaretPosition(getDocument().getLength());
        }
        if (chatGUI.getChatMessanger().readyGUI()) {
            if (!chatGUI.isFocused()) {
                chatGUI.setTitle("ChatMessanger - New Message");
                chatGUI.requestFocus();
            }
            if (chatGUI.getTitle().equalsIgnoreCase("ChatMessanger - New Message")) {
                ChatMessangerSound.playNotificationSound();
            }
        }

    }

    public ChatGUI getChatGUI() {
        return chatGUI;
    }
}