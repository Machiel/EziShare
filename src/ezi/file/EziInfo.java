package ezi.file;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class EziInfo implements Serializable {
    
    private long size;
    private String fastCheckSum;
    private ArrayList<File> localFiles;

    protected EziInfo(long size, String checksum,File file) {
        this.size = size;
        this.fastCheckSum = checksum;
        this.localFiles = new ArrayList<>();
        this.localFiles.add(file);
    }

    protected long getSize() {
        return size;
    }

    protected String getFastCheckSum() {
        return fastCheckSum;
    }
    
    protected void addFile(File file){
        boolean exists = false;
        for(File tempfile : localFiles){
            if(tempfile.equals(file)){
                exists = true;
                break;
            }
        }
        if(!exists){
                localFiles.add(file);
        }
    }
    
    protected File getFirstFile(){
        File file = localFiles.get(0);
        return file;
    }
    
    protected int getNumberOfLocations(){
        return localFiles.size();
    }
}
