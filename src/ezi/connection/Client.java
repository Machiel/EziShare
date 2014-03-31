/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi.connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 *
 * @author Elwin
 */
public class Client implements Runnable {

    private MulticastSocket socket = null;
    private DatagramPacket packet = null;
    private Thread thread = null;
    private boolean run = true;

    protected Client() {
        try {
            socket = new MulticastSocket(4446);
            InetAddress address = InetAddress.getByName("230.0.0.1");
            socket.joinGroup(address);
            byte[] buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
        } catch (IOException ex) {
            System.out.println("ERROR-Client: could not initialize variables");
        }
    }

    protected void stop() {
        run = false;
        thread = null;
    }

    protected void start() {
        run = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (run) {
            try {
                socket.receive(packet);
            } catch (IOException ex) {
                System.out.println("ERROR-Client: could not recieve packet");
            }
        }
    }
}
