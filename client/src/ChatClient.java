import java.io.*;
import java.net.Socket;

public class ChatClient {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12500;
    private static final String INPUT_NAME_MSG = "닉네임을 입력해주세요 : ";

    public static void main(String[] args) {
        Socket socket = null;

        DataInputStream in = null;

        BufferedReader reader = null;

        DataOutputStream out = null;

        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

            InputStream inputStream = socket.getInputStream();
            in = new DataInputStream(inputStream);

            reader = new BufferedReader(new InputStreamReader(System.in));

            OutputStream outputStream = socket.getOutputStream();
            out = new DataOutputStream(outputStream);

            System.out.print(INPUT_NAME_MSG);
            String data = reader.readLine();

            // 닉네임 전송
            out.writeUTF(data);

            // 클라이언트가 채팅 내용을 입력 및 서버로 전송하기 위한 스레드 생성
            Thread thread = new Thread(new Send(out));
            
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 클라이언트의 메인 스레드는 서버로부터 데이터를 읽어들이는것을 반복
        while (true) {
            try {
                String str = in.readUTF();
                System.out.println(str);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
