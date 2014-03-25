/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

/**
 *
 * @author Elwin
 */
public class EziPacketRequest {

    private String filename;
    private long partNumber;
    private int partSize;

    public EziPacketRequest(String fileName, long part, int size) {
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
