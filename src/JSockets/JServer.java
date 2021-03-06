package JSockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class JServer {

    public static ServerSocket ss;
    private DataOutputStream dos;

    public static LinkedList<Socket> sockets = new LinkedList<>();

//    public static List<Socket> sockets = new ArrayList<>();
//    private final static int MAX_CLIENTS = 10;

    public JServer() {
        try {
            ss = new ServerSocket(9001);
            acceptSockets();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Thread listeningtoSocket;

    public void acceptSockets() {

        listeningtoSocket = new Thread(() -> {
            while (true) {
                try {
//                    if (sockets.size() < 10) {
                    Socket s = ss.accept();
                    sockets.add(s);
                    new JEchoThread(s).start();
                    System.out.println("Socket Accepted");
//                    } else {
//                        System.out.println("Max Clients Limit Reached");
//                        break;
//                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });

        listeningtoSocket.start();
    }

    public static void closeThread() {
        listeningtoSocket.interrupt();
        try {
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcastMessages(String msg) {
        try {
            for (Socket s : sockets) {
                try {
                    DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                    dos.writeUTF(msg);
                } catch (IOException e) {
                    try {
                        s.close();
                        sockets.remove(s);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("Exception in JServer");
        }
    }
}