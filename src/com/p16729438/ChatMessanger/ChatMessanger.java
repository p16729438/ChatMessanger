package com.p16729438.ChatMessanger;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.p16729438.ChatMessanger.Client.Client;
import com.p16729438.ChatMessanger.GUI.ChatGUI.ChatGUI;
import com.p16729438.ChatMessanger.GUI.SelectGUI.SelectGUI;
import com.p16729438.ChatMessanger.Server.Server;

public class ChatMessanger {
    private boolean usingGUI = true;
    private boolean readyGUI = false;

    private Scanner sc;

    private Client client;
    private Server server;

    private ChatGUI chatGUI;

    public ChatMessanger() {
        try {
            chatGUI = new ChatGUI(this);
            if (isLatestVersion()) {
                new SelectGUI(this);
            }
        } catch (HeadlessException e) {
            usingGUI = false;
            if (isLatestVersion()) {
                selectOption();
            }
        }
    }

    private boolean isLatestVersion() {
        try {
            BufferedReader thisVersionReader = new BufferedReader(
                    new InputStreamReader(Main.class.getResourceAsStream("/com/p16729438/ChatMessanger/version.txt")));
            String thisVersion = thisVersionReader.readLine();
            thisVersionReader.close();
            BufferedReader compareVersionReader = new BufferedReader(new InputStreamReader(
                    new URL("http://16729438.kro.kr/ChatMessanger/version/version.txt").openStream()));
            String compareVersion = compareVersionReader.readLine();
            compareVersionReader.close();
            if (Integer.parseInt(thisVersion.split("\\.", -1)[0]) < Integer
                    .parseInt(compareVersion.split("\\.", -1)[0])) {
                output("새로운 버전이 있습니다");
                output("http://16729438.kro.kr");
                output("위 링크에 접속하여 새로운 버전을 다운로드해주세요");
                return false;
            } else if (Integer.parseInt(thisVersion.split("\\.",
                    -1)[0]) == Integer.parseInt(
                            compareVersion.split("\\.",
                                    -1)[0])) {
                if (Integer.parseInt(thisVersion.split("\\.",
                        -1)[1]) < Integer.parseInt(compareVersion.split("\\.", -1)[1])) {
                    output("새로운 버전이 있습니다");
                    output("http://16729438.kro.kr");
                    output("위 링크에 접속하여 새로운 버전을 다운로드해주세요");
                    return false;
                } else if (Integer.parseInt(thisVersion.split("\\.",
                        -1)[1]) == Integer
                                .parseInt(compareVersion.split("\\.", -1)[1])) {
                    if (Integer.parseInt(thisVersion.split("\\.",
                            -1)[2]) < Integer.parseInt(compareVersion.split("\\.", -1)[2])) {
                        output("새로운 버전이 있습니다");
                        output("http://16729438.kro.kr");
                        output("위 링크에 접속하여 새로운 버전을 다운로드해주세요");
                        return false;
                    }
                }
            }
        } catch (IndexOutOfBoundsException | IOException | NumberFormatException e) {
        }
        return true;
    }

    private void selectOption() {
        System.out.println("옵션 선택(Client: c, Server: s, exit: any Key): ");
        sc = new Scanner(System.in);
        String option = sc.nextLine();
        if (option.equalsIgnoreCase("c")) {
            selectClientAddress();
            input();
        } else if (option.equalsIgnoreCase("s")) {
            selectServerPort();
            input();
        }
    }

    private void selectClientAddress() {
        System.out.println("IP 주소 입력: ");
        String address = sc.nextLine();
        selectClientPort(address);
    }

    private void selectClientPort(String address) {
        System.out.print("포트 번호 입력: ");
        try {
            int port = sc.nextInt();
            if (port >= 0 && port <= 65535) {
                client = new Client(this, address, port);
            } else {
                System.out.println("숫자만 입력하세요 (범위: 0~65535)");
                selectClientPort(address);
            }
        } catch (InputMismatchException e) {
            System.out.println("숫자만 입력하세요 (범위: 0~65535)");
            selectClientPort(address);
        }
    }

    private void selectServerPort() {
        System.out.print("포트 번호 입력: ");
        try {
            int port = sc.nextInt();
            if (port >= 0 && port <= 65535) {
                server = new Server(this, port);
            } else {
                System.out.println("숫자만 입력하세요 (범위: 0~65535)");
                selectServerPort();
            }
        } catch (InputMismatchException e) {
            System.out.println("숫자만 입력하세요 (범위: 0~65535)");
            selectServerPort();
        }
    }

    public void input() {
        String str;
        while ((str = sc.nextLine()) != null) {
            if (client != null) {
                if (str.startsWith("/")) {
                    if (str.split(" ", -1)[0].equalsIgnoreCase("/clear")) {
                        if (str.split(" ").length == 1) {
                            // new ClearCommand(this).execute();
                        }
                    }
                } else {
                    if (!str.equalsIgnoreCase("")) {
                        client.getConnectThread().sendChat(str);
                    }
                }
            } else if (server != null) {
                if (str.startsWith("/")) {
                    if (str.split(" ", -1)[0].equalsIgnoreCase("/clear")) {
                        if (str.split(" ").length == 1) {
                            // new ClearCommand(this).execute();
                        }
                    }
                    if (str.split(" ", -1)[0].equalsIgnoreCase("/setmanager")) {

                    }
                } else {
                    if (!str.equalsIgnoreCase("")) {
                        server.getHostThread().sendChat("Server;" + str);
                    }
                }
            }
        }
        sc.close();
        sc = null;
    }

    public void output(String str) {
        if (usingGUI) {
            chatGUI.getOutputScrollPane().getOutputTextArea().appendText(str);
        } else {
            System.out.println(str);
        }
    }

    public void select(String str) {
        if (str != null) {
            if (str.equalsIgnoreCase("Client")) {
                client = new Client(this);
            } else if (str.equalsIgnoreCase("Server")) {
                server = new Server(this);
            } else {
                chatGUI.dispose();
            }
        } else {
            chatGUI.dispose();
        }
    }

    public ChatGUI getChatGUI() {
        return chatGUI;
    }

    public Client getClient() {
        return client;
    }

    public Server getServer() {
        return server;
    }

    public void setReadyGUI(boolean readyGUI) {
        this.readyGUI = readyGUI;
    }

    public Scanner getScanner() {
        return sc;
    }

    public boolean readyGUI() {
        return readyGUI;
    }

    public boolean usingGUI() {
        return usingGUI;
    }
}