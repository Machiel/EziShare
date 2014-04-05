package ezi.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EziInfo implements Serializable {
    
    private long size;
    private String fastCheckSum;
    private String fullCheckSum;
    private ArrayList<File> localFiles;

    protected EziInfo(long size, String checksum,File file) {
        this.size = size;
        this.fastCheckSum = checksum;
        this.localFiles = new ArrayList<>();
        this.localFiles.add(file);
    }
    
    protected void checkFiles(){
        ArrayList<File> files = new ArrayList<>(getLocalFiles());
        for(File file : files){
            if(!file.exists()){
                removeFile(file);
            }
        }
    }

    protected long getSize() {
        return size;
    }
    
    protected String getFastCheckSum() {
        return fastCheckSum;
    }
    
    protected void addFile(File file){
        boolean exists = false;
        for(File tempfile : getLocalFiles()){
            if(tempfile.equals(file)){
                exists = true;
                break;
            }
        }
        if(!exists){
                getLocalFiles().add(file);
        }
    }
    
    protected void removeFile(File file){
        this.localFiles.remove(file);
    }
    
    protected File getFirstFile(){
        File file = getLocalFiles().get(0);
        return file;
    }
    
    protected int getNumberOfFiles(){
        return getLocalFiles().size();
    }

    protected boolean isFullCheckSumEmpty(){
        return fullCheckSum.isEmpty();
    }
    
    protected String getFullCheckSum() {
        return fullCheckSum;
    }

    protected String generateCheckSum() {
        File file = getFirstFile();
        String checkSum = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Using MessageDigest update() method to provide input
            byte[] buffer = new byte[4096];
            int numOfBytesRead;
            while ((numOfBytesRead = fis.read(buffer)) > 0) {
                md.update(buffer, 0, numOfBytesRead);
            }
            byte[] hash = md.digest();
            checkSum = new BigInteger(1, hash).toString(16); //don't use this, truncates leading zero
            fis.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EziInfoIndexer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException | IOException ex) {
            Logger.getLogger(EziInfoIndexer.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.fullCheckSum = checkSum;
        return checkSum;
    }

    protected ArrayList<File> getLocalFiles() {
        return localFiles;
    }
}
