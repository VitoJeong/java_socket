package com.multi;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServerWorker extends Thread {

    private final Socket clientSocket;
    private final Server server;
    private String login = null;
    private OutputStream outputStream;
    private Set<String> topicSet = new HashSet<>();

    public ServerWorker(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            handleClientSocket(clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleClientSocket(Socket clientSocket) throws IOException, InterruptedException {
        InputStream inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = StringUtils.split(line);
            if (tokens != null && tokens.length > 0) {
                String cmd = tokens[0];
                if ("logoff".equalsIgnoreCase(cmd) || "quit".equalsIgnoreCase(cmd)) {
                    handleLogoff();
                    break;
                } else if ("login".equalsIgnoreCase(cmd)) {
                    if (!isLoginSuccess(outputStream, tokens)) break;
                } else if ("msg".equalsIgnoreCase(cmd)) {
                    String[] tokenMsg = StringUtils.split(line, null, 3);
                    handleMessage(tokenMsg);
                } else if ("join".equalsIgnoreCase(cmd)) {
                    handleJoin(tokens);
                } else if ("leave".equalsIgnoreCase(cmd)) {
                    handleLeave(tokens);
                } else {
                    String msg = login + ": " + cmd + "\n";
                    outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
                }
            }
            // String msg = "You typed:" + line + "\n";
            // outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
        }
        clientSocket.close();
    }

    private void handleLeave(String[] tokens) throws IOException {
        if (tokens.length > 1) {
            String topic = tokens[1];
            topicSet.remove(topic);

            String msg = topic.substring(1) + ": Bye!";
            // outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
            this.send(msg);
        }
    }

    public boolean isMemberOfTopic(String topic) {
        return topicSet.contains(topic);
    }

    private void handleJoin(String[] tokens) throws IOException {
        if (login != null) {
            String msg = "Please sign in to join a topic";
            outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
        }
        if (tokens.length > 1) {
            String topic = tokens[1];
            topicSet.add(topic);

            String msg = "Welcome to " + topic.substring(1);
            // outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
            this.send(msg);
        }
    }

    // format: "msg" "login" body...
    // format: "msg" "#topic" body...
    private void handleMessage(String[] tokens) throws IOException {
        String sendTo = tokens[1];
        String body = tokens[2];

        boolean isTopic = sendTo.charAt(0) == '#';

        List<ServerWorker> workerList = server.getWorkerList();
        for (ServerWorker worker : workerList) {
            if (isTopic) {
                if (worker.isMemberOfTopic(sendTo)) {
                    String msg = "msg " + sendTo + ": " + login + " " + body + "\n";
                    worker.send(msg);
                }
            } else {
                if (sendTo.equalsIgnoreCase(worker.getLogin())) {
                    String msg = "msg " + login + " " + body + "\n";
                    worker.send(msg);
                }
            }
        }
    }


    private void handleLogoff() throws IOException {
        server.removeWorker(this);
        List<ServerWorker> workerList = server.getWorkerList();

        // send other online users current user's status
        String onlineMsg = "offline " + login + "\n";
        for (ServerWorker worker : workerList) {
            if (!login.equals(worker.getLogin())) {
                worker.send(onlineMsg);
            }
        }

        clientSocket.close();
    }

    public String getLogin() {
        return login;
    }

    private boolean isLoginSuccess(OutputStream outputStream, String[] tokens) throws IOException {
        if (tokens.length == 3) {
            String login = tokens[1];
            String password = tokens[2];

            if (checkAccess(login, password)) {
                String msg = "ok login\n";
                outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
                this.login = login;
                System.out.println("User logged in successful!");

                List<ServerWorker> workerList = server.getWorkerList();

                // send current user all other online logins
                for (ServerWorker worker : workerList) {
                    if (worker.getLogin() != null) {
                        if (!login.equals(worker.getLogin())) {
                            String msg2 = "online " + worker.getLogin() + "\n";
                            send(msg2);
                        }
                    }
                }

                // send other online users current user's status
                String onlineMsg = "online " + login + "\n";
                for (ServerWorker worker : workerList) {
                    if (!login.equals(worker.getLogin())) {
                        worker.send(onlineMsg);
                    }
                }
            } else {
                String msg = "invalid access\n";
                outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
                return false;
            }
        }

        return true;
    }

    private void send(String onlineMsg) throws IOException {
        if (login != null) {
            outputStream.write(onlineMsg.getBytes(StandardCharsets.UTF_8));
        }
    }

    private boolean checkAccess(String login, String password) {
        return (login.equals("root") && password.equals("1234")) || (login.equals("ubuntu") && password.equals("ubuntu"));
    }
}