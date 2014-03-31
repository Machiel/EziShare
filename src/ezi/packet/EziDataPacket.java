/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi.packet;

import java.io.Serializable;

/**
 *
 * @author Elwin
 */
public class EziDataPacket extends EziPacket implements Serializable {
    
    private byte[] bytes;

    protected EziDataPacket(long fileHash, int offset, byte[] bytes) 
    {
        this.fileHash = fileHash;
        this.offset = offset;
        this.bytes = bytes;
    }
    
    public byte[] getBytes() {
        return bytes;
    }
    
    public int getSize(){
        return this.bytes.length;
    }
    
}
