/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elwin
 */
public class EziBroadcastClient implements Runnable {

    private MulticastSocket socket = null;
    private DatagramPacket packet = null;
    private Thread thread = null;
    private boolean run = true;

    public EziBroadcastClient() {
        try {
            socket = new MulticastSocket(13338);
            InetAddress address = InetAddress.getByName("224.0.0.1");
            socket.joinGroup(address);
            byte[] buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println("ERROR-Client: could not initialize variables");
        }
    }

    public void stop() {
        run = false;
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(EziClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        socket.close();
        System.out.println("Broadcast Client: stopped");
    }

    public void start() {
        run = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        System.out.println("Broadcast Client: started");
        while (run) {
            try {
                System.out.println("Broadcast Client: listening for packets");
                socket.receive(packet);
                Socket s = new Socket(packet.getAddress(), 4545);
                System.out.println("Broadcast Client: packet recieved ");
            } catch (IOException ex) {
                System.out.println("Broadcast Client-ERROR-: could not recieve packet");
            }
        }
    }
}
