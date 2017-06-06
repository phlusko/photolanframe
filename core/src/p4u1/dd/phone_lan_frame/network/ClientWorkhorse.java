package p4u1.dd.phone_lan_frame.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Paul on 12/20/2016.
 */

public class ClientWorkhorse implements Runnable {
    public Socket socket = null;
    public volatile MessageHolder messageHolder = new MessageHolder();
    PrintWriter out;
    public ClientWorkhorse (Socket arg0) {
        socket = arg0;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String arg0) {
        out.println(arg0);
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            String fromServer;

            while ((fromServer = in.readLine()) != null) {
                messageHolder.addMessage(fromServer);
                if (fromServer.equals("Bye."))
                    break;
            }
        } catch (UnknownHostException e) {
            System.exit(1);
        } catch (IOException e) {
        }
    }
}
