/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi.packet;

import ezi.system.EziDownload;
import ezi.connection.EziPeer;
import ezi.system.EziUpload;
import java.util.ArrayList;

/**
 *
 * @author Elwin
 */
public class EziDistributor {

    private ArrayList<EziDownload> downloads;
    private ArrayList<EziUpload> uploads;

    public EziDistributor() {
        this.downloads = new ArrayList<>();
        this.uploads = new ArrayList<>();
    }

    public void processPacket(Object object, EziPeer p) {
        String classname = object.getClass().getSimpleName();

        switch (classname) {
            case "DataPacket":
                routePacket((EziDataPacket) object, p);
                break;
            case "DataRequest":
                routeDataRequest((EziPacketRequest) object, p);
                break;
        }
    }

    private void routePacket(EziDataPacket packet, EziPeer p) {
        for (EziDownload download : downloads) {
            if (download.getFileHash() == (packet.getFileHash())) {
                download.processFilePacket(packet, p);
                break;
            }
        }
    }

    private void routeDataRequest(EziPacketRequest request, EziPeer p) {
    }
}
