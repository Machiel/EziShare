package Data;

import java.io.Serializable;

public class EziFile implements Serializable {

    private String fileName;
    private long size;

    protected EziFile(String filename, long size) {
        this.fileName = filename;
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public long getSize() {
        return size;
    }
}
