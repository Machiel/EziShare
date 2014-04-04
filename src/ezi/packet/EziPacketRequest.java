/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi.packet;

import ezi.file.EziInfo;

/**
 *
 * @author Elwin
 */
public class EziPacketRequest extends EziPacket{

    public EziPacketRequest(EziInfo fileInfo, int offSet, int byteSize) {
        this.fileInfo = fileInfo;
        this.offset = offSet;
        this.byteSize = byteSize;
    }
}
