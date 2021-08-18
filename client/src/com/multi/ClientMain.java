package com.multi;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientMain {
    private final String serverName;
    private final int serverPort;
    private Socket socket;
    private OutputStream serverOut;
    private InputStream serverIn;
    private BufferedReader reader;

    public ClientMain(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    public static void main(String[] args) throws IOException {
        ClientMain client = new ClientMain("localhost", 8818);
        if (!client.connect()) {
            System.out.println("Connect failed.");
        } else {
            System.out.println("Connect successful!");
            if (client.login("ubuntu", "ubuntu")) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Login failed.");
            }
        }
    }

    private boolean login(String login, String password) throws IOException {
        String cmd = "login " + login + " " + password + "\n";
        serverOut.write(cmd.getBytes(StandardCharsets.UTF_8));

        String response = reader.readLine();
        System.out.println("Response Line: " + response);

        return "ok login".equalsIgnoreCase(response);
    }

    private boolean connect() {
        try {
            this.socket = new Socket(serverName, serverPort);
            System.out.println("Client post is " + socket.getLocalPort());
            this.serverOut = socket.getOutputStream();
            this.serverIn = socket.getInputStream();
            this.reader = new BufferedReader(new InputStreamReader(serverIn));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
