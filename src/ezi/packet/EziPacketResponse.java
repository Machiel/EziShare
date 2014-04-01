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
public class EziPacketResponse extends EziPacket{

    public enum Response{INCOMPLETE,FILENOTFOUND};
    private Response response; 
    
    public EziPacketResponse(EziFile fileInfo, int offSet, int byteSize, Response r) {
        this.fileInfo = fileInfo;
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
