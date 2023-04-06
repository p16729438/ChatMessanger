package com.p16729438.ChatMessanger.Client.GUI.PasswordGUI;

import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import com.p16729438.ChatMessanger.Client.Client;
import com.p16729438.ChatMessanger.Util.ChatMessangerFont;

public class PasswordGUI extends JOptionPane {
    private Client client;

    public PasswordGUI(Client client) {
        this.client = client;

        init();
    }

    private void init() {
        setName("passwordgui");
        setBorder(null);
        setBackground(Color.DARK_GRAY);
        setForeground(Color.LIGHT_GRAY);
        setFont(ChatMessangerFont.ChatMessangerFont);
        setVisible(true);

        JPasswordField passwordField = new JPasswordField();
        showMessageDialog(client.getChatMessanger().getChatGUI(), passwordField, "비밀번호 입력", PLAIN_MESSAGE);
        String password = String.valueOf(passwordField.getPassword());
        client.getConnectThread().sendPassword(password);
    }
}