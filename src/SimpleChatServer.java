import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SimpleChatServer {
    public static void main(String[] args) {
        // 클라이언트와 통신하기 위한 socket
        Socket socket = null;

        // 서버 생성을 위한 serverSocket
        ServerSocket serverSocket = null;

        try {
            // 서버소켓 생성 - 포트바인딩
            serverSocket = new ServerSocket(12000);

            System.out.println("Server is opened!");

            while (true) {
                // 서버 생성, 클라이언트 접속 대기(접속시 소켓 반환)
                socket = serverSocket.accept();

                System.out.println("Server socket is Accepted!  client: " + socket.getRemoteSocketAddress());

                // 클라이언트로부터 데이터를 읽어들이기 위한 입력스트림 생성
                InputStream inputStream = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                // 클라이언트에게 데이터를 내보내기 위한 출력 스트림 생성(auto flush)
                OutputStream outputStream = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8")),true);

                // 클라이언트로부터 데이터를 읽어옴
                // byte[] bytes = reader.readLine().getBytes(StandardCharsets.UTF_8);
                String str = reader.readLine();

                System.out.println("Receive from Client: " + str);

                writer.write(str);

                writer.close();
                outputStream.close();
                reader.close();
                inputStream.close();

                socket.close();

                if (str.equals("stop") || str.isEmpty()) break;
            }

            // 연결 종료
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
