package com.p16729438.ChatMessanger.Server.Command;

import com.p16729438.ChatMessanger.ChatMessanger;

public class ClearCommand {
    private ChatMessanger chatMessanger;

    public ClearCommand(ChatMessanger chatMessanger) {
        this.chatMessanger = chatMessanger;
    }

    public void execute() {
        if (chatMessanger.usingGUI()) {
            chatMessanger.getChatGUI().getOutputScrollPane().getOutputTextArea().setText(null);

        }
    }
}