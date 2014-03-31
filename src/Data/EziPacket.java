/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

/**
 *
 * @author Elwin
 */
public class EziPacket {
    
    protected long fileHash;
    protected int offset;
    
    protected long getFileHash() {
        return fileHash;
    }
   
    protected void setFileHash(long fileHash) {
        this.fileHash = fileHash;
    }

    protected int getOffset() {
        return offset;
    }

    protected void setOffset(int offset) {
        this.offset = offset;
    }
    
}
