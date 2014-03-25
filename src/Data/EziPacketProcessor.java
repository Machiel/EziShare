/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import iziShare.EziDownload;
import iziShare.EziPeer;
import iziShare.EziUpload;
import java.util.ArrayList;

/**
 *
 * @author Elwin
 */
public class EziPacketProcessor {

    private ArrayList<EziDownload> downloads;
    private ArrayList<EziUpload> uploads;

    public EziPacketProcessor() {
        this.downloads = new ArrayList<>();
        this.uploads = new ArrayList<>();
    }

    public void processPacket(Object object, EziPeer p) {
        String classname = object.getClass().getSimpleName();

        switch (classname) {
            case "DataPacket":
                routeData((EziPacket) object, p);
                break;
            case "DataRequest":
                routeDataRequest((EziPacketRequest) object, p);
                break;
        }
    }

    private void routeData(EziPacket packet, EziPeer p) {
        for (EziDownload download : downloads) {
            if (download.getFileName().equals(packet.getFileName())) {
                download.processFilePacket(packet, p);
                break;
            }
        }
    }

    private void routeDataRequest(EziPacketRequest request, EziPeer p) {
    }
}
