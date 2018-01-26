package JSockets;

import java.io.*;
import java.net.Socket;

public class JEchoThread extends Thread {
    protected Socket socket;

    public JEchoThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {
        DataInputStream inp = null;
        BufferedReader brinp = null;
        DataOutputStream out = null;
        try {
            inp = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            return;
        }
        String line;
        while (true) {
            try {
                line = inp.readUTF();
                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                    socket.close();
                    return;
                } else {
                    JServer.broadcastMessages(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}