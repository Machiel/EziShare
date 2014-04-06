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
public class EziPacketResponse extends EziPacket implements Serializable{

    public enum Response{INCOMPLETE,FILENOTFOUND};
    private Response response; 
    
    public EziPacketResponse(String id, String checkSum, int offSet, int byteSize, Response r) {
        this.id = id;
        this.checkSum = checkSum;
        this.offset = offSet;
        this.byteSize = byteSize;
        this.response = r;
    }
    
    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
