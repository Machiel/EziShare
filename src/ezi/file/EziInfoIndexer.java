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
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elwin
 */
public class EziInfoIndexer {

    private String filesFolder;
    private String eziFolder;
    private ArrayList<EziInfo> eziFiles;

    public EziInfoIndexer(String filesFolder, String eziFolder) {
        this.filesFolder = filesFolder;
        this.eziFolder = eziFolder;
        //this.eziFiles = getEziInfoFiles(eziFolder);
        this.eziFiles = indexFolder(new File(filesFolder), eziFolder, new ArrayList<EziInfo>());
    }
    
    public ArrayList<EziInfo> getEziInfoList(){
        return this.eziFiles;
    } 

    private ArrayList<EziInfo> getEziInfoFiles(File eziFolder) {
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

    private ArrayList<EziInfo> indexFolder(File filesFolder, String eziFolder, ArrayList<EziInfo> eziFiles) {
        try {
            for (File file : filesFolder.listFiles()) {
                if (file.isDirectory()) {
                    eziFiles = indexFolder(file, eziFolder, eziFiles);
                } else {
                    if (!file.getName().endsWith(".ezi")) {
                        boolean existsInList = false;
                        String eziId = generateEziId(file);
                        for (EziInfo eziInfo : eziFiles) {
                            if (eziInfo.getEziId().equals(eziId)) {
                                String originalChecksum = eziInfo.generateCheckSum();
                                EziInfo tempEziInfo = new EziInfo(file.length(), eziId, file);
                                String newFileChecksum = tempEziInfo.generateCheckSum();
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
                            EziInfo eziInfo = createEziInfoFile(file, eziFolder, eziId);
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

    private String getEziUri(String eziFolder, String eziId) {
        return eziFolder + "\\" + eziId + ".ezi";
    }

    private String generateEziId(File file) {

        String eziId = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Using MessageDigest update() method to provide input
            byte[] buffer = new byte[4096];
            int numOfBytesRead;
            long step = 0;
            while ((numOfBytesRead = fis.read(buffer)) > 0) {
                fis.skip(step * 1024);
                md.update(buffer, 0, numOfBytesRead);
                step++;
            }
            byte[] hash = md.digest();
            eziId = new BigInteger(1, hash).toString(16); //don't use this, truncates leading zero
            fis.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EziInfoIndexer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException | IOException ex) {
            Logger.getLogger(EziInfoIndexer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return eziId;
    }

    private EziInfo createEziInfoFile(File file, String eziFolder, String eziId) {
        FileOutputStream fileOutput = null;
        EziInfo eziInfo = new EziInfo(file.length(), eziId, file);
        File eziFile = new File(getEziUri(eziFolder, eziId));
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
                updateEziInfoFile(eziFile.getPath(), eziInfo);
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

    private void updateEziInfoFile(String eziFolder, EziInfo eziInfo) {
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
