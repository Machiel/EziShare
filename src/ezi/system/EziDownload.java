package ezi.system;

import ezi.connection.EziPeer;
import ezi.file.EziFile;
import ezi.packet.EziPacketRequest;
import ezi.packet.EziDataPacket;
import ezi.file.EziFileIndexer;
import java.util.ArrayList;

public class EziDownload {
    //--
    protected EziFile eziFile;
    private ArrayList<EziPeer> peers;
    private ArrayList<EziFileIndexer> parts;
    private int partCounter = 1;
    //--
    private String path;
    private int packetSize;
    private int partSize;

    public EziDownload(EziFile ticket, String path, int packSize, int partSize) {
        this.path = path;
        this.packetSize = packSize;
        this.partSize = partSize;
        this.eziFile = ticket;
        parts = new ArrayList<>();
    }

    protected void requestNextPart(EziPeer p) {
        partCounter++;
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
    
    

    public void processFilePacket(EziDataPacket packet, EziPeer p) {
        for (EziFileIndexer part : parts) {
          //  if (part.getPartId() == packet.getPartId()) {
          //      part.processPacket(packet);
           //     if (part.isDone()) {
           //         part = null;
          //          parts.remove(part);
           //         requestNextPart(p);
           //     }
          //      break;
          //  }
        }
    }

    public EziFile getEziFile() {
        return eziFile;
    }

    public void setEziFile(EziFile eziFile) {
        this.eziFile = eziFile;
    }
}
