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
public class EziFileIndexer {

    private File folder;
    private ArrayList<EziFile> localFiles;

    public EziFileIndexer(File folder) {
        this.folder = folder;
        this.localFiles = new ArrayList<>();
        indexFolder(folder);
    }

    private void indexFolder(File folder) {
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                indexFolder(fileEntry);
            } else {
                if (!fileEntry.getName().endsWith(".ezi")) {
                    EziFile eziFile = getEziFile(fileEntry);
                    boolean exists = false;
                    for (EziFile tempEzi : this.localFiles) {
                        if(tempEzi.getFileName().equals(eziFile.getFileName()) && tempEzi.getCheckSum().equals(eziFile.getCheckSum())){
                            exists = true;
                            break;
                        }
                    }
                    if(!exists){
                        this.localFiles.add(eziFile);
                    }
                }
            }
        }
    }

    private boolean checkIntegrity(EziFile eziFile) {
        boolean check = false;
        if (getCheckSum(new File(folder.getPath() + "\\" + eziFile.getFileName())).equals(eziFile.getCheckSum())) {
            check = true;
        }
        return check;
    }

    private String getEziUri(String filename) {
        return folder.getPath() + "\\" + filename + ".ezi";
    }

    private EziFile getEziFile(File file) {
        EziFile eziFile = null;
        File tempfile = new File(getEziUri(file.getName()));

        if (!tempfile.exists()) {
            createEziFile(file);
        }

        eziFile = readEziFile(new File(getEziUri(file.getName())));
        return eziFile;
    }

    public static String getCheckSum(File file) {
        String checksum = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");

            //Using MessageDigest update() method to provide input
            byte[] buffer = new byte[8192];
            int numOfBytesRead;
            while ((numOfBytesRead = fis.read(buffer)) > 0) {
                md.update(buffer, 0, numOfBytesRead);
            }
            byte[] hash = md.digest();
            checksum = new BigInteger(1, hash).toString(16); //don't use this, truncates leading zero
            fis.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EziFileIndexer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException | IOException ex) {
            Logger.getLogger(EziFileIndexer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return checksum;
    }

    private void createEziFile(File file) {
        FileOutputStream fileOutput = null;
        EziFile eziFile = new EziFile(file.getName(), file.length(), getCheckSum(file));
        File writeFile = new File(getEziUri(file.getName()));
        try {
            fileOutput = new FileOutputStream(writeFile);
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
            objectOutput.writeObject(eziFile);
            objectOutput.flush();
            fileOutput.flush();
        } catch (IOException ex) {
            Logger.getLogger(EziFileIndexer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writeEziFile(File file, EziFile eziFile) {

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
