/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi.file;

import ezi.packet.EziDataPacket;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elwin
 */
public class EziFileWriter {

    private EziFile eziFile;
    private String path;
    private File file;
    private FileOutputStream output;

    protected EziFileWriter(EziFile eziFile, String path, File file) {
        this.eziFile = eziFile;
        this.path = path;
        this.file = file;
        initOutput();
    }

    private void initOutput() {
        try {
            this.output = new FileOutputStream(this.file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EziFileIndexer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void writePacket(EziDataPacket p) {
        try {
            output.write(p.getBytes(), p.getOffset(), p.getSize());
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(EziFileIndexer.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateProgress();
    }

    protected void updateProgress() {
        try {
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(EziFileIndexer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
