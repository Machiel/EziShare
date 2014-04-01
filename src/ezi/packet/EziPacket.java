/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ezi.packet;

import ezi.file.EziFile;

/**
 *
 * @author Elwin
 */
public class EziPacket {
    
    protected EziFile fileInfo;
    protected int offset;
    protected int byteSize;

    public EziFile getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(EziFile fileInfo) {
        this.fileInfo = fileInfo;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getByteSize() {
        return byteSize;
    }

    public void setByteSize(int byteSize) {
        this.byteSize = byteSize;
    }
    
}
