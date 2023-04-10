package com.p16729438.ChatMessanger.GUI.ChatGUI.Component.SendButton;

import java.awt.Color;

import javax.swing.JButton;

import com.p16729438.ChatMessanger.GUI.ChatGUI.ChatGUI;
import com.p16729438.ChatMessanger.GUI.ChatGUI.Event.ChatGUI.ChatGUIMouseListener;
import com.p16729438.ChatMessanger.GUI.ChatGUI.Event.SendButton.SendButtonActionListener;
import com.p16729438.ChatMessanger.Util.ChatMessangerFont;

public class SendButton extends JButton {
    private ChatGUI chatGUI;

    public SendButton(ChatGUI chatGUI) {
        this.chatGUI = chatGUI;
        init();
    }

    private void init() {
        setName("sendbutton");
        setBounds(770, 460, 150, 30);
        setBorder(null);
        setBackground(Color.LIGHT_GRAY);
        setFont(ChatMessangerFont.ChatMessangerFont);
        setText("전송");
        addMouseListener(new ChatGUIMouseListener(chatGUI));
        addActionListener(new SendButtonActionListener(this));
    }

    public ChatGUI getChatGUI() {
        return chatGUI;
    }
}