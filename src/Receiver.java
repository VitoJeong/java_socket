import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Receiver implements Runnable {
    Socket socket;
    DataInputStream in;
    String name;
    User user;

    public Receiver(User user, Socket socket) {
        this.user = user;
        this.socket = socket;

        try {
            InputStream inputStream = socket.getInputStream();
            in = new DataInputStream(inputStream);

            // 최초 사용자로부터 닉네임을 읽어들임
            this.name = in.readUTF();
            // 사용자 추가해줍니다.
            user.AddClient(name, socket);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void run() {
        try {
            String msg = in.readUTF();
            user.sendMsg(msg, name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
