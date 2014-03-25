/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.Serializable;

/**
 *
 * @author Elwin
 */
public class EziPacket implements Serializable {

    private int partId;
    private String fileName;
    private byte[] bytes;

    protected EziPacket() {
    }

    public int getPartId() {
        return partId;
    }

    protected String getFileName() {
        return fileName;
    }

    protected byte[] getBytes() {
        return bytes;
    }
}
