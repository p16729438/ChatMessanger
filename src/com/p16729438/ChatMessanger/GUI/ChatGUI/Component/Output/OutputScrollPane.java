package com.p16729438.ChatMessanger.GUI.ChatGUI.Component.Output;

import java.awt.Color;

import javax.swing.JScrollPane;

import com.p16729438.ChatMessanger.GUI.ChatGUI.ChatGUI;
import com.p16729438.ChatMessanger.GUI.ChatGUI.Event.ChatGUI.ChatGUIMouseListener;
import com.p16729438.ChatMessanger.Util.ChatMessangerFont;

public class OutputScrollPane extends JScrollPane {
    private ChatGUI chatGUI;
    private OutputTextArea outputTextArea;

    public OutputScrollPane(ChatGUI chatGUI) {
        this.chatGUI = chatGUI;
        init();
    }

    private void init() {
        setName("outputscrollpane");
        setBounds(20, 20, 720, 420);
        setBorder(null);
        setBackground(Color.LIGHT_GRAY);
        setFont(ChatMessangerFont.ChatMessangerFont);
        setVerticalScrollBar(new OutputVerticalScrollBar(chatGUI));
        outputTextArea = new OutputTextArea(chatGUI);
        setAutoscrolls(true);
        setViewportView(outputTextArea);
        addMouseListener(new ChatGUIMouseListener(chatGUI));
    }

    public OutputTextArea getOutputTextArea() {
        return outputTextArea;
    }

    public ChatGUI getChatGUI() {
        return chatGUI;
    }
}