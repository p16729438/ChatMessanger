package com.p16729438.ChatMessanger;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
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
            BufferedReader thisVersionReader = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("/resources/version.txt")));
            String thisVersion = thisVersionReader.readLine();
            thisVersionReader.close();
            BufferedReader latestVersionReader = new BufferedReader(new InputStreamReader(new URL("http://16729438.kro.kr/ChatMessanger/version/version.txt").openStream()));
            String latestVersion = latestVersionReader.readLine();
            latestVersionReader.close();
            if (Integer.parseInt(thisVersion.split("\\.", -1)[0]) < Integer.parseInt(latestVersion.split("\\.", -1)[0])) {
                output("새로운 버전이 있습니다");
                output("현재 버전: " + thisVersion + ", 최신 버전: " + latestVersion);
                output("http://16729438.kro.kr");
                output("위 링크에 접속하여 최신 버전을 다운로드해주세요");
                return false;
            } else if (Integer.parseInt(thisVersion.split("\\.", -1)[0]) == Integer.parseInt(latestVersion.split("\\.", -1)[0])) {
                if (Integer.parseInt(thisVersion.split("\\.", -1)[1]) < Integer.parseInt(latestVersion.split("\\.", -1)[1])) {
                    output("새로운 버전이 있습니다");
                    output("현재 버전: " + thisVersion + ", 최신 버전: " + latestVersion);
                    output("http://16729438.kro.kr");
                    output("위 링크에 접속하여 최신 버전을 다운로드해주세요");
                    return false;
                } else if (Integer.parseInt(thisVersion.split("\\.", -1)[1]) == Integer.parseInt(latestVersion.split("\\.", -1)[1])) {
                    if (Integer.parseInt(thisVersion.split("\\.", -1)[2]) < Integer.parseInt(latestVersion.split("\\.", -1)[2])) {
                        output("새로운 버전이 있습니다");
                        output("현재 버전: " + thisVersion + ", 최신 버전: " + latestVersion);
                        output("http://16729438.kro.kr");
                        output("위 링크에 접속하여 최신 버전을 다운로드해주세요");
                        return false;
                    } else if (Integer.parseInt(thisVersion.split("\\.", -1)[2]) == Integer.parseInt(latestVersion.split("\\.", -1)[2])) {
                        return true;
                    } else {
                        output("클라이언트가 변조되었거나 서버로부터 가져온 버전 정보가 비정상적일 수 있습니다.");
                        output("현재 버전: " + thisVersion + ", 최신 버전: " + latestVersion);
                        output("http://16729438.kro.kr");
                        output("위 링크에 접속하여 최신 버전을 다운로드해주세요");
                        return false;
                    }
                }
            }
        } catch (IndexOutOfBoundsException | IOException | NumberFormatException e) {
            output("버전 확인 중에 오류가 발생했습니다.");
            e.printStackTrace();
            return false;
        }
        return false;
    }

    private void selectOption() {
        System.out.print("옵션 선택(Client: c, Server: s, exit: any Key): ");
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
        System.out.print("IP 주소 입력: ");
        String address = sc.nextLine();
        selectClientPort(address);
    }

    private void selectClientPort(String address) {
        System.out.print("포트 번호 입력: ");
        try {
            int port = Integer.parseInt(sc.nextLine());
            if (port >= 0 && port <= 65535) {
                client = new Client(this, address, port);
            } else {
                System.out.println("숫자만 입력하세요 (범위: 0~65535)");
                selectClientPort(address);
            }
        } catch (NumberFormatException e) {
            System.out.println("숫자만 입력하세요 (범위: 0~65535)");
            selectClientPort(address);
        }
    }

    private void selectServerPort() {
        System.out.print("포트 번호 입력: ");
        try {
            int port = Integer.parseInt(sc.nextLine());
            if (port >= 0 && port <= 65535) {
                server = new Server(this, port);
            } else {
                System.out.println("숫자만 입력하세요 (범위: 0~65535)");
                selectServerPort();
            }
        } catch (NumberFormatException e) {
            System.out.println("숫자만 입력하세요 (범위: 0~65535)");
            selectServerPort();
        }
    }

    private void input() {
        String str;
        while ((str = sc.nextLine()) != null) {
            if (client != null) {
                if (client.getConnectThread().getNickname() != null && !client.getConnectThread().isRequestingNickname()) {
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
                } else {
                    client.getConnectThread().requestNickname(str);
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

    public boolean readyGUI() {
        return readyGUI;
    }

    public boolean usingGUI() {
        return usingGUI;
    }
}