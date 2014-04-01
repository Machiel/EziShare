package ezi.file;

import java.io.Serializable;

public class EziFile implements Serializable {

    private String fileName;
    private long size;
    private String checkSum;

    protected EziFile(String filename, long size, String checksum) {
        this.fileName = filename;
        this.size = size;
        this.checkSum = checksum;
    }

    public String getFileName() {
        return fileName;
    }

    public long getSize() {
        return size;
    }

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checksum) {
        this.checkSum = checksum;
    }
}
