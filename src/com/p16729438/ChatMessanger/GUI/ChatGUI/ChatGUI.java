package com.p16729438.ChatMessanger.GUI.ChatGUI;

import java.awt.Color;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.p16729438.ChatMessanger.ChatMessanger;
import com.p16729438.ChatMessanger.Main;
import com.p16729438.ChatMessanger.GUI.ChatGUI.Component.ClientList.ClientListScrollPane;
import com.p16729438.ChatMessanger.GUI.ChatGUI.Component.Input.InputTextField;
import com.p16729438.ChatMessanger.GUI.ChatGUI.Component.Output.OutputScrollPane;
import com.p16729438.ChatMessanger.GUI.ChatGUI.Component.SendButton.SendButton;
import com.p16729438.ChatMessanger.GUI.ChatGUI.Event.ChatGUI.ChatGUIMouseListener;
import com.p16729438.ChatMessanger.GUI.ChatGUI.Event.ChatGUI.ChatGUIWindowListener;
import com.p16729438.ChatMessanger.Util.ChatMessangerFont;

public class ChatGUI extends JFrame {
    private ChatMessanger chatMessanger;

    private ClientListScrollPane clientListScrollPane;
    private InputTextField inputTextField;
    private OutputScrollPane outputScrollPane;
    private SendButton sendButton;

    public ChatGUI(ChatMessanger chatMessanger) {
        this.chatMessanger = chatMessanger;

        init();
    }

    private void init() {
        setName("chatgui");
        setTitle("ChatMessanger");
        setSize(960, 540);
        getContentPane().setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            setIconImage(ImageIO.read(Main.class.getResourceAsStream("/com/p16729438/ChatMessanger/chat.png")));
        } catch (IOException e) {
        }
        getContentPane().setBackground(Color.DARK_GRAY);
        setFont(ChatMessangerFont.ChatMessangerFont);

        clientListScrollPane = new ClientListScrollPane(this);
        inputTextField = new InputTextField(this);
        outputScrollPane = new OutputScrollPane(this);
        sendButton = new SendButton(this);

        getContentPane().add(clientListScrollPane);
        getContentPane().add(inputTextField);
        getContentPane().add(outputScrollPane);
        getContentPane().add(sendButton);

        getRootPane().setDefaultButton(sendButton);

        addMouseListener(new ChatGUIMouseListener(this));
        addWindowListener(new ChatGUIWindowListener(this));

        setVisible(true);
    }

    public ClientListScrollPane getClientListScrollPane() {
        return clientListScrollPane;
    }

    public InputTextField getInputTextField() {
        return inputTextField;
    }

    public OutputScrollPane getOutputScrollPane() {
        return outputScrollPane;
    }

    public SendButton getSendButton() {
        return sendButton;
    }

    public ChatMessanger getChatMessanger() {
        return chatMessanger;
    }
}