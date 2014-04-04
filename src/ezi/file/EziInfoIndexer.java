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

    private File folder;
    private File eziFolder;
    private ArrayList<EziInfo> localFiles;

    public EziInfoIndexer(File folder, File eziFolder) {
        this.folder = folder;
        this.eziFolder = eziFolder;
        this.localFiles = new ArrayList<>();
        long start_time = System.currentTimeMillis();
        indexFolder(folder);
        System.out.println("It took : "+(System.currentTimeMillis()-start_time)+" miliseconds");
        for(EziInfo info : localFiles){
            System.out.println(info.getNumberOfLocations());
        }
    }

    private synchronized void indexFolder(File folder) {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                indexFolder(file);
            } else {
                if (!file.getName().endsWith(".ezi")) {
                    boolean exists = false;
                    String checkSum = createFastCheckSum(file);
                    System.out.println(file.getName());
                    for (EziInfo eziInfo : this.localFiles) {
                        if (eziInfo.getFastCheckSum().equals(checkSum)) {
                            eziInfo.addFile(file);
                            updateEziFile(eziInfo);
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        createEziFile(file, checkSum);
                        EziInfo eziFile = fetchEziForFile(file, checkSum);
                        this.localFiles.add(eziFile);
                    }
                }
            }
        }
    }

    private String getEziUri(String eziId) {
        return eziFolder.getPath() + "\\" + eziId + ".ezi";
    }

    private EziInfo fetchEziForFile(File file, String checkSum) {
        boolean found = false;
        EziInfo eziInfo = null;
        File eziFile = new File(getEziUri(checkSum));

        if (!eziFile.exists()) {
            createEziFile(file, checkSum);
        }else{
            found = true;
        }

        if(!found){
            System.out.println("not found!");
        }
        
        eziInfo = readEziFile(checkSum);
        return eziInfo;
    }

    private String createFastCheckSum(File file) {

        String checksum = null;
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
            checksum = new BigInteger(1, hash).toString(16); //don't use this, truncates leading zero
            fis.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EziInfoIndexer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException | IOException ex) {
            Logger.getLogger(EziInfoIndexer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return checksum;
    }

    private void createEziFile(File file, String checkSum) {
        FileOutputStream fileOutput = null;
        EziInfo eziInfo = new EziInfo(file.length(), checkSum, file);
        File eziFile = new File(getEziUri(checkSum));
        try {
            fileOutput = new FileOutputStream(eziFile);
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
            objectOutput.writeObject(eziInfo);
            objectOutput.flush();
            fileOutput.flush();
        } catch (IOException ex) {
            Logger.getLogger(EziInfoIndexer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateEziFile(EziInfo eziFile) {
        FileOutputStream fileOutput = null;
        File writeFile = new File(getEziUri(eziFile.getFastCheckSum()));
        try {
            fileOutput = new FileOutputStream(writeFile);
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
            objectOutput.writeObject(eziFile);
            objectOutput.flush();
            fileOutput.flush();
        } catch (IOException ex) {
            Logger.getLogger(EziInfoIndexer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private EziInfo readEziFile(String eziId) {
        FileInputStream fileInput = null;
        EziInfo eziFile = null;
        File file = new File(getEziUri(eziId));
        try {
            fileInput = new FileInputStream(file);
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            eziFile = (EziInfo) objectInput.readObject();
            objectInput.close();
            fileInput.close();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(EziInfoIndexer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return eziFile;
    }

}
