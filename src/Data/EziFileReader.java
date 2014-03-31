/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elwin
 */
public class EziFileReader {
    
    private EziFile eziFile;
    private String path;
    private File file;
    private FileInputStream input;
   
    protected EziFileReader(EziFile eziFile, String path, File file){
        this.eziFile = eziFile;
        this.path = path;
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
}
