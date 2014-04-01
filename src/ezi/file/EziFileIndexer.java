/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elwin
 */
public class EziFileIndexer {

    private File folder;
    private ArrayList<EziFile> localFiles;

    public EziFileIndexer(File folder) {
        this.folder = folder;
        listFilesForFolder(folder);
    }

    private void listFilesForFolder(File folder) {
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                if (!fileEntry.getName().endsWith(".ezi")) {
                    getEziFile(fileEntry);
                }
            }
        }
    }

    private String getEziUri(String filename) {
        return folder.getPath() + "\\" + filename + ".ezi";
    }

    private boolean doesEziFileExist(String filename) {
        File file = new File(getEziUri(filename));
        return file.exists();
    }

    private EziFile getEziFile(File file) {
        EziFile eziFile = null;

        if (!doesEziFileExist(file.getName())) {
            createEziFile(file);
        }
        eziFile = readEziFile(new File(getEziUri(file.getName())));

        return eziFile;
    }

    private void createEziFile(File file) {
        FileOutputStream fileOutput = null;
        EziFile eziFile = null;
        try {
            fileOutput = new FileOutputStream(file);
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
            objectOutput.writeObject(eziFile);
            objectOutput.flush();
            fileOutput.flush();
        } catch (IOException ex) {
            Logger.getLogger(EziFileIndexer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private EziFile readEziFile(File file) {
        FileInputStream fileInput = null;
        EziFile eziFile = null;

        try {
            fileInput = new FileInputStream(file);
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            eziFile = (EziFile) objectInput.readObject();
            objectInput.close();
            fileInput.close();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(EziFileIndexer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return eziFile;
    }

}
