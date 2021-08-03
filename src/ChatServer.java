import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static void main(String[] args){

        ServerSocket serverSocket = null;

        Socket socket = null;

        // 채팅방에 접속한 클라이언트 관리 객체
        User user = new User();

        int count = 0;

        Thread[] threads = new Thread[10];

        try {
            serverSocket = new ServerSocket(12500);

            while (true) {
                socket = serverSocket.accept();

                threads[count] = new Thread(new Receiver(user, socket));
                threads[count].start();
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
