/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ezi.file;

import ezi.packet.EziDataPacket;
import ezi.packet.EziPacketRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elwin
 */
public class EziFileReader {
    
    private File file;
    private FileInputStream input;
   
    protected EziFileReader(File file){
        this.file = file;
        initInput();
    }
    
    private void initInput(){
        try {
            this.input = new FileInputStream(this.file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EziFileIndexer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected EziDataPacket readPacket(EziPacketRequest r) {
        byte[] bytes = null;
        try {
            input.read(bytes , r.getOffset(), r.getByteSize());
        } catch (IOException ex) {
            Logger.getLogger(EziFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new EziDataPacket(r.getFileInfo(), r.getOffset(), bytes);
    }

    protected void closeInut() {
        try {
            this.input.close();
        } catch (IOException ex) {
            Logger.getLogger(EziFileIndexer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
