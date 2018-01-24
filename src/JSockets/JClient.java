package JSockets;

import JCode.fileHelper;
import JCode.trayHelper;
import objects.Network;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class JClient {

    Socket s;
    DataInputStream dis;

    fileHelper fh = new fileHelper();
    trayHelper th;
    Network network;

    public JClient() {
        try {
            network = fh.getNetworkDetails();
            th = new trayHelper();
            s = new Socket(network.getHost(), 2222);
            dis = new DataInputStream(s.getInputStream());
            boolean isClientOpen = true;
            while (isClientOpen) {
                try {
                    th.displayNotification("Email Received", "Email Received from " + dis.readUTF());
                } catch (IOException e) {
                    // The client may have closed the socket.
                    isClientOpen = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
