package com.p16729438.ChatMessanger.GUI.ChatGUI.Component.Output;

import java.awt.Color;

import javax.swing.JScrollBar;

import com.p16729438.ChatMessanger.GUI.ChatGUI.ChatGUI;
import com.p16729438.ChatMessanger.GUI.ChatGUI.Event.ChatGUI.ChatGUIMouseListener;
import com.p16729438.ChatMessanger.Util.ChatMessangerFont;

public class OutputVerticalScrollBar extends JScrollBar {
    private ChatGUI chatGUI;

    public OutputVerticalScrollBar(ChatGUI chatGUI) {
        this.chatGUI = chatGUI;

        init();
    }

    private void init() {
        setName("outputverticalscrollbar");
        setBorder(null);
        setBackground(Color.LIGHT_GRAY);
        setFont(ChatMessangerFont.ChatMessangerFont);
        setAutoscrolls(true);
        setOrientation(VERTICAL);
        setUnitIncrement(30);

        addMouseListener(new ChatGUIMouseListener(chatGUI));
    }

    public ChatGUI getChatGUI() {
        return chatGUI;
    }
}