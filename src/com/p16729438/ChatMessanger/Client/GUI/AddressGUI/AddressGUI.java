package com.p16729438.ChatMessanger.Client.GUI.AddressGUI;

import java.awt.Color;

import javax.swing.JOptionPane;

import com.p16729438.ChatMessanger.Client.Client;
import com.p16729438.ChatMessanger.Util.ChatMessangerData;
import com.p16729438.ChatMessanger.Util.ChatMessangerFont;

public class AddressGUI extends JOptionPane {
    private Client client;

    public AddressGUI(Client client) {
        this.client = client;
        init();
    }

    private void init() {
        if (client.getChatMessanger().getChatGUI().isVisible()) {
            setName("addressgui");
            setBorder(null);
            setBackground(Color.DARK_GRAY);
            setForeground(Color.LIGHT_GRAY);
            setFont(ChatMessangerFont.ChatMessangerFont);
            setVisible(true);
            String address = (String) showInputDialog(client.getChatMessanger().getChatGUI(), "", "IP 주소 입력", PLAIN_MESSAGE, null, null, ChatMessangerData.getAddressData());
            if (address != null) {
                client.getConnectThread().setAddress(address);
                ChatMessangerData.setAddressData(address);
            } else {
                client.getChatMessanger().getChatGUI().setVisible(false);
                client.getChatMessanger().getChatGUI().dispose();
            }
        }
    }
}