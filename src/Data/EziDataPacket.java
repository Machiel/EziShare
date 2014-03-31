/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.Serializable;

/**
 *
 * @author Elwin
 */
public class EziDataPacket extends EziPacket implements Serializable {
    
    private byte[] bytes;

    protected EziDataPacket(long fileHash, long offset, byte[] bytes) 
    {
        this.fileHash = fileHash;
        this.offset = offset;
        this.bytes = bytes;
    }
    
    protected byte[] getBytes() {
        return bytes;
    }
    
    protected int getSize(){
        return this.bytes.length;
    }
    
}
