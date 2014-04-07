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
    
    private long fileSize;
    private String eziId;
    private String checkSum = "";
    private ArrayList<File> files;

    protected EziInfo(long size, File file) {
        this.fileSize = size;
        this.eziId = generateEziId(file);
        this.files = new ArrayList<>();
        this.files.add(file);
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
    
    protected void checkFiles(){
        ArrayList<File> files = new ArrayList<>(getFiles());
        for(File file : files){
            if(!file.exists()){
                removeFile(file);
            }
            else{
                if(!eziId.equals(generateEziId(file))){
                    removeFile(file);
                }
            }
        }
    }
    
    public String getFileName(){
        return getFirstFile().getName();
    }

    public long getFileSize() {
        return fileSize;
    }
    
    public String getEziId() {
        return eziId;
    }
    
    protected void addFile(File file){
        boolean exists = false;
        for(File tempfile : getFiles()){
            if(tempfile.equals(file)){
                exists = true;
                break;
            }
        }
        if(!exists){
                getFiles().add(file);
        }
    }
    
    protected void removeFile(File file){
        this.files.remove(file);
    }
    
    protected File getFirstFile(){
        File file = getFiles().get(0);
        return file;
    }
    
    protected int getNumberOfFiles(){
        return getFiles().size();
    }
    
    public String getCheckSum() {
        return checkSum;
    }

    public String generateCheckSum() {
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

        this.checkSum = checkSum;
        return checkSum;
    }

    protected ArrayList<File> getFiles() {
        return files;
    }
}
