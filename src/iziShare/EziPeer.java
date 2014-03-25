/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iziShare;

import Data.EziPacketProcessor;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elwin
 */
public class EziPeer implements Serializable {
    
    private Socket socket;
    private InputStream inStr;
    private OutputStream outStr;
    private ObjectInputStream objectInStr;
    private ObjectOutputStream objectOutStr;
    private EziPeerListener listener;

    protected EziPeer(Socket socket, EziPacketProcessor processor) {
        this.socket = socket;
        this.listener = new EziPeerListener(this, processor);
        createStreams();
        createObjectStreams();
    }
    
    private void createStreams(){
        try {
            inStr = socket.getInputStream();
            outStr = socket.getOutputStream();
        } catch (Exception ex) {
            Logger.getLogger(EziPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createObjectStreams() {
        try {
            objectOutStr = new ObjectOutputStream(outStr);
            objectOutStr.flush();
            objectInStr = new ObjectInputStream(inStr);
        } catch (Exception ex) {
            Logger.getLogger(EziPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected Object readObject(){
        Object ob = null;
        try {
            ob = objectInStr.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(EziPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ob;
    }

    protected void writeObject(Object ob){
        try {
            objectOutStr.writeObject(ob);
            objectOutStr.flush();
        } catch (IOException ex) {
            Logger.getLogger(EziPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
