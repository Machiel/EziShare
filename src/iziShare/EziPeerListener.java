/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iziShare;

import Data.EziPacketProcessor;

/**
 *
 * @author Elwin
 */
public class EziPeerListener implements Runnable {

    private EziPeer peer;
    private Thread thread;
    private EziPacketProcessor processor;
    private boolean run = true;

    protected EziPeerListener(EziPeer peer, EziPacketProcessor processor) {
        this.peer = peer;
        this.processor = processor;
    }
    
    protected void stopListening(){
        run = false;
        thread = null;
    }

    protected void startListening(){
        run = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (run) {
            processor.processPacket(peer.readObject(), peer);
        }
    }
}
