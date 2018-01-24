package JSockets;

import java.io.BufferedReader;
<<<<<<< HEAD
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

=======
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JServer {

    private ServerSocket ss;
    private DataOutputStream dos;

    public static List<Socket> sockets = new ArrayList<>();
    private final static int MAX_CLIENTS = 10;

    Scanner s = new Scanner(System.in);

    public JServer() {
        try {
            ss = new ServerSocket(2222);
            acceptSockets();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptSockets() {
        new Thread(() -> {
            while (true) {
                try {
                    if (sockets.size() < 10) {
                        Socket s = ss.accept();
                        sockets.add(s);
                        System.out.println("Socket Accepted");
                    } else {
                        System.out.println("Max Clients Limit Reached");
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void broadcastMessages(String msg) {
        for (Socket s : sockets) {
            try {
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                dos.writeUTF(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
>>>>>>> cee20e9fc4ebca775e87beeaef5db36f6f0417cf
}
