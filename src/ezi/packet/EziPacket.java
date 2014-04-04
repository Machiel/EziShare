/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ezi.packet;

import ezi.file.EziInfo;

/**
 *
 * @author Elwin
 */
public class EziPacket {
    
    protected EziInfo fileInfo;
    protected int offset;
    protected int byteSize;

    public EziInfo getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(EziInfo fileInfo) {
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
