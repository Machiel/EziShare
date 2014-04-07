/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi.file;

import java.io.File;
import java.io.FileInputStream;
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
public class EziInfoIndexer {

    private File filesFolder;
    private File eziFolder;
    private ArrayList<EziInfo> eziFiles;

    public EziInfoIndexer(File filesFolder, File eziFolder) {
        this.filesFolder = filesFolder;
        this.eziFolder = eziFolder;
        this.eziFiles = getExistingEziInfoFiles(eziFolder);
    }
    
    public ArrayList<EziInfo> getEziInfoList(){
        return this.eziFiles;
    }
    
    public void refreshList(){
        System.out.println("Indexer: Indexing files");
        eziFiles = indexFolder(filesFolder, eziFolder, new ArrayList<EziInfo>());
    }

    private ArrayList<EziInfo> getExistingEziInfoFiles(File eziFolder) {
        ArrayList<EziInfo> eziFiles = new ArrayList<>();
        for (File eziFile : eziFolder.listFiles()) {
            if (eziFile.getName().endsWith(".ezi")) {
                EziInfo eziInfo = readEziInfoFile(eziFile);
                if (eziInfo != null) {
                    eziFiles.add(eziInfo);
                }
            }
        }
        return eziFiles;
    }

    private ArrayList<EziInfo> indexFolder(File filesFolder, File eziFolder, ArrayList<EziInfo> eziFiles) {
        try {
            for (File file : filesFolder.listFiles()) {
                if (file.isDirectory()) {
                    eziFiles = indexFolder(file, eziFolder, eziFiles);
                } else {
                    if (!file.getName().endsWith(".ezi")) {
                        boolean existsInList = false;
                        for (EziInfo eziInfo : eziFiles) {
                            EziInfo temp = new EziInfo(file.length(), file);
                            if (eziInfo.getEziId().equals(temp.getEziId())) {
                                String originalChecksum = eziInfo.generateCheckSum();
                                String newFileChecksum = temp.generateCheckSum();
                                if (originalChecksum.equals(newFileChecksum)) {
                                    eziInfo.addFile(file);
                                    updateEziInfoFile(eziFolder, eziInfo);
                                    existsInList = true;
                                }
                                else{
                                    existsInList = false;
                                }
                                break;
                            }
                        }
                        if (!existsInList) {
                            EziInfo eziInfo = createEziInfoFile(file, eziFolder);
                            if (eziInfo != null) {
                                eziFiles.add(eziInfo);
                            }
                        }
                    }
                }
            }
        } catch (NullPointerException exc) {
            System.out.println("No such directory");
        }
        return eziFiles;
    }

    private String getEziUri(File eziFolder, String eziId) {
        return eziFolder.getPath() + "/" + eziId + ".ezi";
    }

    private EziInfo createEziInfoFile(File file, File eziFolder) {
        FileOutputStream fileOutput = null;
        EziInfo eziInfo = new EziInfo(file.length(), file);
        File eziFile = new File(getEziUri(eziFolder, eziInfo.getEziId()));
        try {
            fileOutput = new FileOutputStream(eziFile);
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
            objectOutput.writeObject(eziInfo);
            objectOutput.flush();
            fileOutput.flush();
            fileOutput.close();
            objectOutput.close();
        } catch (IOException ex) {
            Logger.getLogger(EziInfoIndexer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return eziInfo;
    }

    private EziInfo readEziInfoFile(File eziFile) {
        FileInputStream fileInput = null;
        EziInfo eziInfo = null;
        try {
            fileInput = new FileInputStream(eziFile);
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            eziInfo = (EziInfo) objectInput.readObject();
            objectInput.close();
            fileInput.close();

            int numOfFiles = eziInfo.getNumberOfFiles();
            eziInfo.checkFiles();

            if ((eziInfo.getNumberOfFiles() != 0) && (numOfFiles != eziInfo.getNumberOfFiles())) {
                updateEziInfoFile(eziFile, eziInfo);
            }
            if (eziInfo.getNumberOfFiles() == 0) {
                eziFile.delete();
                eziInfo = null;
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(EziInfoIndexer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return eziInfo;
    }

    private void updateEziInfoFile(File eziFolder, EziInfo eziInfo) {
        FileOutputStream fileOutput = null;
        File writeFile = new File(getEziUri(eziFolder, eziInfo.getEziId()));
        try {
            fileOutput = new FileOutputStream(writeFile);
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
            objectOutput.writeObject(eziInfo);
            objectOutput.flush();
            fileOutput.flush();
            objectOutput.close();
            fileOutput.close();
        } catch (IOException ex) {
            Logger.getLogger(EziInfoIndexer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
