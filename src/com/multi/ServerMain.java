package com.multi;

public class ServerMain {
    private static final int PORT = 8818;

    public static void main(String[] args) {
        Server server = new Server(PORT);
        server.start();
    }

}
