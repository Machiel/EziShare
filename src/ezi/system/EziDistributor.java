/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi.system;

import ezi.connection.EziPeer;
import ezi.file.EziInfo;
import ezi.packet.EziDataPacket;
import ezi.packet.EziInfoPacket;
import ezi.packet.EziPacketRequest;
import ezi.packet.EziPacketResponse;
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
            case "EziDataPacket":
                routePacket((EziDataPacket) object, p);
                break;
            case "EziPacketRequest":
                routeDataRequest((EziPacketRequest) object, p);
                break;
            case "EziPacketResponse":
                routeDataResponse((EziPacketResponse) object, p);
                break;
            case "EziInfoPacket":
                routeInfoPacket((EziInfoPacket) object, p);
                break;
        }
    }

    private void routePacket(EziDataPacket dataPacket, EziPeer p) {
        for (EziDownload download : downloads) {
            if (download.getCheckSum().equals(dataPacket.getCheckSum())) {
                download.processDataPacket(dataPacket, p);
                break;
            }
        }
    }

    private void routeDataRequest(EziPacketRequest request, EziPeer p) {

    }

    private void routeDataResponse(EziPacketResponse response, EziPeer p) {

    }
    
    private void routeInfoPacket(EziInfoPacket response, EziPeer p) {
        System.out.println("Files:");
        for(EziInfo ezi : response.getFiles()){
            System.out.println(ezi.getFileName());
        }
    }
}
