package Data;

import java.io.Serializable;

public class EziFile implements Serializable {

    private String fileName;
    private long size;
    private long hash;

    protected EziFile(String filename, long size, long hash) {
        this.fileName = filename;
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public long getSize() {
        return size;
    }

    public long getHash() {
        return hash;
    }

    public void setHash(long hash) {
        this.hash = hash;
    }
}
