package System;

import Connection.EziPeer;
import Data.EziFile;
import Data.EziPacketRequest;
import Data.EziDataPacket;
import Data.EziFileIndexer;
import java.util.ArrayList;

public class EziDownload {
    //--
    private EziFile ticket;
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
        this.ticket = ticket;
        parts = new ArrayList<>();
    }

    protected void requestNextPart(EziPeer p) {
        partCounter++;
        parts.add(new EziFileIndexer(partCounter, ticket.getFileName(), path, packetSize, partSize));
        p.writeObject(new EziPacketRequest(ticket.getFileName(), partCounter, packetSize));
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

    public String getFileName() {
        return ticket.getFileName();
    }

    public void processFilePacket(EziDataPacket packet, EziPeer p) {
        for (EziFileIndexer part : parts) {
            if (part.getPartId() == packet.getPartId()) {
                part.processPacket(packet);
                if (part.isDone()) {
                    part = null;
                    parts.remove(part);
                    requestNextPart(p);
                }
                break;
            }
        }
    }
}
