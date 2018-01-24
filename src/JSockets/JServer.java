package JSockets;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class JServer {

    private List<JClients> clients;

    public JServer() {

        try {

            ServerSocket ss = new ServerSocket(2222);

            while (true) {
                Socket s = ss.accept();
                System.out.println("New Client Accepted");
                new EchoThread(s).start();
                //clients.add(c.c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


//        try {
//            ServerSocket ss = new ServerSocket(2222);
//            Socket s = ss.accept();
//            DataInputStream din = new DataInputStream(s.getInputStream());
//            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
//            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//
//            String str = "", str2 = "";
//            while (!str.equals("stop")) {
//                str = din.readUTF();
//                System.out.println("client says: " + str);
//                str2 = br.readLine();
//                dout.writeUTF(str2);
//                dout.flush();
//            }
//            din.close();
//            s.close();
//            ss.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
