/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi.packet;

/**
 *
 * @author Elwin
 */
public class EziPacketResponse {

    private String filename;
    private long partNumber;
    private int partSize;

    public EziPacketResponse(String fileName, long part, int size) {
        this.filename = fileName;
        this.partNumber = part;
        this.partSize = size;
    }

    protected String getFilename() {
        return filename;
    }

    protected long getPartNumber() {
        return partNumber;
    }

    protected int getPartSize() {
        return partSize;
    }
}
