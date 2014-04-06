/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi.file;

import ezi.packet.EziDataPacket;
import ezi.packet.EziPacketRequest;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elwin
 */
public class EziFileManager {

    private String fileName;
    private String filePath;
    private String checkSum;
    private long fileSize;
    private File file;
    private EziFileWriter writer;
    private EziFileReader reader;

    public EziFileManager(String filePath, String fileName, long fileSize, String checkSum) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.checkSum = checkSum;
        initFile(filePath, fileName);
        this.writer = new EziFileWriter(this.file, fileSize);
        this.reader = new EziFileReader(this.file);
    }

    private void initFile(String filePath, String fileName) {
        this.file = new File(filePath+"\\"+fileName);
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(EziInfoIndexer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public EziDataPacket readPacket(EziPacketRequest r) {
        return this.reader.readPacket(r);
    }

    public void writePacket(EziDataPacket d) {
        if (d.getCheckSum().equals(checkSum)) {
            this.writer.writePacket(d);
        }
    }
}
