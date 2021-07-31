import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        // 서버와 통신하기 위한 socket
        Socket socket = null;

        // 서버로부터 데이터를 읽어들이기 위한 입력스트림
        BufferedReader reader = null;

        // 키보드(사용자)로부터 읽어들이기 위한 입력스트림
        BufferedReader input = null;

        // 서버로 내보내기 위한 입력스트림
        PrintWriter writer = null;

        InetAddress inetAddress = null;

        try {
            inetAddress = InetAddress.getByName("localhost");

            // 서버로 접속
            socket = new Socket(inetAddress, 12000);

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            input = new BufferedReader(new InputStreamReader(System.in));

            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

            System.out.println(socket.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while (true) {
                // 키보드로부터 입력(서버로 보낼 메세지)
                String data = input.readLine();

                // 서버로 데이터 전송
                writer.println(data);

                writer.flush();

                // 서버로부터 되돌아오는 데이터 읽어들임
                String str = reader.readLine();

                System.out.println("서버로부터 되돌아온 메세지: " + str);

                if(!socket.isClosed()) break;
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
