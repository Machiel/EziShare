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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elwin
 */
public class EziFileIndexer {
    
    private ArrayList<EziFile> localFiles;
    private String path;
    
    public EziFileIndexer(String path){
        this.path = path;
        this.packetSize = packSize;
        this.partSize = partSize;
        this.partId = partid;
        this.fileName = filename;
        initFile();
    }
    
    
}
