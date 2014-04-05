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

    private EziInfo eziFile;
    private File file;
    private EziFileWriter writer;
    private EziFileReader reader;

    public EziFileManager(EziInfo eziFile) {
        this.eziFile = eziFile;
        initFile();
        this.writer = new EziFileWriter(this.file);
        this.reader = new EziFileReader(this.file);
    }

    
    //Need to be dynamic. Read existing folder and write to own
    private void initFile() {
        this.file = eziFile.getFirstFile();
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
        if (d.getFileInfo().getFullCheckSum().equals(eziFile.getFullCheckSum())) {
            this.writer.writePacket(d);
        }
    }
}
