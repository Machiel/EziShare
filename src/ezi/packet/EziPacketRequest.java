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
public class EziPacketRequest extends EziPacket implements Serializable{

    public EziPacketRequest(String id, String checkSum, long offset, int byteSize) {
        this.id = id;
        this.checkSum = checkSum;
        this.offset = offset;
        this.byteSize = byteSize;
    }
}
