package com.p16729438.ChatMessanger.GUI.ChatGUI.Component.Input;

import java.awt.Color;

import javax.swing.JTextField;

import com.p16729438.ChatMessanger.GUI.ChatGUI.ChatGUI;
import com.p16729438.ChatMessanger.GUI.ChatGUI.Event.ChatGUI.ChatGUIMouseListener;
import com.p16729438.ChatMessanger.Util.ChatMessangerFont;

public class InputTextField extends JTextField {
    private ChatGUI chatGUI;

    public InputTextField(ChatGUI chatGUI) {
        this.chatGUI = chatGUI;

        init();
    }

    private void init() {
        setName("inputtextfield");
        setBounds(20, 460, 720, 30);
        setBorder(null);
        setBackground(Color.LIGHT_GRAY);
        setSelectionColor(Color.GRAY);
        setFont(ChatMessangerFont.ChatMessangerFont);

        addMouseListener(new ChatGUIMouseListener(chatGUI));
    }

    public ChatGUI getChatGUI() {
        return chatGUI;
    }
}