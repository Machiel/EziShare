/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi.packet;

import ezi.file.EziFile;

/**
 *
 * @author Elwin
 */
public class EziPacketRequest extends EziPacket{

    public EziPacketRequest(EziFile fileInfo, int offSet, int byteSize) {
        this.fileInfo = fileInfo;
        this.offset = offSet;
        this.byteSize = byteSize;
    }
}
