package JSockets;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class JClient {

    Socket s;
    DataInputStream dis;

    public JClient() {
        try {
            s = new Socket("localhost", 2222);
            dis = new DataInputStream(s.getInputStream());
            while (!dis.readUTF().equals("STOP"))
                System.out.println("Message from server: " + dis.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
