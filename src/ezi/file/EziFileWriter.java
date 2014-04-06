/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi.file;

import ezi.packet.EziDataPacket;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elwin
 */
public class EziFileWriter {

    private File file;
    private RandomAccessFile output;

    protected EziFileWriter(File file, long size) {
        this.file = file;
        initOutput(size);
    }

    private void initOutput(long size) {
        try {
            this.output = new RandomAccessFile(this.file, "rw");
            this.output.setLength(size);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EziInfoIndexer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EziFileWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void writePacket(EziDataPacket p) {
        try {
            output.seek(p.getOffset());
            output.write(p.getBytes(), 0, p.getByteSize());
        } catch (IOException ex) {
            Logger.getLogger(EziInfoIndexer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void closeOutput() {
        try {
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(EziInfoIndexer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
