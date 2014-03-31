/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi.connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author Elwin
 */
public class Server implements Runnable {

    private DatagramSocket socket = null;
    private Thread thread = null;
    private boolean run = true;
    private DatagramPacket packet = null;

    protected Server() {
        initVars();
    }

    private void initVars() {
        try {
            this.socket = new DatagramSocket(4445);
        } catch (SocketException ex) {
            System.out.println("ERROR-Server: can't create datagram socket");
        }

        String message = "hello";
        byte[] buf = message.getBytes();
        try {
            InetAddress group = InetAddress.getByName("230.0.0.1");
            packet = new DatagramPacket(buf, buf.length, group, 4446);
        } catch (UnknownHostException ex) {
            System.out.println("ERROR-Server: can't join group");
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
        System.out.println("Server: starting Server");
        while (run) {
            try {
                socket.send(packet);
                Thread.sleep(1000);
            } catch (IOException ex) {
                System.out.println("ERROR-Server: can't send packet");
            } catch (InterruptedException ex) {
                System.out.println("ERROR-Server: can't sleep");
            }
        }
    }
}
