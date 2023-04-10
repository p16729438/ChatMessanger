package com.p16729438.ChatMessanger.GUI.ChatGUI.Event.SendButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.p16729438.ChatMessanger.GUI.ChatGUI.Component.SendButton.SendButton;

public class SendButtonActionListener implements ActionListener {
    private SendButton sendButton;

    public SendButtonActionListener(SendButton sendButton) {
        this.sendButton = sendButton;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (sendButton.getChatGUI().getChatMessanger().getClient() != null) {
            if (sendButton.equals(event.getSource())) {
                String str = sendButton.getChatGUI().getInputTextField().getText();
                if (!str.equalsIgnoreCase("")) {
                    if (sendButton.getChatGUI().getChatMessanger().getClient().getConnectThread().getOutputThread() != null) {
                        if (sendButton.getChatGUI().getChatMessanger().getClient().getConnectThread().getNickname() != null) {
                            sendButton.getChatGUI().getChatMessanger().getClient().getConnectThread().sendChat(str);
                            sendButton.getChatGUI().getInputTextField().setText(null);
                        } else {
                            sendButton.getChatGUI().getOutputScrollPane().getOutputTextArea().appendText("닉네임 승인 대기중입니다.");
                        }
                    } else {
                        sendButton.getChatGUI().getOutputScrollPane().getOutputTextArea().appendText("서버에 연결되어 있지 않습니다.");
                    }
                }
            }
        } else if (sendButton.getChatGUI().getChatMessanger().getServer() != null) {
            if (sendButton.equals(event.getSource())) {
                String str = sendButton.getChatGUI().getInputTextField().getText();
                if (!str.equalsIgnoreCase("")) {
                    sendButton.getChatGUI().getOutputScrollPane().getOutputTextArea().appendText(str);
                    sendButton.getChatGUI().getChatMessanger().getServer().getHostThread().sendChat("Server;" + str);
                    sendButton.getChatGUI().getInputTextField().setText(null);
                }
            }
        }
    }
}