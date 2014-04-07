package ezi.system;

import ezi.connection.EziPeer;
import ezi.file.EziInfo;
import ezi.packet.EziPacketRequest;
import ezi.packet.EziDataPacket;
import ezi.file.EziInfoIndexer;
import java.util.ArrayList;

public class EziDownload {
    //--
    protected EziInfo eziFile;
    private ArrayList<EziPeer> peers;
    private String path;
    private int packetSize;
    private int partSize;

    public EziDownload(EziInfo ticket, String path, int packSize, int partSize) {
        this.path = path;
        this.packetSize = packSize;
        this.partSize = partSize;
        this.eziFile = ticket;
    }

    protected void requestNextPart(EziPeer p) {
        //parts.add(new EziFileIndexer(partCounter, getEziFile().getFileName(), path, packetSize, partSize));
       // p.writeObject(new EziPacketRequest(getEziFile().getFileName(), partCounter, packetSize));
    }

    public void start() {
        for (EziPeer p : peers) {
            requestNextPart(p);
        }
    }

    public void pause() {
    }

    public void delete() {
    }  

    public void processDataPacket(EziDataPacket packet, EziPeer p) {
        
    }
    
    public String getCheckSum(){
        return eziFile.getCheckSum();
    }
}
