package com.multi;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    private static final int PORT = 8818;

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                System.out.println("About to accept client connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket);
                ServerWorker worker = new ServerWorker(clientSocket);
                worker.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
