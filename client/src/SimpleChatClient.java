import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class SimpleChatClient {
    public static void main(String[] args) {
        SimpleChatClient simpleChatClient = new SimpleChatClient();
        while (true) {
            boolean isConnect = simpleChatClient.sendSocketData();

            if (!isConnect) break;
        }
    }

    public boolean sendSocketData() {
        // 서버와 통신하기 위한 socket
        Socket socket = null;

        try {
            InetAddress inetAddress = InetAddress.getByName("localhost");

            // 서버로 접속
            socket = new Socket(inetAddress, 12000);

            // 서버로부터 데이터를 읽어들이기 위한 입력스트림
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

            // 키보드(사용자)로부터 읽어들이기 위한 입력스트림
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            // 서버로 내보내기 위한 입력스트림
            PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);

            // 키보드로부터 입력(서버로 보낼 메세지)
            String data = input.readLine();

            // 서버로 데이터 전송
            writer.println(data);


            // 서버로부터 되돌아오는 데이터 읽어들임
            String str = reader.readLine();

            System.out.println("서버로부터 되돌아온 메세지: " + str);

            writer.close();

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return socket != null;
        }
    }
}
