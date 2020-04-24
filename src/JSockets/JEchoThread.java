package JSockets;

import Email.EmailDashController;

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
                    if (line.equals("R")) {  //Server has been asked to reload Emails
//                        EmailDashController.reload = true;
//                        EmailDashController.list_emailsF.getItems().clear();
                        EmailDashController.loadEmailsStatic();
                    }
                    JServer.broadcastMessages(line);
                }
            } catch (IOException e) {
//                e.printStackTrace();
                System.out.println("Connection Error in JEchoThread");
                return;
            }
        }
    }
}