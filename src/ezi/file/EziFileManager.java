/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi.file;

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
public class EziFileManager {
    
    private File file;
    private EziFileWriter writer;
    private EziFileReader reader;
    
    public EziFileManager(String path, EziFile eziFile){
        initFile(path, eziFile);
        this.writer = new EziFileWriter(eziFile, path, this.file);
        this.reader = new EziFileReader(eziFile, path, this.file);
    }

    private void initFile(String path, EziFile eziFile) {
        this.file = new File(path + eziFile.getFileName());
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(EziFileIndexer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
