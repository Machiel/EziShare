/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi.connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elwin
 */
public class EziBroadCastServer implements Runnable {

    private DatagramSocket socket = null;
    private Thread thread = null;
    private boolean run = true;
    private DatagramPacket packet = null;

    public EziBroadCastServer() {
        initVars();
    }

    private void initVars() {
        try {
            this.socket = new DatagramSocket();
            this.socket.setReuseAddress(true);
            if(!this.socket.isBound()){
                this.socket.bind(new InetSocketAddress(13338));
            }
        } catch (SocketException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Broadcast Server-ERROR-: can't create datagram socket");
        }

        String message = "hello";
        byte[] buf = message.getBytes();
        try {
            InetAddress group = InetAddress.getByName("224.0.0.1");
            packet = new DatagramPacket(buf, buf.length, group, 13338);
        } catch (UnknownHostException ex) {
            System.out.println("Broadcast Server-ERROR-: can't join group");
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
        System.out.println("Broadcast Server: stopped");
    }

    public void start() {
        run = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        System.out.println("Broadcast Server: started");
        while (run) {
            try {
                System.out.println("Broadcast Server: packet sent");
                socket.send(packet);
                Thread.sleep(5000);
            } catch (IOException ex) {
                System.out.println("Broadcast Server-ERROR-: can't send packet");
            } catch (InterruptedException ex) {
                System.out.println("Broadcast Server-ERROR-: can't sleep");
            }
        }
    }
}
