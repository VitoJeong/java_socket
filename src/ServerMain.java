import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class ServerMain {
    private static final int PORT = 8818;

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                System.out.println("About to accept client connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket);
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            handleClientSocket(clientSocket);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void handleClientSocket(Socket clientSocket) throws IOException, InterruptedException {
        OutputStream outputStream = clientSocket.getOutputStream();
        for (int i = 0; i < 10; i++) {
            outputStream.write(("Time now is " + new Date() + "\n").getBytes(StandardCharsets.UTF_8));
            Thread.sleep(1000);
        }
        clientSocket.close();
    }
}
