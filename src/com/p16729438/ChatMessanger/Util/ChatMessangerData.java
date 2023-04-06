package com.p16729438.ChatMessanger.Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ChatMessangerData {
    private static File dataDir = new File("C:/ProgramData/ChatMessanger");
    private static File addressDataFile = new File("C:/ProgramData/ChatMessanger/address.txt");
    private static File nicknameDataFile = new File("C:/ProgramData/ChatMessanger/nickname.txt");
    private static File portDataFile = new File("C:/ProgramData/ChatMessanger/port.txt");

    public static String getAddressData() {
        createFile();
        try {
            BufferedReader r = new BufferedReader(new FileReader(addressDataFile));
            String address = r.readLine();
            r.close();
            return address;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getNicknameData() {
        createFile();
        try {
            BufferedReader r = new BufferedReader(new FileReader(nicknameDataFile));
            String nickname = r.readLine();
            r.close();
            return nickname;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getPortData() {
        createFile();
        try {
            BufferedReader r = new BufferedReader(new FileReader(portDataFile));
            String port = r.readLine();
            r.close();
            return port;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void setAddressData(String address) {
        createFile();
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(addressDataFile));
            w.append(address);
            w.flush();
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setNicknameData(String nickname) {
        createFile();
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(nicknameDataFile));
            w.append(nickname);
            w.flush();
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setPortData(String port) {
        createFile();
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(portDataFile));
            w.append(port);
            w.flush();
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createFile() {
        try {
            if (!dataDir.exists()) {
                dataDir.mkdir();
            }
            if (!addressDataFile.exists()) {
                addressDataFile.createNewFile();
            }
            if (!portDataFile.exists()) {
                portDataFile.createNewFile();
            }
            if (!nicknameDataFile.exists()) {
                nicknameDataFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}