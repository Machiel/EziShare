/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi.connection;

import ezi.system.EziDistributor;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elwin
 */
public class EziPeerListener implements Runnable {

    private EziPeer peer;
    private Thread thread;
    private EziDistributor distributor;
    private ObjectInputStream objectInStr;
    private ObjectOutputStream objectOutStr;
    private boolean run = true;

    protected EziPeerListener(EziPeer peer, EziDistributor distributor) {
        this.peer = peer;
        this.distributor = distributor;
        createObjectStreams();
    }

    private void createObjectStreams() {
        try {
            objectOutStr = new ObjectOutputStream(peer.getSocket().getOutputStream());
            objectOutStr.flush();
            objectInStr = new ObjectInputStream(peer.getSocket().getInputStream());
        } catch (Exception ex) {
            Logger.getLogger(EziPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected Object readObject(){
        Object ob = null;
        try {
            ob = objectInStr.readObject();
            System.out.println("Peer: "+ob.getClass().toString()+" recieved");
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(EziPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ob;
    }

    protected void writeObject(Object ob){
        try {
            objectOutStr.writeObject(ob);
            objectOutStr.flush();
            System.out.println("Peer: "+ob.getClass().toString()+" send");
        } catch (IOException ex) {
            Logger.getLogger(EziPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void stopListening(){
        run = false;
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(EziClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            peer.getSocket().close();
        } catch (IOException ex) {
            Logger.getLogger(EziPeerListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Peer Listener: stopped");
    }

    protected void startListening(){
        run = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (run) {
            System.out.println("Peer Listener: Listening");
            distributor.processPacket(readObject(), peer);
        }
    }
}
