import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        Socket socket = null;

        BufferedReader reader = null;

        BufferedReader input = null;

        PrintWriter writer = null;

        InetAddress inetAddress = null;

        try {
            inetAddress = InetAddress.getByName("서버 주소 입력");

            socket = new Socket(inetAddress, 12000);

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            input = new BufferedReader(new InputStreamReader(System.in));

            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

            System.out.println(socket.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String data = input.readLine();

            writer.println(data);

            writer.flush();

            String str = reader.readLine();

            System.out.println("서버로부터 되돌아온 메세지: " + str);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
