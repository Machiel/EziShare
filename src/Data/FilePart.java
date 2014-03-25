/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elwin
 */
public class FilePart {
    
    private int partId;
    private String fileName;
    private OutputStream output;
    private int packetsProcessed = 0;
    private boolean done = false;
    private String path;
    private int packetSize;
    private int partSize;
    
    public FilePart(int partid, String filename, String path, int packSize, int partSize){
        this.path = path;
        this.packetSize = packSize;
        this.partSize = partSize;
        this.partId = partid;
        this.fileName = filename;
        initFile();
    }
    
    private void initFile(){
        File file = new File(path+this.fileName);
        file.delete();
        try {
            file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(FilePart.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.output = new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FilePart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void processPacket(EziPacket p){
        try {
            output.write(p.getBytes(), 0, packetSize);
            output.flush();
            packetsProcessed++;
        } catch (IOException ex) {
            Logger.getLogger(FilePart.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateProgress();
    }
    
    private void updateProgress(){
        if(partSize == packetsProcessed){
            try {
                output.close();
                done = true;
            } catch (IOException ex) {
                Logger.getLogger(FilePart.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public boolean isDone(){
        return this.done;
    }
    
    /**
     * @return the partId
     */
    public int getPartId() {
        return partId;
    }

    /**
     * @param partId the partId to set
     */
    protected void setPartId(int partId) {
        this.partId = partId;
    }

    /**
     * @return the fileName
     */
    protected String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    protected void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
