/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi.connection;

import ezi.system.EziDistributor;
import java.io.Serializable;
import java.net.Socket;

/**
 *
 * @author Elwin
 */
public class EziPeer implements Serializable {
    
    private Socket socket;
    private EziPeerListener listener;
    private EziDistributor distributor;

    protected EziPeer(Socket socket, EziDistributor distributor) {
        this.socket = socket;
        this.distributor = distributor;
        this.listener = new EziPeerListener(this, distributor);
        this.listener.startListening();
        System.out.println("a");
    }
    
    protected void stop(){
        listener.stopListening();
    }
    
    protected Socket getSocket(){
        return this.socket;
    }
    
    public void sendObject(Object ob){
        listener.writeObject(ob);
    }
}
