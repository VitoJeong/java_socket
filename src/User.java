import javax.xml.crypto.Data;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

public class User {
    HashMap<String, DataOutputStream> clientMap = new HashMap<>();

    private final String WELCOME_MSG = "님이 입장하셨습니다.";
    private final String COUNTING_MEMBER = "채팅 참여 인원: ";
    private final String BYE_MSG = "님이 퇴장하셨습니다.";
    private final String SERVER = "SERVER";

    /**
     * 클라이언트를 추가한다.
     */
    public void AddClient(String name, Socket socket) {
        try {
            sendMsg(WELCOME_MSG, SERVER);

            clientMap.put(name, new DataOutputStream(socket.getOutputStream()));

            System.out.println(COUNTING_MEMBER + clientMap.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 클라이언트를 제거한다.
     */
    public synchronized void removeClient(String name) {
        try {
            clientMap.remove(name);
            sendMsg(name + BYE_MSG, SERVER);
            System.out.println(COUNTING_MEMBER + clientMap.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 클라이언트의 메세지를 전송한다.
     */
    public synchronized void sendMsg(String msg, String name) throws IOException {
        Iterator<String> iterator = clientMap.keySet().iterator();
        while (iterator.hasNext()) {
            String clientName = iterator.next();
            clientMap.get(clientName).writeUTF(name + " : " + msg);
        }
    }
}
