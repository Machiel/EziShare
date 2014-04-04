/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi.packet;

import ezi.file.EziInfo;
import java.io.Serializable;

/**
 *
 * @author Elwin
 */
public class EziDataPacket extends EziPacket implements Serializable {
    
    private byte[] bytes;

    public EziDataPacket(EziInfo fileInfo, int offset, byte[] bytes) 
    {
        this.fileInfo = fileInfo;
        this.offset = offset;
        this.bytes = bytes;
    }
    
    public byte[] getBytes() {
        return bytes;
    }
    
    @Override
    public int getByteSize(){
        return this.bytes.length;
    }
    
}
