/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eshare;

import ezi.file.EziFileManager;
import ezi.file.EziInfo;
import ezi.file.EziInfoIndexer;
import ezi.packet.EziDataPacket;
import ezi.packet.EziPacketRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Elwin
 */
public class Eshare {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        EziInfoIndexer system = new EziInfoIndexer("C:\\Users\\Elwin\\Downloads\\GrabIt Downloads\\workaholics.s04e11.720p.hdtv.x264-remarkable.sample.mkv", "C:\\testSpace\\ezis");
        ArrayList<EziInfo> files = system.getEziInfoList();
        EziInfo ezi = files.get(0);
        ezi.generateCheckSum();
        EziFileManager reader = new EziFileManager("C:\\Users\\Elwin\\Downloads\\GrabIt Downloads\\workaholics.s04e11.720p.hdtv.x264-remarkable.sample.mkv", ezi.getFileName(), ezi.getFileSize(), ezi.getCheckSum());
        EziFileManager writer = new EziFileManager("C:\\testSpace", ezi.getFileName(), ezi.getFileSize(), ezi.getCheckSum());

        long size = files.get(0).getFileSize();
        long offset = 0;
        while (offset < size) {
            EziDataPacket packet = reader.readPacket(new EziPacketRequest(ezi.getEziId(), ezi.getCheckSum(), offset, 5242880));
            offset+= 5242880;
            writer.writePacket(packet);
        }

    }
}
