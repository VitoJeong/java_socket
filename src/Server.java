import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        Socket socket = null; // Client와 통신하기 위한 socket

        ServerSocket serverSocket = null;

        BufferedReader reader = null;

        PrintWriter writer = null;

        try {
            serverSocket = new ServerSocket(12000);
        } catch (IOException e) {
            System.out.println("해당 포트가 열려있습니다!");
            e.printStackTrace();
        }

        try {
            System.out.println("서버 오픈!");
            socket = serverSocket.accept();

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

            String str = null;

            str = reader.readLine();

            System.out.println("client로 부터 온 메세지: " + str);

            writer.write(str);
            writer.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
