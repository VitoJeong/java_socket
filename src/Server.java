import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        // 클라이언트와 통신하기 위한 socket
        Socket socket = null;

        // 서버 생성을 위한 serverSocket
        ServerSocket serverSocket = null;

        // 클라이언트로부터 데이터를 읽어들이기 위한 입력스트림
        BufferedReader reader = null;

        // 클라이언트에게 데이터를 내보내기 위한 출력 스트림
        PrintWriter writer = null;

        try {
            // 서버소켓 생성 - 포트바인딩
            serverSocket = new ServerSocket(12000);
        } catch (IOException e) {
            System.out.println("해당 포트가 열려있습니다!");
            e.printStackTrace();
        }

        try {
            System.out.println("서버 오픈!");
            while (true) {
                // 서버 생성, 클라이언트 접속 대기(접속시 소켓 반환)
                socket = serverSocket.accept();

                System.out.println("연결된 사용자: " + socket.getRemoteSocketAddress());

                // 입력스트림 생성
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // 출력스트림 생성
                writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

                // 클라이언트로부터 데이터를 읽어옴
                String str = reader.readLine();

                System.out.println("클라이언트로 부터 온 메세지: " + str);

                writer.write(str);
                writer.flush();

                if (str.equals("stop") || str.isEmpty()) break;
            }

            // 연결 종료
            socket.close();

            // 서버 중지
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
