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

    public EziDataPacket(String id, String checkSum, long offset, byte[] bytes, int byteSize) {
        this.id = id;
        this.checkSum = checkSum;
        this.offset = offset;
        this.bytes = bytes;
        this.byteSize = byteSize;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
