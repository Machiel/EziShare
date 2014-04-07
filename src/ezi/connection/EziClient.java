/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi.connection;

import ezi.file.EziInfoIndexer;
import ezi.packet.EziInfoPacket;
import ezi.system.EziUpload;
import ezi.system.EziDistributor;
import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elwin
 */
public final class EziClient implements Runnable {

    private ServerSocket serverSocket;
    private EziDistributor distributor;
    private EziInfoIndexer indexer;
    private EziBroadcastClient broadcastClient;
    private EziBroadCastServer broadcastServer;
    public ArrayList<EziPeer> peers;
    private Thread thread;
    private boolean run = true;

    public EziClient(File shareDirectory, File downloadDirectory) {
        this.indexer = new EziInfoIndexer(shareDirectory, downloadDirectory);
        this.indexer.refreshList();
        
        this.distributor = new EziDistributor();
        this.peers = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(4545);
        } catch (IOException ex) {
            Logger.getLogger(EziUpload.class.getName()).log(Level.SEVERE, null, ex);
        }
        broadcastClient = new EziBroadcastClient();
        broadcastServer = new EziBroadCastServer();
    }

    public void stop() {
        run = false;
        for (EziPeer p : peers) {
            p.stop();
        }
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(EziClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(EziClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        broadcastClient.stop();
        broadcastServer.stop();
        System.out.println("Client: thread stopped");
    }

    public void start() {
        run = true;
        thread = new Thread(this);
        thread.start();
        broadcastClient.start();
        broadcastServer.start();
    }

    protected void acceptSocket() {
        try {
            Socket socket;
            socket = serverSocket.accept();
            boolean equal = false;
            for (EziPeer p : peers) {
                if (p.getSocket().getInetAddress().equals(socket.getInetAddress())) {
                    equal = true;
                    break;
                }
            }

            if (!equal && (socket.getInetAddress().toString().equals(socket.getLocalAddress().toString()))) {
                EziPeer peer = new EziPeer(socket, distributor);
                peer.sendObject(new EziInfoPacket(indexer.getEziInfoList()));
                peers.add(peer);
                System.out.println("Client: Accepted: " + socket.getInetAddress().toString());
            }
        } catch (IOException ex) {
            Logger.getLogger(EziUpload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<EziPeer> getNodes() {
        return this.peers;
    }

    @Override
    public void run() {
        System.out.println("Client: listening");
        while (run) {
            acceptSocket();
        }
    }
}
