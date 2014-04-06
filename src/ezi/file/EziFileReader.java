/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi.file;

import ezi.packet.EziDataPacket;
import ezi.packet.EziPacketRequest;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elwin
 */
public class EziFileReader {

    private File file;
    private RandomAccessFile input;

    protected EziFileReader(File file) {
        this.file = file;
        initInput();
    }

    private void initInput() {
        try {
            this.input = new RandomAccessFile(this.file, "r");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EziInfoIndexer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected EziDataPacket readPacket(EziPacketRequest r) {
        byte[] bytes = new byte[r.getByteSize()];
        int size = 0;
        try {
            input.seek(r.getOffset());
            size = input.read(bytes, 0, r.getByteSize());
        } catch (IOException ex) {
            Logger.getLogger(EziFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        EziDataPacket dataPacket = new EziDataPacket (r.getId(), r.getCheckSum(), r.getOffset(), bytes, size);
        return dataPacket;
    }

    protected void closeInput() {
        try {
            this.input.close();
        } catch (IOException ex) {
            Logger.getLogger(EziInfoIndexer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
