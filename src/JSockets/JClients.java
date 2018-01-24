package JSockets;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class JClients {

    public JClients() {
        try {
            Socket s = new Socket("localhost", 2222);
            DataInputStream din = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String str = "", str2 = "";
            synchronized (this) {
//                while (true) {
//                    str = br.readLine();
//                    dout.writeUTF(str);
//                    dout.flush();
//                    str2 = din.readUTF();
//                    System.out.println("Server says: " + str2);
//                }

            }

            dout.close();
            s.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

