import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Send implements Runnable{
    DataOutputStream outputStream = null;
    
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public Send(DataOutputStream outputStream) {
        this.outputStream = outputStream;
    }
    
    @Override
    public void run() {
        while (true) {

            try {
                // 키보드로부터 입력받음
                String msg = reader.readLine();
                // 서버로 전송
                outputStream.writeUTF(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
